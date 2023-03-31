function enter(pi) {
    var em = pi.getEventManager("HorntailBattle");
    var ema = pi.getEventManager("ChaosHorntail");
    if (em != null) {
        var prop = em.getProperty("preheadCheck");

        if (prop != null && prop.equals("0")) {
            //pi.mapMessage(6, "暗黑龙王吼了一声，你必須杀死暗黑龙王的左头颅，才能进入下一关。")
            em.setProperty("preheadCheck", "1");
        }
    }
    if (ema != null) {
        var prop = ema.getProperty("preheadCheck");

        if (prop != null && prop.equals("0")) {
            //pi.mapMessage(6, "混沌暗黑龙王吼了一声，你必須杀死混沌暗黑龙王的左头颅，才能进入下一关。")
            ema.setProperty("preheadCheck", "1");
        }
    }
}