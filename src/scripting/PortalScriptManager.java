package scripting;

import client.MapleClient;
import handling.login.LoginServer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import server.MaplePortal;
import tools.EncodingDetect;
import tools.FileoutputUtil;
import tools.StringUtil;

public class PortalScriptManager {

    private static final PortalScriptManager instance = new PortalScriptManager();
    private final Map<String, PortalScript> scripts = new HashMap<String, PortalScript>();
    private final static ScriptEngineFactory sef = new ScriptEngineManager().getEngineByName("javascript").getFactory();

    public final static PortalScriptManager getInstance() {
        return instance;
    }

    private final PortalScript getPortalScript(final String scriptName) {
        if (scripts.containsKey(scriptName)) {
            return scripts.get(scriptName);
        }

        final File scriptFile = new File("scripts/portal/" + scriptName + ".js");
        if (!scriptFile.exists()) {
            return null;
        }

        InputStreamReader fr = null;
        final ScriptEngine portal = sef.getScriptEngine();
        try {
            fr = new InputStreamReader(new FileInputStream(scriptFile), EncodingDetect.getJavaEncode(scriptFile));
            CompiledScript compiled = ((Compilable) portal).compile(fr);
            compiled.eval();
        } catch (final Exception e) {
            System.err.println("讀取傳送点脚本出錯: " + scriptName + ":" + e);
            FileoutputUtil.logToFile(FileoutputUtil.ScriptEx_Log, "讀取傳送点脚本出錯 (" + scriptName + ") " + e);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (final IOException e) {
                    System.err.println("ERROR CLOSING" + e);
                }
            }
        }
        final PortalScript script = ((Invocable) portal).getInterface(PortalScript.class);
        scripts.put(scriptName, script);
        return script;
    }

    public final void executePortalScript(final MaplePortal portal, final MapleClient c) {
        final PortalScript script = getPortalScript(portal.getScriptName());

        if (script != null) {
            try {
                if (c.getPlayer().isAdmin() || LoginServer.isLogPackets()) {
                    c.getPlayer().dropMessage(6, "[系統提示]您已经建立与PortalScript:[" + portal.getScriptName() + ".js]的对话。" + (script != null ? "" : "(脚本不存在或异常)"));
                }
                script.enter(new PortalPlayerInteraction(c, portal));
            } catch (Exception e) {
                System.err.println("Error entering Portalscript: " + portal.getScriptName() + " : " + e);
            }
        } else {
            if (c.getPlayer().isAdmin() || LoginServer.isLogPackets()) {
                c.getPlayer().dropMessage(5, "未找到傳送点脚本名为:(" + portal.getScriptName() + ".js)的文件 在地图 " + c.getPlayer().getMapId() + " - " + c.getPlayer().getMap().getMapName());
            }
            System.out.println("Unhandled portal script " + portal.getScriptName() + " on map " + c.getPlayer().getMapId());
            FileoutputUtil.logToFile(FileoutputUtil.ScriptEx_Log, "Unhandled portal script " + portal.getScriptName() + " on map " + c.getPlayer().getMapId());
        }
    }

    public final void clearScripts() {
        scripts.clear();
    }
}
