function start() {
    cm.sendSimple ("I am the almighty creator of Timeless Weapons. Bring me 25 Rock of Time, and I shall merge you a weapon.\r\n#r#L0##eWhere do I find Rock of Time?#n#l\r\n#k#L1##eTimeless Executioner (1h Sword)#n#l#k\r\n#k#L2##eTimeless Bardiche (1h Axe)#n#l#k\r\n#k#L3##eTimeless Allargando (1h Mace)#n#l#k\r\n#k#L4##eTimeless Nibleheim (2h Sword)#n#l#k\r\n#k#L5##eTimeless Tabarzin (2h Axe)#n#l#k\r\n#k#L6##eTimeless Bellocce (2h Mace)#n#l#k\r\n#k#L7##eTimeless Alchupiz (Spear)#n#l#k\r\n#k#L8##eTimeless Diesra (Polearm)#n#l#k\r\n#k#L9##eTimeless Aeas Hand (Staff)#n#l#k\r\n#k#L10##eTimeless Enreal Tear (Wand)#n#l#k\r\n#k#L11##eTimeless Engaw (Bow)#n#l#k\r\n#k#L12##eTimeless Black Beauty (Crossbow)#n#l#k\r\n#k#L13##eTimeless Killic (Dagger-DEX)#n#l#k\r\n#k#L14##eTimeless Pescas (Dagger-STR)#n#l#k\r\n#k#L15##eTimeless Equinox (Knuckle)#n#l#k\r\n#k#L16##eTimeless Blindness (Gun)#n#l\r\n#k#L17##eTimeless Blooms (Bowgun)#n#l#k");
}

function action(mode, type, selection) {
    cm.dispose();
    switch(selection){
        case 0: 
            if(cm.getMeso() >= 0){
                cm.sendOk("Rocks of Time can be found from any monster, at a 5% Drop Rate");
                cm.dispose();
            }else{
                cm.sendOk("Rocks of Time can be found from any monster, at a 5% Drop Rate");
                cm.dispose();
            }
            break;
        case 1: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Executioners.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1302081, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 2: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Bardiche.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1312037, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 3: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Allargando!");
                cm.gainItem(4021010, -25);
                cm.gainItem(1322060, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 4: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Nibleheim.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1402046, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 5: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Tabarzi.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1412033, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 6: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Bellocce.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1422037, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 7: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Alchupiz.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1432047, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 8: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Diesra.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1442063, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 9: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Aeas Hand.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1382057, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 10: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Enreal Tear.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1372044, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
        case 11: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Engaw.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1452057, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    case 12: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Black Beauty.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1462050, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    case 13: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Killic.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1332074, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    case 14: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Pescas.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1332073, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    case 15: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Equinox.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1482023, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    case 16: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Blindness.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1492023, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
      case 17: 
            if (cm.haveItem(4021010, 25)) {  
                cm.sendOk("You now possess the Timeless Blooms.");
                cm.gainItem(4021010, -25);
                cm.gainItem(1522015, 1);
                cm.dispose();
            }else{
                cm.sendOk("You don't have enough Rocks, please double check your inventory.");
                cm.dispose();
            }
            break;
    }
}