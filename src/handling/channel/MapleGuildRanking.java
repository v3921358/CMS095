package handling.channel;

import database.DBConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tools.FileoutputUtil;

public class MapleGuildRanking {

    private static MapleGuildRanking instance = new MapleGuildRanking();
    private List<GuildRankingInfo> ranks = new LinkedList<GuildRankingInfo>();
    private final List<levelRankingInfo> ranks1 = new LinkedList<>();
    private final List<mesoRankingInfo> ranks2 = new LinkedList<>();
    private final Map<Integer, List<JobRankingInfo>> JobRanks = new HashMap();

    public static MapleGuildRanking getInstance() {
        return instance;
    }

    public void load() {
        if (ranks.isEmpty()) {
            reload();
        }
    }

    public List<JobRankingInfo> getJobRank(int type) {
        if (JobRanks.get(type) == null || JobRanks.get(type).isEmpty()) {
            loadJobRank(type);
        }
        return JobRanks.get(type);
    }

    public List<GuildRankingInfo> getRank() {
        return ranks;
    }

    public List<levelRankingInfo> getLevelRank() {
        if (ranks1.isEmpty()) {
            showLevelRank();
        }
        return ranks1;
    }

    public List<mesoRankingInfo> getMesoRank() {
        if (ranks2.isEmpty()) {
            showMesoRank();
        }
        return ranks2;
    }

    private void loadJobRank(int type) {
        if (JobRanks.get(type) != null) {
            JobRanks.get(type).clear();
        }
        String jobRange = "";

        if (type == 1) {
            jobRange = "and job >= '100' and job <= '132'";
        } else if (type == 2) {
            jobRange = "and job >= '200' and job <= '232'";
        } else if (type == 3) {
            jobRange = "and job >= '300' and job <= '322'";
        } else if (type == 4) {
            jobRange = "and job >= '400' and job <= '432'";
        } else if (type == 5) {
            jobRange = "and job >= '500' and job <= '522'";
        } else if (type == 6) {
            jobRange = "and job >= '2100' and job <= '2112'";
        } else if (type == 7) {
            jobRange = "and job >= '1000' and job <= '1512'";
        } else if (type == 8) {
            jobRange = "and job >= '3000' and job <= '3512'";
        } else if (type == 9) {
            jobRange = "and job >= '2200' and job <= '2218'";
        }

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm = 0 " + jobRange + " and accountid in (select id from accounts where banned= '0') ORDER BY `level` DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            LinkedList<JobRankingInfo> JobRankList = new LinkedList();
            while (rs.next()) {
                final JobRankingInfo JobRank = new JobRankingInfo(
                        rs.getString("name"),
                        rs.getInt("level"),
                        rs.getInt("job"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"));
                JobRankList.add(JobRank);
            }
            JobRanks.put(type, JobRankList);
            ps.close();
            rs.close();
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
            System.err.println("未能显示职业" + type + "排行");
        }
    }

    private void showLevelRank() {
        ranks1.clear();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM characters WHERE gm < 1 ORDER BY `level` DESC LIMIT 100");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                final levelRankingInfo rank1 = new levelRankingInfo(
                        rs.getString("name"),
                        rs.getInt("level"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"));
                ranks1.add(rank1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.err.println("未能显示等級排行");
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
    }

    private void reload() {
        ranks.clear();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM guilds ORDER BY `GP` DESC LIMIT 50");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                final GuildRankingInfo rank = new GuildRankingInfo(
                        rs.getString("name"),
                        rs.getInt("GP"),
                        rs.getInt("logo"),
                        rs.getInt("logoColor"),
                        rs.getInt("logoBG"),
                        rs.getInt("logoBGColor"));

                ranks.add(rank);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            System.err.println("Error handling guildRanking");
            e.printStackTrace();
        }
    }

    private void showMesoRank() {
        ranks2.clear();
        ResultSet rs;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT *, ( chr.meso + s.meso ) as money FROM `characters` as chr , `storages` as s WHERE chr.gm < 1  AND s.accountid = chr.accountid ORDER BY money DESC LIMIT 20")) {
            rs = ps.executeQuery();
            while (rs.next()) {
                final mesoRankingInfo rank2 = new mesoRankingInfo(
                        rs.getString("name"),
                        rs.getLong("money"),
                        rs.getInt("str"),
                        rs.getInt("dex"),
                        rs.getInt("int"),
                        rs.getInt("luk"));
                ranks2.add(rank2);
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("未能显示財產排行");
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
    }

    public static class levelRankingInfo {

        private final String name;
        private final int level, str, dex, _int, luk;

        public levelRankingInfo(String name, int level, int str, int dex, int intt, int luk) {
            this.name = name;
            this.level = level;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getStr() {
            return str;
        }

        public int getDex() {
            return dex;
        }

        public int getInt() {
            return _int;
        }

        public int getLuk() {
            return luk;
        }
    }

    public static class mesoRankingInfo {

        private final String name;
        private final long meso;
        private final int str, dex, _int, luk;

        public mesoRankingInfo(String name, long meso, int str, int dex, int intt, int luk) {
            this.name = name;
            this.meso = meso;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }

        public String getName() {
            return name;
        }

        public long getMeso() {
            return meso;
        }

        public int getStr() {
            return str;
        }

        public int getDex() {
            return dex;
        }

        public int getInt() {
            return _int;
        }

        public int getLuk() {
            return luk;
        }
    }

    public static class GuildRankingInfo {

        private String name;
        private int gp, logo, logocolor, logobg, logobgcolor;

        public GuildRankingInfo(String name, int gp, int logo, int logocolor, int logobg, int logobgcolor) {
            this.name = name;
            this.gp = gp;
            this.logo = logo;
            this.logocolor = logocolor;
            this.logobg = logobg;
            this.logobgcolor = logobgcolor;
        }

        public String getName() {
            return name;
        }

        public int getGP() {
            return gp;
        }

        public int getLogo() {
            return logo;
        }

        public int getLogoColor() {
            return logocolor;
        }

        public int getLogoBg() {
            return logobg;
        }

        public int getLogoBgColor() {
            return logobgcolor;
        }
    }

    public static class ItemRankingInfo {

        private final String name;
        private final int level, quantity, job;

        public ItemRankingInfo(String name, int level, int job, int quantity) {
            this.name = name;
            this.level = level;
            this.job = job;
            this.quantity = quantity;

        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getQuantity() {
            return quantity;
        }

        public int getJob() {
            return job;
        }
    }

    public static class JobRankingInfo {

        private final String name;
        private final int level, str, dex, _int, luk, job;

        public JobRankingInfo(String name, int level, int job, int str, int dex, int intt, int luk) {
            this.name = name;
            this.level = level;
            this.job = job;
            this.str = str;
            this.dex = dex;
            this._int = intt;
            this.luk = luk;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getStr() {
            return str;
        }

        public int getDex() {
            return dex;
        }

        public int getInt() {
            return _int;
        }

        public int getLuk() {
            return luk;
        }

        public int getJob() {
            return job;
        }
    }
}
