package scripting;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.MapleJob;
import client.MapleStat;
import client.Skill;
import client.SkillEntry;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DBConPool;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.channel.handler.HiredMerchantHandler;
import handling.channel.handler.InterServerHandler;
import handling.channel.handler.PlayersHandler;
import handling.login.LoginInformationProvider;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.World;
import handling.world.exped.ExpeditionType;
import handling.world.guild.MapleGuild;
import handling.world.guild.MapleGuildAlliance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.script.Invocable;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleShopFactory;
import server.MapleSquad;
import server.MapleStatEffect;
import server.SpeedRunner;
import server.StructPotentialItem;
import server.Timer.CloneTimer;
import server.gashapon.Gashapon;
import server.gashapon.GashaponFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MonsterDropEntry;
import server.maps.Event_DojoAgent;
import server.maps.Event_PyramidSubway;
import server.maps.MapleMap;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import static tools.FileoutputUtil.CurrentReadable_Date;
import tools.HexTool;
import tools.packet.MaplePacketCreator;
import tools.Randomizer;
import tools.SearchGenerator;
import tools.StringUtil;
import tools.Triple;
import tools.packet.PlayerShopPacket;

public class NPCConversationManager extends AbstractPlayerInteraction {

    private String getText;
    private byte type; // -1 = NPC, 0 = start quest, 1 = end quest
    private byte lastMsg = -1;
    public boolean pendingDisposal = false;
    private Invocable iv;

    public NPCConversationManager(MapleClient c, int npc, int questid, int mode, String npcscript, byte type, Invocable iv) {
        super(c, npc, questid, npcscript, mode);
        this.type = type;
        this.iv = iv;
    }

    public Invocable getIv() {
        return iv;
    }

    
     //获取在线时间
    public int getTodayOnlineTime() {
        return this.c.getPlayer().getTodayOnlineTime();
    }
    
    public int getNpc() {
        return id;
    }

    public int getQuest() {
        return id2;
    }

    public String getScript() {
        return script;
    }

    public int getMode() {
        return mode;
    }

    public byte getType() {
        return type;
    }

    public void safeDispose() {
        pendingDisposal = true;
    }

    public void dispose() {
        NPCScriptManager.getInstance().dispose(c);
    }

    public void askMapSelection(final String sel) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getMapSelection(id, sel));
        lastMsg = (byte) (GameConstants.GMS ? 0x11 : 0x10);
    }

    public void sendNext(String text) {
        sendNext(text, id);
    }

    public void sendNext(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { //sendNext will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "00 01", (byte) 0));
        lastMsg = 0;
    }

    public void sendPlayerToNpc(String text) {
        sendNextS(text, (byte) 3, id);
    }

    public void sendNextNoESC(String text) {
        sendNextS(text, (byte) 1, id);
    }

    public void sendNextNoESC(String text, int id) {
        sendNextS(text, (byte) 1, id);
    }

    public void sendNextS(String text, byte type) {
        sendNextS(text, type, id);
    }

    public void sendNextS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimpleS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "00 01", type, idd));
        lastMsg = 0;
    }

    public void sendPrev(String text) {
        sendPrev(text, id);
    }

    public void sendPrev(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "01 00", (byte) 0));
        lastMsg = 0;
    }

    public void sendPrevS(String text, byte type) {
        sendPrevS(text, type, id);
    }

    public void sendPrevS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimpleS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "01 00", type, idd));
        lastMsg = 0;
    }

    public void sendNextPrev(String text) {
        sendNextPrev(text, id);
    }

    public void sendNextPrev(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "01 01", (byte) 0));
        lastMsg = 0;
    }

    public void PlayerToNpc(String text) {
        sendNextPrevS(text, (byte) 3);
    }

    public void sendNextPrevS(String text) {
        sendNextPrevS(text, (byte) 3);
    }

    public void sendNextPrevS(String text, byte type) {
        sendNextPrevS(text, type, id);
    }

    public void sendNextPrevS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimpleS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "01 01", type, idd));
        lastMsg = 0;
    }

    public void sendOk(String text) {
        sendOk(text, id);
    }

    public void sendOk(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "00 00", (byte) 0));
        lastMsg = 0;
    }

    public void sendOkS(String text, byte type) {
        sendOkS(text, type, id);
    }

    public void sendOkS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimpleS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 0, text, "00 00", type, idd));
        lastMsg = 0;
    }

    public void sendYesNo(String text) {
        sendYesNo(text, id);
    }

    public void sendYesNo(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 2, text, "", (byte) 0));
        lastMsg = 2;
    }

    public void sendYesNoS(String text, byte type) {
        sendYesNoS(text, type, id);
    }

    public void sendYesNoS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimpleS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 2, text, "", type, idd));
        lastMsg = 2;
    }

    public void sendAcceptDecline(String text) {
        askAcceptDecline(text);
    }

    public void sendAcceptDeclineNoESC(String text) {
        askAcceptDeclineNoESC(text);
    }

    public void askAcceptDecline(String text) {
        askAcceptDecline(text, id);
    }

    public void askAcceptDecline(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        lastMsg = (byte) (GameConstants.GMS ? 0xF : 0xE);
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) lastMsg, text, "", (byte) 0));
    }

    public void askAcceptDeclineNoESC(String text) {
        askAcceptDeclineNoESC(text, id);
    }

    public void askAcceptDeclineNoESC(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        lastMsg = (byte) (GameConstants.GMS ? 0xF : 0xE);
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) lastMsg, text, "", (byte) 1));
    }

    public void askAvatar(String text, int... args) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkStyle(id, text, false, args));
        lastMsg = 9;
    }

    public void askAvatar(String text, int card, int... args) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkStyle(id, text, card, false, args));
        lastMsg = 9;
    }

    public void sendSimple(String text) {
        sendSimple(text, id);
    }

    public void sendSimple(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (!text.contains("#L")) { //sendSimple will dc otherwise!
            sendNext(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 5, text, "", (byte) 0));
        lastMsg = 5;
    }

    public void sendSimpleS(String text, byte type) {
        sendSimpleS(text, type, id);
    }

    public void sendSimpleS(String text, byte type, int idd) {
        if (lastMsg > -1) {
            return;
        }
        if (!text.contains("#L")) { //sendSimple will dc otherwise!
            sendNextS(text, type);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalk(id, (byte) 5, text, "", (byte) type, idd));
        lastMsg = 5;
    }

    public void sendStyle(String text, int styles[]) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkStyle(id, text, false, styles));
        lastMsg = 9;
    }

    public void sendStyle(String text, int styles[], int card) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkStyle(id, text, card, false, styles));
        lastMsg = 9;
    }

    public void sendStyleAndroid(String text, int styles[]) {
        if (lastMsg > -1) {
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkStyle(id, text, true, styles));
        lastMsg = 10;
    }

    public void sendGetNumber(String text, int def, int min, int max) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkNum(id, text, def, min, max));
        lastMsg = 4;
    }

    public void sendGetText(String text) {
        sendGetText(text, id);
    }

    public void sendGetText(String text, int id) {
        if (lastMsg > -1) {
            return;
        }
        if (text.contains("#L")) { // will dc otherwise!
            sendSimple(text);
            return;
        }
        c.getSession().write(MaplePacketCreator.getNPCTalkText(id, text));
        lastMsg = 3;
    }

    public void setGetText(String text) {
        this.getText = text;
    }

    public String getText() {
        return getText;
    }

    public void setHair(int hair) {
        getPlayer().setHair(hair);
        getPlayer().updateSingleStat(MapleStat.HAIR, hair);
        getPlayer().equipChanged();
    }

    public void setFace(int face) {
        getPlayer().setFace(face);
        getPlayer().updateSingleStat(MapleStat.FACE, face);
        getPlayer().equipChanged();
    }

    public void setSkin(int color) {
        getPlayer().setSkinColor((byte) color);
        getPlayer().updateSingleStat(MapleStat.SKIN, color);
        getPlayer().equipChanged();
    }

    public int setRandomAvatar(int ticket, int... args_all) {
        if (!haveItem(ticket)) {
            return -1;
        }
        gainItem(ticket, (short) -1);

        int args = args_all[Randomizer.nextInt(args_all.length)];
        if (args < 100) {
            c.getPlayer().setSkinColor((byte) args);
            c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        } else if (args < 30000) {
            c.getPlayer().setFace(args);
            c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        } else {
            c.getPlayer().setHair(args);
            c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        c.getPlayer().equipChanged();

        return 1;
    }

    public int setAvatar(int ticket, int args) {
        if (!haveItem(ticket)) {
            return -1;
        }
        gainItem(ticket, (short) -1);

        if (args < 100) {
            c.getPlayer().setSkinColor((byte) args);
            c.getPlayer().updateSingleStat(MapleStat.SKIN, args);
        } else if (args < 30000) {
            c.getPlayer().setFace(args);
            c.getPlayer().updateSingleStat(MapleStat.FACE, args);
        } else {
            c.getPlayer().setHair(args);
            c.getPlayer().updateSingleStat(MapleStat.HAIR, args);
        }
        c.getPlayer().equipChanged();

        return 1;
    }

    public void sendStorage() {
        if (!World.isShopShutDown) {
            c.getPlayer().setConversation(4);
            c.getPlayer().getStorage().sendStorage(c, id);
        } else {
            c.getPlayer().dropMessage(1, "目前不能使用仓库。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
        }
    }

    public void openShop(int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(c);
    }

    public void openShopNPC(int id) {
        MapleShopFactory.getInstance().getShop(id).sendShop(c, this.id);
    }

    public int gainGachaponItem(int id, int quantity) {
        return gainGachaponItem(id, quantity, c.getPlayer().getMap().getStreetName() + " - " + c.getPlayer().getMap().getMapName());
    }

    public int gainGachaponItem(int id, int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final Item item = MapleInventoryManipulator.addbyId_Gachapon(c, id, (short) quantity);

            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness == 1) {
                if (c.getPlayer().getMapId() == 910000000) {
                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                } else {
                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                }
                //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他抽到了，大家恭喜他吧！", item, rareness));
            } else if (rareness == 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            } else if (rareness > 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他從枫叶轉蛋機轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        } catch (Exception e) {
        }
        return -1;
    }

    public void getGachaponMega(String msg, Item item) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, (byte) 1, c.getPlayer().getClient().getChannel()));
    }

    public int gainGachaponItemS(int id, int quantity) {
        return gainGachaponItemS(id, quantity, c.getPlayer().getMap().getStreetName() + " - " + c.getPlayer().getMap().getMapName());
    }

    public int gainGachaponItemS(int id, int quantity, final String msg) {
        try {
            if (!MapleItemInformationProvider.getInstance().itemExists(id)) {
                return -1;
            }
            final Item item = MapleInventoryManipulator.addbyId_Gachapon(c, id, (short) quantity);

            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItemS(item.getItemId());
            if (rareness == 1) {
                if (c.getPlayer().getMapId() == 910000000) {
                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他從超級枫叶轉蛋機轉到了，大家恭喜他吧", false, c.getChannel(), item));
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                } else {
                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他從超級枫叶轉蛋機轉到了，大家恭喜他吧", false, c.getChannel(), item));
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                }
            } else if (rareness == 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            } else if (rareness > 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他從枫叶轉蛋機轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        } catch (Exception e) {
        }
        return -1;
    }

    public int gainGachaponItemTime(int id, int quantity, long period) {
        return gainGachaponItemTime(id, quantity, c.getPlayer().getMap().getStreetName() + " - " + c.getPlayer().getMap().getMapName(), period);
    }

    public int gainGachaponItemTime(int id, int quantity, final String msg, long period) {
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (!ii.itemExists(id)) {
                return -1;
            }
            final Item item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTime(c, id, (short) quantity, period) : MapleInventoryManipulator.addbyId_Gachapon(c, id, (short) quantity);

            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
            if (rareness == 1) {
                if (c.getPlayer().getMapId() == 910000000) {
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[自由市場]", " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                } else {
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[隱藏地图-轉蛋屋]", " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                }
            } else if (rareness == 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            } else if (rareness > 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他從枫叶轉蛋機轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        } catch (Exception e) {
        }
        return -1;
    }

    public int gainGachaponItemTimeS(int id, int quantity, long period) {
        return gainGachaponItemTimeS(id, quantity, c.getPlayer().getMap().getStreetName() + " - " + c.getPlayer().getMap().getMapName(), period);
    }

    public int gainGachaponItemTimeS(int id, int quantity, final String msg, long period) {
        try {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (!ii.itemExists(id)) {
                return -1;
            }
            final Item item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTime(c, id, (short) quantity, period) : MapleInventoryManipulator.addbyId_Gachapon(c, id, (short) quantity);

            if (item == null) {
                return -1;
            }
            final byte rareness = GameConstants.gachaponRareItemS(item.getItemId());
            if (rareness == 1) {
                if (c.getPlayer().getMapId() == 910000000) {
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[自由市場]", " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他從超級枫叶轉蛋機轉到了，大家恭喜他吧", false, c.getChannel(), item));
                } else {
                    //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[隱藏地图-轉蛋屋]", " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他從超級枫叶轉蛋機轉到了，大家恭喜他吧", false, c.getChannel(), item));
                }
            } else if (rareness == 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            } else if (rareness > 2) {
                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他從枫叶轉蛋機轉到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
            }
            return item.getItemId();
        } catch (Exception e) {
        }
        return -1;
    }

    public void changeJob(int job) {
        c.getPlayer().changeJob(job);
    }

    public void startQuest(int idd) {
        MapleQuest.getInstance(idd).start(getPlayer(), id);
    }

    public void completeQuest(int idd) {
        MapleQuest.getInstance(idd).complete(getPlayer(), id);
    }

    public void forfeitQuest(int idd) {
        MapleQuest.getInstance(idd).forfeit(getPlayer());
    }

    public void forceStartQuest() {
        MapleQuest.getInstance(id2).forceStart(getPlayer(), getNpc(), null);
    }

    public void forceStartQuest(int idd) {
        MapleQuest.getInstance(idd).forceStart(getPlayer(), getNpc(), null);
    }

    public void forceStartQuest(String customData) {
        MapleQuest.getInstance(id2).forceStart(getPlayer(), getNpc(), customData);
    }

    public void forceCompleteQuest() {
        MapleQuest.getInstance(id2).forceComplete(getPlayer(), getNpc());
    }

    public void forceCompleteQuest(final int idd) {
        MapleQuest.getInstance(idd).forceComplete(getPlayer(), getNpc());
    }

    public String getQuestCustomData() {
        return c.getPlayer().getQuestNAdd(MapleQuest.getInstance(id2)).getCustomData();
    }

    public void setQuestCustomData(String customData) {
        getPlayer().getQuestNAdd(MapleQuest.getInstance(id2)).setCustomData(customData);
    }

    public int getMeso() {
        return getPlayer().getMeso();
    }

    public void gainAp(final int amount) {
        c.getPlayer().gainAp((short) amount);
    }

    public void expandInventory(byte type, int amt) {
        c.getPlayer().expandInventory(type, amt);
    }

    public void unequipEverything() {
        MapleInventory equipped = getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        MapleInventory equip = getPlayer().getInventory(MapleInventoryType.EQUIP);
        List<Short> ids = new LinkedList<Short>();
        for (Item item : equipped.newList()) {
            ids.add(item.getPosition());
        }
        for (short id : ids) {
            MapleInventoryManipulator.unequip(getC(), id, equip.getNextFreeSlot());
        }
    }

    public final void clearSkills() {
        Map<Skill, SkillEntry> skills = new HashMap<Skill, SkillEntry>(getPlayer().getSkills());
        for (Entry<Skill, SkillEntry> skill : skills.entrySet()) {
            getPlayer().changeSkillLevel(skill.getKey(), (byte) 0, (byte) 0);
        }
        skills.clear();
    }

    public final void clearSkillsZs() {
        Map<Skill, SkillEntry> skills = new HashMap<Skill, SkillEntry>(getPlayer().getSkills());
        for (Entry<Skill, SkillEntry> skill : skills.entrySet()) {
            final int curLevel = getPlayer().getSkillLevel(skill.getKey().getId());
            if (!(skill.getKey().getId() < 1000000) && !(skill.getKey().getId() > 10000000 && skill.getKey().getId() < 11000000) && !(skill.getKey().getId() > 20000000 && skill.getKey().getId() < 21000000) && !(skill.getKey().getId() > 30000000 && skill.getKey().getId() < 31000000) && !(skill.getKey().getId() >= 91000000)) {
                getPlayer().changeSkillLevel(skill.getKey(), (byte) 0, (byte) curLevel);
            }
        }
        skills.clear();
    }

    public boolean hasSkill(int skillid) {
        Skill theSkill = SkillFactory.getSkill(skillid);
        if (theSkill != null) {
            return c.getPlayer().getSkillLevel(theSkill) > 0;
        }
        return false;
    }

    public void showEffect(boolean broadcast, String effect) {
        if (broadcast) {
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.showEffect(effect));
        } else {
            c.getSession().write(MaplePacketCreator.showEffect(effect));
        }
    }

    public void playSound(boolean broadcast, String sound) {
        if (broadcast) {
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.playSound(sound));
        } else {
            c.getSession().write(MaplePacketCreator.playSound(sound));
        }
    }

    public void environmentChange(boolean broadcast, String env) {
        if (broadcast) {
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.environmentChange(env, 2));
        } else {
            c.getSession().write(MaplePacketCreator.environmentChange(env, 2));
        }
    }

    public void updateBuddyCapacity(int capacity) {
        c.getPlayer().setBuddyCapacity((byte) capacity);
    }

    public int getBuddyCapacity() {
        return c.getPlayer().getBuddyCapacity();
    }

    public int partyMembersInMap() {
        int inMap = 0;
        if (getPlayer().getParty() == null) {
            return inMap;
        }
        for (MapleCharacter char2 : getPlayer().getMap().getCharactersThreadsafe()) {
            if (char2.getParty() != null && char2.getParty().getId() == getPlayer().getParty().getId()) {
                inMap++;
            }
        }
        return inMap;
    }

    public List<MapleCharacter> getPartyMembers() {
        if (getPlayer().getParty() == null) {
            return null;
        }
        List<MapleCharacter> chars = new LinkedList<MapleCharacter>(); // creates an empty array full of shit..
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            for (ChannelServer channel : ChannelServer.getAllInstances()) {
                MapleCharacter ch = channel.getPlayerStorage().getCharacterById(chr.getId());
                if (ch != null) { // double check <3
                    chars.add(ch);
                }
            }
        }
        return chars;
    }

    public void warpPartyWithExp(int mapId, int exp) {
        if (getPlayer().getParty() == null) {
            warp(mapId, 0);
            gainExp(exp);
            return;
        }
        MapleMap target = getMap(mapId);
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            MapleCharacter curChar = c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && getPlayer().getEventInstance() == null) || curChar.getEventInstance() == getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
            }
        }
    }

    public void warpPartyWithExpMeso(int mapId, int exp, int meso) {
        if (getPlayer().getParty() == null) {
            warp(mapId, 0);
            gainExp(exp);
            gainMeso(meso);
            return;
        }
        MapleMap target = getMap(mapId);
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            MapleCharacter curChar = c.getChannelServer().getPlayerStorage().getCharacterByName(chr.getName());
            if ((curChar.getEventInstance() == null && getPlayer().getEventInstance() == null) || curChar.getEventInstance() == getPlayer().getEventInstance()) {
                curChar.changeMap(target, target.getPortal(0));
                curChar.gainExp(exp, true, false, true);
                curChar.gainMeso(meso, true);
            }
        }
    }

    public MapleSquad getSquad(String type) {
        return c.getChannelServer().getMapleSquad(type);
    }

    public int getSquadAvailability(String type) {
        final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        }
        return squad.getStatus();
    }

    public boolean registerSquad(String type, int minutes, String startText) {
        if (c.getChannelServer().getMapleSquad(type) == null) {
            final MapleSquad squad = new MapleSquad(c.getChannel(), type, c.getPlayer(), minutes * 60 * 1000, startText);
            final boolean ret = c.getChannelServer().addMapleSquad(squad, type);
            if (ret) {
                final MapleMap map = c.getPlayer().getMap();

                map.broadcastMessage(MaplePacketCreator.getClock(minutes * 60));
                map.broadcastMessage(MaplePacketCreator.serverNotice(6, c.getPlayer().getName() + startText));
            } else {
                squad.clear();
            }
            return ret;
        }
        return false;
    }

    public boolean getSquadList(String type, byte type_) {
        try {
            final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
            if (squad == null) {
                return false;
            }
            if (type_ == 0 || type_ == 3) { // Normal viewing
                sendNext(squad.getSquadMemberString(type_));
            } else if (type_ == 1) { // Squad Leader banning, Check out banned participant
                sendSimple(squad.getSquadMemberString(type_));
            } else if (type_ == 2) {
                if (squad.getBannedMemberSize() > 0) {
                    sendSimple(squad.getSquadMemberString(type_));
                } else {
                    sendNext(squad.getSquadMemberString(type_));
                }
            }
            return true;
        } catch (NullPointerException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
            return false;
        }
    }

    public byte isSquadLeader(String type) {
        final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        } else if (squad.getLeader() != null && squad.getLeader().getId() == c.getPlayer().getId()) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean reAdd(String eim, String squad) {
        EventInstanceManager eimz = getDisconnected(eim);
        MapleSquad squadz = getSquad(squad);
        if (eimz != null && squadz != null) {
            squadz.reAddMember(getPlayer());
            eimz.registerPlayer(getPlayer());
            return true;
        }
        return false;
    }

    public void banMember(String type, int pos) {
        final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.banMember(pos);
        }
    }

    public void acceptMember(String type, int pos) {
        final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
        if (squad != null) {
            squad.acceptMember(pos);
        }
    }

    public int addMember(String type, boolean join) {
        try {
            final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
            if (squad != null) {
                return squad.addMember(c.getPlayer(), join);
            }
            return -1;
        } catch (NullPointerException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, ex);
            return -1;
        }
    }

    public byte isSquadMember(String type) {
        final MapleSquad squad = c.getChannelServer().getMapleSquad(type);
        if (squad == null) {
            return -1;
        } else if (squad.getMembers().contains(c.getPlayer())) {
            return 1;
        } else if (squad.isBanned(c.getPlayer())) {
            return 2;
        } else {
            return 0;
        }
    }

    public void resetReactors() {
        getPlayer().getMap().resetReactors();
    }

    public void genericGuildMessage(int code) {
        c.getSession().write(MaplePacketCreator.genericGuildMessage((byte) code));
    }

    public void disbandGuild() {
        final int gid = c.getPlayer().getGuildId();
        if (gid <= 0 || c.getPlayer().getGuildRank() != 1) {
            return;
        }
        World.Guild.disbandGuild(gid);
    }

    public void increaseGuildCapacity(boolean trueMax) {
        if (c.getPlayer().getMeso() < 500000 && !trueMax) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "你沒有足够的枫币。"));
            return;
        }
        final int gid = c.getPlayer().getGuildId();
        if (gid <= 0) {
            return;
        }
        if (World.Guild.increaseGuildCapacity(gid, trueMax)) {
            if (!trueMax) {
                c.getPlayer().gainMeso(-500000, true, true);
            } else {
                gainGP(-25000);
            }
            sendNext("你的公会人數扩充了。");
        } else if (!trueMax) {
            sendNext("请检查你的公会人數是否达到上限(限制: 100)");
        } else {
            sendNext("Please check if your guild capacity is full, if you have the GP needed or if subtracting GP would decrease a guild level. (Limit: 200)");
        }
    }

    public void displayGuildRanks() {
        c.getSession().write(MaplePacketCreator.showGuildRanks(id, MapleGuildRanking.getInstance().getRank()));
    }

    public void showlvl() {
        c.getSession().write(MaplePacketCreator.showlevelRanks(id, MapleGuildRanking.getInstance().getLevelRank()));
    }

    public void showmeso() {
        c.getSession().write(MaplePacketCreator.showmesoRanks(id, MapleGuildRanking.getInstance().getMesoRank()));
    }

    public boolean removePlayerFromInstance() {
        if (c.getPlayer().getEventInstance() != null) {
            c.getPlayer().getEventInstance().removePlayer(c.getPlayer());
            return true;
        }
        return false;
    }

    public boolean isPlayerInstance() {
        if (c.getPlayer().getEventInstance() != null) {
            return true;
        }
        return false;
    }

    public void changeStat(byte slot, int type, int amount) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(slot);
        switch (type) {
            case 0:
                sel.setStr((short) amount);
                break;
            case 1:
                sel.setDex((short) amount);
                break;
            case 2:
                sel.setInt((short) amount);
                break;
            case 3:
                sel.setLuk((short) amount);
                break;
            case 4:
                sel.setHp((short) amount);
                break;
            case 5:
                sel.setMp((short) amount);
                break;
            case 6:
                sel.setWatk((short) amount);
                break;
            case 7:
                sel.setMatk((short) amount);
                break;
            case 8:
                sel.setWdef((short) amount);
                break;
            case 9:
                sel.setMdef((short) amount);
                break;
            case 10:
                sel.setAcc((short) amount);
                break;
            case 11:
                sel.setAvoid((short) amount);
                break;
            case 12:
                sel.setHands((short) amount);
                break;
            case 13:
                sel.setSpeed((short) amount);
                break;
            case 14:
                sel.setJump((short) amount);
                break;
            case 15:
                sel.setUpgradeSlots((byte) amount);
                break;
            case 16:
                sel.setViciousHammer((byte) amount);
                break;
            case 17:
                sel.setLevel((byte) amount);
                break;
            case 18:
                sel.setEnhance((byte) amount);
                break;
            case 19:
                sel.setPotential1((short) amount);
                break;
            case 20:
                sel.setPotential2((short) amount);
                break;
            case 21:
                sel.setPotential3((short) amount);
                break;
            case 22:
                sel.setPotential4((short) amount);
                break;
            case 23:
                sel.setPotential5((short) amount);
                break;
            case 24:
                sel.setOwner(getText());
                break;
            default:
                break;
        }
        c.getPlayer().forceReAddItem(sel, MapleInventoryType.EQUIP);
        c.getPlayer().equipChanged();
    }

    public void forceReAddItem(Item item, byte type) {
        c.getPlayer().forceReAddItem(item, MapleInventoryType.getByType(type));
        c.getPlayer().equipChanged();
    }

    public void openMerchantItemStore() {
        c.getPlayer().setConversation(3);
        HiredMerchantHandler.displayMerch(c);
    }

    public void sendPVPWindow() {
        c.getSession().write(MaplePacketCreator.sendPVPWindow());
        c.getSession().write(MaplePacketCreator.sendPVPMaps());
    }

    public void sendRepairWindow() {
        c.getSession().write(MaplePacketCreator.sendRepairWindow(id));
    }

    public void sendProfessionWindow() {
        c.getSession().write(MaplePacketCreator.sendProfessionWindow(0));
    }

    public final int getDojoPoints() {
        return dojo_getPts();
    }

    public void setDojoPoints(int point) {
        final int dojo = c.getPlayer().getIntRecord(GameConstants.DOJO) + point;
        c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO)).setCustomData(String.valueOf(dojo));
    }

    public final int getDojoRecord() {
        return c.getPlayer().getIntNoRecord(GameConstants.DOJO_RECORD);
    }

    public void setDojoRecord(final boolean reset) {
        if (reset) {
            c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO_RECORD)).setCustomData("0");
            c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO)).setCustomData("0");
        } else {
            c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.DOJO_RECORD)).setCustomData(String.valueOf(c.getPlayer().getIntRecord(GameConstants.DOJO_RECORD) + 1));
        }
    }

    public boolean start_DojoAgent(final boolean dojo, final boolean party) {
        if (dojo) {
            return Event_DojoAgent.warpStartDojo(c.getPlayer(), party);
        }
        return Event_DojoAgent.warpStartAgent(c.getPlayer(), party);
    }

    public boolean start_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpStartPyramid(c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpStartSubway(c.getPlayer());
    }

    public boolean bonus_PyramidSubway(final int pyramid) {
        if (pyramid >= 0) {
            return Event_PyramidSubway.warpBonusPyramid(c.getPlayer(), pyramid);
        }
        return Event_PyramidSubway.warpBonusSubway(c.getPlayer());
    }

    public final short getKegs() {
        return c.getChannelServer().getFireWorks().getKegsPercentage();
    }

    public void giveKegs(final int kegs) {
        c.getChannelServer().getFireWorks().giveKegs(c.getPlayer(), kegs);
    }

    public final short getSunshines() {
        return c.getChannelServer().getFireWorks().getSunsPercentage();
    }

    public void addSunshines(final int kegs) {
        c.getChannelServer().getFireWorks().giveSuns(c.getPlayer(), kegs);
    }

    public final short getDecorations() {
        return c.getChannelServer().getFireWorks().getDecsPercentage();
    }

    public void addDecorations(final int kegs) {
        try {
            c.getChannelServer().getFireWorks().giveDecs(c.getPlayer(), kegs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final MapleCarnivalParty getCarnivalParty() {
        return c.getPlayer().getCarnivalParty();
    }

    public final MapleCarnivalChallenge getNextCarnivalRequest() {
        return c.getPlayer().getNextCarnivalRequest();
    }

    public final MapleCarnivalChallenge getCarnivalChallenge(MapleCharacter chr) {
        return new MapleCarnivalChallenge(chr);
    }

    public void StatsZs() {
        Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);

        c.getPlayer().setLevel((short) 1);
        c.getPlayer().levelUp();
        if (c.getPlayer().getExp() < 0) {
            c.getPlayer().gainExp(-c.getPlayer().getExp(), false, false, true);
        }
        c.getPlayer().getStat().str = (short) 4;
        c.getPlayer().getStat().dex = (short) 4;
        c.getPlayer().getStat().int_ = (short) 4;
        c.getPlayer().getStat().luk = (short) 4;
        c.getPlayer().setHpApUsed((short) 0);
        c.getPlayer().setRemainingAp((short) (350 * c.getPlayer().getTotalWins()));
        c.getPlayer().setRemainingSp(0, 0);
        c.getPlayer().setRemainingSp(0, 1);
        c.getPlayer().setRemainingSp(0, 2);
        c.getPlayer().setRemainingSp(0, 3);
        c.getPlayer().setRemainingSp(0, 4);
        c.getPlayer().setRemainingSp(0, 5);
        c.getPlayer().setRemainingSp(0, 6);
        c.getPlayer().setRemainingSp(0, 7);
        c.getPlayer().setRemainingSp(0, 8);
        c.getPlayer().setRemainingSp(0, 9);

        c.getSession().write(MaplePacketCreator.updateSp(c.getPlayer(), false));

        //c.getPlayer().getStat().maxhp = 99999;
        //c.getPlayer().getStat().maxmp = 99999;
        //c.getPlayer().getStat().setHp(99999, c.getPlayer());
        //c.getPlayer().getStat().setMp(99999, c.getPlayer());
        statup.put(MapleStat.STR, Integer.valueOf(c.getPlayer().getStat().getStr()));
        statup.put(MapleStat.DEX, Integer.valueOf(c.getPlayer().getStat().getDex()));
        statup.put(MapleStat.LUK, Integer.valueOf(c.getPlayer().getStat().getLuk()));
        statup.put(MapleStat.INT, Integer.valueOf(c.getPlayer().getStat().getInt()));
        statup.put(MapleStat.HP, c.getPlayer().getStat().getHp());
        statup.put(MapleStat.MAXHP, c.getPlayer().getStat().getMaxHp());
        statup.put(MapleStat.MP, c.getPlayer().getStat().getMp());
        statup.put(MapleStat.MAXMP, c.getPlayer().getStat().getMaxMp());
        statup.put(MapleStat.AVAILABLEAP, (int) c.getPlayer().getRemainingAp());
        c.getPlayer().getStat().recalcLocalStats(c.getPlayer());
        c.getSession().write(MaplePacketCreator.updatePlayerStats(statup, c.getPlayer().getJob()));
        c.getPlayer().marriage();
        c.getPlayer().fakeRelog();
    }

    public void maxStats() {
        Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);
        c.getPlayer().getStat().str = (short) 999;
        c.getPlayer().getStat().dex = (short) 999;
        c.getPlayer().getStat().int_ = (short) 999;
        c.getPlayer().getStat().luk = (short) 999;

        c.getPlayer().getStat().maxhp = 99999;
        c.getPlayer().getStat().maxmp = 99999;
        c.getPlayer().getStat().setHp(99999, c.getPlayer());
        c.getPlayer().getStat().setMp(99999, c.getPlayer());

        statup.put(MapleStat.STR, Integer.valueOf(999));
        statup.put(MapleStat.DEX, Integer.valueOf(999));
        statup.put(MapleStat.LUK, Integer.valueOf(999));
        statup.put(MapleStat.INT, Integer.valueOf(999));
        statup.put(MapleStat.HP, Integer.valueOf(99999));
        statup.put(MapleStat.MAXHP, Integer.valueOf(99999));
        statup.put(MapleStat.MP, Integer.valueOf(GameConstants.isDemon(c.getPlayer().getJob()) ? 120 : 99999));
        statup.put(MapleStat.MAXMP, Integer.valueOf(GameConstants.isDemon(c.getPlayer().getJob()) ? 120 : 99999));
        c.getPlayer().getStat().recalcLocalStats(c.getPlayer());
        c.getSession().write(MaplePacketCreator.updatePlayerStats(statup, c.getPlayer().getJob()));
        c.getPlayer().marriage();
    }

    public Triple<String, Map<Integer, String>, Long> getSpeedRun(String typ) {
        final ExpeditionType type = ExpeditionType.valueOf(typ);
        if (SpeedRunner.getSpeedRunData(type) != null) {
            return SpeedRunner.getSpeedRunData(type);
        }
        return new Triple<>("", new HashMap<>(), 0L);
    }

    public boolean getSR(Triple<String, Map<Integer, String>, Long> ma, int sel) {
        if (ma.mid.get(sel) == null || ma.mid.get(sel).length() <= 0) {
            dispose();
            return false;
        }
        sendOk(ma.mid.get(sel));
        return true;
    }

    public Equip getEquip(int itemid) {
        return (Equip) MapleItemInformationProvider.getInstance().getEquipById(itemid);
    }

    public Equip getEquipItem(byte type, byte slot) {
        return (Equip) c.getPlayer().getInventory(MapleInventoryType.getByType(type)).getItem(slot);
    }

    public void setExpiration(Object statsSel, long expire) {
        if (statsSel instanceof Equip) {
            ((Equip) statsSel).setExpiration(System.currentTimeMillis() + (expire * 24 * 60 * 60 * 1000));
        }
    }

    public void setLock(Object statsSel) {
        if (statsSel instanceof Equip) {
            Equip eq = (Equip) statsSel;
            if (eq.getExpiration() == -1) {
                eq.setFlag((byte) (eq.getFlag() | ItemFlag.LOCK.getValue()));
            } else {
                eq.setFlag((byte) (eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
            }
        }
    }

    public boolean addFromDrop(Object statsSel) {
        if (statsSel instanceof Item) {
            final Item it = (Item) statsSel;
            return MapleInventoryManipulator.checkSpace(getClient(), it.getItemId(), it.getQuantity(), it.getOwner()) && MapleInventoryManipulator.addFromDrop(getClient(), it, false);
        }
        return false;
    }

    public boolean replaceItem(int slot, int invType, Object statsSel, int offset, String type) {
        return replaceItem(slot, invType, statsSel, offset, type, false);
    }

    public boolean replaceItem(int slot, int invType, Object statsSel, int offset, String type, boolean takeSlot) {
        MapleInventoryType inv = MapleInventoryType.getByType((byte) invType);
        if (inv == null) {
            return false;
        }
        Item item = getPlayer().getInventory(inv).getItem((byte) slot);
        if (item == null || statsSel instanceof Item) {
            item = (Item) statsSel;
        }
        if (offset > 0) {
            if (inv != MapleInventoryType.EQUIP) {
                return false;
            }
            Equip eq = (Equip) item;
            if (takeSlot) {
                if (eq.getUpgradeSlots() < 1) {
                    return false;
                } else {
                    eq.setUpgradeSlots((byte) (eq.getUpgradeSlots() - 1));
                }
                if (eq.getExpiration() == -1) {
                    eq.setFlag((byte) (eq.getFlag() | ItemFlag.LOCK.getValue()));
                } else {
                    eq.setFlag((byte) (eq.getFlag() | ItemFlag.UNTRADEABLE.getValue()));
                }
            }
            if (type.equalsIgnoreCase("Slots")) {
                eq.setUpgradeSlots((byte) (eq.getUpgradeSlots() + offset));
                eq.setViciousHammer((byte) (eq.getViciousHammer() + offset));
            } else if (type.equalsIgnoreCase("Level")) {
                eq.setLevel((byte) (eq.getLevel() + offset));
            } else if (type.equalsIgnoreCase("Hammer")) {
                eq.setViciousHammer((byte) (eq.getViciousHammer() + offset));
            } else if (type.equalsIgnoreCase("STR")) {
                eq.setStr((short) (eq.getStr() + offset));
            } else if (type.equalsIgnoreCase("DEX")) {
                eq.setDex((short) (eq.getDex() + offset));
            } else if (type.equalsIgnoreCase("INT")) {
                eq.setInt((short) (eq.getInt() + offset));
            } else if (type.equalsIgnoreCase("LUK")) {
                eq.setLuk((short) (eq.getLuk() + offset));
            } else if (type.equalsIgnoreCase("HP")) {
                eq.setHp((short) (eq.getHp() + offset));
            } else if (type.equalsIgnoreCase("MP")) {
                eq.setMp((short) (eq.getMp() + offset));
            } else if (type.equalsIgnoreCase("WATK")) {
                eq.setWatk((short) (eq.getWatk() + offset));
            } else if (type.equalsIgnoreCase("MATK")) {
                eq.setMatk((short) (eq.getMatk() + offset));
            } else if (type.equalsIgnoreCase("WDEF")) {
                eq.setWdef((short) (eq.getWdef() + offset));
            } else if (type.equalsIgnoreCase("MDEF")) {
                eq.setMdef((short) (eq.getMdef() + offset));
            } else if (type.equalsIgnoreCase("ACC")) {
                eq.setAcc((short) (eq.getAcc() + offset));
            } else if (type.equalsIgnoreCase("Avoid")) {
                eq.setAvoid((short) (eq.getAvoid() + offset));
            } else if (type.equalsIgnoreCase("Hands")) {
                eq.setHands((short) (eq.getHands() + offset));
            } else if (type.equalsIgnoreCase("Speed")) {
                eq.setSpeed((short) (eq.getSpeed() + offset));
            } else if (type.equalsIgnoreCase("Jump")) {
                eq.setJump((short) (eq.getJump() + offset));
            } else if (type.equalsIgnoreCase("ItemEXP")) {
                eq.setItemEXP(eq.getItemEXP() + offset);
            } else if (type.equalsIgnoreCase("Expiration")) {
                eq.setExpiration((long) (eq.getExpiration() + offset));
            } else if (type.equalsIgnoreCase("Flag")) {
                eq.setFlag((byte) (eq.getFlag() + offset));
            }
            item = eq.copy();
        }
        MapleInventoryManipulator.removeFromSlot(getClient(), inv, (short) slot, item.getQuantity(), false);
        return MapleInventoryManipulator.addFromDrop(getClient(), item, false);
    }

    public boolean replaceItem(int slot, int invType, Object statsSel, int upgradeSlots) {
        return replaceItem(slot, invType, statsSel, upgradeSlots, "Slots");
    }

    public boolean isCash(final int itemId) {
        return MapleItemInformationProvider.getInstance().isCash(itemId);
    }

    public int getTotalStat(final int itemId) {
        return MapleItemInformationProvider.getInstance().getTotalStat((Equip) MapleItemInformationProvider.getInstance().getEquipById(itemId));
    }

    public int getReqLevel(final int itemId) {
        return MapleItemInformationProvider.getInstance().getReqLevel(itemId);
    }

    public MapleStatEffect getEffect(int buff) {
        return MapleItemInformationProvider.getInstance().getItemEffect(buff);
    }

    public void buffGuild(final int buff, final int duration, final String msg) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (ii.getItemEffect(buff) != null && getPlayer().getGuildId() > 0) {
            final MapleStatEffect mse = ii.getItemEffect(buff);
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr.getGuildId() == getPlayer().getGuildId()) {
                        mse.applyTo(chr, chr, true, null, duration);
                        chr.dropMessage(5, "Your guild has gotten a " + msg + " buff.");
                    }
                }
            }
        }
    }

    public boolean createAlliance(String alliancename) {
        MapleParty pt = c.getPlayer().getParty();
        MapleCharacter otherChar = c.getChannelServer().getPlayerStorage().getCharacterById(pt.getMemberByIndex(1).getId());
        if (otherChar == null || otherChar.getId() == c.getPlayer().getId()) {
            return false;
        }
        try {
            return World.Alliance.createAlliance(alliancename, c.getPlayer().getId(), otherChar.getId(), c.getPlayer().getGuildId(), otherChar.getGuildId());
        } catch (Exception re) {
            re.printStackTrace();
            return false;
        }
    }

    public boolean addCapacityToAlliance() {
        try {
            final MapleGuild gs = World.Guild.getGuild(c.getPlayer().getGuildId());
            if (gs != null && c.getPlayer().getGuildRank() == 1 && c.getPlayer().getAllianceRank() == 1) {
                if (World.Alliance.getAllianceLeader(gs.getAllianceId()) == c.getPlayer().getId() && World.Alliance.changeAllianceCapacity(gs.getAllianceId())) {
                    gainMeso(-MapleGuildAlliance.CHANGE_CAPACITY_COST);
                    return true;
                }
            }
        } catch (Exception re) {
            re.printStackTrace();
        }
        return false;
    }

    public boolean disbandAlliance() {
        try {
            final MapleGuild gs = World.Guild.getGuild(c.getPlayer().getGuildId());
            if (gs != null && c.getPlayer().getGuildRank() == 1 && c.getPlayer().getAllianceRank() == 1) {
                if (World.Alliance.getAllianceLeader(gs.getAllianceId()) == c.getPlayer().getId() && World.Alliance.disbandAlliance(gs.getAllianceId())) {
                    return true;
                }
            }
        } catch (Exception re) {
            re.printStackTrace();
        }
        return false;
    }

    public byte getLastMsg() {
        return lastMsg;
    }

    public final void setLastMsg(final byte last) {
        this.lastMsg = last;
    }

    public final void maxAllSkills() {
        for (Skill skil : SkillFactory.getAllSkills()) {
            if (GameConstants.isApplicableSkill(skil.getId()) && skil.getId() < 90000000) { //no db/additionals/resistance skills
                teachSkill(skil.getId(), (byte) skil.getMaxLevel(), (byte) skil.getMaxLevel());
            }
        }
    }

    public final void maxSkillsByJob() {
        for (Skill skil : SkillFactory.getAllSkills()) {
            if (GameConstants.isApplicableSkill(skil.getId()) && skil.canBeLearnedBy(getPlayer().getJob())) { //no db/additionals/resistance skills
                teachSkill(skil.getId(), (byte) skil.getMaxLevel(), (byte) skil.getMaxLevel());
            }
        }
    }

    public final void resetStats(int str, int dex, int z, int luk) {
        c.getPlayer().resetStats(str, dex, z, luk);
    }

    public final boolean dropItem(int slot, int invType, int quantity) {
        MapleInventoryType inv = MapleInventoryType.getByType((byte) invType);
        if (inv == null) {
            return false;
        }
        return MapleInventoryManipulator.drop(c, inv, (short) slot, (short) quantity, true);
    }

    public final List<Integer> getAllPotentialInfo() {
        List<Integer> list = new ArrayList<Integer>(MapleItemInformationProvider.getInstance().getAllPotentialInfo().keySet());
        Collections.sort(list);
        return list;
    }

    public final List<Integer> getAllPotentialInfoSearch(String content) {
        List<Integer> list = new ArrayList<Integer>();
        for (Entry<Integer, List<StructPotentialItem>> i : MapleItemInformationProvider.getInstance().getAllPotentialInfo().entrySet()) {
            for (StructPotentialItem ii : i.getValue()) {
                if (ii.toString().contains(content)) {
                    list.add(i.getKey());
                }
            }
        }
        Collections.sort(list);
        return list;
    }

    public final String getPotentialInfo(final int id) {
        final List<StructPotentialItem> potInfo = MapleItemInformationProvider.getInstance().getPotentialInfo(id);
        final StringBuilder builder = new StringBuilder("#b#e潜在能力 ID: ");
        builder.append(id);
        builder.append("#n#k\r\n\r\n");
        int minLevel = 1, maxLevel = 10;
        for (StructPotentialItem item : potInfo) {
            builder.append("#e等級 ");
            builder.append(minLevel);
            builder.append("~");
            builder.append(maxLevel);
            builder.append(": #n");
            builder.append(item.toString());
            minLevel += 10;
            maxLevel += 10;
            builder.append("\r\n");
        }
        return builder.toString();
    }

    public final String getPotentialInfo2(final int itemid, final int id) {

        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<StructPotentialItem> potInfo = MapleItemInformationProvider.getInstance().getPotentialInfo(id);
        final StringBuilder builder = new StringBuilder("#b#e属性#k:");
        if (id == 0) {
            return builder.toString() + "#r(无)#k";
        }
        int minLevel = 1, maxLevel = 10;
        final Map<String, Integer> stats = ii.getEquipStats(itemid);
        int reqLevel = stats.get("reqLevel");
        for (StructPotentialItem item : potInfo) {
            if (reqLevel >= minLevel && reqLevel <= maxLevel) {
                builder.append("#r(").append(item.toString()).append(")#k");
            }
            minLevel += 10;
            maxLevel += 10;
        }
        return builder.toString();
    }

    public final void sendRPS() {
        c.getSession().write(MaplePacketCreator.getRPSMode((byte) 8, -1, -1, -1));
    }

    public final void setQuestRecord(Object ch, final int questid, final String data) {
        ((MapleCharacter) ch).getQuestNAdd(MapleQuest.getInstance(questid)).setCustomData(data);
    }

    public final void doWeddingEffect(final Object ch) {
        final MapleCharacter chr = (MapleCharacter) ch;
        final MapleCharacter player = getPlayer();
        getMap().broadcastMessage(MaplePacketCreator.yellowChat(player.getName() + ", 妳愿意承认 " + chr.getName() + " 做妳的丈夫，诚实遵照上帝的誡命，和他生活在一起，无論在什麼環境願順服他、愛惜他、安慰他、尊重他保護他，以致奉召歸主？"));
        CloneTimer.getInstance().schedule(new Runnable() {

            public void run() {
                if (chr == null || player == null) {
                    warpMap(680000500, 0);
                } else {
                    chr.getMap().broadcastMessage(MaplePacketCreator.yellowChat(chr.getName() + ", 你愿意承认接纳 " + player.getName() + " 做你的妻子，诚实遵照上帝的誡命，和她生活在一起，无論在什麼環境，願意終生養她、愛惜她、安慰她、尊重她、保護她，以至奉召歸主？"));
                }
            }
        }, 10000);
        CloneTimer.getInstance().schedule(new Runnable() {

            public void run() {
                if (chr == null || player == null) {
                    if (player != null) {
                        setQuestRecord(player, 160001, "3");
                        setQuestRecord(player, 160002, "0");
                    } else if (chr != null) {
                        setQuestRecord(chr, 160001, "3");
                        setQuestRecord(chr, 160002, "0");
                    }
                    warpMap(680000500, 0);
                } else {
                    setQuestRecord(player, 160001, "2");
                    setQuestRecord(chr, 160001, "2");
                    sendNPCText(getPlayer().getName() + " 和 " + chr.getName() + "， 我希望你們兩个能在此時此刻永遠愛著对方！", 9201002);
                    getMap().startExtendedMapEffect("那麼現在请新娘親吻 " + getPlayer().getName() + "！", 5120006);
                    if (chr.getGuildId() > 0) {
                        World.Guild.guildPacket(chr.getGuildId(), MaplePacketCreator.sendMarriage(false, chr.getName()));
                    }
                    if (chr.getFamilyId() > 0) {
                        World.Family.familyPacket(chr.getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), chr.getId());
                    }
                    if (player.getGuildId() > 0) {
                        World.Guild.guildPacket(player.getGuildId(), MaplePacketCreator.sendMarriage(false, player.getName()));
                    }
                    if (player.getFamilyId() > 0) {
                        World.Family.familyPacket(player.getFamilyId(), MaplePacketCreator.sendMarriage(true, chr.getName()), player.getId());
                    }
                }
            }
        }, 20000); //10 sec 10 sec
    }

    public void putKey(int key, int type, int action) {
        getPlayer().changeKeybinding(key, (byte) type, action);
        getClient().getSession().write(MaplePacketCreator.getKeymap(getPlayer().getKeyLayout()));
    }

    public void logDonator(String log, int previous_points) {
        final StringBuilder logg = new StringBuilder();
        logg.append(MapleCharacterUtil.makeMapleReadable(getPlayer().getName()));
        logg.append(" [CID: ").append(getPlayer().getId()).append("] ");
        logg.append(" [Account: ").append(MapleCharacterUtil.makeMapleReadable(getClient().getAccountName())).append("] ");
        logg.append(log);
        logg.append(" [Previous: " + previous_points + "] [Now: " + getPlayer().getPoints() + "]");

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO donorlog VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, MapleCharacterUtil.makeMapleReadable(getClient().getAccountName()));
            ps.setInt(2, getClient().getAccID());
            ps.setString(3, MapleCharacterUtil.makeMapleReadable(getPlayer().getName()));
            ps.setInt(4, getPlayer().getId());
            ps.setString(5, log);
            ps.setString(6, FileoutputUtil.CurrentReadable_Time());
            ps.setInt(7, previous_points);
            ps.setInt(8, getPlayer().getPoints());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            e.printStackTrace();
        }
        FileoutputUtil.log(FileoutputUtil.Donator_Log, logg.toString());
    }

    public void doRing(final String name, final int itemid) {
        PlayersHandler.DoRing(getClient(), name, itemid);
    }

    public int getNaturalStats(final int itemid, final String it) {
        Map<String, Integer> eqStats = MapleItemInformationProvider.getInstance().getEquipStats(itemid);
        if (eqStats != null && eqStats.containsKey(it)) {
            return eqStats.get(it);
        }
        return 0;
    }

    public boolean isEligibleName(String t) {
        return MapleCharacterUtil.canCreateChar(t, getPlayer().isGM()) && (!LoginInformationProvider.getInstance().isForbiddenName(t) || getPlayer().isGM());
    }

    public String checkDrop(MapleCharacter chr, int mobId, boolean GM) {/*NoBu Added*/
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0, itemId = 0, ch = 0;
            MonsterDropEntry de;
            StringBuilder name = new StringBuilder();
            StringBuilder error = new StringBuilder();
            name.append("【#r#o" + mobId + "##k】爆率物品查詢列表:#b" + "\r\n");
            for (int i = 0; i < ranks.size(); i++) {
                de = ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    /*   if (num == 0) {
                        name.append("【#r#o"+ mobId + "##k】掉寶数据列表:#b" +"\r\n");
                    }*/
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) { //meso
                        itemId = 4031041; //display sack of cash
                        namez = (de.Minimum * getClient().getChannelServer().getMesoRate() / 10) + " to " + (de.Maximum * getClient().getChannelServer().getMesoRate() / 10) + " #b枫币#l#k";
                    } else if (itemId != 0 && ii.itemExists(itemId)) {
                        ch = de.chance * getClient().getChannelServer().getDropRate();
                         if (getPlayer().isAdmin()) {
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append(" - ").append(Integer.valueOf(ch >= 999999 ? 1000000 : ch).doubleValue() / 10000.0D).append("%的爆率. ").append((de.questid > 0) && (MapleQuest.getInstance(de.questid).getName().length() > 0) ? new StringBuilder().append("需要接受任务: ").append(MapleQuest.getInstance(de.questid).getName()).toString() : "").append("\r\n");
                    } else {
                        //不显示概率
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append((de.questid > 0) && (MapleQuest.getInstance(de.questid).getName().length() > 0) ? new StringBuilder().append("    需要接受任务: ").append(MapleQuest.getInstance(de.questid).getName()).toString() : "").append("\r\n");
                    }
                        //                       name.append("#k" + (num + 1) + ": #v" + itemId + "# " + namez + " #d" +"%\r\n#b(掉落條件:" + (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0 ? ("需要接取任务#r " + MapleQuest.getInstance(de.questid).getName() + " #b)\r\n") : "#r无#b)") + ((chr.isGM())?"掉落機率：" + (Integer.valueOf(ch >= 999999 ? 1000000 : ch).doubleValue() / 10000.0) +"\r\n":"\r\n"));

                        num++;
                    } else {
                        error.append(itemId + "\r\n");
                    }
                }
            }
            if (GM == true) {
                name.append("\r\n#L" + 10000 + "##k" + (num + 1) + ": #b我要额外新增掉落物品!");
            }
            if (error.length() > 0) {
                chr.dropMessage(1, "无效的物品ID:\r\n" + error.toString());
            }
            if (name.length() > 0) {
                return name.toString();
            }

        }
        return GM == true ? "该怪物查无任何掉落数据。\r\n#L" + 10000 + "##k" + ": #b我要額外新增掉落物品!" : "该怪物查无任何掉落数据。";
    }

    public String checkDrop(int mobId) {
        final List<MonsterDropEntry> ranks = MapleMonsterInformationProvider.getInstance().retrieveDrop(mobId);
        if (ranks != null && ranks.size() > 0) {
            int num = 0, itemId = 0, ch = 0;
            MonsterDropEntry de;
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < ranks.size(); i++) {
                de = ranks.get(i);
                if (de.chance > 0 && (de.questid <= 0 || (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0))) {
                    itemId = de.itemId;
                    if (!MapleItemInformationProvider.getInstance().itemExists(itemId)) {
                        continue;
                    }
                    if (num == 0) {
                        name.append("当前怪物 #o" + mobId + "# 的爆率为:#\r\n");
                        name.append("--------------------------------------\r\n");
                    }
                    String namez = "#z" + itemId + "#";
                    if (itemId == 0) { //meso
                        itemId = 4031041; //display sack of cash
                        namez = (de.Minimum * getClient().getChannelServer().getMesoRate() / 10) + " 到 " + (de.Maximum * getClient().getChannelServer().getMesoRate() / 10) + " 枫币";
                    }
                    ch = de.chance * getClient().getChannelServer().getDropRate();
                  //  name.append((num + 1) + ") #v" + itemId + "#" + namez /*+ " - " + (Integer.valueOf(ch >= 999999 ? 1000000 : ch).doubleValue() / 10000.0) + "% 爆率. "*/ + (de.questid > 0 && MapleQuest.getInstance(de.questid).getName().length() > 0 ? ("需要接受任务 " + MapleQuest.getInstance(de.questid).getName() + "") : "") + "\r\n");
                  if (getPlayer().isAdmin()) {
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append(" - ").append(Integer.valueOf(ch >= 999999 ? 1000000 : ch).doubleValue() / 10000.0D).append("%的爆率. ").append((de.questid > 0) && (MapleQuest.getInstance(de.questid).getName().length() > 0) ? new StringBuilder().append("需要接受任务: ").append(MapleQuest.getInstance(de.questid).getName()).toString() : "").append("\r\n");
                    } else {
                        //不显示概率
                        name.append(num + 1).append(") #v").append(itemId).append("#").append(namez).append((de.questid > 0) && (MapleQuest.getInstance(de.questid).getName().length() > 0) ? new StringBuilder().append("    需要接受任务: ").append(MapleQuest.getInstance(de.questid).getName()).toString() : "").append("\r\n");
                    }
                  
                  num++;
                }
            }
            if (name.length() > 0) {
                return name.toString();
            }

        }
        return "该怪物查无任何掉落数据。";
    }

    public void checkMobs(MapleCharacter chr) {/*NoBu Added*/
        if (this.getMap().getAllMonstersThreadsafe().size() <= 0) {
            sendOk("#地图上沒有怪物哦!!。");
            dispose();
        }
        String msg = "玩家 #b" + chr.getName() + "#k 此地图怪物掉落查询:\r\n#r(若有任何掉落问题,请至社團BUG區回報怪物名稱和代码)\r\n#d";
        Iterator monster = getMap().getAllUniqueMonsters().iterator();
        while (monster.hasNext()) {
            Object monsterid = monster.next();
//            msg += "#L" + monsterid + "##o" + monsterid + "#" + ((chr.isGM()) ? " 代码:" + monsterid + "#l\r\n" : "(查看)#l\r\n");
            msg += "#L" + monsterid + "##o" + monsterid + "#" + " 代码:" + monsterid + " (查看)#l\r\n";
        }
        sendOk(msg);
    }

    public void SystemOutPrintln(String text) {
        if (c.getPlayer().isGM()) {
            this.c.getPlayer().dropMessage(1, "[Npc_Debug]" + text);
        }
        System.out.println(text);
    }

    public void UpdateDropChance(int chance, int dropperid, int itemid) {
        MapleMonsterInformationProvider.getInstance().UpdateDropChance(chance, dropperid, itemid);
    }

    public void AddDropData(int chance, int dropperid, int itemid, int questid) {
        MapleMonsterInformationProvider.getInstance().AddDropData(chance, dropperid, itemid, questid);
    }

    public void handleDivorce() {
        if (getPlayer().getMarriageId() <= 0) {
            sendNext("Please make sure you have a marriage.");
            return;
        }
        final int chz = World.Find.findChannel(getPlayer().getMarriageId());
        if (chz == -1) {
            //sql queries
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps = con.prepareStatement("UPDATE queststatus SET customData = ? WHERE characterid = ? AND (quest = ? OR quest = ?)");
                ps.setString(1, "0");
                ps.setInt(2, getPlayer().getMarriageId());
                ps.setInt(3, 160001);
                ps.setInt(4, 160002);
                ps.executeUpdate();
                ps.close();

                ps = con.prepareStatement("UPDATE characters SET marriageid = ? WHERE id = ?");
                ps.setInt(1, 0);
                ps.setInt(2, getPlayer().getMarriageId());
                ps.executeUpdate();
                ps.close();
            } catch (SQLException e) {
                FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
                outputFileError(e);
                return;
            }
            setQuestRecord(getPlayer(), 160001, "0");
            setQuestRecord(getPlayer(), 160002, "0");
            getPlayer().setMarriageId(0);
            sendNext("You have been successfully divorced...");
            return;
        } else if (chz < -1) {
            sendNext("Please make sure your partner is logged on.");
            return;
        }
        MapleCharacter cPlayer = ChannelServer.getInstance(chz).getPlayerStorage().getCharacterById(getPlayer().getMarriageId());
        if (cPlayer != null) {
            cPlayer.dropMessage(1, "Your partner has divorced you.");
            cPlayer.setMarriageId(0);
            setQuestRecord(cPlayer, 160001, "0");
            setQuestRecord(getPlayer(), 160001, "0");
            setQuestRecord(cPlayer, 160002, "0");
            setQuestRecord(getPlayer(), 160002, "0");
            getPlayer().setMarriageId(0);
            sendNext("You have been successfully divorced...");
        } else {
            sendNext("An error occurred...");
        }
    }

    public String getReadableMillis(long startMillis, long endMillis) {
        return StringUtil.getReadableMillis(startMillis, endMillis);
    }

    public void sendUltimateExplorer() {
        getClient().getSession().write(MaplePacketCreator.ultimateExplorer());
    }

    public void sendPendant(boolean b) {
        c.getSession().write(MaplePacketCreator.pendantSlot(b));
    }

    public Triple<Integer, Integer, Integer> getCompensation() {
        Triple<Integer, Integer, Integer> ret = null;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM compensationlog_confirmed WHERE chrname LIKE ?");
            ps.setString(1, getPlayer().getName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret = new Triple<Integer, Integer, Integer>(rs.getInt("value"), rs.getInt("taken"), rs.getInt("donor"));
            }
            rs.close();
            ps.close();
            return ret;
        } catch (SQLException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            return ret;
        }
    }

    public boolean deleteCompensation(int taken) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE compensationlog_confirmed SET taken = ? WHERE chrname LIKE ?");
            ps.setInt(1, taken);
            ps.setString(2, getPlayer().getName());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            return false;
        }
    }

    public String searchData(int type, String search) {
        return SearchGenerator.searchData(type, search);
    }

    public int[] getSearchData(int type, String search) {
        Map<Integer, String> data = SearchGenerator.getSearchData(type, search);
        if (data.isEmpty()) {
            return null;
        }
        int[] searches = new int[data.size()];
        int i = 0;
        for (int key : data.keySet()) {
            searches[i] = key;
            i++;
        }
        return searches;
    }

    public boolean foundData(int type, String search) {
        return SearchGenerator.foundData(type, search);
    }

    public void setPartyBossLog(String bossid) {
        MapleParty party = getPlayer().getParty();
        for (MaplePartyCharacter pc : party.getMembers()) {
            MapleCharacter chr = World.getStorage(this.getChannelNumber()).getCharacterById(pc.getId());
            if (chr != null) {
                chr.setBossLog(bossid);
            }
        }
    }

    public int getRandomZanZu(int... args_all) {
        int args = args_all[Randomizer.nextInt(args_all.length)];
        return args;
    }

    public int getRandom(int... args_all) {
        int args = args_all[Randomizer.nextInt(args_all.length)];
        return args;
    }

    public void getxixuezhijie(int itemId, int n) {
        Equip equip;
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (!ii.itemExists(itemId)) {
            c.getPlayer().dropMessage(5, itemId + " 不存在");
            return;
        }
        equip = ii.randomizeStats((Equip) ii.getEquipById(itemId));
        equip.setInt((short) n);
        equip.setUpgradeSlots((byte) 0);
        equip.setViciousHammer((byte) 1);
        MapleInventoryManipulator.addbyItem(c, equip);
    }

    public void EnterCS(int mod) {
        c.getPlayer().setCsMod(mod);
        InterServerHandler.EnterCS(c, c.getPlayer());
    }

    // 轉蛋
    public Gashapon getGashapon() {
        return GashaponFactory.getInstance().getGashaponByNpcId(this.getNpc());
    }

    public int setAndroid(int args) {
        if (args < 30000) {
            c.getPlayer().getAndroid().setFace(args);
            c.getPlayer().getAndroid().saveToDb();
        } else {
            c.getPlayer().getAndroid().setHair(args);
            c.getPlayer().getAndroid().saveToDb();
        }
        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.updateAndroidLook(false, c.getPlayer(), c.getPlayer().getAndroid()));
        c.getPlayer().setAndroid(c.getPlayer().getAndroid()); //Respawn it
        c.getPlayer().equipChanged();
        return 1;
    }

    public String ShowJobRank(int type) {
        StringBuilder sb = new StringBuilder();
        List<MapleGuildRanking.JobRankingInfo> Ranking = MapleGuildRanking.getInstance().getJobRank(type);
        if (Ranking != null) {
            int num = 0;
            for (MapleGuildRanking.JobRankingInfo info : Ranking) {
                num++;
                sb.append("#n#e#k排名:#r ");
                sb.append(num);
                sb.append("\r\n#n#e#k玩家名稱:#d ");
                sb.append(StringUtil.getRightPaddedStr(info.getName(), ' ', 13));
                sb.append("\r\n#n#e#k等級:#e#r ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLevel()), ' ', 3));
                sb.append("\r\n#n#e#k职业:#e#b ");
                sb.append(MapleJob.getName(MapleJob.getById(info.getJob())));
                sb.append("\r\n#n#e#k力量:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getStr()), ' ', 4));
                sb.append("\r\n#n#e#k敏捷:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getDex()), ' ', 4));
                sb.append("\r\n#n#e#k智力:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getInt()), ' ', 4));
                sb.append("\r\n#n#e#k幸運:#e#d ");
                sb.append(StringUtil.getRightPaddedStr(String.valueOf(info.getLuk()), ' ', 4));
                sb.append("\r\n");
                sb.append("#n#k======================================================\r\n");
            }
        } else {
            sb.append("#r查詢无任何結果唷");
        }
        return sb.toString();
    }

    public void reloadKeymap() {
        c.getSession().write(HexTool.getByteArrayFromHexString("E5 01 00 00 00 00 00 00 00 00 00 00 00 04 0A 00 00 00 04 0C 00 00 00 04 0D 00 00 00 04 12 00 00 00 04 17 00 00 00 04 1C 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 04 08 00 00 00 04 05 00 00 00 04 00 00 00 00 04 04 00 00 00 04 1B 00 00 00 04 1E 00 00 00 00 00 00 00 00 04 01 00 00 00 04 18 00 00 00 04 13 00 00 00 04 0E 00 00 00 04 0F 00 00 00 00 00 00 00 00 05 34 00 00 00 00 00 00 00 00 04 02 00 00 00 00 00 00 00 00 04 19 00 00 00 04 11 00 00 00 04 0B 00 00 00 00 00 00 00 00 04 03 00 00 00 04 14 00 00 00 04 1A 00 00 00 04 10 00 00 00 04 16 00 00 00 00 00 00 00 00 04 09 00 00 00 05 32 00 00 00 05 33 00 00 00 04 06 00 00 00 00 00 00 00 00 04 1D 00 00 00 00 00 00 00 00 04 07 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 05 35 00 00 00 05 36 00 00 00 00 00 00 00 00 06 64 00 00 00 06 65 00 00 00 06 66 00 00 00 06 67 00 00 00 06 68 00 00 00 06 69 00 00 00 06 6A 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00"));
    }

    public void getMobs(int itemid) {
        MapleMonsterInformationProvider mi = MapleMonsterInformationProvider.getInstance();
        final List<Integer> mobs = MapleMonsterInformationProvider.getInstance().getMobByItem(itemid);
        String text = "#d这些怪物会掉落您查詢的物品#k: \r\n\r\n";

        for (int i = 0; i < mobs.size(); i++) {
            int quest = 0;
            if (mi.getDropQuest(mobs.get(i)) > 0) {
                quest = mi.getDropQuest(mobs.get(i));
            }
            int chance = mi.getDropChance(mobs.get(i)) * getClient().getChannelServer().getDropRate();

            text += "#r#o" + mobs.get(i) + "##k " /*+ (Integer.valueOf(chance >= 999999 ? 1000000 : chance).doubleValue() / 10000.0) + "%" */ + (quest > 0 && MapleQuest.getInstance(quest).getName().length() > 0 ? ("#b需要进行 " + MapleQuest.getInstance(quest).getName() + " 任务來取得#k") : "") + "\r\n";

        }
        sendNext(text);
    }

    public void openBeans() {//打开豆豆机界面
        c.getSession().write(MaplePacketCreator.openBeans(getPlayer().getBeans(), 0));
        c.getPlayer().dropMessage(5, "按住左右鍵可以调整力道,建议调好角度一路給他打,不要按暂停若九宮格卡住沒反應请离开在近來");
    }

    public int gainGachaponItem(int id, int quantity, String msg, int rareness) {
        return gainGachaponItem(id, quantity, msg, rareness, false, 0L);
    }

    public int gainGachaponItem(int id, int quantity, String msg, int rareness, long period) {
        return gainGachaponItem(id, quantity, msg, rareness, false, period);
    }

    public int gainGachaponItem(int id, int quantity, String msg, int rareness, boolean buy) {
        return gainGachaponItem(id, quantity, msg, rareness, buy, 0L);
    }

    public int gainGachaponItem(int id, int quantity, String msg, int rareness, boolean buy, long period) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        try {
            if (!ii.itemExists(id)) {
                return -1;
            }
            Item item = MapleInventoryManipulator.addbyId_Gachapon(this.c, id, (short) quantity);
            if (item == null) {
                return -1;
            }
            if ((rareness == 1) || (rareness == 2) || (rareness == 3)) {
                //World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), new StringBuilder().append(" : 我从").append(msg).append("中").append(buy ? "购买" : "获得").append("了{").append(ii.getName(item.getItemId())).append("}！大家一起恭喜我吧！！！").toString(), item, (byte) rareness, c.getChannel()));
                World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone(msg + " : " + "被 " + c.getPlayer().getName() + " 抽到,大家一起恭喜我吧！！！", c.getChannel(), item));
            }
            return item.getItemId();
        } catch (Exception e) {

        }
        return -1;
    }

    public int 判断点券() {
        return c.getPlayer().getCSPoints(1);
   
 }

    public int 判断兑换卡是否存在(String id) {
        int data = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT code as DATA FROM nxcodez WHERE code = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data += 1;
                }
            }
        } catch (SQLException Ex) {
            System.err.println("判断兑换卡是否存在、出错");
        }

        return data;
    }

    public int 判断兑换卡类型(String code) throws SQLException {
        int item = -1;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `leixing` FROM nxcodez WHERE code = ?");
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = rs.getInt("leixing");
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("判断兑换卡是否存在、出错");
        }
        return item;
    }

    public int 判断兑换卡数额(String code) throws SQLException {
        int item = -1;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `valid` FROM nxcodez WHERE code = ?");
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = rs.getInt("valid");
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("判断兑换卡是否存在、出错");
        }
        return item;
    }

    public int 判断兑换卡礼包(String code) throws SQLException {
        int item = -1;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT `itme` FROM nxcodez WHERE code = ?");
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = rs.getInt("itme");
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("判断兑换卡是否存在、出错");
        }
        return item;
    }

    //删除兑换卡
    public void Deleteexchangecard(String a) {
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ps1 = con.prepareStatement("SELECT * FROM nxcodez ");
            rs = ps1.executeQuery();
            while (rs.next()) {
                String sqlstr = " delete from nxcodez where code = '" + a + "' ";
                ps1.executeUpdate(sqlstr);
            }
        } catch (SQLException ex) {
        }
    }

    public void 充值卡兑换记录(String msg1, String msg2) {
        FileoutputUtil.logToFile("充值卡兑换记录/" + CurrentReadable_Date() + "/" + msg1 + " 充值卡.txt", "" + msg2 + "\r\n");
    }

    public void 打开网页(String web) {//打开网址
        c.getSession().write(MaplePacketCreator.openWeb(web));
    }

    public void 小纸条(String type1, String type2) {
        c.getPlayer().sendNote(type1, type2);
    }
    
            public static String SN取出售(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? && channel = 1");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String SN取库存(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 2");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String SN取折扣(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 3");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String SN取限购(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 4");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String SN取类型(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT Point as DATA FROM character7 WHERE Name = ? &&  channel = 5");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static int 角色名字取ID(String id) {
        int data = 0;
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT id as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色名字取ID出错 - 数据库查询失败：" + Ex);
        }

        return data;
    }
    public static String 角色ID取名字(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM characters WHERE id = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    public static int 角色名字取账号ID(String id) {
        int data = 0;
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT accountid as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getInt("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色名字取ID出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    public static String IP取账号(String id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE SessionIP = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getString("DATA");
                    return data;
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String MAC取账号(String id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE macs = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色ID取名字出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }
    public static String 账号ID取账号(String id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT name as DATA FROM accounts WHERE id = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色名字取ID出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }

    public static String 账号ID取在线(int id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT loggedin as DATA FROM accounts WHERE id = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色名字取ID出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }

    public static String 角色名字取等级(String id) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT level as DATA FROM characters WHERE name = ?");
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取角色名字取ID出错 - 数据库查询失败：" + Ex);
        }
        if (data == null) {
            data = "匿名人士";
        }
        return data;
    }

    public static String 物品获取掉落怪物(int itemid) {
        String data = "";
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT dropperid as DATA FROM drop_data WHERE itemid = ?");
            ps.setInt(1, itemid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    data = rs.getString("DATA");
                }
            }
            ps.close();
        } catch (SQLException Ex) {
            System.err.println("获取物品获取掉落怪物出错 - 数据库查询失败：" + Ex);
        }
        return data;
    }
    

    public Item addbyId_Gachapon(final MapleClient c, final int itemId, short quantity) {
        return MapleInventoryManipulator.addbyId_Gachapon(c, itemId, quantity);
    }
}
