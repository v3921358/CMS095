var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    var em = cm.getEventManager("CWKPQ");
    if (em == null) {
	cm.sendNext("The event isn't started...");
	cm.dispose();
	return;
    }
    if (!cm.isLeader()) {
	cm.sendNext("I wish for your leader to talk to me.");
	cm.dispose();
	return;
    }
    switch(cm.getPlayer().getMapId()) {
	case 610030100:
	    if (status == 0) {
		cm.sendNext("啊，你成功了。让我很快告诉你：他们已经抓到我们了。守护大师马上就要来了。我们最好快点。");
	    } else if (status == 1) {
		cm.sendNext("通往扭曲大师的大门被摧毁了。我们必须找到另一条路，一条能带我们穿过许多死亡陷阱的路。");
	    } else if (status == 2) {
		cm.sendNext("你可以在这附近找到入口。。。你最好快点找到它。我会赶上的。");
		cm.dispose();
		em.setProperty("glpq1", "1");
	    }
	    break;
	case 610030200:
	   if (status == 0) {
		cm.sendNext("那是成功的！现在，对于这条路，我相信我们需要每一个冒险家班的一个通过。");
	   } else if (status == 1) {
		cm.sendNext("他们需要把他们的技能用在每一件叫做Sigils的事情上。五个都做完了，我们就可以过去了。");
		cm.dispose();
	   }
	   break;
	case 610030300:
	   if (status == 0) {
		cm.sendNext("现在我们这里有更多的信号。所有五个冒险家都必须爬到最顶端并通过入口。");
	   } else if (status == 1) {
		cm.sendNext("当心这些死亡陷阱：门希尔。他们真的很厉害。");
		cm.dispose();
	   }
	   break;
	case 610030400:
	   if (status == 0) {
		cm.sendNext("现在我们这里有更多的信号。然而，其中一些不起作用。");
	   } else if (status == 1) {
		cm.sendNext("这些麻烦会妨碍你，但它们只是分散你的注意力。试着每一个信号直到它们起作用。");
		cm.dispose();
	   }
	   break;
	case 610030500:
	   if (status == 0) {
		cm.sendNext("你居然能走这么远！你在这里看到的是克里森伍德的雕像，但没有任何武器。");
	   } else if (status == 1) {
		cm.sendNext("雕像周围有五个房间，每个房间附近都有一座雕像。");
	   } else if (status == 2) {
		cm.sendNext("我怀疑每个房间都有雕像的五件武器之一。");
	   } else if (status == 3) {
		cm.sendNext("把武器带回来，把它们恢复到掌握的遗迹！");
		cm.dispose();
	   }
	   break;
	case 610030700:
	   cm.sendNext("那是个不错的工作！这条路通向扭曲的主人的军械库。");
	   cm.dispose();
	   break;
    }
}