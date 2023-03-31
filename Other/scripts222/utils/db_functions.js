/**
 * 数据库操作相关方法函数
 */
function 获取BossLog(boss, id) {
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth() + 1;
	var days = myDate.getDate();
	var con = cm.getDataSource().getConnection();
	var count = 0;
	var ps;
	var day = "" + year + "-" + month + "-" + days + "";
	ps = con.prepareStatement("SELECT COUNT(*) FROM bosslog WHERE characterid = ? AND bossid = ? AND lastattempt >= ?");
	ps.setInt(1, id);
	ps.setString(2, boss);
	ps.setString(3, day);
	var rs = ps.executeQuery();
	if (rs.next()) {
		count = rs.getInt(1);
	} else {
		count = -1;
	}
	rs.close();
	ps.close();
	con.close();
	return count;
}

function getFbLog(bossname, ints) {
	var con = cm.getDataSource().getConnection();
	var count = 0;
	var ps = con.prepareStatement("SELECT * FROM fblog WHERE bossname = ?");
	ps.setString(1, bossname);
	var rs = ps.executeQuery();
	if (ints == 1) {
		if (rs.next()) {
			count = rs.getInt("points");
		} else {
			count = -1
		}
	}
	if (ints == 2) {
		if (rs.next()) {
			count = rs.getInt("count");
		} else {
			count = -1
		}
	}
	rs.close();
	ps.close();
	con.close();
	return count;
}


function setFbLog(bossname, type, ints) {
	var con = cm.getDataSource().getConnection();
	var pointss = 0;
	var ps = con.prepareStatement("SELECT * FROM fblog WHERE bossname = ?");
	ps.setString(1, bossname);
	var rs = ps.executeQuery();
	if (type == 1) {
		if (rs.next()) {
			pointss = rs.getInt("points");
			var ps = con.prepareStatement("UPDATE fblog SET points = ? WHERE bossname = ?");
			ps.setInt(1, ints + pointss);
			ps.setString(2, bossname);
			ps.executeUpdate();
		} else {
			var ps = con.prepareStatement("insert into fblog  (bossname,points,count) values (?,?,?)");
			ps.setString(1, bossname);
			ps.setInt(2, ints);
			ps.setInt(3, 0);
			ps.executeUpdate();
		}
	}
	if (type == 2) {
		if (rs.next()) {
			pointss = rs.getInt("count");
			var ps = con.prepareStatement("UPDATE fblog SET count = ? WHERE bossname = ?");
			ps.setInt(1, ints + pointss);
			ps.setString(2, bossname);
			ps.executeUpdate();
		} else {
			var ps = con.prepareStatement("insert into fblog  (bossname,points,count) values (?,?,?)");
			ps.setString(1, bossname);
			ps.setInt(2, 0);
			ps.setInt(3, ints);
			ps.executeUpdate();
		}
	}
	rs.close();
	ps.close();
	con.close();
}

function getAccCid(bossid) {
	var con = cm.getDataSource().getConnection();
	var count = [];
	var counts = 0;
	var countss = 0;
	var ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
	ps.setInt(1, bossid);
	var rs = ps.executeQuery();
	if (rs.next()) {
		counts = rs.getInt("accountid");
	} else {
		return undefined;
		rs.close();
		ps.close();
		con.close();
	}
	var ps1 = con.prepareStatement("SELECT COUNT(*) FROM characters WHERE accountid = ?");
	ps1.setInt(1, counts);
	var ps2 = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
	ps2.setInt(1, counts);
	var rs1 = ps1.executeQuery();
	var rs2 = ps2.executeQuery();
	if (rs1.next()) {
		countss = rs1.getInt(1);
	} else {
		counts = -1;
	}
	for (var i = 0; i < countss; i++) {
		if (rs2.next()) {
			count.push(rs2.getInt("id"));
		}
	}
	rs2.close();
	ps2.close();
	rs1.close();
	ps1.close();
	rs.close();
	ps.close();
	con.close();
	return count;
}
/**
 * 检查发型或脸型代码是否存在
 * @param {*} code 代码
 * @param {*} type 1为发型，2为脸型
 */
function checkHaveCode(code, type) {
	var res = false;
	var con = cm.getDataSource().getConnection();
	if (type == 1) {
		var ps = con.prepareStatement("SELECT * FROM wz_hairdata WHERE hairid = ?");
		ps.setInt(1, code);
	} else {
		var ps = con.prepareStatement("SELECT * FROM wz_facedata WHERE faceid = ?");
		ps.setInt(1, code);
	}
	var rs = ps.executeQuery();
	if (rs.next()) {
		res = true;
	}
	rs.close();
	ps.close();
	con.close();
	return res;
}
/**
 * 随机获取指定数量的发型或脸型代码
 * @param {*} num 获取数量
 * @param {*} type 1为发型，2为脸型
 */
function getRundomCodeList(num, type) {
	var res = [];
	var con = cm.getDataSource().getConnection();
	if (type == 1) {
		var ps = con.prepareStatement("SELECT hairid FROM wz_hairdata ORDER BY RAND() LIMIT ?");
		ps.setInt(1, num);
	} else {
		var ps = con.prepareStatement("SELECT faceid FROM wz_facedata ORDER BY RAND() LIMIT ?");
		ps.setInt(1, num);
	}
	var rs = ps.executeQuery();
	while (rs.next()) {
		if (type == 1) {
			res.push(rs.getInt("hairid"));
		} else {
			res.push(rs.getInt("faceid"));
		}
	}
	rs.close();
	ps.close();
	con.close();
	return res;
}
/**
 * 随机获取指定数量的发型或脸型代码
 * @param {*} num 获取数量
 * @param {*} type 1为发型，2为脸型
 */
 function getbtNumCodeList(start, end,type) {
	var res = [];
	var con = cm.getDataSource().getConnection();
	if (type == 1) {
		var ps = con.prepareStatement("SELECT hairid FROM wz_hairdata where hairid between ? and ? order by hairid");
		ps.setInt(1, start);
		ps.setInt(2, end);
	} else {
		var ps = con.prepareStatement("SELECT faceid FROM wz_facedata ORDER BY RAND() LIMIT ?");
		ps.setInt(1, num);
	}
	var rs = ps.executeQuery();
	while (rs.next()) {
		if (type == 1) {
			res.push(rs.getInt("hairid"));
		} else {
			res.push(rs.getInt("faceid"));
		}
	}
	rs.close();
	ps.close();
	con.close();
	return res;
}