function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    /*if (mode == 1) {
        status++;
    } else if (mode == 0 && status != 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
    if (selection == 0) {*/
		var text = "\t目前本服的充值情况如下：\r\n\r\n";
            var rankinfo_list = cm.getBossRankCountTop9("充值积分");
			var arr = Array();
			var jf = Array();
			for (var i = 0;i < rankinfo_list.size(); i++){
					arr.push(rankinfo_list.get(i).getCname());
					jf.push(rankinfo_list.get(i).getCount())
			}
			if (arr != null) {
				for (var i = 0;i < arr.length; i++){
                var info = arr[i];
				var 积分 = jf[i]
				var 等级 = cm.getCharacterByNameLevel(info);
				text += "\t#r" + (i + 1) + "#k#n. ";
				text += info + " \t";
				text += "\t#b充值积分" + 积分 + "";
				text += "#k";
				text += "\r\n";
				}
				
			} else {
				text += "目前没有人充值！";
			}
			cm.sendOkS(text,2);
			 
        }
	
           
