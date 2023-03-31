function enter(pi) {
    if (!pi.isQuestActive(3935)) {
               pi.dispose();
               return;
           } else {
               pi.warp(926000010, 0);
           }
           pi.dispose();
}
