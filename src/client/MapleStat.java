package client;

import constants.GameConstants;

public enum MapleStat {
    SKIN(0x1),
    FACE(0x2),
    HAIR(0x4),
    PET(0x8),
    LEVEL(0x10),
    JOB(0x20),
    STR(0x40),
    DEX(0x80),
    INT(0x100),
    LUK(0x200),
    HP(0x400),
    MAXHP(0x800),
    MP(0x1000),
    MAXMP(0x2000),
    AVAILABLEAP(0x4000),
    AVAILABLESP(0x8000),
    EXP(0x10000),
    FAME(0x20000),
    MESO(0x40000),
    FATIGUE(0x80000),
    CHARISMA(0x100000),
    INSIGHT(0x200000),
    WILL(0x400000),
    CRAFT(0x8000000),
    SENSE(0x1000000),
    CHARM(0x2000000),
    TRAIT_LIMIT(0x4000000),
    BATTLE_EXP(0x8000000),
    BATTLE_RANK(0x10000000),
    BATTLE_POINTS(0x20000000),
    ICE_GAGE(0x40000000),
    GACHAPONEXP(0x80000000),
    VIRTUE(0x80000000),;

    private final long i;

    private MapleStat(long i) {
        this.i = i;
    }

    public long getValue() {
        return i;
    }

    public static final MapleStat getByValue(final long value) {
        for (final MapleStat stat : MapleStat.values()) {
            if (stat.i == value) {
                return stat;
            }
        }
        return null;
    }

    public static enum Temp {
        STR(0x1),
        DEX(0x2),
        INT(0x4),
        LUK(0x8),
        WATK(0x10),
        WDEF(0x20),
        MATK(0x40),
        MDEF(0x80),
        ACC(0x100),
        AVOID(0x200),
        SPEED(0x400),
        JUMP(0x800);

        private final int i;

        private Temp(int i) {
            this.i = i;
        }

        public int getValue() {
            return i;
        }
    }
}
