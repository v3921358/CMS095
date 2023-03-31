/*
This file is part of the OdinMS Maple Story Server
Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
Matthias Butz <matze@odinms.de>
Jan Christian Meyer <vimes@odinms.de>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License version 3
as published by the Free Software Foundation. You may not use, modify
or distribute this program under any other version of the
GNU Affero General Public License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package client.anticheat;

import client.MapleBuffStat;
import java.awt.Point;
import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import constants.GameConstants;
import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.SkillFactory;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.inventory.MapleWeaponType;
import constants.ServerConstants;
import handling.world.World;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import server.AutobanManager;
import server.Timer.CheatTimer;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.StringUtil;

public class CheatTracker {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock rL = lock.readLock(), wL = lock.writeLock();
    private final Map<CheatingOffense, CheatingOffenseEntry> offenses = new LinkedHashMap<CheatingOffense, CheatingOffenseEntry>();
    private WeakReference<MapleCharacter> chr;
    // For keeping track of speed attack hack.
    private long lastAttackTime = 0;
    private int inMapIimeCount = 0;
    private int lastAttackTickCount = 0;
    private byte Attack_tickResetCount = 0;
    private long Server_ClientAtkTickDiff = 0;
    private long lastDamage = 0;
    private long takingDamageSince;
    private int numSequentialDamage = 0;
    private long lastDamageTakenTime = 0;
    private byte numZeroDamageTaken = 0;
    private int numSequentialSummonAttack = 0;
    private long summonSummonTime = 0;
    private int numSameDamage = 0;
    private Point lastMonsterMove;
    private int monsterMoveCount;
    private int attacksWithoutHit = 0;
    private byte dropsPerSecond = 0;
    private long lastDropTime = 0;
    private byte msgsPerSecond = 0;
    private long lastMsgTime = 0;
    private ScheduledFuture<?> invalidationTask;
    private int gm_message = 0;
    private int lastTickCount = 0, tickSame = 0;
    private long lastSmegaTime = 0, lastBBSTime = 0, lastASmegaTime = 0;

    //private int lastFamiliarTickCount = 0;
    //private byte Familiar_tickResetCount = 0;
    //private long Server_ClientFamiliarTickDiff = 0;
    private int numSequentialFamiliarAttack = 0;
    private long familiarSummonTime = 0;
    public long[] lastTime = new long[6];
    private long lastLieDetectorTime = 0;

    public CheatTracker(final MapleCharacter chr) {
        start(chr);
    }

    public final void checkAttack(final int skillId, final int tickcount) {
        int AtkDelay = GameConstants.getAttackDelay(skillId, skillId == 0 ? null : SkillFactory.getSkill(skillId));
        if ((tickcount - lastAttackTickCount) < AtkDelay) {
            registerOffense(CheatingOffense.FASTATTACK);
        }
        lastAttackTime = System.currentTimeMillis();
        //if (chr.get() != null && lastAttackTime - chr.get().getChangeTime() > 600000) { //chr was afk for 10 mins and is now attacking
        //    chr.get().setChangeTime();
        // }

        Item weapon_item = chr.get().getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -11);
        MapleWeaponType weapon = weapon_item == null ? MapleWeaponType.NOT_A_WEAPON : GameConstants.getWeaponType(weapon_item.getItemId());
        /* 攻击速度增加判斷 */
        if ((tickcount - lastAttackTickCount) > 0 && (tickcount - lastAttackTickCount) < AtkDelay) {
            //registerOffense(CheatingOffense.快速攻击, "攻击速度异常，技能: " + SkillFactory.getNamea(skillId) + "[" + skillId + "]" + " 玩家回傳: " + (tickcount - lastAttackTickCount) + " " + "服务端計算: " + AtkDelay + " " + (weapon_item == null ? 0 : weapon_item.getItemId()) + "(" + weapon.name() + ")" + ((chr.get().getBuffedValue(MapleBuffStat.SPEED_INFUSION) == null ? "" : ("最终极速:" + chr.get().getBuffedValue(MapleBuffStat.SPEED_INFUSION)))) + ((chr.get().getBuffedValue(MapleBuffStat.BOOSTER) == null ? "" : ("增加功速" + chr.get().getBuffedValue(MapleBuffStat.BOOSTER)))));
        }

        if (ServerConstants.LieDetector) {
            this.lastAttackTime = System.currentTimeMillis();
            if (chr.get() != null && lastAttackTime - chr.get().getChangeTime() > 60000) {
                chr.get().setChangeTime(false);

                if (!GameConstants.isBossMap(chr.get().getMapId()) && chr.get().getEventInstance() == null && chr.get().getMap().getMobsSize() >= 1) {
                    this.inMapIimeCount += 1;
                    if (this.inMapIimeCount >= 60) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " ID " + chr.get().getId() + " " + chr.get().getName() + " 打怪時间超过 60 分鐘，该玩家可能在掛機。 "));
                    }
                    if (this.inMapIimeCount >= 60) {
                        this.inMapIimeCount = 0;
                        chr.get().startLieDetector(false);
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " ID " + chr.get().getId() + " " + chr.get().getName() + " 打怪時间超过 60 分鐘，系統启動测谎器系統。 "));
                    }
                }
            }
        }

        final long STime_TC = lastAttackTime - tickcount; // hack = - more
        if (Server_ClientAtkTickDiff - STime_TC > 1000) { // 250 is the ping, TODO
            registerOffense(CheatingOffense.FASTATTACK2);
        }
        final long STime_TCa = System.currentTimeMillis() - tickcount; // hack = - more
        final long ping = Server_ClientAtkTickDiff - STime_TCa;
        if (ping > 1500) { // 250 is the ping, TODO
            registerOffense(CheatingOffense.无延迟攻击, "无延迟,地图[" + chr.get().getMapId() + "] 技能: " + SkillFactory.getSkillName(skillId) + " Server_ClientAtkTickDiff: " + Server_ClientAtkTickDiff + " STime_TC: " + STime_TCa + " 间隔" + (Server_ClientAtkTickDiff - STime_TCa));
        }
        // if speed hack, client tickcount values will be running at a faster pace
        // For lagging, it isn't an issue since TIME is running simotaniously, client
        // will be sending values of older time

//	System.out.println("Delay [" + skillId + "] = " + (tickcount - lastAttackTickCount) + ", " + (Server_ClientAtkTickDiff - STime_TC));
        Attack_tickResetCount++; // Without this, the difference will always be at 100
        if (Attack_tickResetCount >= (AtkDelay <= 200 ? 1 : 4)) {
            Attack_tickResetCount = 0;
            Server_ClientAtkTickDiff = STime_TC;
        }
        updateTick(tickcount);
        lastAttackTickCount = tickcount;
    }

    //unfortunately PVP does not give a tick count
    public final void checkPVPAttack(final int skillId) {
        final int AtkDelay = GameConstants.getAttackDelay(skillId, skillId == 0 ? null : SkillFactory.getSkill(skillId));
        final long STime_TC = System.currentTimeMillis() - lastAttackTime; // hack = - more
        if (STime_TC < AtkDelay) { // 250 is the ping, TODO
            registerOffense(CheatingOffense.FASTATTACK);
        }
        lastAttackTime = System.currentTimeMillis();
    }

    public final long getLastAttack() {
        return lastAttackTime;
    }

    public final void checkTakeDamage(final int damage) {
        numSequentialDamage++;
        lastDamageTakenTime = System.currentTimeMillis();

        // System.out.println("tb" + timeBetweenDamage);
        // System.out.println("ns" + numSequentialDamage);
        // System.out.println(timeBetweenDamage / 1500 + "(" + timeBetweenDamage / numSequentialDamage + ")");
        if (lastDamageTakenTime - takingDamageSince / 500 < numSequentialDamage) {
            registerOffense(CheatingOffense.FAST_TAKE_DAMAGE);
        }
        if (lastDamageTakenTime - takingDamageSince > 4500) {
            takingDamageSince = lastDamageTakenTime;
            numSequentialDamage = 0;
        }
        /*	(non-thieves)
        Min Miss Rate: 2%
        Max Miss Rate: 80%
        (thieves)
        Min Miss Rate: 5%
        Max Miss Rate: 95%*/
        if (damage == 0) {
            numZeroDamageTaken++;
            if (numZeroDamageTaken >= 35) { // Num count MSEA a/b players
                numZeroDamageTaken = 0;
                registerOffense(CheatingOffense.HIGH_AVOID);
            }
        } else if (damage != -1) {
            numZeroDamageTaken = 0;
        }
    }

    public final void checkSameDamage(final int dmg, final double expected) {
        if (dmg > 2000 && lastDamage == dmg && chr.get() != null && (chr.get().getLevel() < 175 || dmg > expected * 2)) {
            numSameDamage++;

            if (numSameDamage > 5) {
                registerOffense(CheatingOffense.SAME_DAMAGE, numSameDamage + " times, damage " + dmg + ", expected " + expected + " [Level: " + chr.get().getLevel() + ", Job: " + chr.get().getJob() + "]");
                numSameDamage = 0;
            }
        } else {
            lastDamage = dmg;
            numSameDamage = 0;
        }
    }

    public final void checkMoveMonster(final Point pos) {
        if (pos == lastMonsterMove) {
            monsterMoveCount++;
            if (monsterMoveCount > 10) {
                registerOffense(CheatingOffense.MOVE_MONSTERS, "Position: " + pos.x + ", " + pos.y);
                monsterMoveCount = 0;
            }
        } else {
            lastMonsterMove = pos;
            monsterMoveCount = 1;
        }
    }

    public final void resetSummonAttack() {
        summonSummonTime = System.currentTimeMillis();
        numSequentialSummonAttack = 0;
    }

    public final boolean checkSummonAttack() {
        numSequentialSummonAttack++;
        //estimated
        // System.out.println(numMPRegens + "/" + allowedRegens);
        if ((System.currentTimeMillis() - summonSummonTime) / (1000 + 1) < numSequentialSummonAttack) {
            //registerOffense(CheatingOffense.FAST_SUMMON_ATTACK);
            return false;
        }
        return true;
    }

    public final void resetFamiliarAttack() {
        familiarSummonTime = System.currentTimeMillis();
        numSequentialFamiliarAttack = 0;
        //lastFamiliarTickCount = 0;
        //Familiar_tickResetCount = 0;
        //Server_ClientFamiliarTickDiff = 0;
    }

    public final boolean checkFamiliarAttack(final MapleCharacter chr) {
        /*final int tickdifference = (tickcount - lastFamiliarTickCount);
        if (tickdifference < 500) {
            chr.getCheatTracker().registerOffense(CheatingOffense.FAST_SUMMON_ATTACK);
        }
        final long STime_TC = System.currentTimeMillis() - tickcount;
        final long S_C_Difference = Server_ClientFamiliarTickDiff - STime_TC;
        if (S_C_Difference > 500) {
            chr.getCheatTracker().registerOffense(CheatingOffense.FAST_SUMMON_ATTACK);
        }
        Familiar_tickResetCount++;
        if (Familiar_tickResetCount > 4) {
            Familiar_tickResetCount = 0;
            Server_ClientFamiliarTickDiff = STime_TC;
        }
        lastFamiliarTickCount = tickcount;*/
        numSequentialFamiliarAttack++;
        //estimated
        // System.out.println(numMPRegens + "/" + allowedRegens);
        if ((System.currentTimeMillis() - familiarSummonTime) / (600 + 1) < numSequentialFamiliarAttack) {
            //registerOffense(CheatingOffense.FAST_SUMMON_ATTACK);
            return false;
        }
        return true;
    }

    public final void checkDrop() {
        checkDrop(false);
    }

    public final void checkDrop(final boolean dc) {
        if ((System.currentTimeMillis() - lastDropTime) < 1000) {
            dropsPerSecond++;
            if (dropsPerSecond >= (dc ? 32 : 16) && chr.get() != null && !chr.get().isGM()) {
                if (dc) {
                    //chr.get().getClient().getSession().close();
                } else {
                    chr.get().getClient().setMonitored(true);
                }
            }
        } else {
            dropsPerSecond = 0;
        }
        lastDropTime = System.currentTimeMillis();
    }

    public final void checkMsg() { //ALL types of msg. caution with number of  msgsPerSecond
        if ((System.currentTimeMillis() - lastMsgTime) < 1000) { //luckily maplestory has auto-check for too much msging
            msgsPerSecond++;
            if (msgsPerSecond > 10 && chr.get() != null && !chr.get().isGM()) {
                //chr.get().getClient().getSession().close();
            }
        } else {
            msgsPerSecond = 0;
        }
        lastMsgTime = System.currentTimeMillis();
    }

    public final int getAttacksWithoutHit() {
        return attacksWithoutHit;
    }

    public final void setAttacksWithoutHit(final boolean increase) {
        if (increase) {
            this.attacksWithoutHit++;
        } else {
            this.attacksWithoutHit = 0;
        }
    }

    public final void registerOffense(final CheatingOffense offense) {
        registerOffense(offense, null);
    }

    public final void registerOffense(final CheatingOffense offense, final String param) {
        final MapleCharacter chrhardref = chr.get();
        if (chrhardref == null || !offense.isEnabled() || chrhardref.isClone() || chrhardref.isGM()) {
            return;
        }
        CheatingOffenseEntry entry = null;
        rL.lock();
        try {
            entry = offenses.get(offense);
        } finally {
            rL.unlock();
        }
        if (entry != null && entry.isExpired()) {
            expireEntry(entry);
            entry = null;
            //gm_message = 0;
        }
        if (entry == null) {
            entry = new CheatingOffenseEntry(offense, chrhardref.getId());
        }
        if (param != null) {
            entry.setParam(param);
        }
        entry.incrementCount();
        if (offense.shouldAutoban(entry.getCount())) {
            final byte type = offense.getBanType();
            String outputFileName;
            switch (type) {
                case 1:
                    //AutobanManager.getInstance().autoban(chrhardref.getClient(), StringUtil.makeEnumHumanReadable(offense.name()));
                    break;
                case 2:
                    outputFileName = "未斷线";
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " ID " + chrhardref.getId() + " " + chrhardref.getName() + " 未自動斷线 類別: " + offense.toString() + " 原因: " + (param == null ? "" : (" - " + param))));
                    FileoutputUtil.logToFile("logs/Hack/" + outputFileName + ".txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家：" + chr.get().getName() + " 項目：" + offense.toString() + " 原因： " + (param == null ? "" : (" - " + param)));
                    //chrhardref.getClient().getSession().close();
                    break;
                case 3:
                    boolean ban = false;
                    outputFileName = "未封锁";
                    String show = "使用違法程式練功";
                    String real = "";
                    switch (offense) {
                        case ITEMVAC_SERVER:
                            outputFileName = "全圖吸物";
                            real = "使用全圖吸物";
                            break;
                        case 召喚獸无延迟:
                            outputFileName = "召喚獸无延迟";
                            real = "使用召喚獸无延迟攻击";
                            break;
                        case MOB_VAC_X:
                            outputFileName = "X吸怪";
                            real = "使用X吸怪";
                            break;
                        case 吸怪:
                            outputFileName = "吸怪";
                            real = "使用吸怪";
                            break;
                        case ATTACK_FARAWAY_MONSTER_BAN:
                            outputFileName = "全圖打";
                            real = "使用全圖打";
                            break;
                        case 攻击怪物數量异常:
                            outputFileName = "技能异常";
                            real = "打怪數量异常";
                            break;
                        case 技能攻击次數异常:
                            outputFileName = "技能异常";
                            real = "技能攻击次數";
                            break;
                        case 无延迟攻击:
                        case 快速攻击:
                            outputFileName = "技能异常";
                            real = "无延迟使用技能";
                            break;
                        default:
                            ban = false;
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + MapleCharacterUtil.makeMapleReadable(chrhardref.getName()) + " (編號: " + chrhardref.getId() + " )使用外挂! " + StringUtil.makeEnumHumanReadable(offense.name()) + (param == null ? "" : (" - " + param))));
                            break;
                    }
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " (編號: " + chrhardref.getId() + " )" + chrhardref.getName() + " " + real + "疑似外挂! "));
                    break;
                default:
                    break;

            }

            gm_message = 100;
            return;
        }
        wL.lock();
        try {
            offenses.put(offense, entry);
        } finally {
            wL.unlock();
        }
        switch (offense) {
            case 快速攻击:
                gm_message--;
                if (gm_message % 10 == 0) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! 无延迟攻击" + (param == null ? "" : (" - " + param))));
                    FileoutputUtil.logToFile("logs/Hack/无延迟.txt", "\r\n" + FileoutputUtil.NowTime() + " " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! 无延迟攻击" + (param == null ? "" : (" - " + param)));
                }
                break;
            case 控怪:
                gm_message--;
                if (gm_message % 10 == 0) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! 控怪" + (param == null ? "" : (" - " + param))));
                    FileoutputUtil.logToFile("logs/Hack/控怪.txt", "\r\n" + FileoutputUtil.NowTime() + " " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! 控怪" + (param == null ? "" : (" - " + param)));
                }
                break;
            case 召喚獸无延迟:
            case ITEMVAC_SERVER:
            case 吸怪:
            //case HIGH_DAMAGE_MAGIC:
            case HIGH_DAMAGE_MAGIC_2:
            //case HIGH_DAMAGE:
            case HIGH_DAMAGE_2:
            case ATTACK_FARAWAY_MONSTER:
            case ATTACK_FARAWAY_MONSTER_SUMMON:
            case SAME_DAMAGE:
                gm_message--;
                String out_log = "";
                String show = offense.name();
                boolean log = false;
                boolean out_show = true;

                switch (show) {
                    case "ATTACK_FARAWAY_MONSTER":
                        show = "全圖打";
                        out_log = "攻击范围异常";
                        log = true;
                        break;
                    case "MOB_VAC":
                        show = "使用吸怪";
                        out_log = "吸怪";
                        out_show = false;
                        log = true;
                        break;
                }

                if (gm_message % 5 == 0) {
                    if (out_show) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! " + show + (param == null ? "" : (" - " + param))));
                    }
                    if (log) {
                        FileoutputUtil.logToFile("logs/Hack/" + out_log + ".txt", "\r\n" + FileoutputUtil.NowTime() + " " + chrhardref.getName() + " (編號:" + chrhardref.getId() + ")疑似外挂! " + show + (param == null ? "" : (" - " + param)));
                    }
                }

                if (gm_message == 0) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[封號系統] " + chrhardref.getName() + " (編號: " + chrhardref.getId() + " )疑似外挂！" + show + (param == null ? "" : (" - " + param))));
                    //AutobanManager.getInstance().autoban(chrhardref.getClient(), StringUtil.makeEnumHumanReadable(offense.name()));
                    gm_message = 100;
                }

                //if (gm_message % 100 == 0) {
                //    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM Message] " + MapleCharacterUtil.makeMapleReadable(chrhardref.getName()) + " (level " + chrhardref.getLevel() + ") suspected of hacking! " + StringUtil.makeEnumHumanReadable(offense.name()) + (param == null ? "" : (" - " + param))));
                //}
                /*if (gm_message >= 300 && chrhardref.getLevel() < (offense == CheatingOffense.SAME_DAMAGE ? 175 : 150)) {
                    final Timestamp created = chrhardref.getClient().getCreated();
                    long time = System.currentTimeMillis();
                    if (created != null) {
                        time = created.getTime();
                    }
                    if (time + (15 * 24 * 60 * 60 * 1000) >= System.currentTimeMillis()) { //made within 15
                        AutobanManager.getInstance().autoban(chrhardref.getClient(), StringUtil.makeEnumHumanReadable(offense.name()) + " over 500 times " + (param == null ? "" : (" - " + param)));
                    } else {
                        gm_message = 0;
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM Message] " + MapleCharacterUtil.makeMapleReadable(chrhardref.getName()) + " (level " + chrhardref.getLevel() + ") suspected of autoban! " + StringUtil.makeEnumHumanReadable(offense.name()) + (param == null ? "" : (" - " + param))));
                        FileoutputUtil.log(FileoutputUtil.Hacker_Log, "[GM Message] " + MapleCharacterUtil.makeMapleReadable(chrhardref.getName()) + " (level " + chrhardref.getLevel() + ") suspected of autoban! " + StringUtil.makeEnumHumanReadable(offense.name()) + (param == null ? "" : (" - " + param)));
                    }
                }*/
                break;
        }
        CheatingOffensePersister.getInstance().persistEntry(entry);
    }

    public void updateTick(int newTick) {
        if (newTick <= lastTickCount) { //definitely packet spamming or the added feature in many PEs which is to generate random tick
            if (tickSame >= 5 && chr.get() != null && !chr.get().isGM()) {
                //chr.get().getClient().getSession().close();
            } else {
                tickSame++;
            }
        } else {
            tickSame = 0;
        }
        lastTickCount = newTick;
    }

    public boolean canSmega() {
        if (lastSmegaTime + 15000 > System.currentTimeMillis() && chr.get() != null && !chr.get().isGM()) {
            return false;
        }
        lastSmegaTime = System.currentTimeMillis();
        return true;
    }

    public boolean canAvatarSmega() {
        if (lastASmegaTime + 300000 > System.currentTimeMillis() && chr.get() != null && !chr.get().isGM()) {
            return false;
        }
        lastASmegaTime = System.currentTimeMillis();
        return true;
    }

    public boolean canBBS() {
        if (lastBBSTime + 60000 > System.currentTimeMillis() && chr.get() != null && !chr.get().isGM()) {
            return false;
        }
        lastBBSTime = System.currentTimeMillis();
        return true;
    }

    public final void expireEntry(final CheatingOffenseEntry coe) {
        wL.lock();
        try {
            offenses.remove(coe.getOffense());
        } finally {
            wL.unlock();
        }
    }

    public final int getPoints() {
        int ret = 0;
        CheatingOffenseEntry[] offenses_copy;
        rL.lock();
        try {
            offenses_copy = offenses.values().toArray(new CheatingOffenseEntry[offenses.size()]);
        } finally {
            rL.unlock();
        }
        for (final CheatingOffenseEntry entry : offenses_copy) {
            if (entry.isExpired()) {
                expireEntry(entry);
            } else {
                ret += entry.getPoints();
            }
        }
        return ret;
    }

    public final Map<CheatingOffense, CheatingOffenseEntry> getOffenses() {
        return Collections.unmodifiableMap(offenses);
    }

    public final String getSummary() {
        final StringBuilder ret = new StringBuilder();
        final List<CheatingOffenseEntry> offenseList = new ArrayList<CheatingOffenseEntry>();
        rL.lock();
        try {
            for (final CheatingOffenseEntry entry : offenses.values()) {
                if (!entry.isExpired()) {
                    offenseList.add(entry);
                }
            }
        } finally {
            rL.unlock();
        }
        Collections.sort(offenseList, new Comparator<CheatingOffenseEntry>() {

            @Override
            public final int compare(final CheatingOffenseEntry o1, final CheatingOffenseEntry o2) {
                final int thisVal = o1.getPoints();
                final int anotherVal = o2.getPoints();
                return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
            }
        });
        final int to = Math.min(offenseList.size(), 4);
        for (int x = 0; x < to; x++) {
            ret.append(StringUtil.makeEnumHumanReadable(offenseList.get(x).getOffense().name()));
            ret.append(": ");
            ret.append(offenseList.get(x).getCount());
            if (x != to - 1) {
                ret.append(" ");
            }
        }
        return ret.toString();
    }

    public final void dispose() {
        if (invalidationTask != null) {
            invalidationTask.cancel(false);
        }
        invalidationTask = null;
        chr = new WeakReference<MapleCharacter>(null);
    }

    public final void start(final MapleCharacter chr) {
        this.chr = new WeakReference<MapleCharacter>(chr);
        invalidationTask = CheatTimer.getInstance().register(new InvalidationTask(), 60000);
        takingDamageSince = System.currentTimeMillis();
    }

    private final class InvalidationTask implements Runnable {

        @Override
        public final void run() {
            CheatingOffenseEntry[] offenses_copy;
            rL.lock();
            try {
                offenses_copy = offenses.values().toArray(new CheatingOffenseEntry[offenses.size()]);
            } finally {
                rL.unlock();
            }
            for (CheatingOffenseEntry offense : offenses_copy) {
                if (offense.isExpired()) {
                    expireEntry(offense);
                }
            }
            if (chr.get() == null) {
                dispose();
            }
        }
    }

    public synchronized boolean GMSpam(int limit, int type) {
        if (type < 0 || lastTime.length < type) {
            type = 1; // default xD
        }
        if (System.currentTimeMillis() < limit + lastTime[type]) {
            return true;
        }
        lastTime[type] = System.currentTimeMillis();
        return false;
    }

    public boolean canLieDetector() {
        if ((this.lastLieDetectorTime + 300000 > System.currentTimeMillis()) && (this.chr.get() != null)) {
            return false;
        }
        this.lastLieDetectorTime = System.currentTimeMillis();
        return true;
    }

    public void resetInMapIimeCount() {
        this.inMapIimeCount = 0;
    }

}
