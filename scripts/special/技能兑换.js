load(ServerConstants.SCRIPT_PAH+"/"+utils/base.js")

var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
var rn = "\r\n\r\n"; // 换行

var bindingKeyCode = 57;

var status = 0;
var selectionArr = [0, 0, 0]
var selectionData = [0, 0, 0]

var CDKEY_ERR_NOT_FOUND = -1;
var CDKEY_ERR_NOT_VALID = -2;
var CDKEY_ERR_INPUT_ERROR = -3;
var CDKEY_OK = 0;

/**
 * 可兑换技能列表
 * @type {*[]}
 */
var skillList = [
    4111006,//二段跳
    2101002,//瞬移
    22141004,//飞龙传动瞬移
    2001002,//魔法盾
    1321002,//泰山
    1311006,//龙咆哮
    1301007,//圣火
    2311003,//祈祷
    4201003  // 轻功
]

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

        if (status === 0) {
            showMenu();
        } else if (status === 1) {
			if(selection==99){
                deleteSkillsAlreadyHas();
				cm.sendSimple("#b清除成功#k，若“缺少技能点”的话，重新再这个页面#r“学习”#k就可以啦！");
				return ;
			}
            var skillId = skillList[selection];
            selectionData[1] = skillId;
            
            if (cm.hasSkill(skillId) || checkSkillAlreadyHas(skillId)) {
                selectionArr[1] = 0;
                cm.sendSimple("你已经拥有该技能#s " + skillList[selection] + "#！是否需要将其重新绑定到#b空格键#b上，请确保#b空格键#b当前没有其他技能！");
            } else {
                selectionArr[1] = 1;
                cm.sendGetText("注意兑换后技能#s" + skillList[selection] + "#绑定到#b空格键#b上，请确保#b空格键#b当前没有其他技能！" + rn + "请输入兑换技能CDKey：");
            }
        } else if (status === 2) {
            var skillId = selectionData[1];
            if (selectionArr[1] === 0) {
                applySkill(skillId);
                cm.dispose();
            } else if (selectionArr[1] === 1) {
                var cdkey = cm.getText().trim();
                var ret = useCDKey(cdkey);
                if (ret === CDKEY_ERR_INPUT_ERROR) {
                    cm.sendOk("CDKey不能为空，请检查后重新输入！");
                    cm.dispose();
                    return;
                } else if (ret === CDKEY_ERR_NOT_FOUND) {
                    cm.sendOk("CDKey未找到，请检查后重新输入！");
                    cm.dispose();
                    return;
                } else if (ret === CDKEY_ERR_NOT_VALID) {
                    cm.sendOk("CDKey已过期，请检查后重新输入！");
                    cm.dispose();
                    return;
                } else if (ret === CDKEY_OK) {
                    applySkill(skillId);
                    updateSkillExchangeDb(skillId, cdkey);
                    cm.setBossRankCount("点券积分", 50);
                    cm.dispose();
                    return;
                }
            }
        }
    }
}

/**
 * 显示菜单
 */
function showMenu() {
    var text = "您好，这里是技能兑换中心，赞助50元可兑换一个技能，账号内角色通用。\r\n若出现本职业技能无法加点的情况，请点击：\r\n#L99##r临时清除已购技能#k" + rn;
    for (var i = 0; i < skillList.length; i++) {
        text += "#L" + i + "##b兑换#q"+ skillList[i] + "##s" + skillList[i] + "#" + rn;
    }
    cm.sendSimple(text);
}

/**
 * 应用技能
 * @param skillId
 */
function applySkill(skillId) {
    cm.teachSkillMaxLevel(skillId);
    cm.changeKeybinding(bindingKeyCode, 1, skillId);
    cm.sendOk("恭喜你，兑换技能#s" + skillId + "#成功！请查看你的键位设置，并将其设置到合适的键位！" + rn
        + "#r技能如果不小心遗失，不要担心，只需要重新点击兑换即可！#r");
}

/**
 * 更新技能数据库
 * @param skillId
 */
function updateSkillExchangeDb(skillId, cdkey) {
    var accId = cm.getPlayer().getClient().getAccID();
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("INSERT INTO skill_exchange (accid, skillid, date, cdkey) VALUES (?, ?, CURRENT_TIMESTAMP(), ?)");
    ps.setInt(1, accId);
    ps.setInt(2, skillId);
    ps.setString(3, cdkey);
    ps.executeUpdate();
    ps.close();
    con.close();
}

/**
 * 输入CDKey
 */
function useCDKey(cdkey) {
    if (strIsEmpty(cdkey)) {
        return CDKEY_ERR_INPUT_ERROR;
    }

    // 查询 CDKey
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("SELECT * FROM nxcodez WHERE code = ?");
    ps.setString(1, cdkey);
    var rs = ps.executeQuery();
    var ret = CDKEY_ERR_NOT_FOUND;

    while (rs.next()) {
        if (rs.getInt("valid") !== 1) {
            ret = CDKEY_ERR_NOT_VALID;
        } else {
            ret = CDKEY_OK;
        }

        break;
    }

    // 使用该CDKey
    if (ret === CDKEY_OK) {
        ps.close();
        ps = con.prepareStatement("UPDATE nxcodez SET valid = ? WHERE code = ?")
        ps.setInt(1, 0);
        ps.setString(2, cdkey);
        ps.executeUpdate();
    }

    rs.close();
    ps.close();
    con.close();
    return ret;
}

/**
 * 检查是否已经拥有该技能
 * @param skillId
 */
function checkSkillAlreadyHas(skillId) {
    var accId = cm.getPlayer().getClient().getAccID();
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("SELECT * FROM skill_exchange WHERE accid = ?");
    ps.setInt(1, accId);
    var rs = ps.executeQuery();
    while (rs.next()) {
        if (rs.getInt("skillid") === skillId) {
            rs.close();
            ps.close();
            con.close();
            return true;
        }
    }

    rs.close();
    ps.close();
    con.close();
    return false;
}

/**
 * 清除已购技能
 * @param skillId
 */
 function deleteSkillsAlreadyHas() {
    var accId = cm.getPlayer().getClient.getAccID();
    // for(var i=0;i<skillList.length;i++){
    //     cm.teachSkill(skillList[i],0,0);
    // }
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("select skillid from skill_exchange where accid=?");
    ps.setInt(1, accId);
    var rs = ps.executeQuery();
    while (rs.next()) {
        cm.teachSkill(rs.getInt("skillid"),0,0);
    }
    ps.close();
    con.close();
    return true;
}