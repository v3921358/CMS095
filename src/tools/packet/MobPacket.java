package tools.packet;

import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import constants.GameConstants;
import handling.SendPacketOpcode;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.movement.LifeMovementFragment;
import tools.Pair;
import tools.data.MaplePacketLittleEndianWriter;

public class MobPacket {

    public static byte[] damageMonster(final int oid, final long damage) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.write(0);
        if (damage > Integer.MAX_VALUE) {
            mplew.writeInt(Integer.MAX_VALUE);
        } else {
            mplew.writeInt((int) damage);
        }

        return mplew.getPacket();
    }

    public static byte[] damageFriendlyMob(final MapleMonster mob, final long damage, final boolean display) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        mplew.writeInt(mob.getObjectId());
        mplew.write(display ? 1 : 2); //false for when shammos changes map!
        if (damage > Integer.MAX_VALUE) {
            mplew.writeInt(Integer.MAX_VALUE);
        } else {
            mplew.writeInt((int) damage);
        }
        if (mob.getHp() > Integer.MAX_VALUE) {
            mplew.writeInt((int) (((double) mob.getHp() / mob.getMobMaxHp()) * Integer.MAX_VALUE));
        } else {
            mplew.writeInt((int) mob.getHp());
        }
        if (mob.getMobMaxHp() > Integer.MAX_VALUE) {
            mplew.writeInt(Integer.MAX_VALUE);
        } else {
            mplew.writeInt((int) mob.getMobMaxHp());
        }

        return mplew.getPacket();
    }

    public static byte[] killMonster(final int oid, final int animation) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.KILL_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.write(animation); // 0 = dissapear, 1 = fade out, 2+ = special
        if (animation == 4) {
            mplew.writeInt(-1);
        }

        return mplew.getPacket();
    }

    public static byte[] suckMonster(final int oid, final int chr) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.KILL_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.write(4);
        mplew.writeInt(chr);

        return mplew.getPacket();
    }

    public static byte[] healMonster(final int oid, final long heal) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.DAMAGE_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.write(0);
        mplew.writeInt(-(heal > 2147483647 ? 2147483647 : (int) heal));

        return mplew.getPacket();
    }

    public static byte[] showMonsterHP(int oid, int remhppercentage) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.SHOW_MONSTER_HP.getValue());
        mplew.writeInt(oid);
        mplew.write(remhppercentage);

        return mplew.getPacket();
    }

    public static byte[] showBossHP(final MapleMonster mob) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        mplew.write(5);
        mplew.writeInt(mob.getId() == 9400589 ? 9300184 : mob.getId()); //hack: MV cant have boss hp bar
        if (mob.getHp() > Integer.MAX_VALUE) {
            mplew.writeInt((int) (((double) mob.getHp() / mob.getMobMaxHp()) * Integer.MAX_VALUE));
        } else {
            mplew.writeInt((int) mob.getHp());
        }
        if (mob.getMobMaxHp() > Integer.MAX_VALUE) {
            mplew.writeInt(Integer.MAX_VALUE);
        } else {
            mplew.writeInt((int) mob.getMobMaxHp());
        }
        mplew.write(mob.getStats().getTagColor());
        mplew.write(mob.getStats().getTagBgColor());

        return mplew.getPacket();
    }

    public static byte[] showBossHP(final int monsterId, final long currentHp, final long maxHp) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.BOSS_ENV.getValue());
        mplew.write(5);
        mplew.writeInt(monsterId); //has no image
        if (currentHp > Integer.MAX_VALUE) {
            mplew.writeInt((int) (((double) currentHp / maxHp) * Integer.MAX_VALUE));
        } else {
            mplew.writeInt((int) (currentHp <= 0 ? -1 : currentHp));
        }
        if (maxHp > Integer.MAX_VALUE) {
            mplew.writeInt(Integer.MAX_VALUE);
        } else {
            mplew.writeInt((int) maxHp);
        }
        mplew.write(6);
        mplew.write(5);

        //colour legend: (applies to both colours)
        //1 = red, 2 = dark blue, 3 = light green, 4 = dark green, 5 = black, 6 = light blue, 7 = purple
        return mplew.getPacket();
    }

    public static byte[] moveMonster(boolean useskill, int skill, int unk, int oid, Point startPos, List<LifeMovementFragment> moves) {
        return moveMonster(useskill, skill, unk, oid, startPos, moves, null, null);
    }

    public static byte[] moveMonster(boolean useskill, int skill, int unk, int oid, Point startPos, List<LifeMovementFragment> moves, final List<Integer> unk2, final List<Pair<Integer, Integer>> unk3) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.MOVE_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.write(0);
        mplew.write(0);
        mplew.write(useskill ? 1 : 0);
        mplew.write(skill);
        mplew.writeInt(unk);
        mplew.writeInt(unk3 == null ? 0 : unk3.size()); // For each, 2 short
        if (unk3 != null) {
            for (final Pair<Integer, Integer> i : unk3) {
                mplew.writeInt(i.left);
                mplew.writeInt(i.right);
            }
        }
        mplew.writeInt(unk2 == null ? 0 : unk2.size()); // For each, 1 short
        if (unk2 != null) {
            for (final Integer i : unk2) {
                mplew.writeInt(i);
            }
        }
        mplew.writePos(startPos);
        mplew.writeShort(0);
        mplew.writeShort(0);
        PacketHelper.serializeMovementList(mplew, moves);

        return mplew.getPacket();
    }

    public static byte[] spawnMonster(MapleMonster life, int spawnType, int link) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.SPAWN_MONSTER.getValue());
        mplew.writeInt(life.getObjectId());
        mplew.write(1); // 1 = Control normal, 5 = Control none
        mplew.writeInt(life.getId());
        Collection<MonsterStatusEffect> buffs = life.getStati().values();
        EncodeTemporary(mplew, life, buffs);
        addMonsterInformation(mplew, life, true, true, (byte) spawnType, link);
        return mplew.getPacket();
    }

    private static void writeBuffMask(MaplePacketLittleEndianWriter mplew, Collection<MonsterStatusEffect> ss) {
        int[] mask = new int[GameConstants.MAX_BUFFSTAT];
        for (MonsterStatusEffect statup : ss) {
            mask[(statup.getStati().getPosition() - 1)] |= statup.getStati().getValue();
        }
        for (int i = mask.length; i >= 1; i--) {
            mplew.writeInt(mask[(i - 1)]);
        }
    }

    public static void EncodeTemporary(MaplePacketLittleEndianWriter mplew, MapleMonster life, Collection<MonsterStatusEffect> buffs) {
        if (life.getStati().size() <= 0) {
            life.addEmpty();
        }
        Set<MonsterStatus> mobstat = new HashSet();
        writeBuffMask(mplew, buffs);

        for (MonsterStatusEffect buff : buffs) {
            mobstat.add(buff.getStati());
            if ((buff.getStati() != MonsterStatus.EMPTY) && (buff.getStati() != MonsterStatus.SUMMON)) {
                mplew.writeShort(buff.getX());
                if (buff.getMobSkill() != null) {
                    mplew.writeShort(buff.getMobSkill().getSkillId());
                    mplew.writeShort(buff.getMobSkill().getSkillLevel());
                } else {
                    mplew.writeInt(buff.getSkill() > 0 ? buff.getSkill() : 0);
                }
                mplew.writeShort((short) ((buff.getCancelTask() - System.currentTimeMillis()) / 1000));
            }
        }

        if (mobstat.contains(MonsterStatus.EMPTY)) {
            int a7 = 0;
            mplew.write(a7);
            if (a7 > 0) {
                for (int i = 0; i < a7; i++) {
                    mplew.writeInt(a7);
                    mplew.writeInt(a7);
                    mplew.writeInt(a7);
                    mplew.writeInt(a7);
                    mplew.writeInt(a7);
                    mplew.writeInt(a7);
                }

            }

        }
        if (mobstat.contains(MonsterStatus.WEAPON_DAMAGE_REFLECT)) {
            mplew.writeInt(0);
        }
        if (mobstat.contains(MonsterStatus.MAGIC_DAMAGE_REFLECT)) {
            mplew.writeInt(0);
        }
        if ((mobstat.contains(MonsterStatus.WEAPON_DAMAGE_REFLECT)) || (mobstat.contains(MonsterStatus.MAGIC_DAMAGE_REFLECT))) {
            mplew.writeInt(0);
            mplew.writeInt(0);
        }

        if (mobstat.contains(MonsterStatus.SUMMON)) {
            int v47 = 0;
            mplew.write(v47);
            mplew.write(v47);
        }
    }

    public static void addMonsterInformation(MaplePacketLittleEndianWriter mplew, MapleMonster life, boolean newSpawn, boolean summon, byte spawnType, int link) {
        mplew.writePos(life.getTruePosition());
        mplew.write(life.getStance());
        mplew.writeShort(0);
        mplew.writeShort(life.getFh());
        if (summon) {
            mplew.write(spawnType);
            if ((spawnType == -3) || (spawnType >= 0)) {
                mplew.writeInt(link);
            }
        } else {
            mplew.write(newSpawn ? -2 : life.isFake() ? -4 : -1);
        }
        mplew.write(life.getCarnivalTeam());
        mplew.writeInt(life.getHp() > 2147483647 ? 2147483647 : (int) life.getHp());
        if (life.getId() / 10000 == 961) {
            mplew.writeAsciiString("");
        }
    }

    public static void addMonsterStatus(MaplePacketLittleEndianWriter mplew, MapleMonster life) {
        if (life.getStati().size() <= 1) {
            life.addEmpty(); //not done yet lulz ok so we add it now for the lulz
        }
        mplew.write(life.getChangedStats() != null ? 1 : 0);
        if (life.getChangedStats() != null) {
            mplew.writeInt(life.getChangedStats().hp > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) life.getChangedStats().hp);
            mplew.writeInt(life.getChangedStats().mp);
            mplew.writeInt(life.getChangedStats().exp);
            mplew.writeInt(life.getChangedStats().watk);
            mplew.writeInt(life.getChangedStats().matk);
            mplew.writeInt(life.getChangedStats().PDRate);
            mplew.writeInt(life.getChangedStats().MDRate);
            mplew.writeInt(life.getChangedStats().acc);
            mplew.writeInt(life.getChangedStats().eva);
            mplew.writeInt(life.getChangedStats().pushed);
            mplew.writeInt(life.getChangedStats().level);
        }
    }

    public static byte[] controlMonster(MapleMonster life, boolean newSpawn, boolean aggro) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.SPAWN_MONSTER_CONTROL.getValue());
        mplew.write(aggro ? 2 : 1);
        mplew.writeInt(life.getObjectId());
        mplew.write(1); // 1 = Control normal, 5 = Control none
        mplew.writeInt(life.getId());
        Collection<MonsterStatusEffect> buffs = life.getStati().values();
        EncodeTemporary(mplew, life, buffs);
        addMonsterInformation(mplew, life, newSpawn, false, (byte) (life.isFake() ? 1 : 0), 0);

        return mplew.getPacket();
    }

    public static byte[] stopControllingMonster(int oid) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.SPAWN_MONSTER_CONTROL.getValue());
        mplew.write(0);
        mplew.writeInt(oid);

        return mplew.getPacket();
    }

    public static byte[] makeMonsterReal(MapleMonster life) {
        return spawnMonster(life, -1, 0);
    }

    public static byte[] makeMonsterFake(MapleMonster life) {
        return spawnMonster(life, -4, 0);
    }

    public static byte[] makeMonsterEffect(MapleMonster life, int effect) {
        return spawnMonster(life, effect, 0);
    }

    public static byte[] moveMonsterResponse(int objectid, short moveid, int currentMp, boolean useSkills, int skillId, int skillLevel) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.MOVE_MONSTER_RESPONSE.getValue());
        mplew.writeInt(objectid);
        mplew.writeShort(moveid);
        mplew.write(useSkills ? 1 : 0);
        mplew.writeShort(currentMp);
        mplew.write(skillId);
        mplew.write(skillLevel);
        mplew.writeInt(0);

        return mplew.getPacket();
    }

    private static void getLongMask_NoRef(MaplePacketLittleEndianWriter mplew, Collection<MonsterStatusEffect> ss, boolean ignore_imm) {
        int[] mask = new int[GameConstants.MAX_BUFFSTAT];
        for (MonsterStatusEffect statup : ss) {
            if (statup != null && statup.getStati() != MonsterStatus.WEAPON_DAMAGE_REFLECT && statup.getStati() != MonsterStatus.MAGIC_DAMAGE_REFLECT && (!ignore_imm || (statup.getStati() != MonsterStatus.WEAPON_IMMUNITY && statup.getStati() != MonsterStatus.MAGIC_IMMUNITY && statup.getStati() != MonsterStatus.DAMAGE_IMMUNITY))) {
                mask[statup.getStati().getPosition() - 1] |= statup.getStati().getValue();
            }
        }
        for (int i = mask.length; i >= 1; i--) {
            mplew.writeInt(mask[i - 1]);
        }
    }

    /*public static byte[] applyMonsterStatus(final int oid, final MonsterStatus mse, int x, MobSkill skil) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(oid);
        PacketHelper.writeSingleMask(mplew, mse);

        mplew.writeInt(x); //TODO JUMP
        mplew.writeShort(skil.getSkillId());
        mplew.writeShort(skil.getSkillLevel());
        mplew.writeShort(mse.isEmpty() ? 1 : 0); // might actually be the buffTime but it's not displayed anywhere
        mplew.writeShort(0); // delay in ms
        mplew.write(1); // size
        mplew.write(1); // ? v97

        return mplew.getPacket();
    }

    public static byte[] applyMonsterStatus(final MapleMonster mons, final MonsterStatusEffect ms) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(mons.getObjectId());
        PacketHelper.writeSingleMask(mplew, ms.getStati());
        mplew.writeInt(ms.getX().intValue()); //TODO JUMP

        if (ms.isMonsterSkill()) {
            mplew.writeShort(ms.getMobSkill().getSkillId());
            mplew.writeShort(ms.getMobSkill().getSkillLevel());
        } else if (ms.getSkill() > 0) {
            mplew.writeInt(ms.getSkill());
        }
        mplew.writeShort(ms.getStati().isEmpty() ? 1 : 0); // might actually be the buffTime but it's not displayed anywhere
        mplew.writeShort(0); // delay in ms
        mplew.write(1); // size
        mplew.write(1); // ? v97

        return mplew.getPacket();
    }

    public static byte[] applyMonsterStatus(final MapleMonster mons, final List<MonsterStatusEffect> mse) {
        if (mse.size() <= 0 || mse.get(0) == null) {
            return MaplePacketCreator.enableActions();
        }
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(mons.getObjectId());
        final MonsterStatusEffect ms = mse.get(0);
        if (ms.getStati() == MonsterStatus.POISON) { //stack ftw
            PacketHelper.writeSingleMask(mplew, MonsterStatus.EMPTY);
            mplew.write(mse.size());
            for (MonsterStatusEffect m : mse) {
                mplew.writeInt(m.getFromID()); //character ID
                if (m.isMonsterSkill()) {
                    mplew.writeShort(m.getMobSkill().getSkillId());
                    mplew.writeShort(m.getMobSkill().getSkillLevel());
                } else if (m.getSkill() > 0) {
                    mplew.writeInt(m.getSkill());
                }
                mplew.writeInt(m.getX()); //dmg
                mplew.writeLong(1000); //delay -> tick count
                mplew.writeInt(0); // idk
                mplew.writeInt(5); //buff time ?
                mplew.writeInt(0);
            }
            mplew.writeShort(300); // delay in ms
            mplew.write(1); // size
            mplew.write(1);
        } else {
            PacketHelper.writeSingleMask(mplew, ms.getStati());
            mplew.writeInt(ms.getX().intValue()); //TODO JUMP

            if (ms.isMonsterSkill()) {
                mplew.writeShort(ms.getMobSkill().getSkillId());
                mplew.writeShort(ms.getMobSkill().getSkillLevel());
            } else if (ms.getSkill() > 0) {
                mplew.writeInt(ms.getSkill());
            }
            mplew.writeShort(0); // might actually be the buffTime but it's not displayed anywhere
            mplew.writeShort(0); // delay in ms
            mplew.write(1); // size
            mplew.write(1); // ? v97
        }
        return mplew.getPacket();
    }

    public static byte[] applyMonsterStatus(final int oid, final Map<MonsterStatus, Integer> stati, final List<Integer> reflection, MobSkill skil) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(oid);
        PacketHelper.writeMask(mplew, stati.keySet());

        for (Map.Entry<MonsterStatus, Integer> mse : stati.entrySet()) {
            mplew.writeInt(mse.getValue().intValue()); //TODO JUMP
            mplew.writeShort(skil.getSkillId());
            mplew.writeShort(skil.getSkillLevel());
            mplew.writeShort(0); // might actually be the buffTime but it's not displayed anywhere
        }
        for (Integer ref : reflection) {
            mplew.writeInt(ref);
        }
        mplew.writeLong(0);
        mplew.writeShort(0); // delay in ms

        int size = stati.size(); // size
        if (reflection.size() > 0) {
            size /= 2; // This gives 2 buffs per reflection but it's really one buff
        }
        mplew.write(size); // size
        mplew.write(1); // ? v97

        return mplew.getPacket();
    }*/
    public static byte[] applyMonsterStatus(MapleMonster mons, MonsterStatusEffect ms) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(mons.getObjectId());
        SingleProcessStatSet(mplew, mons, ms);

        return mplew.getPacket();
    }

    public static void SingleProcessStatSet(MaplePacketLittleEndianWriter mplew, MapleMonster life, MonsterStatusEffect buff) {
        Set<MonsterStatusEffect> ss = new HashSet<>();
        ss.add(buff);
        ProcessStatSet(mplew, life, ss);
    }

    public static void ProcessStatSet(MaplePacketLittleEndianWriter mplew, MapleMonster life, Collection<MonsterStatusEffect> buffs) {
        EncodeTemporary(mplew, life, buffs);
        mplew.writeShort(2);
        mplew.write(2);
        mplew.write(1);
    }

    public static byte[] applyMonsterStatus(MapleMonster mons, List<MonsterStatusEffect> mse) {
        if ((mse.size() <= 0) || (mse.get(0) == null)) {
            return MaplePacketCreator.enableActions();
        }
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.APPLY_MONSTER_STATUS.getValue());
        mplew.writeInt(mons.getObjectId());
        ProcessStatSet(mplew, mons, mse);

        return mplew.getPacket();
    }

    public static byte[] cancelMonsterStatus(MapleMonster mons, MonsterStatusEffect ms) {
        List<MonsterStatusEffect> mse = new ArrayList<>();
        mse.add(ms);
        return cancelMonsterStatus(mons, mse);
    }

    public static byte[] cancelMonsterStatus(MapleMonster mons, List<MonsterStatusEffect> mse) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.CANCEL_MONSTER_STATUS.getValue());
        mplew.writeInt(mons.getObjectId());
        writeBuffMask(mplew, mse);
        mplew.write(2);

        mplew.writeZeroBytes(30);
        mplew.write(1);

        return mplew.getPacket();
    }

    /*public static byte[] cancelMonsterStatus(int oid, MonsterStatus stat) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.CANCEL_MONSTER_STATUS.getValue());
        mplew.writeInt(oid);
        PacketHelper.writeSingleMask(mplew, stat);
        mplew.write(1); // reflector is 3~!??
        mplew.write(2); // ? v97

        return mplew.getPacket();
    }

    public static byte[] cancelPoison(int oid, MonsterStatusEffect m) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.CANCEL_MONSTER_STATUS.getValue());
        mplew.writeInt(oid);
        PacketHelper.writeSingleMask(mplew, MonsterStatus.EMPTY);
        mplew.writeInt(0);
        mplew.writeInt(1); //size probably
        mplew.writeInt(m.getFromID()); //character ID
        if (m.isMonsterSkill()) {
            mplew.writeShort(m.getMobSkill().getSkillId());
            mplew.writeShort(m.getMobSkill().getSkillLevel());
        } else if (m.getSkill() > 0) {
            mplew.writeInt(m.getSkill());
        }
        mplew.write(3); // ? v97

        return mplew.getPacket();
    }*/
    public static byte[] talkMonster(int oid, int itemId, String msg) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.TALK_MONSTER.getValue());
        mplew.writeInt(oid);
        mplew.writeInt(500); //?
        mplew.writeInt(itemId);
        mplew.write(itemId <= 0 ? 0 : 1);
        mplew.write(msg == null || msg.length() <= 0 ? 0 : 1);
        if (msg != null && msg.length() > 0) {
            mplew.writeMapleAsciiString(msg);
        }
        mplew.writeInt(1); //?

        return mplew.getPacket();
    }

    public static byte[] removeTalkMonster(int oid) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.REMOVE_TALK_MONSTER.getValue());
        mplew.writeInt(oid);
        return mplew.getPacket();
    }

    public static byte[] itemBombAttack(int skillid, int x, int y, int tybe) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.TIME_BOMB_ATTACK.getValue());
        mplew.writeInt(skillid);
        mplew.writeInt(x);
        mplew.writeInt(y);
        mplew.writeInt(tybe);
        mplew.writeInt(0);
        return mplew.getPacket();
    }
}
