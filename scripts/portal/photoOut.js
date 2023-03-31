/* ==================
 脚本类型:  传送门    
 版权：游戏盒团队     
 =====================
 */
 function enter(pi) {
	var returnMap = pi.getSavedLocation("DONGDONGCHIANG");
	if (returnMap < 0) {
		returnMap = 100000000;
	}
	pi.clearSavedLocation("DONGDONGCHIANG");
	pi.warp(returnMap,0);
	return true;
}