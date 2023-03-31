load("scripts/utils/base.js")

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
    [50, 4111006],//二段跳
    [50, 2101002],//瞬移
    [50, 22141004],//飞龙传动瞬移
    [50, 2001002],//魔法盾
    [50, 1321002],//泰山
    [50, 1311006],//龙咆哮
    [50, 1301007],//圣火
    [50, 9001002],//祈祷
    [50, 4201003]// 轻功
];
var selye;
var 赞助余额;

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
            if (selection == 99) {
                deleteSkillsAlreadyHas();
                cm.sendSimple("#b清除成功#k，若“缺少技能点”的话，重新再这个页面#r“学习”#k就可以啦！");
                return;
            }
            var skillId = skillList[selection][1];
            
            selectionData[1] = skillId;

            if (cm.hasSkill(skillId) || checkSkillAlreadyHas(skillId)) {
                selectionArr[1] = 0;
                cm.sendSimple("你已经拥有该技能#s " + skillList[selection][1] + "#！是否需要将其重新绑定到#b空格键#b上，请确保#b空格键#b当前没有其他技能！");
            } else {
                赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
                selye=skillList[selection][0];
                if (selye>赞助余额) {
                    cm.sendOk("账号余额不足！");
                    cm.dispose();
                    return;
                }
                selectionArr[1] = 1;
                cm.sendYesNo("当前账号余额：【"+赞助余额+"】，需要消耗余额：【"+selye+"】 \r\n注意兑换后技能#s" + skillList[selection][1] + "#绑定到#b空格键#b上，请确保#b空格键#b当前没有其他技能！");
            }
        } else if (status === 2) {
            var skillId = selectionData[1];
            if (selectionArr[1] === 0) {
                applySkill(skillId);
                cm.dispose();
            } else if (selectionArr[1] === 1) {
               
                if (selye>赞助余额) {
                    cm.sendOk("账号余额不足！");
                    cm.dispose();
                    return;
                } else {
                    cm.setBossRankCount("赞助余额", -selye);
                    applySkill(skillId);
                    updateSkillExchangeDb(skillId, cdkey);
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
    var text = "您好，这里是技能兑换中心，使用余额可兑换技能，账号内角色通用。\r\n若出现本职业技能无法加点的情况，请点击：\r\n#L99##r临时清除已购技能#k" + rn;
    for (var i = 0; i < skillList.length; i++) {
        text += "#L" + i + "##b使用【"+skillList[i][0]+"】余额-兑换#q" + skillList[i][1] + "##s" + skillList[i][1] + "#" + rn;
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
        cm.teachSkill(rs.getInt("skillid"), 0, 0);
    }
    ps.close();
    con.close();
    return true;
}