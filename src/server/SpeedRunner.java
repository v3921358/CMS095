package server;

import database.DBConPool;
import handling.world.exped.ExpeditionType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import tools.FileoutputUtil;
import tools.Pair;
import tools.StringUtil;
import tools.Triple;

public class SpeedRunner {

    private static final Map<ExpeditionType, Triple<String, Map<Integer, String>, Long>> speedRunData = new EnumMap<ExpeditionType, Triple<String, Map<Integer, String>, Long>>(ExpeditionType.class);

    public final static Triple<String, Map<Integer, String>, Long> getSpeedRunData(ExpeditionType type) {
        return speedRunData.get(type);
    }

    public final static void addSpeedRunData(ExpeditionType type, Pair<StringBuilder, Map<Integer, String>> mib, long tmp) {
        speedRunData.put(type, new Triple<String, Map<Integer, String>, Long>(mib.getLeft().toString(), mib.getRight(), tmp));
    }

    public final static void removeSpeedRunData(ExpeditionType type) {
        speedRunData.remove(type);
    }

    public final static void loadSpeedRuns() {
        if (speedRunData.size() > 0) {
            return;
        }
        for (ExpeditionType type : ExpeditionType.values()) {
            loadSpeedRunData(type);
        }
    }

    public static String getTypeName(final String name) {
        switch (name) {
            case "Normal_Balrog":
                return "巴洛古";
            case "Horntail":
                return "闇黑龍王";
            case "Zakum":
                return "殘暴炎魔";
            case "Chaos_Zakum":
                return "混沌殘暴炎魔";
            case "ChaosHT":
                return "混沌闇黑龍王";
            case "Pink_Bean":
                return "品克缤";
            case "Von_Leon":
                return "凡雷恩";
            case "Cygnus":
                return "西格諾斯";
            case "Hilla":
                return "希拉";
            case "Chaos_Pink_Bean":
                return "混沌品克缤";
            case "DoJo":
                return "武陵到場";
        }
        return "";
    }

    public final static String getPreamble(ExpeditionType type) {
        return "#r挑战" + StringUtil.makeEnumHumanReadable(getTypeName(type.name())).toUpperCase() + "#k\r\n\r\n";
    }

    public final static void loadSpeedRunData(ExpeditionType type) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM speedruns WHERE type = ? ORDER BY time LIMIT 25"); //or should we do less
            ps.setString(1, type.name());
            StringBuilder ret = new StringBuilder(getPreamble(type));
            Map<Integer, String> rett = new LinkedHashMap<Integer, String>();
            ResultSet rs = ps.executeQuery();
            int rank = 1;
            Set<String> leaders = new HashSet<String>();
            boolean cont = rs.first();
            boolean changed = cont;
            long tmp = 0;
            while (cont) {
                if (!leaders.contains(rs.getString("leader"))) {
                    addSpeedRunData(ret, rett, rs.getString("members"), rs.getString("leader"), rank, rs.getString("timestring"));
                    rank++;
                    leaders.add(rs.getString("leader"));
                    tmp = rs.getLong("time");
                }
                cont = rs.next() && rank < 25;
            }
            rs.close();
            ps.close();
            if (changed) {
                speedRunData.put(type, new Triple<String, Map<Integer, String>, Long>(ret.toString(), rett, tmp));
            }
        } catch (SQLException e) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            e.printStackTrace();
        }
    }

    public final static Pair<StringBuilder, Map<Integer, String>> addSpeedRunData(StringBuilder ret, Map<Integer, String> rett, String members, String leader, int rank, String timestring) {
        StringBuilder rettt = new StringBuilder();
        String[] membrz = members.split(",");
        rettt.append("#b他們是由隊長 " + leader + "帶領的队伍 排名 " + rank + ".#k\r\n\r\n");
        for (int i = 0; i < membrz.length; i++) {
            rettt.append("#r#e");
            rettt.append(i + 1);
            rettt.append(".#n ");
            rettt.append(membrz[i]);
            rettt.append("#k\r\n");
        }
        rett.put(rank, rettt.toString());
        ret.append("#b#L").append(rank).append("#排名 #e").append(rank).append("#n#k : ").append(leader).append(", 在 ").append(timestring);
        if (membrz.length > 1) {
            ret.append("#l");
        }
        ret.append("\r\n");
        return new Pair<StringBuilder, Map<Integer, String>>(ret, rett);
    }
}
