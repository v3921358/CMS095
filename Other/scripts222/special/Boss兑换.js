var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var itemSet = Array(
Array(4310010,2430158,100,10,100,2430158,0),                   //第一个兑换物品 2代表材料1 3代表数量 4代表点卷 5金币 6物品2 7数量
Array(4310009,4000630,100,10,100,2430158,1),
Array(4310010,4000630,300,10,100,4310009,1)   
   

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
            var add ="\t\t\t" + hwx + "#r#e < Boss兑换 > #k#n" + hwx + "\r\n";
            for (var i = 0; i < itemSet.length; i++) {	
                    add += "\r\n\t\t#L" + i + "##z";
                    add += 2430158+"#"+"兑换 #v4310010# ";
                    add += "#l#k";
                };

            cm.sendSimple(add);
    } else if (status == 2) {

            selectedItem = selection;
            item = itemSet[selectedItem][0];
            req = itemSet[selectedItem][1];
			req1 = itemSet[selectedItem][5];
            co = itemSet[selectedItem][2];
			co1 = itemSet[selectedItem][6];
			jb = itemSet[selectedItem][4];
			dj = itemSet[selectedItem][3];
            var bdd ="";
            bdd += "\r\n\t\t\t\t#i" +item+"# "+ " #t" + item + "#\n\r\n\r\n\r\n\r";
            bdd += "    需要材料:" + dj + " * 点卷 " + jb + " * 金币\n\r#\t#i " + req + "#*" + co + " 个#i " + req1 + "#*" + co1 + " 个\r\n\r\n";
           // bdd += "单个物品需要材料个数:#r " + co + "个\r\n\r\n\r\n";
            bdd += "请输入兑换个数\r\n";
            cm.sendGetNumber(bdd,1,1,100)
            //cm.sendYesNo(bdd);
    } else if (status == 3) {
	qty = (selection > 0) ? selection : (selection < 0 ? -selection : 1);
	cost=qty;   //花费为物品单价*输入的数量
          if (cm.getMeso() < cost*jb || cm.getPlayer().getCSPoints(1) < cost*dj) {
            cm.sendOk("#b您的点卷或金币不足哦");
	        cm.dispose();
	       } else if (!cm.haveItem(req1, cost*co1)) {
           cm.sendOk("#b您的材料#v"+req1+"#不足哦");
            cm.dispose();
            return;
			 } else if (!cm.haveItem(req, cost*co)) {
           cm.sendOk("#b您的材料#v"+req+"#不足哦");
            cm.dispose();
            return;
			
            } else {
			            cm.gainMeso(-cost * jb);
				        cm.gainNX(-cost * dj);
            
            	        cm.gainItem(req1,-cost*co1);
						cm.gainItem(req,-cost*co);
            	        cm.gainItem(item,qty);
                        cm.sendOk("#b购买成功");
	        cm.dispose();
            }
            cm.dispose();
    }
}
