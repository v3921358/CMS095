var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var itemSet = Array(
Array(4001126,3994059,5),           //（卷轴代码，材料物品代码，单个物品所需材料个数）
Array(4001126,3994060,5),  
Array(4001126,3994061,5), 
Array(4001126,3994062,5),
Array(4001126,3994063,5),  
Array(4001126,3994064,5),
Array(4001126,3994065,5),
Array(4001126,3994066,5),
Array(4001126,3994067,5),
Array(4001126,3994068,5),
Array(4001126,3994069,5),
Array(4001126,3994070,5),
Array(4001126,3994071,5),
Array(4001126,3994072,5),
Array(4001126,3994073,5),
Array(4001126,3994074,5),
Array(4001126,3994075,5),
Array(4001126,3994076,5),
Array(4001126,3994077,5),
Array(4001126,3994078,5),
Array(4001126,3994079,5),
Array(4001126,3994080,5)
  

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
            var add ="\t\t" + hwx + "#r#e < 枫叶兑换 > #k#n" + hwx + "\r\n\r\n\r\n";
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
