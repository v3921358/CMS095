var cost = 100000;
var rn = "\r\n\r\n"; // 换行
var status;
var historyJobSkills = [];
var selectionid = 0;
var 职业 = Array(
    // Array("战士", 100, 10, 0),
    // Array("剑客", 110, 30, 100),
    // Array("勇士", 111, 70, 110),
    Array("英雄", 112, 120, 111),
    // Array("准骑士", 120, 30, 100),
    // Array("骑士", 121, 70, 120),
    Array("圣骑士", 122, 120, 121),
    // Array("枪战士", 130, 30, 100),
    // Array("龙骑士", 131, 70, 130),
    Array("黑骑士", 132, 120, 131),
    // Array("魔法师", 200, 8, 0),
    // Array("火毒法师", 210, 30, 200),
    // Array("火毒巫师", 211, 70, 210),
    Array("火毒魔导士", 212, 120, 211),
    // Array("冰雷法师", 220, 30, 200),
    // Array("冰雷巫师", 221, 70, 220),
    Array("冰雷魔导士", 222, 120, 221),
    // Array("牧师", 230, 30, 200),
    // Array("祭司", 231, 70, 230),
    Array("主教", 232, 120, 231),
    // Array("弓箭手", 300, 10, 0),
    // Array("猎人", 310, 30, 300),
    // Array("射手", 311, 70, 310),
    Array("神射手", 312, 120, 311),
    // Array("弩弓手", 320, 30, 300),
    // Array("游侠", 321, 70, 320),
    Array("箭神", 322, 120, 321),
    // Array("飞侠", 400, 10, 0),
    // Array("刺客", 410, 30, 400),
    // Array("无影人", 411, 70, 410),
    Array("隐士", 412, 120, 411),
    // Array("侠客", 420, 30, 400),
    // Array("独行客", 421, 70, 420),
    Array("侠盗", 422, 120, 421),
    // Array("海盗", 500, 10, 0),
    // Array("拳手", 510, 30, 500),
    // Array("斗士", 511, 70, 510),
    Array("冲锋队长", 512, 120, 511),
    // Array("火枪手", 520, 30, 500),
    // Array("大副", 521, 70, 520),
    Array("船长", 522, 120, 521),
    // Array("魂骑士（一转）", 1100, 10, 1000),
    // Array("魂骑士（二转）", 1110, 30, 1100),
    // Array("魂骑士（三转）", 1111, 70, 1110),
    Array("魂骑士（四转）", 1112, 120, 1111),
    // Array("炎术士（一转）", 1200, 10, 1000),
    // Array("炎术士（二转）", 1210, 30, 1200),
    // Array("炎术士（三转）", 1211, 70, 1210),
    Array("炎术士（四转）", 1212, 120, 1211),
    // Array("风灵使者（一转）", 1300, 10, 1000),
    // Array("风灵使者（二转）", 1310, 30, 1300),
    // Array("风灵使者（三转）", 1311, 70, 1310),
    Array("风灵使者（四转）", 1312, 120, 1311),
    // Array("夜行者（一转）", 1400, 10, 1000),
    // Array("夜行者（二转）", 1410, 30, 1400),
    // Array("夜行者（三转）", 1411, 70, 1410),
    Array("夜行者（四转）", 1412, 120, 1411),
    // Array("奇袭者（一转）", 1500, 10, 1000),
    // Array("奇袭者（二转）", 1510, 30, 1500),
    // Array("奇袭者（三转）", 1511, 70, 1510),
    Array("奇袭者（四转）", 1512, 120, 1511),

    // Array("见习双刀", 430, 20, 400),  
    // Array("双刀客", 431, 30, 430),
    // Array("双刀侠", 432, 55, 431),
    // Array("血刀", 433, 70, 432),
    Array("暗影双刀", 434, 120, 433),


    // Array("机械师（一转）", 3500, 10, 3000),
    // Array("机械师（二转）", 3510, 30, 3500),
    // Array("机械师（三转）", 3511, 70, 3510),
    Array("机械师（四转）", 3512, 120, 3511),
    // Array("唤灵斗师（一转）", 3200, 10, 3000),
    // Array("唤灵斗师（二转）", 3210, 30, 3200),
    // Array("唤灵斗师（三转）", 3211, 70, 3210),
    Array("唤灵斗师（四转）", 3212, 120, 3211),
    // Array("豹弩游侠（一转）", 3300, 10, 3000),
    // Array("豹弩游侠（二转）", 3310, 30, 3300),
    // Array("豹弩游侠（三转）", 3311, 70, 3310),
    Array("豹弩游侠（四转）", 3312, 120, 3311),

    // Array("龙神（一转）", 2200, 10, 2001),
    // Array("龙神（二转）", 2210, 20, 2200),
    // Array("龙神（三转）", 2211, 30, 2210),
    // Array("龙神（四转）", 2212, 40, 2211),
    // Array("龙神（五转）", 2213, 50, 2212),
    // Array("龙神（六转）", 2214, 60, 2213),
    // Array("龙神（七转）", 2215, 80, 2214),
    // Array("龙神（八转）", 2216, 100, 2215),
    //Array("龙神（九转）", 2217, 120, 2216),
    Array("龙神（十转）", 2218, 160, 2217),

    // Array("战神（一转）", 2100, 10, 2000),
    // Array("战神（二转）", 2110, 30, 2100),
    // Array("战神（三转）", 2111, 70, 2110),
    Array("战神（四转）", 2112, 120, 2111));

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && type > 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var level = cm.getPlayer().getLevel();
            var jobid = cm.getJob();
            var jobname = "未进行四转";
            for (var i = 0; i < 职业.length; i++) {
                if (jobid + "" == 职业[i][1]) {
                    jobname = 职业[i][0];
                }
            }
            if (level >= 120) {
                var text = "你可以自由转职了，你当前的职业是【" + jobname + "】" + rn;
                text += "请选择下列你要转换的职业，本职业转换系统保留历史技能加点（#r即：当你再次转回原来的职业，技能加点和技能等级都可以还原回来！#k），选择下方一键还原技能即可！！\r\n#e#L99##b【一键还原技能】#k #l\r\n";
                text += " \r\n";
                for (var i = 0; i < 职业.length; i++) {
                    text += "#L" + 职业[i][1] + "##r" + 职业[i][0] + "#k#l\r\n";
                }
                cm.sendSimple(text);
            } else {
                cm.sendOk("当前等级还不满足转职条件,要120级才可以使用哦");
                cm.dispose();
            }

        } else if (status == 1) {
            var jobname1 = 0;
            selectionid = selection;
            jobid = cm.getJob();
            //一键还原职业技能
            if (selectionid == 99) {
                initHistoryJobSkills();
                if (historyJobSkills.length > 0) {
                    for(var j=0;j<historyJobSkills.length;j++){
                        if(historyJobSkills[j][0]==jobid){
                            if (historyJobSkills[j][3] != 0) {
                                cm.teachSkill(historyJobSkills[j][1], historyJobSkills[j][2], historyJobSkills[j][3]);
                                // cm.getPlayer().gainSP(-historyJobSkills[j][2], spbook);
                                
                            } else {
                                cm.teachSkill(historyJobSkills[j][1], historyJobSkills[j][2], 0);
                                // cm.getPlayer().gainSP(-historyJobSkills[j][2], spbook);
                            }
                        }

                    }
                    cm.sendOk("一键还原历史转职技能成功！");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("#r未查询到历史转职技能保留记录。#k");
                    cm.dispose();
                    return;
                }
            }
            for (var i = 0; i < 职业.length; i++) {
                if (selection + "" == 职业[i][1]) {
                    jobname1 = 职业[i][0];
                }
            }

            var text = "#r你选择的职业是【" + jobname1 + "】\r\n";
            text += "1、转职会清空键盘技能;" + rn;
            text += "2、保留历史技能加点，技能书！" + rn;
            text += "3、初始化能力值属性点;" + rn;
            text += "4、HP洗血会保存;" + rn;
            text += "5、转职手续费游戏金币1个亿。" + rn;
            cm.sendYesNo(text);

        } else if (status == 2) {
            if (selectionid == 2218 && cm.getPlayer().getLevel() < 160) {
                cm.sendOk("转职失败！龙神十转要160级哦！");
                cm.dispose();
                return;
            }
            if (cm.getMeso() < 100000000) {
                cm.sendOk("您的游戏金币不足1个亿。");
                cm.dispose();
                return;
            }
            if (!cm.canHold(2280002)) {
                cm.sendOk("背包已满！请保证有5个以上空间。");
                cm.dispose();
                return;
            }
            //保存技能
            var skillsObj = cm.getPlayer().getSkills();
            var jobid = cm.getJob();
            for (var skill in skillsObj) {
                if (((jobid + "").substring(0, 2) == (skill.getId() + "").substring(0, 2) || (skill.getId() + "").substring(1, 2) == "0") && (skill.getId() + "").length == ((jobid + "").length + 4)) {
                    savejobSkills(skill.getId(), cm.getPlayer().getSkillLevel(skill.getId()), cm.getPlayer().getMasterLevel(skill), skill.getMaxLevel());
                }

            }

            cm.gainMeso(-100000000);
            cm.gainItem(2280002, 1); //勇士意志
            cm.gainItem(2280003, 1); //送一本冒险岛勇士技能

            changeHpMp(selectionid);
            setAttribute();
            cm.changeJob("9" + selectionid); //由于后台转职会默认提升属性值，加了个9进行判断单独控制
            if (selectionid == 3312) { //豹子职业学习技能；
                cm.teachSkill(30001061, 1, 1);
                cm.teachSkill(30001062, 1, 1);
            }
            if (selectionid == 3512) { //机械师前冲技能
                cm.teachSkill(30001068, 1, 1);
            }
            if (selectionid == 322) { //箭神一击要害技能
                cm.teachSkill(3221007, 1, 1);
            }
            cm.teachSkill(cm.getSkillByJob(cm.getPlayer(), 1003, cm.getPlayer().getJob()), 1, 0); // 匠人技能
            cm.teachSkill(cm.getSkillByJob(cm.getPlayer(), 1026, cm.getPlayer().getJob()), 1, 0); // 飞翔技能
            cm.teachSkill(cm.getSkillByJob(cm.getPlayer(), 1017, cm.getPlayer().getJob()), 1, 0); // 骑宠技能
            cm.teachSkill(cm.getSkillByJob(cm.getPlayer(), 1005, cm.getPlayer().getJob()), 1, 0); // 英雄回声技能
            cm.teachSkill(cm.getSkillByJob(cm.getPlayer(), 8, cm.getPlayer().getJob()), 1, 0); // 群宠技能
            cm.getPlayer().changeChannel(cm.getPlayer().getClient().getChannel() == 4 ? 1 : cm.getPlayer().getClient().getChannel() + 1);
            cm.sendOk("转职成功");
            cm.dispose();
        }
    }
}

function setAttribute() {
    //清空全部键盘按键
    for (var i = 2; i <= 88; i++) {
        cm.changeKeybinding(i, 0, 0);
    }
    //初始化能力值
    cm.resetStats(4, 4, 4, 4);
    //清空宠物技能
    updatePetsSkill();

}
/**
 * 清空宠物技能、初始化该角色所有职业技能加点，不清除技能最大等级
 * 
 */
function updatePetsSkill() {
    var playerId = cm.getPlayer().getId();
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("select petid,skillid from inventoryitems a,pets b where a.uniqueid=b.petid and  inventorytype=5 and characterid=?");
    ps.setInt(1, playerId);
    var rs = ps.executeQuery();

    while (rs.next()) {
        if (rs.getInt("skillid") !== 0) {
            ps = con.prepareStatement("update pets set skillid=0 where petid=?");
            ps.setInt(1, rs.getInt("petid"));
            ps.executeUpdate();
        }
    }
    cm.playerMessage("技能已初始化");
    ps.close();
    con.close();
}


function savejobSkills(skillid, skilllevel, masterlevel, maxlevel) {
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("select count(*) ct from skills_savejobskill a where a.characterid=? and  jobid=? and skillid=?");
    ps.setInt(1, cm.getPlayer().getId());
    ps.setInt(2, cm.getJob());
    ps.setInt(3, skillid);
    var rs = ps.executeQuery();

    while (rs.next()) {
        if (rs.getInt("ct") > 0) {
            ps = con.prepareStatement("update skills_savejobskill set skilllevel=?,masterlevel=?,maxlevel=? where characterid=?  and  jobid=? and skillid=? ");
            ps.setInt(1, skilllevel);
            ps.setInt(2, masterlevel);
            ps.setInt(3, maxlevel);
            ps.setInt(4, cm.getPlayer().getId());
            ps.setInt(5, cm.getJob());
            ps.setInt(6, skillid);
        } else {
            ps = con.prepareStatement("insert into skills_savejobskill(characterid,jobid,skillid,skilllevel,masterlevel,maxlevel) values(?,?,?,?,?,?)");
            ps.setInt(1, cm.getPlayer().getId());
            ps.setInt(2, cm.getJob());
            ps.setInt(3, skillid);
            ps.setInt(4, skilllevel);
            ps.setInt(5, masterlevel);
            ps.setInt(6, maxlevel);
        }


    }

    ps.executeUpdate();
    ps.close();
    rs.close();
    con.close();
}

function initHistoryJobSkills() {
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("select * from skills_savejobskill a where a.characterid=? and  jobid=? ");
    ps.setInt(1, cm.getPlayer().getId());
    ps.setInt(2, cm.getJob());
    var rs = ps.executeQuery();
    while (rs.next()) {
        var skillmsg = [];
        skillmsg.push(rs.getInt("jobid"));
        skillmsg.push(rs.getInt("skillid"));
        skillmsg.push(rs.getInt("skilllevel"));
        skillmsg.push(rs.getInt("masterlevel"));
        skillmsg.push(rs.getInt("maxlevel"));
        historyJobSkills.push(skillmsg);
    }
    ps.close();
    rs.close();
    con.close();
}

function changeHpMp(job) {
    var playerLevel = cm.getPlayer().getLevel();
    var maxhp = 50;
    var maxmp = 50;
    if (job === 0) { // Beginner
        maxhp = playerLevel * Math.floor(Math.random() * (16 - 12) + (12 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (12 - 10) + (10 + 1));
    } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1112)) { // Warrior
        maxhp = playerLevel * Math.floor(Math.random() * (52 - 48) + (48 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (6 - 4) + (4 + 1));
    } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1212)) { // Magician
        maxhp = playerLevel * Math.floor(Math.random() * (14 - 10) + (10 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (52 - 48) + (48 + 1));
    } else if (job >= 3200 && job <= 3212) { //battle mages get their own little neat thing
        maxhp = playerLevel * Math.floor(Math.random() * (24 - 20) + (20 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (44 - 42) + (42 + 1));
    } else if ((job >= 300 && job <= 322) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 1500 && job <= 1512) || (job >= 3300 && job <= 3312) || (job >= 2300 && job <= 2312)) { // Bowman, Thief, Wind Breaker and Night Walker
        maxhp = playerLevel * Math.floor(Math.random() * (24 - 20) + (20 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (16 - 14) + (14 + 1));
    } else if ((job >= 510 && job <= 512) || (job >= 1510 && job <= 1512)) { // Pirate
        maxhp = playerLevel * Math.floor(Math.random() * (41 - 37) + (37 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (22 - 18) + (18 + 1));
    } else if ((job >= 500 && job <= 532) || (job >= 3500 && job <= 3512) || job == 1500) { // Pirate
        maxhp = playerLevel * Math.floor(Math.random() * (24 - 20) + (20 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (22 - 18) + (18 + 1));
    } else if (job >= 2100 && job <= 2112) { // Aran
        maxhp = playerLevel * Math.floor(Math.random() * (52 - 50) + (50 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (6 - 4) + (4 + 1));
    } else if (job >= 2200 && job <= 2218) { // Evan
        maxhp = playerLevel * Math.floor(Math.random() * (16 - 12) + (12 + 1));
        maxmp = playerLevel * Math.floor(Math.random() * (52 - 50) + (50 + 1));
    } else { // GameMaster
        // maxhp = playerLevel * Math.floor(Math.random() * (100 - 50) + (50 + 1));
        // maxmp = playerLevel * Math.floor(Math.random() * (100 - 50) + (50 + 1));
    }
    if (cm.getPlayer().getMaxHp() < maxhp) {
        cm.getPlayer().setMaxHp(maxhp);
    }
    cm.getPlayer().setMaxMp(maxmp);
}