package client;

import java.util.Arrays;
import java.util.List;

public class MapleFriendship {
    private MapleFriendshipType type;
    private int totalPoints = 0;
    
    public static enum MapleFriendshipType {
        JoeJoe(9410165, 1202023, 1202024, 1202025, 1202026, 1202043, 1202039),
        Hermonniny(9410166, 1202027, 1202028, 1202029, 1202030, 1202044, 1202040),
        LittleDragon(9410167, 1202031, 1202032, 1202033, 1202034, 1202045, 1202041),
        Ika(9410168, 1202035, 1202036, 1202037, 1202038, 1202046, 1202042);
        
        final int mobid;
        public List<Integer> id;

        private MapleFriendshipType(int type, Integer... ids) {
            this.mobid = type;
            this.id = Arrays.asList(ids);
        }

        public int getMobId() {
            return mobid;
        }

        public static MapleFriendshipType getById(int q) {
            for (MapleFriendshipType f : MapleFriendshipType.values()) {
                if (f.id.contains(q)) {
                    return f;
                }
            }
            return JoeJoe; // default
        }

        public static MapleFriendshipType getByName(String q) {
            for (MapleFriendshipType f : MapleFriendshipType.values()) {
                if (f.name().equals(q)) {
                    return f;
                }
            }
            return null;
        }
    }

    public MapleFriendship(MapleFriendshipType t) {
        this.type = t;
    }

    public void setPoints(int e) {
        this.totalPoints = e;
    }

    public void addPoints(int e) {
        this.totalPoints += e;
    }

    public int getPoints() {
        return totalPoints;
    }

    public MapleFriendshipType getType() {
        return type;
    }
}
