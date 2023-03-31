var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var itemSet = Array(
                 //第一个兑换物品 0代表金币袋 1代表物品 2代表物品数量 3金币 
Array(4031891,3994081,3,100000),  
Array(4031891,3994082,3,300000), 
Array(4031891,3994083,3,500000),   
Array(4031891,3994084,3,700000)  

);
var status = 0;
var selectedItem;
var item;
var req;
var req1;
var cost;
var qty;
var co;
var co1;
var dj;
var jb;
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
		
            var add ="\t\t\t" + hwx + "#r#e < 兑换金币 > #k#n" + hwx + "\r\n";
            for (var i = 0; i < itemSet.length; i++) {	
                    add += "\r\n\t\t#L" + i + "##i " + itemSet[i][1] + "##z";
                    add += itemSet[i][1]+"#"+"兑换 #i " + itemSet[i][0] + "# " + itemSet[i][3] + " 金币 ";
                    add += "#l#k";
                };

            cm.sendSimple(add);
    } else if (status == 2) {

            selectedItem = selection;
            item = itemSet[selectedItem][0];
            req = itemSet[selectedItem][1];
			req1 = itemSet[selectedItem][2];
            co = itemSet[selectedItem][3];
            var bdd ="";
            bdd += "\r\n\t\t\t\t#i" +item+"# "+ " " + co + " 金币\n\r\n\r\n\r\n\r";
            bdd += "    需要材料:\r\n\t#i " + req + "#*" + req1 + " 个\r\n\r\n";
           // bdd += "单个物品需要材料个数:#r " + req1 + "个\r\n\r\n\r\n";
            bdd += "请输入兑换个数\r\n";
            cm.sendGetNumber(bdd,1,1,100)
            //cm.sendYesNo(bdd);
    } else if (status == 3) {
	qty = (selection > 0) ? selection : (selection < 0 ? -selection : 1);
	cost=qty;   //花费为物品单价*输入的数量
           if (!cm.haveItem(req, cost*req1)) {
           cm.sendOk("#b您的材料#v"+req+"#不足哦");
            cm.dispose();
            return;
			
            } else {
			            cm.gainMeso(cost * co);
				       // cm.gainNX(-cost * dj);
            
            	        cm.gainItem(req,-cost*req1);
						//cm.gainItem(req,-cost*co);
            	      //  cm.gainItem(item,qty);
                        cm.sendOk("#b购买成功");
	        cm.dispose();
            }
            cm.dispose();
    }
}
