var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var itemSet = Array(
Array(2046006,2430112,100,1000,1000000,2028061,30),                   //第一个兑换物品 2代表材料1 3代表数量 4代表点卷 5金币 6物品2 7数量
Array(2046007,2430112,100,1000,1000000,2028061,30),
Array(2046106,2430112,100,1000,1000000,2028061,30),
Array(2046107,2430112,100,1000,1000000,2028061,30),
Array(2046214,2430112,200,1000,1000000,2028061,40),
Array(2046309,2430112,200,1000,1000000,2028061,40),
Array(2046008,2430112,150,1000,1000000,2028061,40),
Array(2046009,2430112,150,1000,1000000,2028061,40),
Array(2046108,2430112,150,1000,1000000,2028061,40),
Array(2046109,2430112,150,1000,1000000,2028061,40),
Array(2046220,2430112,300,1000,1000000,2028061,50),
Array(2046311,2430112,300,1000,1000000,2028061,50),
Array(2049130,2430112,400,1000,1000000,2028061,60),
Array(2049131,2430112,400,1000,1000000,2028061,60),
Array(2049004,2430112,100,1000,1000000,2028061,50),
Array(2049005,2430112,400,1000,1000000,2028061,60),
Array(2340000,2430112,200,8000,1000000,2028061,100),
Array(5064000,2430112,200,8000,1000000,2028061,100),
Array(2049400,2430112,200,1000,1000000,2028061,100),
Array(2049402,2430112,200,8000,1000000,2028061,100),
Array(2049406,2430112,200,10000,1000000,2028061,100),
Array(2049407,2430112,200,20000,1000000,2028061,100)


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
            var add ="\t\t\t" + hwx + "#r#e < 卷轴兑换 > #k#n" + hwx + "\r\n";
            for (var i = 0; i < itemSet.length; i++) {	
                    add += "\r\n\t\t\t#L" + i + "##i " + itemSet[i][0] + "##z";
                    add += itemSet[i][0]+"#"+" ";
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
