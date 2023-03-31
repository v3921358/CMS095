/*
 NPC Name: 		Spinel
 Map(s): 		Victoria Road : Henesys (100000000), Victoria Road : Ellinia (101000000), Victoria Road : Perion (102000000), Victoria Road : Kerning City (103000000), Victoria Road : Lith Harbor (104000000), Orbis : Orbis (200000000), Ludibrium : Ludibrium (220000000), Leafre : Leafre (240000000), Zipangu : Mushroom Shrine (800000000)
 Description: 		World Tour Guide
 */

var status = -1;
var cost, sel;
var togo1, togo2, togo3, togo4/*, togo5*/, togo6, togo7;
var map;
var back = true;
var maps = Array(
	Array(1,500000000),
	Array(1,540000000),
	Array(1,550000000),
	Array(1,701000000),
	Array(1,702000000),
	Array(1,800000000)

)
function start() {
    switch (cm.getMapId()) {
        case 800000000:
        case 500000000:
            //case 950100000:
		//case 104020000:
        case 701000000:
        case 702000000:
        case 540000000:
        case 550000000:
            map = cm.getSavedLocation("WORLDTOUR");
            cm.sendSimple("怎麽样的旅游你享受了吗？ \n\r #b#L0#我还可以去哪边?#l \n\r #L1#我旅行完了,我要回去#m" + map + "##l\n\r #L2#送我去樱花城#m800040000##l");
            break;
        default:
            back = false;
            if (cm.getJob() == 0 && cm.getJob() == 1000 && cm.getJob() == 2000) {
                cm.sendNext("如果对疲倦的生活厌烦了，何不去旅行呢？不仅可以感受别的文化,还能学到很多知识！向您推荐由我们枫之谷旅行社准备的#b世界旅行#k!担心会有很大烂够吗？请不必担心，我们的\r\n#b枫之谷世界旅行#k! 只需 #b300 金币#k就可以。");
                cost = 300;
            } else {
                cm.sendNext("如果对疲倦的生活厌烦了，何不去旅行呢？不仅可以感受别的文化,还能学到很多知识！向您推荐由我们枫之谷旅行社准备的#b世界旅行#k!担心会有很大烂够吗？请不必担心，我们的\r\n#b枫之谷世界旅行#k! 只需 #b3000 金币#k就可以。");
                cost = 3000;
            }
            break;
    }
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if ((status <= 2 && mode == 0) || (status == 4 && mode == 1)) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (!back) {
            if (status == 0) {
				var txt = "选择你想要的旅行地点? \n\r";
				for (var i = 0; i < maps.length; i++){
					txt += " #b#L" + i + "##m" + maps[i][1] + "# (3,000 金币)#l \n\r";
				}	
                cm.sendSimple(txt);
            } else if (status == 1) {
                if (cm.getMeso() < cost) {
                    cm.sendPrev("请确认身上金币是否足够");
                } else {
                    cm.gainMeso(-cost);
                    cm.saveLocation("WORLDTOUR");
                    cm.warp(maps[selection][1], 0);
                    cm.dispose();
                }
            }
        } else {
            if (status == 0) {
                if (selection == 0) {
                    switch (cm.getMapId()) {
						case 104020000:
							togo1 = 800000000;
                            togo2 = 701000000;
                            togo3 = 500000000;
                            togo4 = 702000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;
                        case 740000000:
                            togo1 = 800000000;
                            togo2 = 701000000;
                            togo3 = 500000000;
                            togo4 = 702000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;
                        case 500000000:
                            togo1 = 800000000;
                            togo2 = 701000000;
                            //togo3 = 740000000;
                            togo4 = 702000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;
                            break;
                        case 800000000:
                            togo1 = 701000000;
                            togo2 = 500000000;
                            // togo3 = ;
                            togo4 = 702000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;
                            break;
                        case 701000000:
                            togo1 = 500000000;
                            togo2 = 800000000;
                            //togo3 = 740000000;
                            togo4 = 702000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;

                            break;
                        case 702000000:
                            togo1 = 500000000;
                            togo2 = 701000000;
                            //togo3 = 740000000;
                            togo4 = 800000000;
                            //togo5 = 950100000;
                            togo6 = 540000000;
                            togo7 = 550000000;
                            break;
                        case 540000000:
                            togo1 = 500000000;
                            togo2 = 701000000;
                            //togo3 = 740000000;
                            togo4 = 800000000;
                            //togo5 = 950100000;
                            togo6 = 702000000;
                            togo7 = 550000000;
                            break;
                        case 550000000:
                            togo1 = 500000000;
                            togo2 = 701000000;
                            //togo3 = 740000000;
                            togo4 = 800000000;
                            //togo5 = 950100000;
                            togo6 = 702000000;
                            togo7 = 540000000;
                            break;
                            //case 950100000:
                            //	togo1 = 500000000;
                            //	togo2 = 701000000;
                            //  togo3 = 740000000;
                            //	togo4 = 800000000;
                            //	togo5 = 702000000;
                            break;
                    }
                    cm.sendSimple("选择你想要的旅行地点? \n\r #b#L0##m" + togo1 + "# (3,000 金币)#l \n\r #L1##m" + togo2 + "# (3,000 金币)#l \n\r #L2##m" + togo4 + "# (3,000 金币)#l \n\r #L4##m" + togo6 + "# (3,000 金币)#l \n\r #L5##m" + togo7 + "# (3,000 金币)#l "/*+"\r\n#L4##m" + togo5 + "# (3,000 金币)#l"*//*\n\r #L3##m" + togo4 + "# (3,000 金币)#l */);

                } else if (selection == 1) {
                    cm.warp(map == -1 ? 100000000 : map);
                    cm.clearSavedLocation("WORLDTOUR");
                    cm.dispose();
                } else if (selection == 2) {
                    cm.warp(800040000);
                    cm.clearSavedLocation("WORLDTOUR");
                    cm.dispose();
                }
            } else if (status == 1) {
                sel = selection;
                if (sel == 0) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo1 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                } else if (sel == 1) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo2 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                } else if (sel == 2) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo4 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                } else if (sel == 3) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo6 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                } else if (sel == 4) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo6 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                } else if (sel == 5) {
                    cm.sendNext("你想要去这个地方旅行? #b#m" + togo7 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                }
                /*else if (sel == 4) {
                 cm.sendNext("你想要去这个地方旅行? #b#m" + togo5 + "##k? 我将带你去只需要 #b3,000 金币#k. 你现在愿意去?");
                 }*/
            } else if (status == 2) {
                if (sel == 0) {
					cm.gainMeso(-3000);
                    cm.warp(togo1);
                } else if (sel == 1) {
					cm.gainMeso(-3000);
                    cm.warp(togo2);
                } else if (sel == 2) {
					cm.gainMeso(-3000);
                    cm.warp(togo4);
                } else if (sel == 3) {
					cm.gainMeso(-3000);
                    cm.warp(togo4);
                } else if (sel == 4) {
					cm.gainMeso(-3000);
                    cm.warp(togo6);
                } else if (sel == 5) {
					cm.gainMeso(-3000);
                    cm.warp(togo7);
                }

                /*else if (sel == 4) {
                 cm.warp(togo5);
                 }*/
                cm.dispose();
            }
        }
    }
}