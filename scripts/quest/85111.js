var status = -1;

function start(mode, type, selection) {
    qm.sendAcceptDecline("嗯...高敬先生所推薦來的吗？果然是...但我还不能百分之百相信你。我还沒看过你的能力，怎么能只听別人说就相信你呢？如果你能帮助我，我也会相信你，可以拜託更重要的事情吧。怎么樣？你会帮我吗？");
    qm.dispose();
}

function end(mode, type, selection) {
    qm.forceCompleteQuest(8512);
    qm.dispose();
}