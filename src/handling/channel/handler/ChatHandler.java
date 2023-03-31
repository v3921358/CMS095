package handling.channel.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.messages.CommandProcessor;
import constants.ServerConstants;
import constants.ServerConstants.CommandType;
import handling.channel.ChannelServer;
import handling.world.MapleMessenger;
import handling.world.MapleMessengerCharacter;
import handling.world.World;
import java.util.Arrays;
import tools.BitTools;
import tools.CPUSampler;
import tools.CollectionUtil;
import tools.FileoutputUtil;
import tools.SearchGenerator;
import tools.packet.MaplePacketCreator;
import tools.data.LittleEndianAccessor;

public class ChatHandler {

    public static final void GeneralChat(final String text, final byte unk, final MapleClient c, final MapleCharacter chr) {
        if (text.length() > 0 && chr != null && chr.getMap() != null && !CommandProcessor.processCommand(c, text, CommandType.NORMAL)) {
            if (!chr.isIntern() && text.length() >= 80) {
                return;
            }
            if (CollectionUtil.copyFirst(text) == 1) {
                return;
            }
            if (chr.getCanTalk() || chr.isStaff()) {
                //Note: This patch is needed to prevent chat packet from being broadcast to people who might be packet sniffing.
                if (chr.isHidden()) {
                    if (chr.isIntern() && !chr.isSuperGM() && unk == 0) {
                        chr.getMap().broadcastGMMessage(chr, MaplePacketCreator.getChatText(chr.getId(), text, false, (byte) 1), true);
                        if (unk == 0) {
                            chr.getMap().broadcastGMMessage(chr, MaplePacketCreator.serverNotice(2, chr.getName() + " : " + text), true);
                        }
                    } else {
                        chr.getMap().broadcastGMMessage(chr, MaplePacketCreator.getChatText(chr.getId(), text, c.getPlayer().isSuperGM(), unk), true);
                    }
                } else {
                    chr.getCheatTracker().checkMsg();
                    if (chr.isIntern() && !chr.isSuperGM() && unk == 0) {
                        chr.getMap().broadcastMessage(MaplePacketCreator.getChatText(chr.getId(), text, false, (byte) 1), c.getPlayer().getTruePosition());
                        if (unk == 0) {
                            chr.getMap().broadcastMessage(MaplePacketCreator.serverNotice(2, chr.getName() + " : " + text), c.getPlayer().getTruePosition());
                        }
                    } else {
                        chr.getMap().broadcastMessage(MaplePacketCreator.getChatText(chr.getId(), text, c.getPlayer().isSuperGM(), unk), c.getPlayer().getTruePosition());
                    }
                }
                if (ServerConstants.logs_chat) {
                    FileoutputUtil.logToFile("logs/聊天/普通聊天.txt", "\r\n" + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 『" + chr.getName() + "』 地图『" + chr.getMapId() + "』：  " + text);
                }
                if (ServerConstants.message_generalchat) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』普聊：  " + text));
                }
            } else {
                c.getSession().write(MaplePacketCreator.serverNotice(6, "在这个地方不能说话。"));
            }
        }
    }

    public static final void Others(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final int type = slea.readByte();
        final byte numRecipients = slea.readByte();
        if (numRecipients <= 0) {
            return;
        }
        int recipients[] = new int[numRecipients];

        for (byte i = 0; i < numRecipients; i++) {
            recipients[i] = slea.readInt();
        }
        final String chattext = slea.readMapleAsciiString();
        if (SearchGenerator.foundData(chattext) == 1) {
            return;
        }
        if (chr == null || !chr.getCanTalk()) {
            c.getSession().write(MaplePacketCreator.serverNotice(6, "在这个地方不能说话。"));
            return;
        }

        if (c.isMonitored()) {
            String chattype = "Unknown";
            switch (type) {
                case 0:
                    chattype = "Buddy";
                    break;
                case 1:
                    chattype = "Party";
                    break;
                case 2:
                    chattype = "Guild";
                    break;
                case 3:
                    chattype = "Alliance";
                    break;
                case 4:
                    chattype = "Expedition";
                    break;
            }
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM Message] " + MapleCharacterUtil.makeMapleReadable(chr.getName())
                    + " said (" + chattype + "): " + chattext));

        }
        if (chattext.length() <= 0 || CommandProcessor.processCommand(c, chattext, CommandType.NORMAL)) {
            return;
        }
        chr.getCheatTracker().checkMsg();
        switch (type) {
            case 0:
                if (ServerConstants.logs_chat) {
                    FileoutputUtil.logToFile("logs/聊天/好友聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 好友ID: " + Arrays.toString(recipients) + " 玩家: " + chr.getName() + " 说了 :" + chattext);
                }
                if (ServerConstants.message_buddychat) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』好友聊天：" + " 好友ID: " + Arrays.toString(recipients) + " 玩家: " + chr.getName() + " 说了 :" + chattext));
                }
                World.Buddy.buddyChat(recipients, chr.getId(), chr.getName(), chattext);
                break;
            case 1:
                if (chr.getParty() == null) {
                    break;
                }
                if (ServerConstants.logs_chat) {
                    FileoutputUtil.logToFile("logs/聊天/队伍聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 队伍: " + chr.getParty().getId() + " 玩家: " + chr.getName() + " 说了 :" + chattext);
                }
                if (ServerConstants.message_partychat) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』队伍聊天：" + " 队伍: " + chr.getParty().getId() + " 玩家: " + chr.getName() + " 说了 :" + chattext));
                }
                World.Party.partyChat(chr.getParty().getId(), chattext, chr.getName());
                break;
            case 2:
                if (chr.getGuildId() <= 0) {
                    break;
                }
                if (ServerConstants.logs_chat) {
                    FileoutputUtil.logToFile("logs/聊天/公会聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 公会: " + chr.getGuildId() + " 玩家: " + chr.getName() + " 说了 :" + chattext);
                }
                if (ServerConstants.message_guildchat) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』公会聊天：" + " 公会: " + chr.getGuildId() + " 玩家: " + chr.getName() + " 说了 :" + chattext));
                }
                World.Guild.guildChat(chr.getGuildId(), chr.getName(), chr.getId(), chattext);
                break;
            case 3:
                if (chr.getGuildId() <= 0) {
                    break;
                }
                if (ServerConstants.logs_chat) {
                    FileoutputUtil.logToFile("logs/聊天/联盟聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 公会: " + chr.getGuildId() + " 玩家: " + chr.getName() + " 说了 :" + chattext);
                }
                if (ServerConstants.message_alliancechat) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』联盟聊天：" + " 公会: " + chr.getGuildId() + " 玩家: " + chr.getName() + " 说了 :" + chattext));
                }
                World.Alliance.allianceChat(chr.getGuildId(), chr.getName(), chr.getId(), chattext);
                break;
            case 4:
                if (chr.getParty() == null || chr.getParty().getExpeditionId() <= 0) {
                    break;
                }
                World.Party.expedChat(chr.getParty().getExpeditionId(), chattext, chr.getName());
                break;
        }
    }

    public static final void Messenger(final LittleEndianAccessor slea, final MapleClient c) {
        String input;
        MapleMessenger messenger = c.getPlayer().getMessenger();

        switch (slea.readByte()) {
            case 0x00: // open
                if (messenger == null) {
                    int messengerid = slea.readInt();
                    if (messengerid == 0) { // create
                        c.getPlayer().setMessenger(World.Messenger.createMessenger(new MapleMessengerCharacter(c.getPlayer())));
                    } else { // join
                        messenger = World.Messenger.getMessenger(messengerid);
                        if (messenger != null) {
                            final int position = messenger.getLowestPosition();
                            if (position > -1 && position < 4) {
                                c.getPlayer().setMessenger(messenger);
                                World.Messenger.joinMessenger(messenger.getId(), new MapleMessengerCharacter(c.getPlayer()), c.getPlayer().getName(), c.getChannel());
                            }
                        }
                    }
                }
                break;
            case 0x02: // exit
                if (messenger != null) {
                    final MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(c.getPlayer());
                    World.Messenger.leaveMessenger(messenger.getId(), messengerplayer);
                    c.getPlayer().setMessenger(null);
                }
                break;
            case 0x03: // invite

                if (messenger != null) {
                    final int position = messenger.getLowestPosition();
                    if (position <= -1 || position >= 4) {
                        return;
                    }
                    input = slea.readMapleAsciiString();
                    final MapleCharacter target = c.getChannelServer().getPlayerStorage().getCharacterByName(input);

                    if (target != null) {
                        if (target.getMessenger() == null) {
                            if (!target.isIntern() || c.getPlayer().isIntern()) {
                                c.getSession().write(MaplePacketCreator.messengerNote(input, 4, 1));
                                target.getClient().getSession().write(MaplePacketCreator.messengerInvite(c.getPlayer().getName(), messenger.getId()));
                            } else {
                                c.getSession().write(MaplePacketCreator.messengerNote(input, 4, 0));
                            }
                        } else {
                            c.getSession().write(MaplePacketCreator.messengerChat(c.getPlayer().getName() + " : " + target.getName() + " is already using Maple Messenger."));
                        }
                    } else if (World.isConnected(input)) {
                        World.Messenger.messengerInvite(c.getPlayer().getName(), messenger.getId(), input, c.getChannel(), c.getPlayer().isIntern());
                    } else {
                        c.getSession().write(MaplePacketCreator.messengerNote(input, 4, 0));
                    }
                }
                break;
            case 0x05: // decline
                final String targeted = slea.readMapleAsciiString();
                final MapleCharacter target = c.getChannelServer().getPlayerStorage().getCharacterByName(targeted);
                if (target != null) { // This channel
                    if (target.getMessenger() != null) {
                        target.getClient().getSession().write(MaplePacketCreator.messengerNote(c.getPlayer().getName(), 5, 0));
                    }
                } else // Other channel
                 if (!c.getPlayer().isIntern()) {
                        World.Messenger.declineChat(targeted, c.getPlayer().getName());
                    }
                break;
            case 0x06: // message
                if (messenger != null) {
                    final String chattext = slea.readMapleAsciiString();

                    if (BitTools.doubleToShortBits(chattext) == 1) {
                        return;
                    }
                    if (ServerConstants.logs_chat) {
                        FileoutputUtil.logToFile("logs/聊天/Messenger聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " Messenger: " + messenger.getId() + " " + chattext);
                    }
                    if (ServerConstants.message_chat) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + c.getPlayer().getName() + "』(" + c.getPlayer().getId() + ")地图『" + c.getPlayer().getMapId() + "』Messenger聊：  " + " Messenger: " + messenger.getId() + " " + chattext));
                    }

                    World.Messenger.messengerChat(messenger.getId(), chattext, c.getPlayer().getName());
                    if (messenger.isMonitored() && chattext.length() > c.getPlayer().getName().length() + 3) { //name : NOT name0 or name1
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM Message] " + MapleCharacterUtil.makeMapleReadable(c.getPlayer().getName()) + "(Messenger: " + messenger.getMemberNamesDEBUG() + ") said: " + chattext));
                    }
                }
                break;
        }
    }

    public static final void Whisper_Find(final LittleEndianAccessor slea, final MapleClient c) {
        final byte mode = slea.readByte();
        slea.readInt(); //ticks
        switch (mode) {
            case 68: //buddy
            case 5: { // Find
                final String recipient = slea.readMapleAsciiString();
                MapleCharacter player = c.getChannelServer().getPlayerStorage().getCharacterByName(recipient);
                if (player != null) {
                    if (!player.isIntern() || c.getPlayer().isIntern() && player.isIntern()) {

                        c.getSession().write(MaplePacketCreator.getFindReplyWithMap(player.getName(), player.getMap().getId(), mode == 68));
                    } else {
                        c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 0));
                    }
                } else { // Not found
                    int ch = World.Find.findChannel(recipient);
                    if (ch > 0) {
                        player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(recipient);
                        if (player == null) {
                            break;
                        }
                        if (player != null) {
                            if (!player.isIntern() || (c.getPlayer().isIntern() && player.isIntern())) {
                                c.getSession().write(MaplePacketCreator.getFindReply(recipient, (byte) ch, mode == 68));
                            } else {
                                c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 0));
                            }
                            return;
                        }
                    }
                    if (ch == -10) {
                        c.getSession().write(MaplePacketCreator.getFindReplyWithCS(recipient, mode == 68));
                    } else if (ch == -20) {
                        c.getPlayer().dropMessage(5, "'" + recipient + "' is at the MTS."); //idfc
                    } else {
                        c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 0));
                    }
                }
                break;
            }
            case 6: { // Whisper
                if (c.getPlayer() == null || c.getPlayer().getMap() == null) {
                    return;
                }
                if (!c.getPlayer().getCanTalk()) {
                    c.getSession().write(MaplePacketCreator.serverNotice(6, "在这个地方不能说话。"));
                    return;
                }
                c.getPlayer().getCheatTracker().checkMsg();
                final String recipient = slea.readMapleAsciiString();
                final String text = slea.readMapleAsciiString();
                if (CPUSampler.getTopConsumers(text) == 1) {
                    return;
                }
                final int ch = World.Find.findChannel(recipient);
                if (ch > 0) {
                    MapleCharacter player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(recipient);
                    if (player == null) {
                        break;
                    }
                    player.getClient().getSession().write(MaplePacketCreator.getWhisper(c.getPlayer().getName(), c.getChannel(), text));
                    if (!c.getPlayer().isIntern() && player.isIntern()) {
                        c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 0));
                    } else {
                        c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 1));
                    }
                    if (ServerConstants.logs_chat) {
                        FileoutputUtil.logToFile("logs/聊天/玩家聊天.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 玩家: " + c.getPlayer().getName() + " 对玩家: " + recipient + " 说了 :" + text);
                    }
                    if (ServerConstants.message_whisperchat) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + c.getPlayer().getName() + "』(" + c.getPlayer().getId() + ")地图『" + c.getPlayer().getMapId() + "』玩家聊天：" + " 玩家: " + c.getPlayer().getName() + " 对玩家: " + recipient + " 说了 :" + text));
                    }
                    if (c.isMonitored()) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, c.getPlayer().getName() + " whispered " + recipient + " : " + text));
                    } else if (player.getClient().isMonitored()) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, c.getPlayer().getName() + " whispered " + recipient + " : " + text));
                    }
                } else {
                    c.getSession().write(MaplePacketCreator.getWhisperReply(recipient, (byte) 0));
                }
            }
            break;
        }
    }
}
