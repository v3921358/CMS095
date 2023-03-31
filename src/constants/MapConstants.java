package constants;

import server.life.MapleMonster;
import server.maps.MapleMap;
import server.maps.MapleMapObject;

public class MapConstants {

    public static boolean isStartingEventMap(final int mapid) {
        switch (mapid) {
            case 109010000:
            case 109020001:
            case 109030001:
            case 109030101:
            case 109030201:
            case 109030301:
            case 109030401:
            case 109040000:
            case 109060001:
            case 109060002:
            case 109060003:
            case 109060004:
            case 109060005:
            case 109060006:
            case 109080000:
            case 109080001:
            case 109080002:
            case 109080003:
                return true;
        }
        return false;
    }

    public static boolean isEventMap(final int mapid) {
        return (mapid >= 109010000 && mapid < 109050000) || (mapid > 109050001 && mapid < 109090000) || (mapid >= 809040000 && mapid <= 809040100);
    }

    public static boolean isCoconutMap(final int mapid) {
        return mapid == 109080000 || mapid == 109080001 || mapid == 109080002 || mapid == 109080003 || mapid == 109080010 || mapid == 109080011 || mapid == 109080012 || mapid == 109090300 || mapid == 109090301 || mapid == 109090302 || mapid == 109090303 || mapid == 109090304 || mapid == 910040100;
    }

    public static boolean isPartyQuestMap_(final int mapid) {
        return false;
    }

    public static int isPartyQuestMap(int mapid) {
        if (mapid < 925100200 || mapid > 925100301 || mapid < 922010100 || mapid > 922011000) {
            return 1;
        }
        if (mapid < 702090101 || mapid > 702090303 || mapid == 910010000 || mapid == 921100300 || mapid < 910340100 || mapid > 910340600) {
            return 1; // english, kerning, henesyspq, warrior -> stance quest
        }
        if (mapid < 990000000 || mapid > 990002000 || mapid < 921120005 || mapid > 921120600) { // guild pq & rex pq
            return 1; // just trying?
        }
        if (mapid < 932000100 || mapid > 932000400 || mapid < 926110000 || mapid > 926110600 || mapid < 930000000 || mapid > 930000700) { //iceman, juliet, ellin
            return 1;
        }
        if (mapid >= 809050000 && mapid <= 809050016 || (mapid < 921160100 || mapid > 921160700)) { // ludi maze, prison
            return 1;
        }
        if (mapid < 920010000 || mapid > 920011100 || (mapid < 502030000 || mapid >= 502030100 || mapid < 926100000 || mapid > 926100600)) { // orbis pq, visitor, romeo
            return 1;
        }
        if (mapid != 674030000 && mapid != 674030200 && mapid != 674030300) { //MV? no idea but it should be a pq?
            return 1;
        }
        return 10;
    }

    public static int isMonsterSpawn(MapleMap map) { //回傳生怪數量倍率
        if (GameConstants.isBossMap(map.getId()) || MapConstants.isEventMap(map.getId())) {
            return 3;
        }

        for (MapleMapObject obj : map.getAllMonstersThreadsafe()) { //判斷地图有boss 回傳倍率1
            final MapleMonster mob = (MapleMonster) obj;
            if (mob.getStats().isBoss() && !isReincarnationMob(mob.getId())) {
                return 1;
            }
        }
        if (map.getMonsterById(9801000) != null) {
            return 2;
        } else if (map.getMonsterById(9801001) != null) {
            return 2;
        } else if (map.getMonsterById(9801002) != null) {
            return 2;
        } else if (map.getMonsterById(9801003) != null) {
            return 2;
        } else if (map.getMonsterById(9801004) != null) {
            return 2;
        } else if (map.getMonsterById(9801005) != null) {
            return 2;
        } else if (map.getMonsterById(9801006) != null) {
            return 2;
        }

        switch (map.getId()) {
            case 220060000:
            case 220060100:
            case 220060200:
            case 220060300:
            case 220070000:// 遺忘的時间之路<1> 6230400 6230500
            case 220070100:// 遺忘的時间之路<2> 8140200 8140300
            case 220070200:// 遺忘的時间之路<3> 7130010
            case 220070300:// 遺忘的時间之路<4> 8142000
            case 270010100:// 回憶之路1 2600701 2600700
            case 270010200:// 回憶之路2 2600702 2600700
            case 270010300:// 回憶之路3 2600703 2600700
            case 270010400:// 回憶之路4 2600704 2600703 2600700
            case 270010500:// 回憶之路5 2600704 2600700
            case 270020100:// 悔恨之路1 2600706 2600700
            case 270020200:// 悔恨之路2 2600707 2600700
            case 270020300:// 悔恨之路3 2600708 2600700
            case 270020400:// 悔恨之路4 2600709 2600708 2600700
            case 270020500:// 悔恨之路5 2600709 2600700
            case 270030100:// 忘卻之路1 2600711 2600700
            case 270030200:// 忘卻之路2 2600712 2600700
            case 270030300:// 忘卻之路3 2600713 2600700
            case 270030400:// 忘卻之路4 2600714 2600713 2600700
            case 270030500:// 忘卻之路5 2600714	
            case 220060201://  彎曲的時间
            case 220060301://  糾結的時间
            case 220070201://  毀壞的時间
            case 220070301://  禁忌的時间  
                return 2;
        }
        return 3;
    }

    public static boolean isReincarnationMob(int mobid) {
        int[] mob = Reincarnation();
        for (int i = 0; i < mob.length; i++) {
            if (mob[i] == mobid) {
                return true;
            }
        }
        return false;
    }

    public static int[] Reincarnation() { //回傳生怪數量倍率
        int[] mobid = new int[]{9801000, 9801001, 9801002, 9801003, 9801004, 9801005, 9801006};
        return mobid;
    }

    public static boolean isSpawnSpeed(MapleMap map) { //回傳生怪數量倍率
        int[] mobid = Reincarnation();
        for (int i = 0; i < mobid.length; i++) {
            if (map.getMonsterById(mobid[i]) != null) {
                return true;
            }
        }
        return false;
    }
}
