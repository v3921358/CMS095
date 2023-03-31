/*
耶雷弗100等坐騎的任務
*/

// function start(mode, type, selection) {
//     qm.sendNext("请去找奇里督。");
//     qm.forceCompleteQuest();
//     qm.dispose();
// }


var status = -1;
var selectionLog = [];
function start(d, c, b) {
    
    if (status == 0 && d == 0) {
        qm.dispose();
        return;
    }
    d == 1 ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        qm.dispose();
    } else {
        if (status == a++) {
            qm.sendSimpleS('怎么会这样……你到了100级，骑宠还是普通#t1902005#吗？看你脸都红了。你觉得这样能保持高级骑士的品味吗？', 0, 1101002);
        } else {
            if (status === a++) {
                qm.sendSimpleS('相信每个人都会和你说，你这种行为会给女皇的名誉造成影响。希望你为女皇着想，作出正确的行动。你去见见#b#p1102002##k，他会告诉你#b更强的骑宠#k的事情。', 0, 1101002);
            } else {
                if (status === a++) {
                    qm.forceCompleteQuest();
                    qm.sendSimpleS('你该不会忘了#p1102002#的位置吧？……不会真忘了吧？为了饲养#t1902005#，他就在#b#m130010220##k。', 0, 1101002);
                    qm.dispose();
                }
            }
        }
    }
}
function end(mode, type, selection) {
    qm.forceCompleteQuest(20527);
    qm.dispose();
}