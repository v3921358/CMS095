var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var itemSet = Array(
Array(4022017,4022011,3),                     //（卷轴代码，材料物品代码，单个物品所需材料个数）
Array(4022018,4022012,3),
Array(4022019,4022013,3),
Array(4022020,4022014,3),
Array(4022021,4022016,3)
);
var status = 0;
var selectedItem;
var item;
var req;
var cost;
var qty;
var co;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    status++;
    if (mode == -1){
        cm.dispose();
        return;
    } else if (mode == 0) {
    	cm.dispose();
    	return;
    }
    if (status == 1) {
            var add ="\t\t\t" + hwx + "#r#e < 匠人兑换 > #k#n" + hwx + "\r\n\r\n\r\n";
            for (var i = 0; i < itemSet.length; i++) {	
                    add += "\r\n#L" + i + "##i " + itemSet[i][0] + "##z";
                    add += itemSet[i][0]+"#"+"  需要材料:#i " + itemSet[i][1]+"#";
                    add += " ×" + itemSet[i][2]+" 个#l#k";
                };

            cm.sendSimple(add);
    } else if (status == 2) {

            selectedItem = selection;
            item = itemSet[selectedItem][0];
            req = itemSet[selectedItem][1];
            co = itemSet[selectedItem][2];
            var bdd ="你确定要兑换\r\n";
            bdd += "\r\n#i" +item+"# "+ " #t" + item + "#";
            bdd += "    需要材料:#i " + req + "\r\n\r\n";
            bdd += "单个物品需要材料个数:#r " + co + "个\r\n\r\n\r\n";
            bdd += "请输入购买个数\r\n";
            cm.sendGetNumber(bdd,1,1,100)
            //cm.sendYesNo(bdd);
    } else if (status == 3) {
	qty = (selection > 0) ? selection : (selection < 0 ? -selection : 1);
	cost=co*qty;   //花费为物品单价*输入的数量
            if (!cm.haveItem(req, cost)) {
                        cm.sendOk("#b您的材料不足哦");
	        cm.dispose();
            } else {
            	        cm.gainItem(req,-cost);
            	        cm.gainItem(item,qty);
                        cm.sendOk("#b购买成功");
	        cm.dispose();
            }
            cm.dispose();
    }
}
