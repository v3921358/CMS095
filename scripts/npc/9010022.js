/**
 * Dimensional Mirror
 * Warps you to Party Quests/Special Maps
 */
var text = "";

function start() {
    if (cm.getPlayerStat("LVL") < 10) {
        cm.askMapSelection("");
        cm.dispose();
        return;
    }
    if (cm.getPlayerStat("LVL") >= 10) {
        //text += "#20#导游地图";
    }
    if (cm.getPlayerStat("LVL") >= 10) {
        //text += "#0#纳希竞技大会";
    }
    if (cm.getPlayerStat("LVL") >= 25) {
        text += "#1#武陵道场";
    }
    if (cm.getPlayerStat("LVL") >= 30) {
        text += "#2#怪物嘉年华1";
    }
    if (cm.getPlayerStat("LVL") >= 50) {
        text += "#3#怪物嘉年华2";
    }
    if (cm.getPlayerStat("LVL") >= 40) {
        text += "#5#奈特的金字塔";
    }
    if (cm.getPlayerStat("LVL") >= 25 && cm.getPlayerStat("LVL") <= 30) {
        //    text += "#6#废弃的地铁月台";
    }
    //text += "#7#幸福村";
    //text += "#8#黄金寺院";
    if (cm.getPlayerStat("LVL") >= 10) {
        text += "#9#月秒的年糕";
    }
    if (cm.getPlayerStat("LVL") >= 20) {
        text += "#10#废弃副本";
    }
    if (cm.getPlayerStat("LVL") >= 30) {
        text += "#11#玩具塔101";
    }
    if (cm.getPlayerStat("LVL") >= 40) {
        text += "#12#毒雾森林";
    }
    if (cm.getPlayerStat("LVL") >= 50) {
        text += "#13#女神塔";
    }
	if (cm.getPlayerStat("LVL") >= 60) {
        text += "#4#雾海幽灵船";
    }
    if (cm.getPlayerStat("LVL") >= 60) {
        text += "#14#海盗船";
    }
    if (cm.getPlayerStat("LVL") >= 70) {
        text += "#15#罗密欧与茱丽叶";
    }
    if (cm.getPlayerStat("LVL") >= 80) {
        //text += "#16#侏儒帝王的复活";
    }
    if (cm.getPlayerStat("LVL") >= 100) {
        text += "#17#御龙魔";
    }
    cm.askMapSelection(text);
}

function action(mode, type, selection) {
    if (mode == 1) {
        if (cm.getPlayerStat("LVL") < 10) { // they cannot use any
            cm.dispose();
            return;
        }
        switch (selection) {
            case 0: // Boss Party Quest / Ariant Coliseum
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(980010000, 3);
                break;
            case 1: // Mu Lung Training Center
                if (cm.getPlayerStat("LVL") >= 25) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(925020000, 4);
                }
                break;
            case 2: // Monster Carnival 1
                if (cm.getPlayerStat("LVL") >= 30) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(980000000, 4);
                }
                break;
            case 3: // Monster Carnival 2
                if (cm.getPlayerStat("LVL") >= 50) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(980030000, 4);
                }
                break;
            case 4: // Dual Raid
                if (cm.getPlayerStat("LVL") >= 60) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(923020000, 0);
                }
                break;
            case 5: // Nett's Pyramid
                if (cm.getPlayerStat("LVL") >= 40) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(926010000, 4);
                }
                break;
            case 6: // Kerning Subway
                if (cm.getPlayerStat("LVL") >= 25 && cm.getPlayerStat("LVL") <= 30) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(910320000, 2);
                }
                break;
            case 7: // Happyville
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(209000000, 0);
                break;
            case 8: // Golden Temple
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(950100000, 9);
                break;
            case 9: // Moon Bunny
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(910010500, 0);
                break;
            case 10: // First Time Together
                if (cm.getPlayerStat("LVL") >= 20) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(910340700, 0);
                }
                break;
            case 11: // Dimensional Crack
                if (cm.getPlayerStat("LVL") >= 30) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(221023300, 2);
                }
                break;
            case 12: // Forest of Poison Haze
                if (cm.getPlayerStat("LVL") >= 40) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(300030100, 1);
                }
                break;
            case 13: // Remnant of the Goddess
                if (cm.getPlayerStat("LVL") >= 50) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(200080101, 1);
                }
                break;
            case 14: // Lord Pirate
                if (cm.getPlayerStat("LVL") >= 60) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(251010404, 1);
                }
                break;
            case 15: // Romeo and Juliet
                if (cm.getPlayerStat("LVL") >= 70) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(261000021, 5);
                }
                break;
            case 16: // Resurrection of the Hoblin King
                if (cm.getPlayerStat("LVL") >= 80) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(211000002, 0);
                }
                break;
            case 17: // Dragon's Nest
                if (cm.getPlayerStat("LVL") >= 100) {
                    cm.saveReturnLocation("MULUNG_TC");
                    cm.warp(240080000, 2);
                }
                break;
            case 19: // Haunted Mansion
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(600000000, 0);
                break;
            case 20: 
                cm.saveReturnLocation("MULUNG_TC");
                cm.warp(950000000, 0);
                break;

        }
    }
    cm.dispose();
}