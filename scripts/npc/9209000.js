﻿/*
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

status = -1;
var sel;
var pickup = -1;

function start() {
    cm.sendSimple("我是亞都蘭 是一位仲介商人 需要我帮忙什么??#b\r\n#L0#我想賣一些不错的貨.\r\n#L1#我想了解一下目前的市场價格.\r\n#L2#一个商人中介？是什么？");
}

function action(mode, type, selection) {
    status++;
    if(mode != 1){
        if(mode == 0 && status == 0){
            cm.dispose();
            return;
        }else if(mode == 0 && sel == 0 && status == 2){
            cm.sendNext("你不想馬上把它賣掉？你可以以后再賣掉，但要記住的特殊项目僅一个星期有價值...");
            cm.dispose();
            return;
        }else if(mode == 0 && sel == 2)
            status -= 2;
    }
    if(status == 0){
        if(sel == undefined)
            sel = selection;
        if (selection == 0){
            var text = "让我看看你带來了什么貨...#b";
            for(var i = 0; i < 5; i++)
                text += "\r\n#L" + i + "##t" + (3994090 + i) + "#";
            cm.sendSimple(text);
        }else if (selection == 1){
            var text = "";
            for(var i = 0; i < 5; i++)
                text += "目前的市场價格为 #t" + (i + 3994090) + "# 是 #r180#k 的 金币\r\n";
            cm.sendNext(text);
            cm.dispose();
        }else
            cm.sendNext("我购买的產品在枫第七天市场和其他城鎮賣給他们。我換紀念品，香料，动物標本鯊魚，还有更多...但沒有懶惰Daisy的蛋.");
    }else if(status == 1){
        if(sel == 0){
            if(cm.haveItem(3994090 + selection)){
                pickup = 3994090 + selection;
                cm.sendYesNo("目前的價格为180 金币。你想现在把它賣掉？"); //Make a price changer by hour.
            }else{
                cm.sendNext("你沒有任何東西。別再浪費我的时间......我是一个忙碌的人。");
                cm.dispose();
            }
        }else
            cm.sendNextPrev("枫葉7日星期日市场是我的休息日。如果你需要看我，你将不得不前來周一至周五...");
    }else if(status == 2){
        if(sel == 0)
            cm.sendGetNumber("你想要賣多少个?", 0, 0, 200);
        else{
            cm.sendPrev("哦，價格也可能发生变化。我不能让棒的短端，我要留在企業！检查回來我頻繁，我的價格变化按小时!");
        }
    }else if(status == 3){
        if(sel == 0)
            if(selection != 1)
                cm.sendNext("交易數量是不对的。请再检查一遍.");
            else{
                cm.sendNext("交易已经完成。下次見.");
                cm.gainMeso(180);
                cm.gainItem(pickup, -1);
            }
        cm.dispose();
    }
}