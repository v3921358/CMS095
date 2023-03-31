package handling.channel.handler;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import server.maps.AnimatedMapleMapObject;
import server.movement.*;
import tools.FileoutputUtil;
import tools.data.LittleEndianAccessor;

public class MovementParse {//1 = player, 2 = mob, 3 = pet, 4 = summon, 5 = dragon

    public static List<LifeMovementFragment> parseMovement(final LittleEndianAccessor lea, final int kind) {
        final List<LifeMovementFragment> res = new ArrayList<>();
        final byte numCommands = (byte) lea.readUByte();
        if (numCommands == 0) {
            return null;
        }
        for (byte i = 0; i < numCommands; i++) {
            final byte command = lea.readByte();
            switch (command) {
                case 0:
                case 6:
                case 0xD:
                case 0xF:
                case 0x25:
                case 0x26: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xwobble = lea.readShort();
                    final short ywobble = lea.readShort();
                    final short unk = lea.readShort();
                    short fh = 0;
                    if (command == 0xD) {
                        fh = lea.readShort();
                    }
                    short xoffset = lea.readShort();
                    short yoffset = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();

                    final AbsoluteLifeMovement alm = new AbsoluteLifeMovement(command, new Point(xpos, ypos), duration, newstate);
                    alm.setUnk(unk);
                    alm.setFh(fh);
                    alm.setPixelsPerSecond(new Point(xwobble, ywobble));
                    alm.setOffset(new Point(xoffset, yoffset));
                    res.add(alm);
                    break;
                }
                case 0x24: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xoffset = lea.readShort();
                    final short yoffset = lea.readShort();
                    final short unk = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();

                    final JumpDownMovement jd = new JumpDownMovement(command, new Point(xpos, ypos), duration, newstate);
                    jd.setOffset(new Point(xoffset, yoffset));
                    jd.setUnk(unk);
                    res.add(jd);
                    break;
                }
                case 1:
                case 2:
                case 0xE:
                case 0x11:
                case 0x13:
                case 0x20:
                case 0x21:
                case 0x22:
                case 0x23: {
                    final short xmod = lea.readShort();
                    final short ymod = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();

                    final RelativeLifeMovement rlm = new RelativeLifeMovement(command, new Point(xmod, ymod), duration, newstate);
                    res.add(rlm);
                    break;
                }
                case 0x10:
                case 0x15:
                case 0x16:
                case 0x17:
                case 0x18:
                case 0x19:
                case 0x1A:
                case 0x1B:
                case 0x1C:
                case 0x1D:
                case 0x1E:
                case 0x1F: {
                    final byte newstate = lea.readByte();
                    final short unk = lea.readShort();
                    final GroundMovement am = new GroundMovement(command, new Point(0, 0), unk, newstate);
                    res.add(am);
                    break;
                }
                case 3:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                case 0xB: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short fh = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();
                    final TeleportMovement tm = new TeleportMovement(command, new Point(xpos, ypos), duration, newstate);
                    tm.setFh(fh);
                    res.add(tm);
                    break;
                }
                case 0x0C: {
                    final short xwobble = lea.readShort();
                    final short ywobble = lea.readShort();
                    short fh = lea.readShort();
                    byte newstate = lea.readByte();
                    short duration = lea.readShort();
                    JumpDownMovement jump = new JumpDownMovement(command, new Point(xwobble, ywobble), duration, newstate);
                    jump.setFH(fh);
                    jump.setPixelsPerSecond(new Point(xwobble, ywobble));
                    res.add(jump);
                    break;
                }
                case 0x12: {
                    final short xpos = lea.readShort();
                    final short ypos = lea.readShort();
                    final short xoffset = lea.readShort();
                    final short yoffset = lea.readShort();
                    final byte newstate = lea.readByte();
                    final short duration = lea.readShort();

                    final BounceMovement bm = new BounceMovement(command, new Point(xpos, ypos), duration, newstate);
                    bm.setOffset(new Point(xoffset, yoffset));
                    res.add(bm);
                    break;
                }

                case 0xA: { // Update Equip or Dash
                    res.add(new ChangeEquipSpecialAwesome(command, lea.readByte()));
                    break;
                }
                default:
                    //System.out.println("Kind movement: " + kind + ", Remaining : " + (numCommands - res.size()) + " New type of movement ID : " + command + ", packet : " + lea.toString(true));
                    FileoutputUtil.log(FileoutputUtil.Movement_Log, "Kind movement: " + kind + ", Remaining : " + (numCommands - res.size()) + " New type of movement ID : " + command + ", packet : " + lea.toString(true));
                    return null;
            }
        }
        double skip = lea.readByte();
        skip = Math.ceil(skip / 2.0D);
        lea.skip((int) skip);
        if (numCommands != res.size()) {
            //System.out.println("循環次數[" + numCommands + "]和實際上获取的循環次數[" + res.size() + "]不符");
            FileoutputUtil.logToFile("logs/移動封包出錯.txt", "循環次數[" + numCommands + "]和實際上获取的循環次數[" + res.size() + "]不符 " + "移動封包 剩餘次數: " + (numCommands - res.size()) + "  封包: " + lea.toString(true));
            return null; // Probably hack
        }
        return res;
    }

    public static void updatePosition(final List<LifeMovementFragment> movement, final AnimatedMapleMapObject target, final int yoffset) {
        if (movement == null) {
            return;
        }
        for (final LifeMovementFragment move : movement) {
            if (move instanceof LifeMovement) {
                if (move instanceof AbsoluteLifeMovement) {
                    final Point position = ((LifeMovement) move).getPosition();
                    position.y += yoffset;
                    target.setPosition(position);
                }
                target.setStance(((LifeMovement) move).getNewstate());
            }
        }
    }
}
