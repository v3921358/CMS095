function act() {
    var g = rm.getPlayer().getEventInstance();
    var a = rm.getPlayer().getEventInstance().getPlayers();
    var e = g.getMapInstance(240050100);
    var j = rm.getPlayer().getMapId();
    var d;
    var f = 4001087;
    var h = -1;
    rm.mapMessage(6, "The key is teleported somewhere...");
    switch (j) {
        case 240050101:
            d = f;
            h = 2;
            break;
        case 240050102:
            d = f + 1;
            h = 3;
            break;
        case 240050103:
            d = f + 2;
            h = 4;
            break;
        case 240050104:
            d = f + 3;
            h = 5;
            break;
        default:
            d = -1;
            break
    }
    var i = new client.Item(d, 0, 1);
    var c = e.getReactorByName("keyDrop1");
    var b = g.getPlayers().get(0);
    e.spawnItemDrop(c, b, i, c.getPosition(), true, true);
    e.dropMessage(5, "A bright flash of light, then a key suddenly appears somewhere in the map.")
};