load(ServerConstants.SCRIPT_PAH+"/"+utils/db_functions.js");
var status = 0;
var beauty = 0;
var arr = 0;
var hairprice = 1000000;
var haircolorprice = 1000000;
var haircard = 5150040;
var facecard = 5152053;
var useitem=4002003;

//32020,33430,
//32120, 32130, 32140, 32150, 32160, 32440, 32450, 32460, 32470, 32480,
//32490, 32500, 32510, 32560, 32580, 32640, 32650, 32660, 35000, 35010, 
//35020, 35030, 35040, 35050, 35070, 35090, 35100, 35110, 35120, 35130, 
//35150, 35160, 35170, 35180, 35190, 35200, 35210, 35220, 35230, 35240, 
//35250, 35260, 35270, 35290, 35310, 35320, 35330, 35340, 35350, 35360,
//35370, 35380, 35390, 35400, 35410, 35420, 35440, 35450, 35480, 35490, 
//35530, 35540, 35570, 35580, 35610, 35650, 35660, 35680, 35700, 35720,
//35740, 35770, 35800, 35850, 35870, 35880, 35920, 35940, 35970, 36000,
//36030, 36050, 36080, 36120, 36800, 36830, 36860, 36880, 36950, 36990




//35850, 35870, 35880, 35920, 35940, 35970, 
var mhair = Array(
    31650, 35530, 35540, 35570, 35700, 35720,
    35250, 35260, 35270, 35290, 35310, 35320, 35330, 35340, 35350, 35360,
    35150, 35160, 35170, 35180, 35190, 35200, 35210, 35220, 35230, 35240,
    35000, 35010, 35020, 35030, 35040, 35050, 35070, 35090, 35100, 35110, 35120, 35130,
    33030, 33050, 33070, 33080, 33090, 33100, 33110, 33120, 33130,
    33140, 33150, 33160, 33170, 33180, 33190, 33200, 33210, 33220, 33230,
    33240, 33250, 33260, 33300, 33310, 33320, 33340, 33350, 33360,
    33370, 33380, 33390, 33440, 33470, 33480, 30420, 30440, 30450, 30460,
    30470, 30480, 30490, 30510, 30520, 30530, 30540, 30550, 30560, 30600,
    30610, 30620, 30630, 30640, 30650, 30660, 30670, 30680, 30700, 30710,
    30720, 30730, 30740, 30750, 30790, 30810, 30820, 30840, 30850, 30860,
    30870, 30880, 30890, 30900, 30910, 30920, 30930, 30940, 30950

    //	32020,33430,	32440, 32450, 32460, 32470, 32480,, 32560, 32580,,32640, 32650, 32660, 
    //32490,
    //	32120, 32130, 32140, 32150, 32160, 32440, 32450, 32460, 32470, 32480,
    //32490, 32500, 32510, 32560, 32580, 32640, 32650, 32660, 
    //35000, 35010, 

    //35020, 35030, 35040, 35050, 35070, 35090, 35100, 35110, 35120, 35130, 
    //35150, 35160, 35170, 35180, 35190, 35200, 35210, 35220, 35230, 35240, 
    //35250, 35260, 35270, 35290, 35310, 35320, 35330, 35340, 35350, 35360,
    //35370, 35380, 35390, 35400, 35410, 35420, 35440, 35450, 35480, 35490, 
    //35530, 35540, 35570, 35580, 35610, 35650, 35660, 35680, 35700, 35720,
    //35740, 35770, 35800, 36000,
    //	36030, 36050, 36080, 36120, 36800, 36830, 36860, 36880, 36950, 36990
);
var fhair = Array(

    37000, 37050, 34270,  37130,
    32120, 32130, 32140, 32150, 32160, 32430, 32440, 32470, 32490, 32500,
    32510, 33000,  38610, 38760,
    37200, 37240, 37250, 37260, 37270, 37280, 37320, 37330, 37340,
    37350, 37370, 37380, 37400, 37410, 37420, 37430, 37440, 37450, 37460,
    37490, 37500, 37510, 37520, 37530, 37540, 37550, 37560, 37580, 37600,
    37610, 37620, 37630, 37640, 37650, 37660, 37670, 37680, 37690, 37700,
    37710, 37720, 37730, 37740, 37750, 37760, 37770, 37930, 39100, 39130, 39140, 39160,
    34000, 34010, 34040, 34050, 34060, 34070, 34090, 34100, 34110, 34120,
    34130, 34140, 34150, 34160, 34170, 34180, 34190, 34200, 34210, 34220,
    34230, 34240, 34250, 34260, 31450, 31460, 31470, 31480, 31490, 31510,
    31520, 31530, 31540, 31550, 31560, 31590, 31610, 31620, 31630, 31640,
    31650, 31670, 31680, 31690, 31710, 31720, 31730, 31770, 31990, 31950,
    31790, 31800, 31820, 31830, 31840, 31850, 31860, 31870, 31880, 31890,
    31900, 31910, 31920, 31930, 31940
);

//39170, 39220, 39230, 39260, 
//39280, 39300, 39330, 39350, 39360, 39390, 39410, 39440, 39470, 39490, 
//39510, 39550, 39600, 39620, 39640, 39660, 39670, 39680, 39690, 39720, 
//39750, 39760, 39780, 39810, 39860, 39880, 39920, 39940, 39960, 39970, 
//39980, 39990, 

//	38390, 38760, 
//38770, 38790, 38800, 38820, 38840, 38860, 38880, 38910, 38930, 38960, 
//38980, 38990,


//	37000, 37050, 34270, 34670, 37130,
//	34450, 37200, 37240, 37250, 37260, 37270, 37280, 37320, 37330, 37340,
//37350, 37370, 37380, 37400, 37410, 37420, 37430, 37440, 37450, 37460,
//37490, 37500, 37510, 37520, 37530, 37540, 37550, 37560, 37580, 37600, 
//37610, 37620, 37630, 37640, 37650, 37660, 37670, 37680, 37690, 37700,
//37710, 37720, 37730, 37740, 37750, 37760, 37770, 37930, 38390, 38760, 
//38770, 38790, 38800, 38820, 38840, 38860, 38880, 38910, 38930, 38960, 
//38980, 38990
//, 39100, 39130, 39140, 39160, 39170, 39220, 39230, 39260, 
//39280, 39300, 39330, 39350, 39360, 39390, 39410, 39440, 39470, 39490, 
//39510, 39550, 39600, 39620, 39640, 39660, 39670, 39680, 39690, 39720, 
//39750, 39760, 39780, 39810, 39860, 39880, 39920, 39940, 39960, 39970, 
//39980, 39990 

var mface = Array(
    20020, 20021, 20022, 20023, 20025, 20026, 20027, 20028, 20029, 20030,
    20031, 20032, 20033, 20035, 20036, 20037, 20043, 20044, 20045, 20046,
    20047

);
var fface = Array(
    21020, 21021, 21022, 21023, 21025, 21026, 21027, 21028, 21029, 21030,
    21031, 21033, 21034, 21035, 21041, 21042, 21043, 21044, 21045, 24071,
    24072

);
var hairnew = Array();
var haircolor = Array();
var hair_Colo_new = Array();
var facenew = Array();
var face_Colo_new = Array()
var 原始头发 = 0;
var 原始脸型 = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status <= 0 && mode <= 0) {
        cm.dispose();
        return
    }
    if (mode <= 0 && selection == -1 && status == 2) {
        cm.dispose();
        return
    }
    if (mode == 1) {
        status++
    } else {
        status--
    }


    if (status == 0)
        cm.sendSimple("我是皇家负责人爱德华。就让我帮你美容美发吧。请选择。\r\n#L1##b随机理发(无性别)预览（每次内容不同）#k#l\r\n#L2##b随机整容(无性别)预览（每次内容不同）#k#l\r\n#L3##r皇家随机理发#k#l\r\n#L4##r皇家随机整容#k#l\r\n#L5##r代码自选发型#k#l\r\n#L6##r代码自选脸型#k#l");
    else if (status == 1) {
        if (selection == 7) {
            beauty = 7;
            cm.sendGetText("请输入预览代码区间例：#b30000,310000  #k\r\n\r\n");
        } 
        if (selection == 1) {
            beauty = 1;
            hairnew = getRundomCodeList(50,1);
            // if (cm.getPlayer().getGender() == 0)
            //     for (var i = 0; i < mhair.length; i++)
            //         hairnew.push(mhair[i] + parseInt(cm.getPlayer().getHair() % 10));
            // if (cm.getPlayer().getGender() == 1)
            //     for (var i = 0; i < fhair.length; i++)
            //         hairnew.push(fhair[i] + parseInt(cm.getPlayer().getHair() % 10));
            cm.sendStyle("预览一个想要的发型..", hairnew, 5150040);
        } else if (selection == 2) {
            beauty = 2;
            facenew = getRundomCodeList(50,2);
            // if (cm.getPlayer().getGender() == 0) {
            //     for (var i = 0; i < mface.length; i++)
            //         facenew.push(mface[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100));
            // }
            // if (cm.getPlayer().getGender() == 1) {
            //     for (var i = 0; i < fface.length; i++)
            //         facenew.push(fface[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100));
            // }
            cm.sendStyle("预览一个想要的脸型..", facenew, 5150040);
        } else if (selection == 3) {
            beauty = 3;

            hair_Colo_new = Array();

            if (cm.getPlayer().getGender() == 0) {
                for (var i = 0; i < mhair.length; i++) {
                    hair_Colo_new.push(mhair[i] + parseInt(cm.getPlayer().getHair() % 10));
                }
            }
            if (cm.getPlayer().getGender() == 1) {
                for (var i = 0; i < fhair.length; i++) {
                    hair_Colo_new.push(fhair[i] + parseInt(cm.getPlayer().getHair() % 10));
                }
            }
            cm.sendYesNo("确定要使用 #b#t5150040##k 随机理发了？？");

        } else if (selection == 4) {
            beauty = 4;
            face_Colo_new = Array();

            if (cm.getPlayer().getGender() == 0) {
                for (var i = 0; i < mface.length; i++) {
                    face_Colo_new.push(mface[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100));
                }
            }
            if (cm.getPlayer().getGender() == 1) {
                for (var i = 0; i < fface.length; i++) {
                    face_Colo_new.push(fface[i] + cm.getPlayer().getFace() % 1000 - (cm.getPlayer().getFace() % 100));
                }
            }
            cm.sendYesNo("确定要使用 #b#t5152053##k 随机整容了？？");
        } else if (selection == 5) {
            beauty = 5;
            cm.sendGetText("你想花费5张"+"#v"+useitem+"##z"+useitem+"##l自选新“发”型码？\r\n #b（请输入发型ID[注：5位代码必须以3开头，0结尾！]）#k\r\n\r\n");
        } else if (selection == 6) {
            beauty = 6;
            cm.sendGetText("你想花费5张"+"#v"+useitem+"##z"+useitem+"##l自选新“脸”型码？\r\n #b（请输入脸型ID[注：5位代码必须以2开头！]）#k\r\n\r\n");
        }
    } else if (status == 2) {
        //cm.dispose();
        if(beauty == 7){
            var haircode =cm.getText();
            if(haircode.split(",").length<1){
                cm.sendOk("代码不规范");
                cm.dispose();
                return;
            }
            var start=haircode.split(",")[0];
            var end=haircode.split(",")[1];
            hairnew = getbtNumCodeList(start,end,1);
            // if (cm.getPlayer().getGender() == 0)
            //     for (var i = 0; i < mhair.length; i++)
            //         hairnew.push(mhair[i] + parseInt(cm.getPlayer().getHair() % 10));
            // if (cm.getPlayer().getGender() == 1)
            //     for (var i = 0; i < fhair.length; i++)
            //         hairnew.push(fhair[i] + parseInt(cm.getPlayer().getHair() % 10));
            cm.playerMessage(hairnew);
            cm.sendStyle("预览一个想要的发型..", hairnew, 5150040);
        }
        if (beauty == 1) {
            //if (cm.haveItem(5150040)) {
            //    cm.gainItem(5150040, -1);
            //    cm.setHair(hairnew[selection]);
            cm.sendOk("发型ID【" + hairnew[selection] + "】,快去随机发型或自选发型代码吧！");
            cm.dispose();
            return;
            // } else {
            //     cm.sendOk("您貌似没有#b#t5150040##k..");
            //     cm.dispose();
            //      return;
            //   }
        }
        if (beauty == 2) {
            //if (cm.haveItem(5150040) == true) {
            //cm.gainItem(5150040, -1);
            //cm.setFace(facenew[selection]);
            cm.sendOk("脸型ID【" + facenew[selection] + "】,快去随机发型或自选脸型代码吧！");
            cm.dispose();
            return;
            //} else {
            //cm.sendOk("您貌似没有#b#t5150040##k..");
            //cm.dispose();
            //return;
            //}
        }
        if (beauty == 3) {
            arr = 3;
            原始头发 = cm.getPlayer().getHair();
            if (cm.setRandomAvatar(5150040, hair_Colo_new) == 1) {
                cm.sendYesNo("啊哈!!焕然一新的发型\r\n\r\n是否还原发型呢？\r\n\r\n#r需要还原点击“是”，不需要还原点“否”。");
            } else {
                cm.sendOk("啊.... 貌似没有#b#t5150040##k。");
                cm.dispose();
                return;
            }

        }
        if (beauty == 4) {
            arr = 4;
            原始脸型 = cm.getPlayer().getFace();
            if (cm.setRandomAvatar(5150040, face_Colo_new) == 1) {
                cm.sendYesNo("啊哈!!焕然一新的脸型\r\n\r\n是否还原脸型呢？\r\n\r\n#r需要还原点击“是”，不需要还原点“否”。");
            } else {
                cm.sendOk("啊.... 貌似没有#b#t5150040##k。");
                cm.dispose();
                return;
            }
        }
        if (beauty == 5) {
            var haircode =cm.getText();
            if(checkHaveCode(haircode,1)&&cm.haveItem(useitem,5)){
                cm.gainItem(useitem,-4);
                cm.setAvatar(useitem,haircode);
                cm.sendOk("啊哈!!焕然一新的闪闪发型。");
            } else {
                cm.sendOk("啊.... 发型代码不存在，或#v"+useitem+"##z"+useitem+"##l不足5个。");
                cm.dispose();
                return;
            }
        }
        if (beauty == 6) {
            var facecode =cm.getText();
            if(checkHaveCode(facecode,2)&&cm.haveItem(useitem,5)){
                cm.gainItem(useitem,-4);
                cm.setAvatar(useitem,facecode);
                cm.sendOk("啊哈!!焕然一新的帅气脸型。");
            } else {
                cm.sendOk("啊.... 脸型代码不存在，或#v"+useitem+"##z"+useitem+"##l不足5个。");
                cm.dispose();
                return;
            }
        }
    } else if (status == 3) {
        if (arr == 3) {
            cm.sendOk("已还原!");
            cm.setHair(原始头发);
            cm.dispose();
            return;
        }
        if (arr == 4) {
            cm.sendOk("已还原!");
            cm.setFace(原始脸型);
            cm.dispose();
            return;
        } else {
            cm.dispose();
            return;
        }
    }
}