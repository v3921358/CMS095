package handling.world.exped;

public enum ExpeditionType {
    DoJo(15, 1000, 1, 200),
    Normal_Balrog(15, 2001, 50, 200),
    Horntail(30, 2003, 80, 200),
    Zakum(30, 2002, 50, 200),
    Chaos_Zakum(30, 2005, 100, 200),
    ChaosHT(30, 2006, 110, 200),
    Pink_Bean(30, 2004, 140, 200),
    CWKPQ(30, 2007, 90, 200),
    Von_Leon(30, 2008, 120, 200),
    Cygnus(18, 2009, 170, 200),
    Arkarium(6, 2010, 120, 200),
    Hilla(6, 2011, 70, 120),
    Chaos_Pink_Bean(30, 2012, 170, 200),
    BossBloody(30, 2013, 120, 200),
    BossPierre(30, 2014, 120, 200),
    BossBanban(30, 2015, 120, 200),
    BossBelen(30, 2016, 120, 200),
    BossMagnus(30, 2017, 120, 200),
    ZHilla(30, 2018, 120, 200);

    public int maxMembers, maxParty, exped, minLevel, maxLevel;

    private ExpeditionType(int maxMembers, int exped, int minLevel, int maxLevel) {
        this.maxMembers = maxMembers;
        this.exped = exped;
        this.maxParty = (maxMembers / 2) + (maxMembers % 2 > 0 ? 1 : 0);
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public static ExpeditionType getById(int id) {
        for (ExpeditionType pst : ExpeditionType.values()) {
            if (pst.exped == id) {
                return pst;
            }
        }
        return null;
    }
}
