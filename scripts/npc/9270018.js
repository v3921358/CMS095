/*
	This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc> 
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

/* Lakelis
 * 
 * Victoria Road: Kerning City (103000000)
 * 
 * Kerning City Party Quest NPC 
*/

var status = 0;
var minLevel = 119;
var maxLevel = 250;
var minPlayers = 1;
var maxPlayers = 1;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
        if (cm.getMapId() == 540010001) {
		if (status == 0) {
			// Lakelis has no preamble, directly checks if you're in a party
			if (cm.getParty() == null) { // no party
				cm.sendOk("每天都可以参加一次航空副本,奖励是大量的枫叶哦,还有小概率获得海量金币，规定的时间内击败BOSS必得1-3的黄金枫叶,在自由市场左下的牌子兑换若干枫叶,机会只有一次！决定好的话组队跟我说吧。注意这是单人副本哦。#rPS:要求等级120以上");
				cm.dispose();
                                return;
			}
			if (!cm.isLeader()) { // not party leader
				cm.sendSimple("如果你准备好了, 那么请 #b你的组长#k 来告诉我！");
				cm.dispose();


						
                        }
			else {
				// check if all party members are within 21-200 range, etc.
				var party = cm.getParty().getMembers();
				var mapId = cm.getChar().getMapId();
				var next = true;
				var levelValid = 0;
				var inMap = 0;
				// Temp removal for testing
				for (var i = 0; i < party.size(); i++) {
					if ((party.get(i).getLevel() >= minLevel) && (party.get(i).getLevel() <= maxLevel)) {
						levelValid += 1;
					}
					if (party.get(i).getMapid() == mapId) {
						inMap += 1;
					}
				}
				if (party.size() < minPlayers || party.size() > maxPlayers) 
					next = false;
				else if (levelValid < minPlayers || inMap < minPlayers) {
						next = false;
				}
			
				if (next) {
					// Kick it into action.  Lakelis says nothing here, just warps you in.
					var em = cm.getEventManager("ZChaosPQ2");
					if (em == null) {
						cm.sendOk("副本还未开通,敬请期待。");
					}
					else	if (cm.getPlayerCount(540010101) > 0){
	            cm.sendOk("已经有人在做了，换线试试.");
                cm.dispose();
				}
					else if (cm.getBossLogD("航空") > 1){
				cm.sendOk("该活动每天可以进入2次，你今天已经进入过2次了！")
				cm.dispose();
				}
				
					else {
						// Begin the PQ.
						em.startInstance(cm.getParty(),cm.getChar().getMap());
						// Remove pass/coupons
						party = cm.getChar().getEventInstance().getPlayers();
						cm.setPartyBossLog("航空");
					}
					cm.dispose();
				}

				 else {
					cm.sendOk("请确认你所在组队成员是否全部在线，再来接受这个挑战任务.  目前有 #b" + levelValid.toString() + " #k成员不在任务规定的等级范围, and #b" + inMap.toString() + "#k are in Kerning. If this seems wrong, #blog out and log back in,#k or reform the party.");
					cm.dispose();
				}
			}
		}
	} else {
		 var pt = cm.getEventManager("ZChaosPQ2");
		 var times = pt.getInstance("ZChaosPQ2").getTimeLeft();
	  	if(times < (1000 * 60 * 28)){
		cm.warp(540010001);
                               
				 cm.worldMessage(5, "玩家" + cm.getPlayer().getName() + "完成了每日航空活动！");
		} else {
                cm.sendOk("至少要经过两分钟你才能找我结束航空旅行.");
            }
		cm.dispose();
		}
	}
}
					