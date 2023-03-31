/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as
 published by the Free Software Foundation version 3 as published by
 the Free Software Foundation. You may not use, modify or distribute
 this program under any other version of the GNU Affero General Public
 License.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.
 
 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
        /* Ms. Tan
         Henesys Skin Change.
         */
        var status = 0;
        var skin = Array(0, 1, 2, 3, 4);
        var price;
        function start() {
        cm.sendSimple("欢迎來到弓箭手村护髮中心! 是否有想要染的皮膚呢?? 就像我的健康皮膚??  如果你有 #b#t5153000##k, 就可以隨意染的想到的皮膚~~~\r\n\#L2#我已经有一个優惠券!#l");
                }

function action(mode, type, selection) {
if (mode < 1)
        cm.dispose();
        else {
        status++;
                if (status == 1)
                cm.sendStyle("選一个想要的風格.", skin, 5153000);
                else {
                if (cm.haveItem(5153000)){
                cm.gainItem(5153000, - 1);
                        cm.setSkin(selection);
                        cm.sendOk("享受!");
                } else
                        cm.sendOk("您貌似沒有#b#t5153000##k..");
                        cm.dispose();
                }
        }
}
