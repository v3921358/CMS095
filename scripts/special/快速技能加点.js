var skillList = [];

var rn = "\r\n\r\n"; // 换行
var status = 0;
var remainingSps = [];
var sumremainingsp = 0; //剩余SP
var sumusesp = 0; //已用SP
var num = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {
            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }

        status = status + (mode === 1 ? 1 : -1);
        if (selection == 99) {
            status = 0;
        }
        if (status === 0) {
            var text = "您好，这里是快速技能加点系统。" + rn;
            text += "#r注：如果技能不在下列列表，请手动先加一点技能！#k" + rn;
            var skillsObj = cm.getPlayer().getSkills();
            var jobid = cm.getJob();
            sumusesp = 0;
            for (var skill in skillsObj) {
                if (((jobid + "").substring(0, 2) == (skill.getId() + "").substring(0, 2) || ((jobid + "").length == 3 && (skill.getId() + "").substring(1, 2) == "0")) &&
                    (skill.getId() + "").length == ((jobid + "").length + 4)) {
                    if ((skill.getMasterLevel() == 0 && cm.getPlayer().getSkillLevel(skill.getId()) < skill.getMaxLevel()) ||
                        skill.getMasterLevel() != 0 && cm.getPlayer().getSkillLevel(skill.getId()) < cm.getPlayer().getMasterLevel(skill)) {
                        text += "#L" + num + "##b加满#q" + skill.getId() + "##s" + skill.getId() + "#" + "[当前：" + cm.getPlayer().getSkillLevel(skill.getId()) + "，可加：" + cm.getPlayer().getMasterLevel(skill) + "，最高：" + skill.getMaxLevel() + "] #l" + rn;
                        var skillmsg = [];
                        skillmsg.push(skill.getId());
                        skillmsg.push(cm.getPlayer().getSkillLevel(skill.getId()));
                        skillmsg.push(cm.getPlayer().getMasterLevel(skill));
                        skillmsg.push(skill.getMaxLevel());
                        skillList.push(skillmsg);
                        num++;
                    }
                    sumusesp += cm.getPlayer().getSkillLevel(skill.getId());

                }

            }
            text += " ";
            cm.sendSimple(text);
        } else if (status === 1) {
            var i = selection;
            if(i<0){
                cm.dispose();
                return;
            }
            var needsp = skillList[i][3] - skillList[i][1];
            if (skillList[i][2] != 0) {
                needsp = skillList[i][2] - skillList[i][1];
            }
            remainingSps = cm.getPlayer().getRemainingSps();
            var spbook = -1;
            for (var k = 0; k < remainingSps.length; k++) {
                if (remainingSps[k] > needsp) {
                    spbook = k;
                }
            }
            if (sumusesp >= (cm.getPlayer().getLevel() - 9) * 3) {
                cm.sendOk("当前已使用的技能点数已超出等级原有！");
                cm.dispose();
                return;
            }
            if (spbook == -1) {
                cm.sendOk("剩余SP不足！");
                cm.dispose();
                return;
            }
            if (skillList[i][2] != 0) {
                cm.teachSkill(skillList[i][0], skillList[i][2], skillList[i][2]);
                cm.getPlayer().gainSP(-(skillList[i][2] - skillList[i][1]), spbook);

            } else {
                cm.teachSkill(skillList[i][0], skillList[i][3], skillList[i][3]);
                cm.getPlayer().gainSP(-(skillList[i][3] - skillList[i][1]), spbook);
            }
            cm.sendSimple("快速技能加点成功！\r\n" + "#r#L99#继续加点#l#k");
            // cm.playerMessage(remainingSps[0]);
            // cm.sendOk("感谢你的光临！");
            // cm.dispose();
            // return;
        } else if (status === 2) {
            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
    }
}