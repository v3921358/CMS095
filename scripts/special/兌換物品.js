

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple(
            "#b☆★☆★☆★☆★☆★各式活动區☆★☆★☆★☆★☆★#l\r\n" +
            "\r\n" +
            "#r#L13#◆每日任務◇#l #r#L19#◆金币副本◇#l #r#L6#◆枫葉之心◇#l\r\n" +
            "#r#L18#◆雪花兌換◇#l #r#L12#◆水晶活动◇#l\r\n" +
            "\r\n\r\n" +
            "#b☆★☆★☆★☆★☆★奖励兌換區☆★☆★☆★☆★☆★#l\r\n" +
            "\r\n" +
            "#r#L11#◆等級奖励◇#l #r#L9#◆贊助奖励◇#l #r#L16#◆月卡奖励◇#l\r\n" +
            "#r#L7#◆洗血戒指◇#l #r#L2#◆獅城兌換◇#l #r#L8#◆打王兌換◇#l\r\n" +
            "#r#L1#◆枫葉兌換◇#l #r#L4#◆职业坐騎◇#l " + /*"#r#L20#◆聖誕礼包◇#l"+*/"\r\n" +
            "\r\n\r\n" +
            "#b☆★☆★☆★☆★☆★商品购买區☆★☆★☆★☆★☆★#l\r\n" +
            "\r\n" +
            "#r#L14#◆GASH商店◇#l #r#L15#◆紅利商店◇#l #r#L0#◆三宠技能◇#l\r\n" +
            "#r#L3#◆变身秘药◇#l #r#L5#◆領取D+片◇#l #r#L10#◆萬能洗血◇#l\r\n" +
            //"#r#L17#◆潛能方塊◇#l \r\n" +
            "\r\n\r\n" +
            "#b☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆#l\r\n"
            /*"#r#L0#三宠技能#l #r#L1#枫葉兌換#l\r\n" +
             "#r#L2#獅子王城#l #r#L3#变身祕药#l\r\n" +
             "#r#L4#职业坐騎#l #r#L5#領取D片#l\r\n" +
             "#r#L6#枫葉之心#l #r#L7#洗血智戒#l\r\n" +
             "#r#L8#打王兌換#l #r#L9#贊助奖励#l\r\n" +
             "#r#L10#萬能洗血#l #r#L11#等級奖励#l\r\n" +
             "#r#L12#水晶兌換#l #r#L13#每日任務#l\r\n" +
             "#r#L14#现金商店#l #r#L15#紅利點數#l\r\n" +
             "#r#L16#紅利月卡#l"*/);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    var level = cm.getPlayer().getLevel();
    if (level < 10) {
        cm.sendOk("少于10等无法使用拍賣功能。");
        cm.dispose();
        return;
    }
    sel = selection;
    if (sel == 0) {
        if (cm.haveItem(5460000)) {
            cm.gainItem(5460000, -1);
            cm.teachSkill(8, 1, 0);
            cm.teachSkill(10000018, 1, 0); // Maker
            cm.teachSkill(20000024, 1, 0); // Maker
            cm.teachSkill(20011024, 1, 0); // Maker
            cm.teachSkill(30001024, 1, 0); // Maker
            cm.sendOk("學習成功。");
            cm.dispose();
            return;
        } else {
            cm.sendOk("你沒有#t5460000##i5460000#。");
            cm.dispose();
        }
    } else if (sel == 1) {
        openNpc(9330012);
    } else if (sel == 2) {
        openNpc(9010000, "獅子王城");
    } else if (sel == 3) {
        cm.gainItem(2210003, 1);
        cm.sendOk("領取成功。");
        cm.dispose();
        return;
    } else if (sel == 4) {
        openNpc(9010000, "职业坐騎");
    } else if (sel == 5) {
        if (!cm.canHoldByType(4, 1)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getBossLogD("D片兌換") >= 1) {
            cm.sendOk("你今天已经換过了");
            cm.dispose();
            return;
        }
        if (cm.haveItem(4031172)) {
            cm.getPlayer().setBossLog("D片兌換");
            cm.gainItem(4031179, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else {
            cm.sendOk("你沒有#t4031172##i4031172#。");
            cm.dispose();
            return;
        }
    } else if (sel == 6) {
        openNpc(9010000, "枫葉之心");
    } else if (sel == 7) {
        openNpc(9010000, "洗血智戒");
    } else if (sel == 8) {
        openNpc(9010000, "打王兌換");
    } else if (sel == 9) {
        openNpc(9010000, "贊助奖励");
    } else if (sel == 10) {
        openNpc(9010000, "萬能洗血");
    } else if (sel == 11) {
        openNpc(9010000, "等級奖励");
    } else if (sel == 12) {
        openNpc(9010000, "水晶兌換");
    } else if (sel == 13) {
        openNpc(9010000, "每日任務");
    } else if (sel == 14) {
        openNpc(9010000, "现金商店");
    } else if (sel == 15) {
        openNpc(9010000, "紅利點數");
    } else if (sel == 16) {
        openNpc(9010000, "月卡奖励");
        //} else if (sel == 17) {
        //    openNpc(9010000, "潛能方塊");
    } else if (sel == 18) {
        openNpc(9010000, "雪花兌換");
    } else if (sel == 19) {
        openNpc(9010000, "金币副本");
    } else if (sel == 20) {
        openNpc(9010000, "聖誕礼包");
    } else {

        cm.sendOk("此功能未完成");
        cm.dispose();
        return;
    }

}

function openNpc(npcid) {
    openNpc(npcid, null);
}

function openNpc(npcid, script) {
    var mapid = cm.getMapId();
    cm.dispose();
    if (cm.getPlayerStat("LVL") < 10) {
        cm.sendOk("你的等級不能小于10等.");
    } else if (
            cm.hasSquadByMap() ||
            cm.hasEventInstance() ||
            cm.hasEMByMap() ||
            mapid >= 990000000 ||
            (mapid >= 680000210 && mapid <= 680000502) ||
            (mapid / 1000 === 980000 && mapid !== 980000000) ||
            mapid / 100 === 1030008 ||
            mapid / 100 === 922010 ||
            mapid / 10 === 13003000
            ) {
        cm.sendOk("你不能在这里使用这个功能.");
    } else {
        if (script == null) {
            cm.openNpc(npcid);
        } else {
            cm.openNpc(npcid, script);
        }
    }
}
