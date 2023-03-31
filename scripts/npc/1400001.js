var status;   
var maxitems = 50;
var error = new String("#e#bI'm sorry but this item is blocked!");
function start() {   
    status = -1;   
    action(1,0,0);   
}   

function action(mode, type, selection) {   
    if (mode == -1) {   
        cm.dispose();   
        return;   
    } else {   
        (mode == 1 ? status++ : status--);   
    }   
    if (status == 0) {   
    cm.sendGetText("#b#eHey there, I'm the free item giver!\r\nI can give you an item if you have the Item ID.");   
    } else if (status == 1) { 
	text = cm.getText(); 

    if (text == "2022766"){cm.sendOk(error);cm.dispose();}
	if (text == "1042003"){cm.sendOk(error);cm.dispose();}
	if (text == "1062007"){cm.sendOk(error);cm.dispose();}
	if (text == "1002140"){cm.sendOk(error);cm.dispose();}
	if (text == "1003142"){cm.sendOk(error);cm.dispose();}
	if (text == "1322013"){cm.sendOk(error);cm.dispose();}
	if (text == "1002959"){cm.sendOk(error);cm.dispose();}
	if (text > 7 || text < 7) {
	cm.sendOk("#e#bItem must be equals to 7 numbers!");
	cm.dispose();
	}
    cm.sendGetNumber("#e#bHow many #v "+cm.getText()+"#'s do you want ?", 1, 1, maxitems); 
	} else if (status == 2) { 
	for(var i = 1; i==1; i++){
	cm.gainItem(cm.getText(), selection); 
	}
        cm.dispose();   
    }   
}