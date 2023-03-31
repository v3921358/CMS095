/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author XiaoMaDengDengWo
 */
public enum MapleJob {
    新手(0),
    战士(100),
    剑客(110),
    勇士(111),
    英雄(112),
    准骑士(120),
    骑士(121),
    圣骑士(122),
    枪战士(130),
    龙骑士(131),
    黑骑士(132),
    魔法师(200),
    火毒法师(210),
    火毒巫师(211),
    火毒魔导师(212),
    冰雷法师(220),
    冰雷巫师(221),
    冰雷魔导师(222),
    牧师(230),
    祭司(231),
    主教(232),
    弓箭手(300),
    猎手(310),
    射手(311),
    神射手(312),
    弩弓手(320),
    游侠(321),
    剑神(322),
    飞侠(400),
    刺客(410),
    无影人(411),
    隐士(412),
    侠客(420),
    独行客(421),
    侠盗(422),
    见习刀客(430),
    双刀客(431),
    双刀侠(432),
    血刀(433),
    暗影双刀(434),
    海盗(500),
    炮手(501),
    拳手(510),
    斗士(511),
    冲锋队长(512),
    火枪手(520),
    大副(521),
    船长(522),
    管理者(800),
    管理员(900),
    初心者(1000),
    魂骑士1转(1100),
    魂骑士2转(1110),
    魂骑士3转(1111),
    魂骑士4转(1112),
    炎术士1转(1200),
    炎术士2转(1210),
    炎术士3转(1211),
    炎术士4转(1212),
    风灵使者1转(1300),
    风灵使者2转(1310),
    风灵使者3转(1311),
    风灵使者4转(1312),
    夜行者1转(1400),
    夜行者2转(1410),
    夜行者3转(1411),
    夜行者4转(1412),
    奇袭者1转(1500),
    奇袭者2转(1510),
    奇袭者3转(1511),
    奇袭者4转(1512),
    战童(2000),
    小不点(2001),
    战神1转(2100),
    战神2转(2110),
    战神3转(2111),
    战神4转(2112),
    龙神1转(2200),
    龙神2转(2210),
    龙神3转(2211),
    龙神4转(2212),
    龙神5转(2213),
    龙神6转(2214),
    龙神7转(2215),
    龙神8转(2216),
    龙神9转(2217),
    龙神10转(2218),
    预备兵(3000),
    唤灵斗师1转(3200),
    唤灵斗师2转(3210),
    唤灵斗师3转(3211),
    唤灵斗师4转(3212),
    豹弩游侠1转(3300),
    豹弩游侠2转(3310),
    豹弩游侠3转(3311),
    豹弩游侠4转(3312),
    机械师1转(3500),
    机械师2转(3510),
    机械师3转(3511),
    机械师4转(3512),
    ADDITIONAL_SKILLS(9000),
    未知(999999),;
    private final int jobid;

    private MapleJob(int id) {
        this.jobid = id;
    }

    public int getId() {
        return this.jobid;
    }

    public static String getName(MapleJob mjob) {
        return mjob.name();
    }

    public static MapleJob getById(int id) {
        for (MapleJob l : values()) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public static boolean isExist(int id) {
        for (MapleJob job : values()) {
            if (job.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
