/*
增加伤害的装备
 */
package abc;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 拍卖行限制 {

    private static 拍卖行限制 instance = null;
    private static boolean CANLOG;
    private Properties itempb_cfg;
    private String PM;

    private static Logger log = LoggerFactory.getLogger(拍卖行限制.class);

    public 拍卖行限制() {
        itempb_cfg = new Properties();
        try {
            InputStreamReader is = new FileReader("拍卖行限制.ini");
            itempb_cfg.load(is);
            is.close();
            PM = itempb_cfg.getProperty("PM");
        } catch (Exception e) {
            log.error("Could not configuration", e);
        }
    }

    public String getPM() {
        return PM;
    }

    public boolean isCANLOG() {
        return CANLOG;
    }

    public void setCANLOG(boolean CANLOG) {
        拍卖行限制.CANLOG = CANLOG;
    }

    public static 拍卖行限制 getInstance() {
        if (instance == null) {
            instance = new 拍卖行限制();
        }
        return instance;
    }
}
