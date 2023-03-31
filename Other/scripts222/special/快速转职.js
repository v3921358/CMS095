var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0) {
        cm.dispose();
		
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
        cm.dispose();
		cm.openNpc(9900004);
    }
    if (status == 0) {
            var text = "";
        for (i = 0; i < 10; i++) {
            text += "";
        }
        text +="#b" + cm.getChar().getName() + " #k欢迎来到eV.095冒险岛，领取奖励后！在转职！伟大的航行，就此拉开序幕··\r\n"
        if(cm.getJob() == 0 && cm.getPlayer().getLevel() >= 8 && cm.getPlayer().getLevel() <= 9){
        text +="#b#L200#我要转职[魔法师]\r\n"
		text +="#r#L10001#新人奖励\r\n"
        }else if(cm.getJob() == 0){
        text +="#b#L100#我要转职[战士]\r\n"
        text +="#b#L200#我要转职[魔法师]\r\n"
        text +="#b#L300#我要转职[弓箭手]\r\n"
        text +="#b#L400#我要转职[飞侠]\r\n"
        text +="#b#L500#我要转职[海盗]\r\n"
		text +="#b#L4000#我要转职[暗影双刀]\r\n"
		text +="#r#L10001#新人奖励\r\n"
        }else if(cm.getJob() == 1000){
        text +="#b#L1100#我要转职[魂骑士]\r\n"
        text +="#b#L1200#我要转职[炎术士]\r\n"
        text +="#b#L1300#我要转职[风灵使者]\r\n"
        text +="#b#L1400#我要转职[夜行者]\r\n"
        text +="#b#L1500#我要转职[奇袭者]\r\n"
		text +="#r#L10001#新人奖励\r\n"
         }else if(cm.getJob() == 1100 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L1110#进行二转\r\n"
        }else if(cm.getJob() == 1110 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L1111#进行三转\r\n"
        }else if(cm.getJob() == 1200 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L1210#进行二转\r\n"
        }else if(cm.getJob() == 1210 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L1211#进行三转\r\n"
        }else if(cm.getJob() == 1300 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L1310#进行二转\r\n"
        }else if(cm.getJob() == 1310 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L1311#进行三转\r\n"
        }else if(cm.getJob() == 1400 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L1410#进行二转\r\n"
        }else if(cm.getJob() == 1410 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L1411#进行三转\r\n"
        }else if(cm.getJob() == 1500 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L1510#进行二转\r\n"
        }else if(cm.getJob() == 1510 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L1511#进行三转\r\n"
        }else if(cm.getJob() == 3000){
         text +="#b#L3200#我要转职[幻灵斗师]\r\n"
         text +="#b#L3300#我要转职[弩豹游侠]\r\n"
         text +="#b#L3500#我要转职[机械师]\r\n"
		 text +="#r#L10001#新人奖励\r\n"
        }else if(cm.getJob() == 3200 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L3210#进行二转\r\n"
        }else if(cm.getJob() == 3210 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L3211#进行三转\r\n"
        }else if(cm.getJob() == 3211 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L3212#进行四转\r\n"
        }else if(cm.getJob() == 3300 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L3310#进行二转\r\n"
        }else if(cm.getJob() == 3310 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L3311#进行三转\r\n"
        }else if(cm.getJob() == 3311 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L3312#进行四转\r\n"
        }else if(cm.getJob() == 3500 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L3510#进行二转\r\n"
        }else if(cm.getJob() == 3510 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L3511#进行三转\r\n"
        }else if(cm.getJob() == 3511 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L3512#进行四转\r\n"
        }else if(cm.getJob() == 100 /*&& cm.getJob() <= 132*/){
        text +="#b#L110#我要转职[剑客]\r\n"
        text +="#b#L120#我要转职[准骑士]\r\n"
        text +="#b#L130#我要转职[枪战士]\r\n"
        }else if(cm.getJob() == 110 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L111#进行三转\r\n"
        }else if(cm.getJob() == 111 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L112#进行四转\r\n"
        }else if(cm.getJob() == 120 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L121#进行三转\r\n"
        }else if(cm.getJob() == 121 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L122#进行四转\r\n"
        }else if(cm.getJob() == 130 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L131#进行三转\r\n"
        }else if(cm.getJob() == 131 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L132#进行四转\r\n"
        }else if(cm.getJob() == 200 /*&& cm.getJob() <= 232*/){
        text +="#b#L210#我要转职[火毒法师]\r\n"
        text +="#b#L220#我要转职[冰雷法师]\r\n"
        text +="#b#L230#我要转职[牧师]\r\n"
        }else if(cm.getJob() == 210 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L211#进行三转\r\n"
        }else if(cm.getJob() == 211 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L212#进行四转\r\n"
        }else if(cm.getJob() == 220 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L221#进行三转\r\n"
        }else if(cm.getJob() == 221 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L222#进行四转\r\n"
        }else if(cm.getJob() == 230 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L231#进行三转\r\n"
        }else if(cm.getJob() == 231 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L232#进行四转\r\n"
        }else if(cm.getJob() == 300 /*&& cm.getJob() <= 322*/){
        text +="#b#L310#我要转职[猎人]\r\n"
        text +="#b#L320#我要转职[弩弓手]\r\n"
        }else if(cm.getJob() == 310 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L311#进行三转\r\n"
        }else if(cm.getJob() == 311 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L312#进行四转\r\n"
        }else if(cm.getJob() == 320 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L321#进行三转\r\n"
        }else if(cm.getJob() == 321 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L322#进行四转\r\n"
        }else if(cm.getJob() == 400 && cm.getPlayer().getLevel() >= 20 && cm.getPlayer().getLevel() < 30){
        text +="#b#L430#我要转职[暗影双刀]\r\n"
        }else if(cm.getJob() == 430 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L431#进行转职\r\n"
        }else if(cm.getJob() == 431 && cm.getPlayer().getLevel() >= 50){
        text +="#b#L432#进行转职\r\n"
        }else if(cm.getJob() == 432 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L433#进行转职\r\n"
        }else if(cm.getJob() == 433 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L434#进行转职\r\n"
        }else if(cm.getJob() == 400/** && cm.getJob() <= 422*/){
        text +="#b#L410#我要转职[刺客]\r\n"
        text +="#b#L420#我要转职[侠客]\r\n"
        }else if(cm.getJob() == 410 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L411#进行三转\r\n"
        }else if(cm.getJob() == 411 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L412#进行四转\r\n"
        }else if(cm.getJob() == 420 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L421#进行三转\r\n"
        }else if(cm.getJob() == 421 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L422#进行四转\r\n"
        }else if(cm.getJob() == 500 && cm.getPlayer().getLevel() >= 30){
        text +="#b#L510#我要转职[拳手]\r\n"
        text +="#b#L520#我要转职[火枪手]\r\n"
        }else if(cm.getJob() == 510 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L511#进行三转\r\n"
        }else if(cm.getJob() == 511 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L512#进行四转\r\n"
        }else if(cm.getJob() == 520 && cm.getPlayer().getLevel() >= 70){
        text +="#b#L521#进行三转\r\n"
        }else if(cm.getJob() == 521 && cm.getPlayer().getLevel() >= 120){
        text +="#b#L522#进行四转\r\n"
        }else {
        cm.sendOk("#r你还不满足下一次转职条件,或者已经不需要转职了！！！\r\n如果是战神/龙神会自动转职....");
		cm.dispose();
        }
        cm.sendSimple(text);
    } else if (status == 1) {
        switch (selection) {
			case 431:
                cm.changeJob(431);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 10001:
                    cm.dispose();
                    cm.openNpc(9900004, "新人礼包");
                    break;
			case 432:
                cm.changeJob(432);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 433:
                cm.changeJob(433);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 434:
                cm.changeJob(434);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3512:
                cm.changeJob(3512);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3511:
                cm.changeJob(3511);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3510:
                cm.changeJob(3510);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3312:
                cm.changeJob(3312);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3311:
                cm.changeJob(3311);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3310:
                cm.changeJob(3310);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3212:
                cm.changeJob(3212);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3211:
                cm.changeJob(3211);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 3210:
                cm.changeJob(3210);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1510:
                cm.changeJob(1510);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1511:
                cm.changeJob(1511);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1410:
                cm.changeJob(1410);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1411:
                cm.changeJob(1411);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1310:
                cm.changeJob(1310);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1311:
                cm.changeJob(1311);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1210:
                cm.changeJob(1210);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1211:
                cm.changeJob(1211);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1110:
                cm.changeJob(1110);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 1111:
                cm.changeJob(1111);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 521:
                cm.changeJob(521);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 522:
                cm.changeJob(522);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 511:
                cm.changeJob(511);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 512:
                cm.changeJob(512);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 500:
                cm.changeJob(500);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
			case 510:
                cm.changeJob(510);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 520:
                cm.changeJob(520);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 421:
                cm.changeJob(421);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 422:
                cm.changeJob(422);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 411:
                cm.changeJob(411);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 412:
                cm.changeJob(412);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 321:
                cm.changeJob(321);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 322:
                cm.changeJob(322);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 311:
                cm.changeJob(311);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 312:
                cm.changeJob(312);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 231:
                cm.changeJob(231);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 232:
                cm.changeJob(232);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 221:
                cm.changeJob(221);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 222:
                cm.changeJob(222);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 211:
                cm.changeJob(211);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 212:
                cm.changeJob(212);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 132:
                cm.changeJob(132);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 131:
                cm.changeJob(131);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 122:
                cm.changeJob(122);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 121:
                cm.changeJob(121);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
			case 112:
                cm.changeJob(112);
                cm.sendOk("转职成功.");
            cm.dispose();
            break;
		case 111:
                cm.changeJob(111);
                cm.sendOk("转职成功.");
				cm.dispose();
            break;
        case 100:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(100);
                cm.sendOk("系统已经为您转职为战士.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 200:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 8) {
                cm.changeJob(200);
                cm.sendOk("系统已经为您转职为魔法师.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到8.");
            }
            cm.dispose();
            break;
        case 300:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(300);
                cm.sendOk("系统已经为您转职为弓箭手.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 400:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(400);
                cm.sendOk("系统已经为您转职为飞侠.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
		     case 4000:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(400);
                cm.sendOk("系统先为您转职为飞侠.等到20级在呼出我转成为暗影双刀  #r切记超出20级将无法转职为暗影双刀");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 509:
            if (cm.getJob() == 0 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(509);
                cm.sendOk("系统已经为您转职为海盗.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 1100:
            if (cm.getJob() == 1000 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(1100);
                cm.sendOk("系统已经为您转职为魂骑士.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 1200:
            if (cm.getJob() == 1000 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(1200);
                cm.sendOk("系统已经为您转职为炎术士.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 1300:
            if (cm.getJob() == 1000 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(1300);
                cm.sendOk("系统已经为您转职为风灵使者.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 1400:
            if (cm.getJob() == 1000 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(1400);
                cm.sendOk("系统已经为您转职为夜行者.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 1500:
            if (cm.getJob() == 1000 && cm.getPlayer().getLevel() >= 10) {
                cm.changeJob(1500);
                cm.sendOk("系统已经为您转职为奇袭者.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 3200:
            if (cm.getJob() == 3000 && cm.getPlayer().getLevel() >= 10) {
                 cm.changeJob(3200);
                cm.sendOk("系统已经为您转职为幻灵斗师.\r\n以后的转职都为自动转职.");//
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 3300:
            if (cm.getJob() == 3000 && cm.getPlayer().getLevel() >= 10) {
                 cm.changeJob(3300);
                cm.sendOk("系统已经为您转职为弩豹游侠.\r\n以后的转职都为自动转职.");//
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 3500:
            if (cm.getJob() == 3000 && cm.getPlayer().getLevel() >= 10) {
                 cm.changeJob(3500);
                cm.sendOk("机械师\r\n以后的转职都为自动转职.");//机械师
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
            
        case 110:
            if (cm.getJob() == 100 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(110);
                cm.sendOk("系统已经为您转职为剑客.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 120:
            if (cm.getJob() == 100 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(120);
                cm.sendOk("系统已经为您转职为准骑士.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 130:
            if (cm.getJob() == 100 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(130);
                cm.sendOk("系统已经为您转职为枪战士.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
            
        case 210:
            if (cm.getJob() == 200 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(210);
                cm.sendOk("系统已经为您转职为火毒法师.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 220:
            if (cm.getJob() == 200 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(220);
                cm.sendOk("系统已经为您转职为准冰雷法师.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 230:
            if (cm.getJob() == 200 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(230);
                cm.sendOk("系统已经为您转职为牧师.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
            
        case 310:
            if (cm.getJob() == 300 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(310);
                cm.sendOk("系统已经为您转职为准猎人.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 320:
            if (cm.getJob() == 300 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(320);
                cm.sendOk("系统已经为您转职为弩弓手.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
            
        case 410:
            if (cm.getJob() == 400 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(410);
                cm.sendOk("系统已经为您转职为刺客.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 420:
            if (cm.getJob() == 400 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(420);
                cm.sendOk("系统已经为您转职为隐士.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
            
        case 580:
            if (cm.getJob() == 509 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(580);
                cm.sendOk("系统已经为您转职为拳手.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 590:
            if (cm.getJob() == 509 && cm.getPlayer().getLevel() >= 30) {
                cm.changeJob(590);
                cm.sendOk("系统已经为您转职为火枪手.\r\n以后的转职都为自动转职.");
            } else {
                cm.sendOk("你不是新手职业 或 你的等级没有达到10.");
            }
            cm.dispose();
            break;
        case 430:
            if (cm.getJob() == 400 && cm.getPlayer().getLevel() >= 20 && cm.getPlayer().getLevel() < 30) {
                cm.changeJob(430);
				cm.getPlayer().gainSP(30);
				cm.getPlayer().equipChanged();
				cm.fakeRelog();
            } else {
                cm.sendOk("你不是飞侠(一转)职业 或 你的等级没有达到20.(双刀一转请转飞侠)");
            }
            cm.dispose();
            break;
        case 7:
            cm.dispose();
            cm.openNpc(9300011, 4);
            break;
        case 8:
            cm.dispose();
            cm.openNpc(9300011, 5);
            break;
        }
    }
}