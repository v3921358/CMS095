package handling.channel.handler;

import client.MapleBuffStat;
import client.MapleCharacter;
import client.PlayerStats;
import client.Skill;
import client.SkillFactory;
import client.anticheat.CheatTracker;
import client.anticheat.CheatingOffense;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import constants.GameConstants;
import handling.world.World;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import server.MapleStatEffect;
import server.life.Element;
import server.life.ElementalEffectiveness;
import server.life.MapleMonster;
import server.life.MapleMonsterStats;
import server.maps.MapleMap;
import server.maps.MapleMapItem;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import tools.AttackPair;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Pair;
import tools.Randomizer;
import tools.data.LittleEndianAccessor;

public class DamageParse {

    public static void applyAttack(final AttackInfo attack, final Skill theSkill, final MapleCharacter player, int attackCount, final double maxDamagePerMonster, final MapleStatEffect effect, final AttackType attack_type) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.ATTACKING_WHILE_DEAD);
            return;
        }
        if (attack.real && GameConstants.getAttackDelay(attack.skill, theSkill) >= 100) {
            player.getCheatTracker().checkAttack(attack.skill, attack.lastAttackTickCount);
        }
        if (attack.skill != 0) {
            if (effect == null) {
                player.getClient().getSession().write(MaplePacketCreator.enableActions());
                player.marriage();
                return;
            }
            if (GameConstants.isMulungSkill(attack.skill)) {
                if (player.getMapId() / 10000 != 92502) {
                    //AutobanManager.getInstance().autoban(player.getClient(), "Using Mu Lung dojo skill out of dojo maps.");
                    return;
                } else {
                    if (player.getMulungEnergy() < 10000) {
                        return;
                    }
                    player.mulung_EnergyModify(false);
                }
            } else if (GameConstants.isPyramidSkill(attack.skill)) {
                if (player.getMapId() / 1000000 != 926) {
                    //AutobanManager.getInstance().autoban(player.getClient(), "Using Pyramid skill outside of pyramid maps.");
                    return;
                } else if (player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player)) {
                    return;
                }
            } else if (GameConstants.isInflationSkill(attack.skill)) {
                if (player.getBuffedValue(MapleBuffStat.GIANT_POTION) == null) {
                    return;
                }
            } else if (attack.targets > effect.getMobCount() && attack.skill != 1211002 && attack.skill != 1220010) { // Must be done here, since NPE with normal atk
                player.getCheatTracker().registerOffense(CheatingOffense.MISMATCHING_BULLETCOUNT);
                return;
            }
        }
        if (player.getClient().getChannelServer().isAdminOnly()) {
            player.dropMessage(-1, "Animation: " + Integer.toHexString(((attack.display & 0x8000) != 0 ? (attack.display - 0x8000) : attack.display)));
        }
        final boolean useAttackCount = attack.skill != 4211006 && attack.skill != 3221007 && attack.skill != 23121003 && (attack.skill != 1311001 || player.getJob() != 132) && attack.skill != 3211006;
        if (attack.hits > attackCount) {
            if (useAttackCount) { //buster
                player.getCheatTracker().registerOffense(CheatingOffense.MISMATCHING_BULLETCOUNT);
                return;
            }
        }
        int last = attackCount;
        boolean mirror_fix = false;
        if (player.getJob() >= 411 && player.getJob() <= 412) {
            mirror_fix = true;
        }
        if (mirror_fix) {
            last *= 2;
        }
        if (attack.skill == 1311001) {
            last += 1;
        }
        if (player.getTotalSkillLevel(3220010) > 0) {
            last += 2;
        }
        if (attack.skill == 3221007) {
            last += 1;
        }

        /* 确认攻击次數 */
        if (attack.hits > last) {
            player.getCheatTracker().registerOffense(CheatingOffense.技能攻击次數异常, "玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(attack.skill) + " 攻击次數 : " + attack.hits + " 正常攻击次數 :" + last);
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") 攻击次數异常已经紀錄。 玩家攻击次數 " + attack.hits + " 正确次數 " + last + " 技能ID " + attack.skill));
            FileoutputUtil.logToFile("logs/Hack/技能攻击次數.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(attack.skill) + " 攻击次數 : " + attack.hits + " 正常攻击次數 :" + last);
            return;
        }

        /* 确认是否超过打怪數量*/
        if (effect != null) {
            int CheckCount = effect.getMobCount();
            if (attack.targets > CheckCount) {
                player.getCheatTracker().registerOffense(CheatingOffense.攻击怪物數量异常, " 玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + "技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + attack.targets + " 服务端怪物量 :" + CheckCount);
                FileoutputUtil.logToFile("logs/Hack/打怪數量异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + "技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + attack.targets + " 服务端怪物量 :" + CheckCount);
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") " + "攻击怪物數量异常。 " + "封包怪物量 " + attack.targets + " 服务端怪物量 " + effect.getMobCount() + " 技能ID " + attack.skill));
            }
        }
        if (attack.hits > 0 && attack.targets > 0) {
            // Don't ever do this. it's too expensive.
            if (!player.getStat().checkEquipDurabilitys(player, -1)) { //i guess this is how it works ?
                player.dropMessage(5, "An item has run out of durability but has no inventory room to go to.");
                return;
            } //lol
        }
        int totDamage = 0;
        final MapleMap map = player.getMap();

        if (attack.skill == 4211006) { // meso explosion
            for (AttackPair oned : attack.allDamage) {
                if (oned.attack != null) {
                    continue;
                }
                final MapleMapObject mapobject = map.getMapObject(oned.objectid, MapleMapObjectType.ITEM);

                if (mapobject != null) {
                    final MapleMapItem mapitem = (MapleMapItem) mapobject;
                    mapitem.getLock().lock();
                    try {
                        if (mapitem.getMeso() > 0) {
                            if (mapitem.isPickedUp()) {
                                return;
                            }
                            map.removeMapObject(mapitem);
                            map.broadcastMessage(MaplePacketCreator.explodeDrop(mapitem.getObjectId()));
                            mapitem.setPickedUp(true);
                        } else {
                            player.getCheatTracker().registerOffense(CheatingOffense.ETC_EXPLOSION);
                            return;
                        }
                    } finally {
                        mapitem.getLock().unlock();
                    }
                } else {
                    player.getCheatTracker().registerOffense(CheatingOffense.EXPLODING_NONEXISTANT);
                    return; // etc explosion, exploding nonexistant things, etc.
                }
            }
        }
        int fixeddmg, totDamageToOneMonster = 0;
        long hpMob = 0;
        final PlayerStats stats = player.getStat();

        int CriticalDamage = stats.passive_sharpeye_percent();
        int ShdowPartnerAttackPercentage = 0;
        if (attack_type == AttackType.RANGED_WITH_SHADOWPARTNER || attack_type == AttackType.NON_RANGED_WITH_MIRROR) {
            final MapleStatEffect shadowPartnerEffect = player.getStatForBuff(MapleBuffStat.SHADOWPARTNER);
            if (shadowPartnerEffect != null) {
                ShdowPartnerAttackPercentage += shadowPartnerEffect.getX();
            }
            attackCount /= 2; // hack xD
        }
        ShdowPartnerAttackPercentage *= (CriticalDamage + 100) / 100;
        if (attack.skill == 4221001) { //amplifyDamage
            ShdowPartnerAttackPercentage *= 10;
        }
        byte overallAttackCount; // Tracking of Shadow Partner additional damage.
        double maxDamagePerHit = 0;
        MapleMonster monster;
        MapleMonsterStats monsterstats;
        boolean Tempest;

        for (final AttackPair oned : attack.allDamage) {
            monster = map.getMonsterByOid(oned.objectid);

            if (monster != null && monster.getLinkCID() <= 0) {
                totDamageToOneMonster = 0;
                hpMob = monster.getMobMaxHp();
                monsterstats = monster.getStats();
                fixeddmg = monsterstats.getFixedDamage();
                Tempest = monster.getStatusSourceID(MonsterStatus.FREEZE) == 21120006 || attack.skill == 21120006 || attack.skill == 1221011;

                if (!Tempest && !player.isGM()) {
                    if ((player.getJob() >= 3200 && player.getJob() <= 3212 && !monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) || attack.skill == 3221007 || attack.skill == 23121003 || ((player.getJob() < 3200 || player.getJob() > 3212) && !monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_DAMAGE_REFLECT))) {
                        maxDamagePerHit = CalculateMaxWeaponDamagePerHit(player, monster, attack, theSkill, effect, maxDamagePerMonster, CriticalDamage);
                    } else {
                        maxDamagePerHit = 1;
                    }
                }

                overallAttackCount = 0; // Tracking of Shadow Partner additional damage.
                Integer eachd;
                for (Pair<Integer, Boolean> eachde : oned.attack) {
                    eachd = eachde.left;
                    overallAttackCount++;

                    if (GameConstants.Novice_Skill(attack.skill)) {//新手技能
                        if (eachd > 40) {
                            boolean apple = false;
                            if (player.getBuffSource(MapleBuffStat.WATK) == 2022179 || player.getBuffSource(MapleBuffStat.MATK) == 2022179 || player.getBuffSource(MapleBuffStat.WDEF) == 2022179) {
                                apple = true;
                            }
                            FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: 40 本次伤害 :" + eachd + " 預計伤害: " + (int) maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss() + " 紫色蘋果: " + apple);
                           // World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系統] " + player.getName() + " 因为伤害异常而被管理員永久停權。"));
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") " + "伤害异常。 " + "最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill));
                           // player.ban(player.getName() + "伤害异常", true, true, false);
                          //  player.getClient().getSession().close();
                            return;
                        }
                    }

                    if (useAttackCount && overallAttackCount - 1 == attackCount) { // Is a Shadow partner hit so let's divide it once
                        maxDamagePerHit = (maxDamagePerHit / 100) * (ShdowPartnerAttackPercentage * (monsterstats.isBoss() ? stats.bossdam_r : stats.dam_r) / 100);
                    }
                    // System.out.println("Client damage : " + eachd + " Server : " + maxDamagePerHit);
                    if (fixeddmg != -1) {
                        if (monsterstats.getOnlyNoramlAttack()) {
                            eachd = attack.skill != 0 ? 0 : fixeddmg;
                        } else {
                            eachd = fixeddmg;
                        }
                    } else if (monsterstats.getOnlyNoramlAttack()) {
                        eachd = attack.skill != 0 ? 0 : Math.min(eachd, (int) maxDamagePerHit);  // Convert to server calculated damage
                    } else if (!player.isGM()) {
                        if (attack.skill == 1221009) {
                            maxDamagePerHit = 1000000;
                        }
                        if (Tempest) { // Monster buffed with Tempest
                            if (eachd > monster.getMobMaxHp()) {
                                eachd = (int) Math.min(monster.getMobMaxHp(), Integer.MAX_VALUE);
                                player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE);

                            }
                        } else if ((player.getJob() >= 3200 && player.getJob() <= 3212 && !monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) || attack.skill == 23121003 || ((player.getJob() < 3200 || player.getJob() > 3212) && !monster.isBuffed(MonsterStatus.DAMAGE_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_IMMUNITY) && !monster.isBuffed(MonsterStatus.WEAPON_DAMAGE_REFLECT))) {
                            if (eachd > (maxDamagePerHit * 10)) {
                                player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE, "[Damage: " + eachd + ", Expected: " + maxDamagePerHit + ", Mob: " + monster.getId() + "] [Job: " + player.getJob() + ", Level: " + player.getLevel() + ", Skill: " + attack.skill + "]");
                                if (attack.real) {
                                    player.getCheatTracker().checkSameDamage(eachd, maxDamagePerHit);
                                }
                                if (eachd > (maxDamagePerHit * 20)) {
                                    //World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " (編號: " + player.getId() + " )" + player.getName() + " 超出攻击算法2倍" + "疑似外挂! "));
                                    player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_2, "[Damage: " + eachd + ", Expected: " + maxDamagePerHit + ", Mob: " + monster.getId() + "] [Job: " + player.getJob() + ", Level: " + player.getLevel() + ", Skill: " + attack.skill + "]");
                                  
                                   
                                }
                            }
                            //} else if (eachd > maxDamagePerHit) {
                            //    eachd = (int) (maxDamagePerHit);
                        }
                    }
                    if (player == null) { // o_O
                        return;
                    }
                    totDamageToOneMonster += eachd;
                    //force the miss even if they dont miss. popular wz edit
                    if ((eachd == 0 || monster.getId() == 9700021) && player.getPyramidSubway() != null) { //miss
                        player.getPyramidSubway().onMiss(player);
                    }
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);
                double range = player.getPosition().distanceSq(monster.getPosition());
                double SkillRange = GameConstants.getAttackRange(player, effect, attack);
                if (range > SkillRange && !GameConstants.isBossMap(player.getMapId())) { // 815^2 <-- the most ranged attack in the game is Flame Wheel at 815 range
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "攻击范围异常,技能:" + attack.skill + "(" + SkillFactory.getNamea(attack.skill) + ")　怪物:" + monster.getId() + " 正常范围:" + (int) SkillRange + " 計算范围:" + (int) range); // , Double.toString(Math.sqrt(distance))
                    if (range > SkillRange * 2) {
                        player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER_BAN, "超大攻击范围,技能:" + attack.skill + "(" + SkillFactory.getNamea(attack.skill) + ")　怪物:" + monster.getId() + " 正常范围:" + (int) SkillRange + " 計算范围:" + (int) range); // , Double.toString(Math.sqrt(distance))
                    }
                }

                if (GameConstants.getAttackDelay(attack.skill, theSkill) >= 100 && !GameConstants.isNoDelaySkill(attack.skill) && attack.skill != 3101005 && !monster.getStats().isBoss() && player.getTruePosition().distanceSq(monster.getTruePosition()) > GameConstants.getAttackRange(effect, player.getStat().defRange)) {
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "[Distance: " + player.getTruePosition().distanceSq(monster.getTruePosition()) + ", Expected Distance: " + GameConstants.getAttackRange(effect, player.getStat().defRange) + " Job: " + player.getJob() + "]"); // , Double.toString(Math.sqrt(distance))
                }
                // pickpocket
                if (player.getBuffedValue(MapleBuffStat.PICKPOCKET) != null) {
                    switch (attack.skill) {
                        case 0:
                        case 4001334:
                        case 4201005:
                        case 4211002:
                        case 4211004:
                        case 4221003:
                        case 4221007:
                            handlePickPocket(player, monster, oned);
                            break;
                    }
                }

                if (totDamageToOneMonster > 0 || attack.skill == 1221011 || attack.skill == 21120006) {
                    if (GameConstants.isDemon(player.getJob())) {
                        player.handleForceGain(monster.getObjectId(), attack.skill);
                    }
                    if (attack.skill != 1221011) {
                        monster.damage(player, totDamageToOneMonster, true, attack.skill);
                    } else {
                        monster.damage(player, (monster.getStats().isBoss() ? 500000 : (monster.getHp() - 1)), true, attack.skill);
                    }

                    if (monster.isBuffed(MonsterStatus.WEAPON_DAMAGE_REFLECT)) { //test
                        player.addHP(-(7000 + Randomizer.nextInt(8000))); //this is what it seems to be?
                    }
                    player.onAttack(monster.getMobMaxHp(), monster.getMobMaxMp(), attack.skill, monster.getObjectId(), totDamage);
                    switch (attack.skill) {
                        case 14001004:
                        case 14111002:
                        case 14111005:
                        case 4301001:
                        case 4311002:
                        case 4311003:
                        case 4331000:
                        case 4331004:
                        case 4331005:
                        case 4341002:
                        case 4341004:
                        case 4341005:
                        case 4331006:
                        case 4341009:
                        case 4221007: // Boomerang Stab
                        case 4221001: // Assasinate
                        case 4211002: // Assulter
                        case 4201005: // Savage Blow
                        case 4001002: // Disorder
                        case 4001334: // Double Stab
                        case 4121007: // Triple Throw
                        case 4111005: // Avenger
                        case 4001344: { // Lucky Seven
                            // Venom
                            int[] skills = {4120005, 4220005, 4340001, 14110004};
                            for (int i : skills) {
                                final Skill skill = SkillFactory.getSkill(i);
                                if (player.getTotalSkillLevel(skill) > 0) {
                                    final MapleStatEffect venomEffect = skill.getEffect(player.getTotalSkillLevel(skill));
                                    if (venomEffect.makeChanceResult()) {
                                        monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.POISON, 1, i, null, false), true, venomEffect.getDuration(), true, venomEffect);
                                    }
                                    break;
                                }
                            }

                            break;
                        }
                        case 4201004: { //steal
                            monster.handleSteal(player);
                            break;
                        }
                        //case 21101003: // body pressure
                        case 21000002: // Double attack
                        case 21100001: // Triple Attack
                        case 21100002: // Pole Arm Push
                        case 21100004: // Pole Arm Smash
                        case 21110002: // Full Swing
                        case 21110003: // Pole Arm Toss
                        case 21110004: // Fenrir Phantom
                        case 21110006: // Whirlwind
                        case 21110007: // (hidden) Full Swing - Double Attack
                        case 21110008: // (hidden) Full Swing - Triple Attack
                        case 21120002: // Overswing
                        case 21120005: // Pole Arm finale
                        case 21120006: // Tempest
                        case 21120009: // (hidden) Overswing - Double Attack
                        case 21120010: { // (hidden) Overswing - Triple Attack
                            if (player.getBuffedValue(MapleBuffStat.WK_CHARGE) != null && !monster.getStats().isBoss()) {
                                final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.WK_CHARGE);
                                if (eff != null) {
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.SPEED, eff.getX(), eff.getSourceId(), null, false), false, eff.getY() * 1000, true, eff);
                                }
                            }
                            if (player.getBuffedValue(MapleBuffStat.BODY_PRESSURE) != null && !monster.getStats().isBoss()) {
                                final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.BODY_PRESSURE);

                                if (eff != null && eff.makeChanceResult() && !monster.isBuffed(MonsterStatus.NEUTRALISE)) {
                                    monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.NEUTRALISE, 1, eff.getSourceId(), null, false), false, eff.getX() * 1000, true, eff);
                                }
                            }
                            break;
                        }
                        default: //passives attack bonuses
                            break;
                    }
                    if (totDamageToOneMonster > 0) {
                        Item weapon_ = player.getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -11);
                        if (weapon_ != null) {
                            MonsterStatus stat = GameConstants.getStatFromWeapon(weapon_.getItemId()); //10001 = acc/darkness. 10005 = speed/slow.
                            if (stat != null && Randomizer.nextInt(100) < GameConstants.getStatChance()) {
                                final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(stat, GameConstants.getXForStat(stat), GameConstants.getSkillForStat(stat), null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, 10000, false, null);
                            }
                        }
                        if (player.getBuffedValue(MapleBuffStat.BLIND) != null) {
                            final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.BLIND);

                            if (eff != null && eff.makeChanceResult()) {
                                final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.ACC, eff.getX(), eff.getSourceId(), null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, eff.getY() * 1000, true, eff);
                            }

                        }
                        if (player.getBuffedValue(MapleBuffStat.HAMSTRING) != null) {
                            final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.HAMSTRING);

                            if (eff != null && eff.makeChanceResult()) {
                                final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.SPEED, eff.getX(), 3121007, null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, eff.getY() * 1000, true, eff);
                            }
                        }
                        if (player.getJob() == 121 || player.getJob() == 122) { // WHITEKNIGHT
                            final Skill skill = SkillFactory.getSkill(1211006);
                            if (player.isBuffFrom(MapleBuffStat.WK_CHARGE, skill)) {
                                final MapleStatEffect eff = skill.getEffect(player.getTotalSkillLevel(skill));
                                final MonsterStatusEffect monsterStatusEffect = new MonsterStatusEffect(MonsterStatus.FREEZE, 1, skill.getId(), null, false);
                                monster.applyStatus(player, monsterStatusEffect, false, eff.getY() * 2000, true, eff);
                            }
                        }
                    }
                    if (effect != null && effect.getMonsterStati().size() > 0) {
                        if (effect.makeChanceResult()) {
                            for (Map.Entry<MonsterStatus, Integer> z : effect.getMonsterStati().entrySet()) {
                                monster.applyStatus(player, new MonsterStatusEffect(z.getKey(), z.getValue(), theSkill.getId(), null, false), effect.isPoison(), effect.getDuration(), true, effect);
                            }
                        }
                    }
                }
            }
        }
        if (attack.skill == 4331003 && (hpMob <= 0 || totDamageToOneMonster < hpMob)) {
            return;
        }
        if (hpMob > 0 && totDamageToOneMonster > 0) {
            player.afterAttack(attack.targets, attack.hits, attack.skill);
        }
        if (attack.skill != 0 && (attack.targets > 0 || (attack.skill != 4331003 && attack.skill != 4341002)) && !GameConstants.isNoDelaySkill(attack.skill)) {
            if (attack.skill == 14111006) {
                effect.applyTo(player, attack.skillposition);
            } else {
                effect.applyTo(player, attack.position);
            }

        }
        if (totDamage > 1 && GameConstants.getAttackDelay(attack.skill, theSkill) >= 100) {
            final CheatTracker tracker = player.getCheatTracker();

            tracker.setAttacksWithoutHit(true);
            if (tracker.getAttacksWithoutHit() > 1000) {
                tracker.registerOffense(CheatingOffense.ATTACK_WITHOUT_GETTING_HIT, Integer.toString(tracker.getAttacksWithoutHit()));
            }
        }
    }

    public static final void applyAttackMagic(final AttackInfo attack, final Skill theSkill, final MapleCharacter player, final MapleStatEffect effect, double maxDamagePerHit) {
        if (!player.isAlive()) {
            player.getCheatTracker().registerOffense(CheatingOffense.ATTACKING_WHILE_DEAD);
            return;
        }
        if (attack.real && GameConstants.getAttackDelay(attack.skill, theSkill) >= 100) {
            player.getCheatTracker().checkAttack(attack.skill, attack.lastAttackTickCount);
        }

        if (attack.hits > effect.getAttackCount() || attack.targets > effect.getMobCount()) {
            player.getCheatTracker().registerOffense(CheatingOffense.MISMATCHING_BULLETCOUNT);
            return;
        }

        int last = effect.getAttackCount() > effect.getBulletCount() ? effect.getAttackCount() : effect.getBulletCount();
        if (attack.hits > last) {
            player.getCheatTracker().registerOffense(CheatingOffense.技能攻击次數异常, "玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(attack.skill) + " 攻击次數 : " + attack.hits + " 正常攻击次數 :" + last);
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") 攻击次數异常已经紀錄。 玩家攻击次數 " + attack.hits + " 正确次數 " + last + " 技能ID " + attack.skill));
            FileoutputUtil.logToFile("logs/Hack/技能攻击次數.txt", "\r\n" + FileoutputUtil.NowTime() + "玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(attack.skill) + " 攻击次數 : " + attack.hits + " 正常攻击次數 :" + last);
            return;
        }

        /* 确认是否超过打怪數量*/
        if (effect != null) {
            int CheckCount = effect.getMobCount();
            if (attack.targets > CheckCount) {
                player.getCheatTracker().registerOffense(CheatingOffense.攻击怪物數量异常, " 玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + "技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + attack.targets + " 服务端怪物量 :" + effect.getMobCount());
                FileoutputUtil.logToFile("logs/Hack/打怪數量异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家: " + player.getName() + "(" + player.getLevel() + ") 地图: " + player.getMapId() + "技能代码: " + attack.skill + " 技能等級: " + player.getSkillLevel(effect.getSourceId()) + " 封包怪物量 : " + attack.targets + " 服务端怪物量 :" + effect.getMobCount());
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") " + "攻击怪物數量异常。 " + "封包怪物量 " + attack.targets + " 服务端怪物量 " + effect.getMobCount() + " 技能ID " + attack.skill));

            }
        }
        if (attack.hits > 0 && attack.targets > 0) {
            if (!player.getStat().checkEquipDurabilitys(player, -1)) { //i guess this is how it works ?
                player.dropMessage(5, "An item has run out of durability but has no inventory room to go to.");
                return;
            } //lol
        }
        if (GameConstants.isMulungSkill(attack.skill)) {
            if (player.getMapId() / 10000 != 92502) {
                //AutobanManager.getInstance().autoban(player.getClient(), "Using Mu Lung dojo skill out of dojo maps.");
                return;
            } else {
                if (player.getMulungEnergy() < 10000) {
                    return;
                }
                player.mulung_EnergyModify(false);
            }
        } else if (GameConstants.isPyramidSkill(attack.skill)) {
            if (player.getMapId() / 1000000 != 926) {
                //AutobanManager.getInstance().autoban(player.getClient(), "Using Pyramid skill outside of pyramid maps.");
                return;
            } else if (player.getPyramidSubway() == null || !player.getPyramidSubway().onSkillUse(player)) {
                return;
            }
        } else if (GameConstants.isInflationSkill(attack.skill)) {
            if (player.getBuffedValue(MapleBuffStat.GIANT_POTION) == null) {
                return;
            }
        }
        if (player.getClient().getChannelServer().isAdminOnly()) {
            player.dropMessage(-1, "Animation: " + Integer.toHexString(((attack.display & 0x8000) != 0 ? (attack.display - 0x8000) : attack.display)));
        }
        final PlayerStats stats = player.getStat();
        final Element element = player.getBuffedValue(MapleBuffStat.ELEMENT_RESET) != null ? Element.NEUTRAL : theSkill.getElement();

        double MaxDamagePerHit = 0;
        int totDamageToOneMonster, totDamage = 0, fixeddmg;
        byte overallAttackCount;
        boolean Tempest;
        MapleMonsterStats monsterstats;
        int CriticalDamage = stats.passive_sharpeye_percent();
        final Skill eaterSkill = SkillFactory.getSkill(GameConstants.getMPEaterForJob(player.getJob()));
        final int eaterLevel = player.getTotalSkillLevel(eaterSkill);

        final MapleMap map = player.getMap();

        for (final AttackPair oned : attack.allDamage) {
            final MapleMonster monster = map.getMonsterByOid(oned.objectid);

            if (monster != null && monster.getLinkCID() <= 0) {
                Tempest = monster.getStatusSourceID(MonsterStatus.FREEZE) == 21120006 && !monster.getStats().isBoss();
                totDamageToOneMonster = 0;
                monsterstats = monster.getStats();
                fixeddmg = monsterstats.getFixedDamage();
                if (!Tempest && !player.isGM()) {
                    if (!monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) {
                        MaxDamagePerHit = CalculateMaxMagicDamagePerHit(player, theSkill, monster, monsterstats, stats, element, CriticalDamage, maxDamagePerHit, effect);
                    } else {
                        MaxDamagePerHit = 1;
                    }
                }
                overallAttackCount = 0;
                Integer eachd;
                for (Pair<Integer, Boolean> eachde : oned.attack) {
                    eachd = eachde.left;
                    overallAttackCount++;
                    if (GameConstants.Novice_Skill(attack.skill)) {//新手技能
                        if (eachd > 40) {
                            FileoutputUtil.logToFile("logs/Hack/Ban/伤害异常.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家<" + player.getLevel() + ">: " + player.getName() + " 怪物 " + monster.getId() + " 地图: " + player.getMapId() + " 技能代码: " + attack.skill + " 最高伤害: 40 本次伤害 :" + eachd + " 預計伤害: " + (int) maxDamagePerHit + "是否为BOSS: " + monster.getStats().isBoss());
                           // World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系統] " + player.getName() + " 因为伤害异常而被管理員永久停權。"));
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + player.getName() + " (等級 " + player.getLevel() + ") " + "伤害异常。 " + "最高伤害 40 本次伤害 " + eachd + " 技能ID " + attack.skill));
                            //player.ban(player.getName() + "伤害异常", true, true, false);
                            //player.getClient().getSession().close();
                            return;
                        }
                    }
                    if (fixeddmg != -1) {
                        eachd = monsterstats.getOnlyNoramlAttack() ? 0 : fixeddmg; // Magic is always not a normal attack
                    } else if (monsterstats.getOnlyNoramlAttack()) {
                        eachd = 0; // Magic is always not a normal attack
                    } else if (!player.isGM()) {
//			    System.out.println("Client damage : " + eachd + " Server : " + MaxDamagePerHit);
                        if (attack.skill == 1221009) {
                            MaxDamagePerHit = 1000000;
                        }
                        if (Tempest) { // Buffed with Tempest
                            // In special case such as Chain lightning, the damage will be reduced from the maxMP.
                            if (eachd > monster.getMobMaxHp()) {
                                eachd = (int) Math.min(monster.getMobMaxHp(), Integer.MAX_VALUE);
                                player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_MAGIC);
                            }
                        } else if (!monster.isBuffed(MonsterStatus.MAGIC_IMMUNITY) && !monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) {
                            if (player.getJob() >= 1200 && player.getJob() <= 1212) {
                                MaxDamagePerHit = MaxDamagePerHit * 50;
                            }
                            if (!(player.getJob() >= 1200 && player.getJob() <= 1212) && (attack.skill == 2001005 || attack.skill == 12001003)) {
                                MaxDamagePerHit = MaxDamagePerHit * 10;
                            }
                            if (eachd > (MaxDamagePerHit * 10)) {
                                player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_MAGIC, "[Damage: " + eachd + ", Expected: " + MaxDamagePerHit + ", Mob: " + monster.getId() + "] [Job: " + player.getJob() + ", Level: " + player.getLevel() + ", Skill: " + attack.skill + "]");
                                if (attack.real) {
                                    player.getCheatTracker().checkSameDamage(eachd, MaxDamagePerHit);
                                }
                                if (eachd > (MaxDamagePerHit * 20)) {
                                    //World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " (編號: " + player.getId() + " )" + player.getName() + " 超出攻击算法2倍" + "疑似外挂!"));
                                    player.getCheatTracker().registerOffense(CheatingOffense.HIGH_DAMAGE_MAGIC_2, "[Damage: " + eachd + ", Expected: " + MaxDamagePerHit + ", Mob: " + monster.getId() + "] [Job: " + player.getJob() + ", Level: " + player.getLevel() + ", Skill: " + attack.skill + "]");
                                    
                                }
                            }
                            //} else if (eachd > MaxDamagePerHit) {
                            //    eachd = (int) (MaxDamagePerHit);
                        }
                    }
                    totDamageToOneMonster += eachd;
                }
                totDamage += totDamageToOneMonster;
                player.checkMonsterAggro(monster);

                double range = player.getPosition().distanceSq(monster.getPosition());
                double SkillRange = GameConstants.getAttackRange(player, effect, attack);

                if (range > SkillRange && ! !GameConstants.isBossMap(player.getMapId())) { // 815^2 <-- the most ranged attack in the game is Flame Wheel at 815 range
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "攻击范围异常,技能:" + attack.skill + "(" + SkillFactory.getNamea(attack.skill) + ")　正常范围:" + (int) SkillRange + " 計算范围:" + (int) range); // , Double.toString(Math.sqrt(distance))
                    if (range > SkillRange * 2) {
                        player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER_BAN, "超大攻击范围,技能:" + attack.skill + "(" + SkillFactory.getNamea(attack.skill) + ")　怪物:" + monster.getId() + " 正常范围:" + (int) SkillRange + " 計算范围:" + (int) range); // , Double.toString(Math.sqrt(distance))
                    }
                }

                if (GameConstants.getAttackDelay(attack.skill, theSkill) >= 100 && !GameConstants.isNoDelaySkill(attack.skill) && !monster.getStats().isBoss() && player.getTruePosition().distanceSq(monster.getTruePosition()) > GameConstants.getAttackRange(effect, player.getStat().defRange)) {
                    player.getCheatTracker().registerOffense(CheatingOffense.ATTACK_FARAWAY_MONSTER, "[Distance: " + player.getTruePosition().distanceSq(monster.getTruePosition()) + ", Expected Distance: " + GameConstants.getAttackRange(effect, player.getStat().defRange) + " Job: " + player.getJob() + "]"); // , Double.toString(Math.sqrt(distance))
                }
                if (attack.skill == 2301002 && !monsterstats.getUndead()) {
                    player.getCheatTracker().registerOffense(CheatingOffense.HEAL_ATTACKING_UNDEAD);
                    return;
                }

                if (totDamageToOneMonster > 0) {
                    monster.damage(player, totDamageToOneMonster, true, attack.skill);
                    if (monster.isBuffed(MonsterStatus.MAGIC_DAMAGE_REFLECT)) { //test
                        player.addHP(-(7000 + Randomizer.nextInt(8000))); //this is what it seems to be?
                    }
                    if (player.getBuffedValue(MapleBuffStat.SLOW) != null) {
                        final MapleStatEffect eff = player.getStatForBuff(MapleBuffStat.SLOW);

                        if (eff != null && eff.makeChanceResult() && !monster.isBuffed(MonsterStatus.SPEED)) {
                            monster.applyStatus(player, new MonsterStatusEffect(MonsterStatus.SPEED, eff.getX(), eff.getSourceId(), null, false), false, eff.getY() * 1000, true, eff);
                        }
                    }
                    player.onAttack(monster.getMobMaxHp(), monster.getMobMaxMp(), attack.skill, monster.getObjectId(), totDamage);
                    switch (attack.skill) {
                        case 2221003:
                            monster.setTempEffectiveness(Element.ICE, effect.getDuration());
                            break;
                        case 2121003:
                            monster.setTempEffectiveness(Element.FIRE, effect.getDuration());
                            break;
                    }
                    if (effect != null && effect.getMonsterStati().size() > 0) {
                        if (effect.makeChanceResult()) {
                            for (Map.Entry<MonsterStatus, Integer> z : effect.getMonsterStati().entrySet()) {
                                monster.applyStatus(player, new MonsterStatusEffect(z.getKey(), z.getValue(), theSkill.getId(), null, false), effect.isPoison(), effect.getDuration(), true, effect);
                            }
                        }
                    }
                    if (eaterLevel > 0) {
                        eaterSkill.getEffect(eaterLevel).applyPassive(player, monster);
                    }
                }
            }
        }
        if (attack.skill != 2301002) {
            effect.applyTo(player);
        }

        if (totDamage > 1 && GameConstants.getAttackDelay(attack.skill, theSkill) >= 100) {
            final CheatTracker tracker = player.getCheatTracker();
            tracker.setAttacksWithoutHit(true);

            if (tracker.getAttacksWithoutHit() > 1000) {
                tracker.registerOffense(CheatingOffense.ATTACK_WITHOUT_GETTING_HIT, Integer.toString(tracker.getAttacksWithoutHit()));
            }
        }
    }

    private static final double CalculateMaxMagicDamagePerHit(final MapleCharacter chr, final Skill skill, final MapleMonster monster, final MapleMonsterStats mobstats, final PlayerStats stats, final Element elem, final Integer sharpEye, final double maxDamagePerMonster, final MapleStatEffect attackEffect) {
        final int dLevel = Math.max(mobstats.getLevel() - chr.getLevel(), 0) * 2;
        int HitRate = Math.min((int) Math.floor(Math.sqrt(stats.getAccuracy())) - (int) Math.floor(Math.sqrt(mobstats.getEva())) + 100, 100);
        if (dLevel > HitRate) {
            HitRate = dLevel;
        }
        HitRate -= dLevel;
        if (HitRate <= 0 && !(GameConstants.isBeginnerJob(skill.getId() / 10000) && skill.getId() % 10000 == 1000)) { // miss :P or HACK :O
            return 0;
        }
        double elemMaxDamagePerMob;
        int CritPercent = sharpEye;
        final ElementalEffectiveness ee = monster.getEffectiveness(elem);
        switch (ee) {
            case IMMUNE:
                elemMaxDamagePerMob = 1;
                break;
            default:
                elemMaxDamagePerMob = ElementalStaffAttackBonus(elem, maxDamagePerMonster * ee.getValue(), stats);
                break;
        }
        // Calculate monster magic def
        // Min damage = (MIN before defense) - MDEF*.6
        // Max damage = (MAX before defense) - MDEF*.5
        int MDRate = monster.getStats().getMDRate();
        MonsterStatusEffect pdr = monster.getBuff(MonsterStatus.MDEF);
        if (pdr != null) {
            MDRate += pdr.getX();
        }
        elemMaxDamagePerMob -= elemMaxDamagePerMob * (Math.max(MDRate - stats.ignoreTargetDEF - attackEffect.getIgnoreMob(), 0) / 100.0);
        // Calculate Sharp eye bonus
        elemMaxDamagePerMob += ((double) elemMaxDamagePerMob / 100.0) * CritPercent;
//	if (skill.isChargeSkill()) {
//	    elemMaxDamagePerMob = (float) ((90 * ((System.currentTimeMillis() - chr.getKeyDownSkill_Time()) / 1000) + 10) * elemMaxDamagePerMob * 0.01);
//	}
//      if (skill.isChargeSkill() && chr.getKeyDownSkill_Time() == 0) {
//          return 1;
//      }
        elemMaxDamagePerMob *= (monster.getStats().isBoss() ? chr.getStat().bossdam_r : chr.getStat().dam_r) / 100.0;
        final MonsterStatusEffect imprint = monster.getBuff(MonsterStatus.IMPRINT);
        if (imprint != null) {
            elemMaxDamagePerMob += (elemMaxDamagePerMob * imprint.getX() / 100.0);
        }
        elemMaxDamagePerMob += (elemMaxDamagePerMob * chr.getDamageIncrease(monster.getObjectId()) / 100.0);
        if (GameConstants.isBeginnerJob(skill.getId() / 10000)) {
            switch (skill.getId() % 10000) {
                case 1000:
                    elemMaxDamagePerMob = 40;
                    break;
                case 1020:
                    elemMaxDamagePerMob = 1;
                    break;
                case 1009:
                    elemMaxDamagePerMob = (monster.getStats().isBoss() ? monster.getMobMaxHp() / 30 * 100 : monster.getMobMaxHp());
                    break;
            }
        }
        switch (skill.getId()) {
            case 32001000:
            case 32101000:
            case 32111002:
            case 32121002:
                elemMaxDamagePerMob *= 1.5;
                break;
        }
        if (elemMaxDamagePerMob > 9999999) {
            elemMaxDamagePerMob = 9999999;
        } else if (elemMaxDamagePerMob <= 0) {
            elemMaxDamagePerMob = 1;
        }

        return elemMaxDamagePerMob;
    }

    private static final double ElementalStaffAttackBonus(final Element elem, double elemMaxDamagePerMob, final PlayerStats stats) {
        switch (elem) {
            case FIRE:
                return (elemMaxDamagePerMob / 100) * (stats.element_fire + stats.getElementBoost(elem));
            case ICE:
                return (elemMaxDamagePerMob / 100) * (stats.element_ice + stats.getElementBoost(elem));
            case LIGHTING:
                return (elemMaxDamagePerMob / 100) * (stats.element_light + stats.getElementBoost(elem));
            case POISON:
                return (elemMaxDamagePerMob / 100) * (stats.element_psn + stats.getElementBoost(elem));
            default:
                return (elemMaxDamagePerMob / 100) * (stats.def + stats.getElementBoost(elem));
        }
    }

    private static void handlePickPocket(final MapleCharacter player, final MapleMonster mob, AttackPair oned) {
        final int maxmeso = player.getBuffedValue(MapleBuffStat.PICKPOCKET).intValue();

        for (final Pair<Integer, Boolean> eachde : oned.attack) {
            final Integer eachd = eachde.left;
            if (player.getStat().pickRate >= 100 || Randomizer.nextInt(99) < player.getStat().pickRate) {
                player.getMap().spawnMesoDrop(Math.min((int) Math.max(((double) eachd / (double) 20000) * (double) maxmeso, (double) 1), maxmeso), new Point((int) (mob.getTruePosition().getX() + Randomizer.nextInt(100) - 50), (int) (mob.getTruePosition().getY())), mob, player, true, (byte) 0);
            }
        }
    }

    private static double CalculateMaxWeaponDamagePerHit(final MapleCharacter player, final MapleMonster monster, final AttackInfo attack, final Skill theSkill, final MapleStatEffect attackEffect, double maximumDamageToMonster, final Integer CriticalDamagePercent) {
        final int dLevel = Math.max(monster.getStats().getLevel() - player.getLevel(), 0) * 2;
        int HitRate = Math.min((int) Math.floor(Math.sqrt(player.getStat().getAccuracy())) - (int) Math.floor(Math.sqrt(monster.getStats().getEva())) + 100, 100);
        if (dLevel > HitRate) {
            HitRate = dLevel;
        }
        HitRate -= dLevel;
        if (HitRate <= 0 && !(GameConstants.isBeginnerJob(attack.skill / 10000) && attack.skill % 10000 == 1000) && !GameConstants.isPyramidSkill(attack.skill) && !GameConstants.isMulungSkill(attack.skill) && !GameConstants.isInflationSkill(attack.skill)) { // miss :P or HACK :O
            return 0;
        }
        if (player.getMapId() / 1000000 == 914 || player.getMapId() / 1000000 == 927) { //aran
            return 999999;
        }

        List<Element> elements = new ArrayList<Element>();
        boolean defined = false;
        int CritPercent = CriticalDamagePercent;
        int PDRate = monster.getStats().getPDRate();
        MonsterStatusEffect pdr = monster.getBuff(MonsterStatus.WDEF);
        if (pdr != null) {
            PDRate += pdr.getX(); //x will be negative usually
        }
        if (theSkill != null) {
            elements.add(theSkill.getElement());
            if (GameConstants.isBeginnerJob(theSkill.getId() / 10000)) {
                switch (theSkill.getId() % 10000) {
                    case 1000:
                        maximumDamageToMonster = 40;
                        defined = true;
                        break;
                    case 1020:
                        maximumDamageToMonster = 1;
                        defined = true;
                        break;
                    case 1009:
                        maximumDamageToMonster = (monster.getStats().isBoss() ? monster.getMobMaxHp() / 30 * 100 : monster.getMobMaxHp());
                        defined = true;
                        break;
                }
            }
            switch (theSkill.getId()) {
                case 1311005:
                    PDRate = (monster.getStats().isBoss() ? PDRate : 0);
                    break;
                case 3221001:
                case 33101001:
                    maximumDamageToMonster *= attackEffect.getMobCount();
                    defined = true;
                    break;
                case 3101005:
                    defined = true; //can go past 500000
                    break;
                case 32001000:
                case 32101000:
                case 32111002:
                case 32121002:
                    maximumDamageToMonster *= 1.5;
                    break;
                case 3221007: //snipe
                case 23121003:
                case 1221009: //BLAST FK
                case 4331003: //Owl Spirit
                    if (!monster.getStats().isBoss()) {
                        maximumDamageToMonster = (monster.getMobMaxHp());
                        defined = true;
                    }
                    break;
                case 1221011://Heavens Hammer
                case 21120006: //Combo Tempest
                    maximumDamageToMonster = (monster.getStats().isBoss() ? 500000 : (monster.getHp() - 1));
                    defined = true;
                    break;
                case 3211006: //Sniper Strafe
                    if (monster.getStatusSourceID(MonsterStatus.FREEZE) == 3211003) { //blizzard in effect
                        defined = true;
                        maximumDamageToMonster = 999999;
                    }
                    break;
            }
        }
        double elementalMaxDamagePerMonster = maximumDamageToMonster;
        if (player.getJob() == 311 || player.getJob() == 312 || player.getJob() == 321 || player.getJob() == 322) {
            //FK mortal blow
            Skill mortal = SkillFactory.getSkill(player.getJob() == 311 || player.getJob() == 312 ? 3110001 : 3210001);
            if (player.getTotalSkillLevel(mortal) > 0) {
                final MapleStatEffect mort = mortal.getEffect(player.getTotalSkillLevel(mortal));
                if (mort != null && monster.getHPPercent() < mort.getX()) {
                    elementalMaxDamagePerMonster = 999999;
                    defined = true;
                    if (mort.getZ() > 0) {
                        player.addHP((player.getStat().getMaxHp() * mort.getZ()) / 100);
                    }
                }
            }
        } else if (player.getJob() == 221 || player.getJob() == 222) {
            //FK storm magic
            Skill mortal = SkillFactory.getSkill(2210000);
            if (player.getTotalSkillLevel(mortal) > 0) {
                final MapleStatEffect mort = mortal.getEffect(player.getTotalSkillLevel(mortal));
                if (mort != null && monster.getHPPercent() < mort.getX()) {
                    elementalMaxDamagePerMonster = 999999;
                    defined = true;
                }
            }
        }
        if (!defined || (theSkill != null && (theSkill.getId() == 33101001 || theSkill.getId() == 3221001))) {
            if (player.getBuffedValue(MapleBuffStat.WK_CHARGE) != null) {
                int chargeSkillId = player.getBuffSource(MapleBuffStat.WK_CHARGE);

                switch (chargeSkillId) {
                    case 1211003:
                    case 1211004:
                        elements.add(Element.FIRE);
                        break;
                    case 1211005:
                    case 1211006:
                    case 21111005:
                        elements.add(Element.ICE);
                        break;
                    case 1211007:
                    case 1211008:
                    case 15101006:
                        elements.add(Element.LIGHTING);
                        break;
                    case 1221003:
                    case 1221004:
                    case 11111007:
                        elements.add(Element.HOLY);
                        break;
                    case 12101005:
                        //elements.clear(); //neutral
                        break;
                }
            }
            if (player.getBuffedValue(MapleBuffStat.LIGHTNING_CHARGE) != null) {
                elements.add(Element.LIGHTING);
            }
            if (player.getBuffedValue(MapleBuffStat.ELEMENT_RESET) != null) {
                elements.clear();
            }
            if (elements.size() > 0) {
                double elementalEffect;

                switch (attack.skill) {
                    case 3211003:
                    case 3111003: // inferno and blizzard
                        elementalEffect = attackEffect.getX() / 100.0;
                        break;
                    default:
                        elementalEffect = (0.5 / elements.size());
                        break;
                }
                for (Element element : elements) {
                    switch (monster.getEffectiveness(element)) {
                        case IMMUNE:
                            elementalMaxDamagePerMonster = 1;
                            break;
                        case WEAK:
                            elementalMaxDamagePerMonster *= (1.0 + elementalEffect + player.getStat().getElementBoost(element));
                            break;
                        case STRONG:
                            elementalMaxDamagePerMonster *= (1.0 - elementalEffect - player.getStat().getElementBoost(element));
                            break;
                    }
                }
            }
            // Calculate mob def
            elementalMaxDamagePerMonster -= elementalMaxDamagePerMonster * (Math.max(PDRate - Math.max(player.getStat().ignoreTargetDEF, 0) - Math.max(attackEffect == null ? 0 : attackEffect.getIgnoreMob(), 0), 0) / 100.0);

            // Calculate passive bonuses + Sharp Eye
            elementalMaxDamagePerMonster += ((double) elementalMaxDamagePerMonster / 100.0) * CritPercent;

            final MonsterStatusEffect imprint = monster.getBuff(MonsterStatus.IMPRINT);
            if (imprint != null) {
                elementalMaxDamagePerMonster += (elementalMaxDamagePerMonster * imprint.getX() / 100.0);
            }

            elementalMaxDamagePerMonster += (elementalMaxDamagePerMonster * player.getDamageIncrease(monster.getObjectId()) / 100.0);
            elementalMaxDamagePerMonster *= (monster.getStats().isBoss() && attackEffect != null ? (player.getStat().bossdam_r + attackEffect.getBossDamage()) : player.getStat().dam_r) / 100.0;
        }
        if (elementalMaxDamagePerMonster > 9999999) {
            if (!defined) {
                elementalMaxDamagePerMonster = 9999999;
            }
        } else if (elementalMaxDamagePerMonster <= 0) {
            elementalMaxDamagePerMonster = 1;
        }
        return elementalMaxDamagePerMonster;
    }

    public static final AttackInfo DivideAttack(final AttackInfo attack, final int rate) {
        attack.real = false;
        if (rate <= 1) {
            return attack; //lol
        }
        for (AttackPair p : attack.allDamage) {
            if (p.attack != null) {
                for (Pair<Integer, Boolean> eachd : p.attack) {
                    eachd.left /= rate; //too ex.
                }
            }
        }
        return attack;
    }

    public static final AttackInfo Modify_AttackCrit(final AttackInfo attack, final MapleCharacter chr, final int type, final MapleStatEffect effect) {
        if (attack.skill != 4211006 && attack.skill != 3211003 && attack.skill != 4111004) { //blizz + shadow meso + m.e no crits
            final int CriticalRate = chr.getStat().passive_sharpeye_rate() + (effect == null ? 0 : effect.getCr());
            final boolean shadow = chr.getBuffedValue(MapleBuffStat.SHADOWPARTNER) != null && (type == 1 || type == 2);
            final List<Integer> damages = new ArrayList<Integer>(), damage = new ArrayList<Integer>();
            int hit, toCrit, mid_att;
            for (AttackPair p : attack.allDamage) {
                if (p.attack != null) {
                    hit = 0;
                    mid_att = shadow ? (p.attack.size() / 2) : p.attack.size();
                    //grab the highest hits
                    toCrit = attack.skill == 4221001 || attack.skill == 3221007 || attack.skill == 23121003 || attack.skill == 4341005 || attack.skill == 4331006 || attack.skill == 21120005 ? mid_att : 0;
                    if (toCrit == 0) {
                        for (Pair<Integer, Boolean> eachd : p.attack) {
                            if (!eachd.right && hit < mid_att) {
                                if (eachd.left > 9999999 || Randomizer.nextInt(100) < CriticalRate) {
                                    toCrit++;
                                }
                                damage.add(eachd.left);
                            }
                            hit++;
                        }
                        if (toCrit == 0) {
                            damage.clear();
                            continue; //no crits here
                        }
                        Collections.sort(damage); //least to greatest
                        for (int i = damage.size(); i > damage.size() - toCrit; i--) {
                            damages.add(damage.get(i - 1));
                        }
                        damage.clear();
                    }
                    hit = 0;
                    for (Pair<Integer, Boolean> eachd : p.attack) {
                        if (!eachd.right) {
                            if (attack.skill == 4221001) { //assassinate never crit first 3, always crit last
                                eachd.right = hit == 3;
                            } else if (attack.skill == 3221007 || attack.skill == 23121003 || attack.skill == 21120005 || attack.skill == 4341005 || attack.skill == 4331006 || eachd.left > 9999999) { //snipe always crit
                                eachd.right = true;
                            } else if (hit >= mid_att) { //shadowpartner copies second half to first half
                                eachd.right = p.attack.get(hit - mid_att).right;
                            } else {
                                //rough calculation
                                eachd.right = damages.contains(eachd.left);
                            }
                        }
                        hit++;
                    }
                    damages.clear();
                }
            }
        }
        return attack;
    }

    public static final AttackInfo parseDmgMa(final LittleEndianAccessor lea, final MapleCharacter chr) {
        //System.out.println(lea.toString());
        final AttackInfo ret = new AttackInfo();

        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        //System.out.println("TBYTE: " + tbyte);
        ret.targets = (byte) ((ret.tbyte >>> 4) & 0xF);
        ret.hits = (byte) (ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        if (ret.skill >= 91000000) { //guild/recipe? no
            return null;
        }

        int move = lea.readByte();
        lea.skip(9);
        if (move != 0) {
            lea.skip(4);
            parseDmgMovement(lea);
            lea.skip(17);
        }

        if (GameConstants.isMagicChargeSkill(ret.skill)) {
            ret.charge = lea.readInt();
        } else {
            ret.charge = -1;
        }
        lea.skip(4);
        ret.unk = lea.readByte();
        ret.display = lea.readUShort();
        lea.skip(4); //big bang
        lea.skip(1); // Weapon class
        if (chr.getCygnusBless()) {
            lea.skip(12);
        }
        ret.speed = lea.readByte(); // Confirmed
        ret.lastAttackTickCount = lea.readInt(); // Ticks
        lea.skip(4); //0

        int damage, oid;
        List<Pair<Integer, Boolean>> allDamageNumbers;
        ret.allDamage = new ArrayList<AttackPair>();

        for (int i = 0; i < ret.targets; i++) {
            oid = lea.readInt();
            lea.skip(14); // [1] Always 6?, [3] unk, [4] Pos1, [4] Pos2, [2] seems to change randomly for some attack

            allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();

            for (int j = 0; j < ret.hits; j++) {
                damage = lea.readInt();
                allDamageNumbers.add(new Pair<Integer, Boolean>(Integer.valueOf(damage), false));
            }
            lea.skip(4);
            lea.skip(4); // CRC of monster [Wz Editing]
            ret.allDamage.add(new AttackPair(Integer.valueOf(oid), allDamageNumbers));
        }
        ret.position = lea.readPos();

        return ret;
    }

    public static final AttackInfo parseDmgM(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();

        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        //雙刀打到怪 18 战神 17
        //System.out.println("TBYTE: " + tbyte);
        ret.targets = (byte) ((ret.tbyte >>> 4) & 0xF);
        ret.hits = (byte) (ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        if (ret.skill >= 91000000) { //guild/recipe? no
            return null;
        }
        int move = 0;
        switch (ret.skill) {
            case 2111007://快速移动精通
            case 2211007://快速移动精通
            case 2311007://快速移动精通
            case 12111007://快速移动精通
            case 22161005://快速移动精通
            case 32111010://快速移动精通
                lea.skip(1);
                break;
            default:
                move = lea.readByte();
                lea.skip(1);
        }
        lea.skip(8);
        if (move != 0) {
            lea.skip(4);
            parseDmgMovement(lea);
            lea.skip(17);
        }

        switch (ret.skill) {
            case 5101004: // Corkscrew
            case 15101003: // Cygnus corkscrew
            case 5201002: // Gernard
            case 14111006: // Poison bomb
            case 4341002:
            case 4341003:
            case 5301001:
            case 5300007:
            case 31001000:
            case 31101000:
            case 31111005:
                ret.charge = lea.readInt();
                break;
            default:
                ret.charge = 0;
                break;
        }
        lea.skip(4);
        ret.unk = lea.readByte();
        ret.display = lea.readUShort();
        lea.skip(4); //big bang
        lea.skip(1); // Weapon class
        if (chr.getCygnusBless()) {
            lea.skip(12);
        }
        ret.speed = lea.readByte(); // Confirmed
        ret.lastAttackTickCount = lea.readInt(); // Ticks
        lea.skip(4); //0

        ret.allDamage = new ArrayList<AttackPair>();

        if (ret.skill == 4211006) { // Meso Explosion
            return parseMesoExplosion(lea, ret, chr);
        }
        int damage, oid;
        List<Pair<Integer, Boolean>> allDamageNumbers;

        for (int i = 0; i < ret.targets; i++) {
            oid = lea.readInt();
            int a = lea.readByte();
            lea.skip(13); // [1] Always 6?, [3] unk, [4] Pos1, [4] Pos2, [2] seems to change randomly for some attack

            allDamageNumbers = new ArrayList<>();

            for (int j = 0; j < ret.hits; j++) {
                damage = lea.readInt();
                // System.out.println("Damage: " + damage);
                allDamageNumbers.add(new Pair<>(damage, false));
            }
            lea.skip(4);
            lea.skip(4); // CRC of monster [Wz Editing]
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
        }
        ret.position = lea.readPos();
        byte unka = lea.readByte();
        for (int i = 0; i < unka; i++) {
            lea.readByte();
            lea.readByte();
        }
        if (ret.skill == 14111006) {
            ret.skillposition = lea.readPos();
        }
        return ret;
    }

    public static final AttackInfo parseDmgR(final LittleEndianAccessor lea, final MapleCharacter chr) {
        final AttackInfo ret = new AttackInfo();

        lea.skip(1);
        lea.skip(8);
        ret.tbyte = lea.readByte();
        //System.out.println("TBYTE: " + tbyte);
        ret.targets = (byte) ((ret.tbyte >>> 4) & 0xF);
        ret.hits = (byte) (ret.tbyte & 0xF);
        lea.skip(8);
        ret.skill = lea.readInt();
        if (ret.skill >= 91000000) { //guild/recipe? no
            return null;
        }
        int move = lea.readByte();
        lea.skip(9);
        if (move != 0) {
            lea.skip(4);
            parseDmgMovement(lea);
            lea.skip(17);
        }

        switch (ret.skill) {
            case 3121004: // Hurricane
            case 3221001: // Pierce
            case 5221004: // Rapidfire
            case 13111002: // Cygnus Hurricane
            case 33121009:
            case 35001001:
            case 35101009:
            case 23121000:
            case 5311002:
                lea.skip(4); // extra 4 bytes
                break;
        }
        lea.skip(4);
        lea.skip(1);
        ret.charge = -1;
        ret.unk = lea.readByte();
        ret.display = lea.readUShort();
        lea.skip(4); //big bang
        lea.skip(1); // Weapon class
        if (chr.getCygnusBless()) {
            lea.skip(12);
        }
        ret.speed = lea.readByte(); // Confirmed
        ret.lastAttackTickCount = lea.readInt(); // Ticks
        lea.skip(4); //0
        ret.slot = (byte) lea.readShort();
        ret.csstar = (byte) lea.readShort();
        ret.AOE = lea.readByte(); // is AOE or not, TT/ Avenger = 41, Showdown = 0

        int damage, oid;
        List<Pair<Integer, Boolean>> allDamageNumbers;
        ret.allDamage = new ArrayList<AttackPair>();

        for (int i = 0; i < ret.targets; i++) {
            oid = lea.readInt();
            lea.skip(14); // [1] Always 6?, [3] unk, [4] Pos1, [4] Pos2, [2] seems to change randomly for some attack

            allDamageNumbers = new ArrayList<Pair<Integer, Boolean>>();
            for (int j = 0; j < ret.hits; j++) {
                damage = lea.readInt();
                allDamageNumbers.add(new Pair<Integer, Boolean>(Integer.valueOf(damage), false));
            }
            lea.skip(4);
            lea.skip(4); // CRC of monster [Wz Editing]
//	    System.out.println(tools.HexTool.toString(lea.read(4)));

            ret.allDamage.add(new AttackPair(Integer.valueOf(oid), allDamageNumbers));
        }
        lea.skip(4);
        ret.position = lea.readPos();
        if (lea.available() == 4L) {
            ret.skillposition = lea.readPos();
        }

        return ret;
    }

    public static final AttackInfo parseMesoExplosion(final LittleEndianAccessor lea, final AttackInfo ret, final MapleCharacter chr) {
        //System.out.println(lea.toString(true));
        byte bullets;
        if (ret.hits == 0) {
            bullets = lea.readByte();
            for (int j = 0; j < bullets; j++) {
                ret.allDamage.add(new AttackPair(lea.readInt(), null));
                lea.skip(1);
            }
            lea.skip(2); // 8F 02
            return ret;
        }
        int oid;
        List<Pair<Integer, Boolean>> allDamageNumbers;

        for (int i = 0; i < ret.targets; i++) {
            oid = lea.readInt();
            lea.skip(12);
            bullets = lea.readByte();
            allDamageNumbers = new ArrayList<>();
            for (int j = 0; j < bullets; j++) {
                allDamageNumbers.add(new Pair<>(lea.readInt(), false)); //m.e. never crits
            }
            ret.allDamage.add(new AttackPair(oid, allDamageNumbers));
            lea.skip(4); // C3 8F 41 94, 51 04 5B 01
            lea.skip(4);
        }
        lea.skip(4);
        bullets = lea.readByte();

        for (int j = 0; j < bullets; j++) {
            ret.allDamage.add(new AttackPair(lea.readInt(), null));
            lea.skip(2);
        }

        return ret;
    }

    public static void parseDmgMovement(LittleEndianAccessor lea) {
        int numCommands = lea.readByte();
        for (int i = 0; i < numCommands; i++) {
            int command = lea.readByte();
            switch (command) {
                case 0xD: {
                    lea.skip(19);
                    break;
                }
                case 0:
                case 6:
                case 0xF:
                case 0x25:
                case 0x26: {
                    lea.skip(17);
                    break;
                }
                case 0x24: {
                    lea.skip(13);
                    break;
                }
                case 1:
                case 2:
                case 0xE:
                case 0x11:
                case 0x13:
                case 0x20:
                case 0x21:
                case 0x22:
                case 0x23: {
                    lea.skip(7);
                    break;
                }
                case 0x10:
                case 0x15:
                case 0x16:
                case 0x17:
                case 0x18:
                case 0x19:
                case 0x1A:
                case 0x1B:
                case 0x1C:
                case 0x1D:
                case 0x1E:
                case 0x1F: {
                    lea.skip(3);
                    break;
                }
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 0xB: {
                    lea.skip(9);
                    break;
                }
                case 0xC: {
                    lea.skip(11);
                    break;
                }
                case 0x12: {
                    lea.skip(9);
                    break;
                }
                case 0xA: {
                    lea.skip(1);
                    break;
                }
                default:
                    //System.out.println("Kind movement: " + kind + ", Remaining : " + (numCommands - res.size()) + " New type of movement ID : " + command + ", packet : " + lea.toString(true));
                    FileoutputUtil.logToFile("logs/攻击移动类型.txt", "\r\n\r\n" + command + "未处理" + "\r\n\r\n");
            }
        }
        double skip = lea.readByte();
        skip = Math.ceil(skip / 2.0D);
        lea.skip((int) skip);

    }
}
