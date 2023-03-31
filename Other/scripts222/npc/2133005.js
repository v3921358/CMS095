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
var minLevel = 51;
var maxLevel = 250;
var minPlayers = 1;
var maxPlayers = 6;

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
		if (status == 0) {
			// Lakelis has no preamble, directly checks if you're in a party
			if (cm.getParty() == null) { // no party
				cm.sendOk("前面是妖精女王的住处了！ 如果你想净化女王，那么请 #b你的组长#k 来告诉我！");
				cm.dispose();
				return;
			}
			if (!cm.isLeader()) { // not party leader
				cm.sendSimple("如果你想挑战一下自己, 那么请 #b你的组长#k 来告诉我！");
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
					var em = cm.getEventManager("BossFairyQueen");
					if (em == null) {
						cm.sendOk("这个PQ不是正确的答案！");
					}
					else if (cm.getPlayerCount(300030310) > 0) {
						cm.sendOk("已经有人在做了，换线试试.");
						cm.dispose();
					}
					else if (cm.getBossLogD("蝴蝶精") > 2) {
						cm.sendOk("该活动每天可以进入3次，你今天已经进入过3次了！")
						cm.dispose();
					}
					else {
						// Begin the PQ.
						em.startInstance(cm.getParty(), cm.getChar().getMap());
						// Remove pass/coupons
						party = cm.getChar().getEventInstance().getPlayers();
						cm.setPartyBossLog("蝴蝶精");
					}
					cm.dispose();
				}
				else {
					cm.sendOk("请确认你所在组队成员是否全部在线，再来接受这个挑战任务.  目前有 #b" + levelValid.toString() + " #k成员不在任务规定的等级范围, and #b" + inMap.toString() + "#k are in Kerning. If this seems wrong, #blog out and log back in,#k or reform the party.");
					cm.dispose();
				}
			}
		}
		else {
			cm.sendOk("对话结束.");
			cm.dispose();
		}
	}
}
