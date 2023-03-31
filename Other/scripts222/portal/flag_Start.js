/* 
 * 
   
   
 */
function enter(pi) {
	if (pi.getMapId() == 932200100) {
		var em = pi.getEventManager("PQS_7");
		if (em == null) {
			pi.playerMessage(5, "副本出错，请联系管理员");
			return;
		}
		if (em.getProperty("gate") == "2") {
			pi.warp(932200100, 16);
		} else {
			pi.playerMessage(5, "不要着急，还没开始哦！");
			return;
		}
	} else {
		if (pi.getMapId() == 932200200) {
			var em = pi.getEventManager("PQS_9");
			if (em == null) {
				pi.playerMessage(5, "副本出错，请联系管理员");
				return;
			}
			if (em.getProperty("gate") == "2") {
				pi.warp(932200100, 16);
			} else {
				pi.playerMessage(5, "不要着急，还没开始哦！");
				return;
			}
		}
	}
}