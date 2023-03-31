/*
 * Time Temple - Kirston
 * Twilight of the Gods
 */

function start() {
    cm.askAcceptDecline("如果我有善良之鏡,我就能召喚黑魔法师!\r\n等等!好像哪裏错了!为什麽召喚不了黑魔法师?我感觉到跟黑魔法师完全不同的……啊啊啊!!!!!!!\r\n\r\n #b(请把奇拉的使命傳遞下去.)");
}

function action(mode, type, selection) {
    if (mode == 1) {
        cm.removeNpc(270050100, 2141000);
        cm.forceStartReactor(270050100, 2709000);
    }
    cm.dispose();

// If accepted, = summon PB + Kriston Disappear + 1 hour timer
// If deny = NoTHING HAPPEN
}