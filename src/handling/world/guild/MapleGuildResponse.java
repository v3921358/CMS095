package handling.world.guild;

import tools.packet.MaplePacketCreator;

public enum MapleGuildResponse {
    ALREADY_IN_GUILD(0x2E),
    NOT_IN_CHANNEL(0x30),
    NOT_IN_GUILD(0x33);
    
    private int value;

    private MapleGuildResponse(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public byte[] getPacket() {
        return MaplePacketCreator.genericGuildMessage((byte) value);
    }
}
