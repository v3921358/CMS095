function act() {
	var em = rm.getEventManager("Pirate");
	if (em != null) {
		rm.mapMessage(6, "其中一个箱子被激活了.");
		em.setProperty("stage5", "" + (parseInt(em.getProperty("stage5")) + 1));
	}
}