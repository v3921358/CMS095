var status = -1;
function action(mode, type, selection) {
    if (mode == 1) 
        status++;
    else 
	status--;
    if (status == 0) {
    	cm.sendNext("嗯…实验似乎进行的相当順利，順利的拿到露。和黑色翅膀合作果然是明智之舉…呵呵呵");
    } else if (status == 1) {
	cm.sendNextPrevS("傑利麥勒果然有先見之明。", 4, 2159008);
    } else if (status == 2) {
	cm.sendNextPrev("黑色翅膀无法挑剔的機器人，就快要完成了。现在实验要开始下一个階段了。比他们时候的还要有趣。");
    } else if (status == 3) {
	cm.sendNextPrevS("下一个階段呢？", 4, 2159008);
    } else if (status == 4) {
	cm.sendNextPrev("呼呼…到现在还不知道吗？光看这个实验室就應該会知道，我现在要制造什么東西。只制造及其不够好玩，比機器人还有趣的…");
    } else if (status == 5) {
	cm.sendNextPrevS("嗯？这实验室吗？你打算对这实验者做什么事吗？", 4, 2159008);
    } else if (status == 6) {
	cm.sendNextPrev("什么，我能了解在你眼中，看不見这实验室偉大的地方。至于你呢！只要把你的任務做好就行了。顧好在这里的沒一个实验者，让他们沒辦法逃跑就行了。");
    } else if (status == 7) {
	cm.sendNextPrev("…嗯？有沒有听到什么奇怪的声音？");
    } else if (status == 8) {
	cm.sendNextPrevS("嗯？奇怪的声音？这樣一说，好像有什么…？", 4, 2159008);
    } else if (status == 9) {
	cm.updateInfoQuest(23007, "vel00=2;vel01=1");
	cm.trembleEffect(0,500);
        cm.MovieClipIntroUI(true);
	cm.showWZEffect("Effect/Direction4.img/Resistance/TalkInLab");
    	cm.dispose();
    }
}