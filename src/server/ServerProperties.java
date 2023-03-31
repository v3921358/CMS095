package server;

import constants.GameConstants;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import tools.EncodingDetect;

public class ServerProperties {

    private static final Properties props = new Properties();

    private ServerProperties() {
    }

    static {
        String toLoad = "channel.properties";
        loadProperties(toLoad);
        toLoad = "world.properties";
        loadProperties(toLoad);

    }

    public static void loadProperties(String s) {
        InputStream in;
        try {
            in = new FileInputStream(s);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, EncodingDetect.getJavaEncode(s)));
            props.load(bf);
            bf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String s) {
        return props.getProperty(s);
    }

    public static void setProperty(String prop, String newInf) {
        props.setProperty(prop, newInf);
    }

    public static String getProperty(String s, String def) {
        return props.getProperty(s, def);
    }
}
