var status = -1;

function end(mode, type, selection) {
  if (mode == 0) {
    status--;
  } else {
    status++;
  }
  if (status == 0) {
    qm.sendNext("什么？……只要让体力噌噌下降不就行了？呵呵，谁说的？一派胡言……那怎么可能？哈哈哈哈！\r\n\r\n#fUI/UIWindow2.img/QuestIcon/11/0# 意志 50\r\n#fUI/UIWindow2.img/QuestIcon/8/0# 4,916,000 exp");
    qm.gainMeso(-1000000);
    qm.gainExp(4916000);
    qm.forceCompleteQuest(3514);
    qm.removeAll(2022337);
    qm.dispose();
  }
}
/*  <imgdir name="02022337">
    <imgdir name="info">
      <canvas name="icon" width="26" height="32">
        <vector name="origin" x="-4" y="32" />
      </canvas>
      <canvas name="iconRaw" width="15" height="31">
        <vector name="origin" x="-8" y="32" />
      </canvas>
      <int name="tradeBlock" value="1" />
      <int name="notSale" value="1" />
      <int name="price" value="1" />
    </imgdir>
    <imgdir name="spec">
      <int name="hpR" value="-100" />
      <int name="mpR" value="-100" />
    </imgdir>*/