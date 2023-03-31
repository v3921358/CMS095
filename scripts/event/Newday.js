
var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
    var nextTime = 15 * 1000;//cal.getTimeInMillis();
    nextTime += java.lang.System.currentTimeMillis();
    setupTask = em.scheduleAtTimestamp("start", nextTime);

}

function cancelSchedule() {
    if (setupTask != null)
        setupTask.cancel(true);
}

function start() {
    scheduleNew()
    var hour = new Date().getHours();
    var minute = new Date().getMinutes();
    var seconds = new Date().getSeconds();
    //每天第一个喇叭提醒。
    if (hour == 23 && minute == 59) {
        em.broadcastServerMsg(5120008, "各位冒险家，新的一天即将开始了！", true);
        resettingFbLog("副本1");
        resettingFbLog("副本2");
        resettingFbLog("副本3");
        resettingFbLog("家族任务");
        var fbcount = [1, 2, 3, 4, 5, 6, 7, 8];
        //var jzcount = [1,2,3,4,5,6];
        var 副本个数 = getRandomNumberByRange(2, 4);
        var 副本 = getRandomArrayElements(fbcount, 副本个数);
        var 家族任务需求物品 = Math.floor(Math.random() * 6) + 1;
        setFbLog("家族任务", 1, 家族任务需求物品);
        for (var i = 0; i < 副本.length; i++) {
            setFbLog("副本" + (i + 1) + "", 1, 副本[i]);
            setFbLog("副本" + (i + 1) + "", 2, getRandomNumberByRange(1, 3));
        }

    }

}
function monsterDrop(eim, player, mob) {}

function getRandomArrayElements(arr, count) {
    var shuffled = arr.slice(0), i = arr.length, min = i - count, temp, index;
    while (i-- > min) {
        index = Math.floor((i + 1) * Math.random());
        temp = shuffled[index];
        shuffled[index] = shuffled[i];
        shuffled[i] = temp;
    }
    return shuffled.slice(min);
}

function getRandomNumberByRange(start, end) {

    return Math.floor(Math.random() * (end - start) + start)
}


function getFbLog(bossname, ints) {
    var con = em.getDataSource().getConnection();
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
    var con = em.getDataSource().getConnection();
    var pointss = 0;
    var ps = con.prepareStatement("SELECT * FROM fblog WHERE bossname = ?");
    ps.setString(1, bossname);
    var rs = ps.executeQuery();
    if (type == 1) {
        if (rs.next()) {
            pointss = rs.getInt("points");
            var ps = con.prepareStatement("UPDATE fblog SET points = ? WHERE bossname = ?");
            ps.setInt(1, ints );
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
            ps.setInt(1, ints );
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

function resettingFbLog(bossname) {
    var con = em.getDataSource().getConnection();
    var pointss = 0;
    var ps = con.prepareStatement("SELECT * FROM fblog WHERE bossname = ?");
    ps.setString(1, bossname);
    var rs = ps.executeQuery();
    if (rs.next()) {
        var ps = con.prepareStatement("UPDATE fblog SET points = ? WHERE bossname = ?");
        ps.setInt(1, 0);
        ps.setString(2, bossname);
        ps.executeUpdate();
        var ps = con.prepareStatement("UPDATE fblog SET count = ? WHERE bossname = ?");
        ps.setInt(1, 0);
        ps.setString(2, bossname);
        ps.executeUpdate();
    } else {
        rs.close();
        ps.close();
        con.close();
    }
    rs.close();
    ps.close();
    con.close();
}
