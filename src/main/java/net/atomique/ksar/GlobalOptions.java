
package net.atomique.ksar;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.UI.Desktop;
import net.atomique.ksar.XML.CnxHistory;
import net.atomique.ksar.XML.ColumnConfig;
import net.atomique.ksar.XML.HostInfo;
import net.atomique.ksar.XML.OSConfig;

/**
 *
 * @author Max
 */
public class GlobalOptions {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalOptions.class);
    
    private static GlobalOptions instance = new GlobalOptions();

    public static GlobalOptions getInstance() {
        return instance;
    }

    public static boolean hasUI() {
        if (UI != null) {
            return true;
        }
        return false;
    }

    GlobalOptions() {
        Properties systemprops = System.getProperties();
        username = (String) systemprops.get("user.name");
        userhome = (String) systemprops.get("user.home") + systemprops.get("file.separator");
        fileseparator = (String) systemprops.get("file.separator");
        columnsMap = new HashMap<String, ColumnConfig>();
        OSlist = new HashMap<String, OSConfig>();
        parserMap = new HashMap<String, Class>();
        historyList = new HashMap<String, CnxHistory>();
        hostInfoList = new HashMap<String, HostInfo>();
        
        String filename = null;
        InputStream is = this.getClass().getResourceAsStream("/Config.xml");
        for (OsParsers parser : OsParsers.values()) {
            try {
                Class tmpclass = Class.forName("net.atomique.ksar.Parser."+parser.name());
                parserMap.put(parser.name(), tmpclass);
            } catch (ClassNotFoundException ex) {
                LOGGER.error("parser not found {}", parser.name(), ex);
            }
        }
        
        XMLConfig tmp = new XMLConfig(is);
        for (String parsername : parserMap.keySet()) {
            is = this.getClass().getResourceAsStream("/" + parsername + ".xml");
            if (is != null) {
                tmp.load_config(is);
            }
        }

        filename = userhome + ".ksarcfg" + fileseparator + "Config.xml";
        if (new File(filename).canRead()) {
            tmp.load_config(filename);
        }
        
        filename = userhome + ".ksarcfg" + fileseparator + "History.xml";
        if (new File(filename).canRead()) {
            tmp.load_config(filename);
        }

    }

    public static Desktop getUI() {
        return UI;
    }

    public static void setUI(Desktop UI) {
        GlobalOptions.UI = UI;
    }

    public static String getUserhome() {
        return userhome;
    }

    public static String getUsername() {
        return username;
    }

    public static HashMap<String, ColumnConfig> getColorlist() {
        return columnsMap;
    }

    public static HashMap<String, OSConfig> getOSlist() {
        return OSlist;
    }

    public static ColumnConfig getColumnConfig(String s) {
        return columnsMap.get(s);
    }
    
    public static Color getDataColor(String s) {
        ColumnConfig tmp = columnsMap.get(s);
        if (tmp != null) {
            return tmp.getDataColor();
        } else {
            LOGGER.warn("color not found for tag {}", s);
        }
        return null;
    }

    public static OSConfig getOSinfo(String s) {
        return OSlist.get(s);
    }

   
    public static String getCLfilename() {
        return CLfilename;
    }

    public static void setCLfilename(String cLFilename) {
        GlobalOptions.CLfilename = cLFilename;
    }

    public static String getFileseparator() {
        return fileseparator;
    }

    public static Class getParser(String s) {
        String tmp = s.replaceAll("-", "");
        if (parserMap.isEmpty()) {
            return null;
        }
        return parserMap.get(tmp);
    }

    public static HashMap<String, HostInfo> getHostInfoList() {
        return hostInfoList;
    }

    public static HostInfo getHostInfo(String s) {
        if (hostInfoList.isEmpty()) {
            return null;
        }
        return hostInfoList.get(s);
    }

    public static void addHostInfo(HostInfo s) {
        hostInfoList.put(s.getHostname(), s);
        saveHistory();
    }

    public static HashMap<String, CnxHistory> getHistoryList() {
        return historyList;
    }

    public static CnxHistory getHistory(String s) {
        if (historyList.isEmpty()) {
            return null;
        }
        return historyList.get(s);
    }

    public static void addHistory(CnxHistory s) {
        CnxHistory tmp = historyList.get(s.getLink());
        if ( tmp != null) {
            Iterator<String> ite = s.getCommandList().iterator();
            while (ite.hasNext()) {
                tmp.addCommand(ite.next());
            }
        } else {
            historyList.put(s.getLink(), s);
        }
        saveHistory();
    }

    

    public static void saveHistory() {
        File tmpfile = null;
        BufferedWriter tmpfile_out = null;

        if (historyList.isEmpty() && hostInfoList.isEmpty()) {
            System.out.println("list is null");
            return;
        }

        try {
            tmpfile = new File(userhome + ".ksarcfg" + fileseparator + "History.xmltemp");

            if ( tmpfile.exists() ) {
                tmpfile.delete();
            }
            if (tmpfile.createNewFile() && tmpfile.canWrite()) {
                tmpfile_out = new BufferedWriter(new FileWriter(tmpfile));
            }
            //xml header
            tmpfile_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ConfiG>\n");
            tmpfile_out.write("\t<History>\n");
            Iterator<String> ite = historyList.keySet().iterator();
            while (ite.hasNext()) {
                CnxHistory tmp = historyList.get(ite.next());
                tmpfile_out.write(tmp.save());
            }
            //xml footer
            tmpfile_out.write("\t</History>\n");
            tmpfile_out.write("\t<HostInfo>\n");
            Iterator<String> ite2 = hostInfoList.keySet().iterator();
            while (ite2.hasNext()) {
                HostInfo tmp = hostInfoList.get(ite2.next());
                tmpfile_out.write(tmp.save());
            }
            //xml footer
            tmpfile_out.write("\t</HostInfo>\n");
            tmpfile_out.write("</ConfiG>\n");
            tmpfile_out.flush();
            tmpfile_out.close();
            File oldfile = new File(userhome + ".ksarcfg" + fileseparator + "History.xml");
            oldfile.delete();
            tmpfile.renameTo(oldfile);

        } catch (IOException ex) {
            LOGGER.error("", ex);
        }

    }
    private static Desktop UI = null;
    private static String userhome;
    private static String username;
    private static String fileseparator;
    private static HashMap<String, ColumnConfig> columnsMap;
    private static HashMap<String, OSConfig> OSlist;
    private static HashMap<String, CnxHistory> historyList;
    private static HashMap<String, HostInfo> hostInfoList;
    private static String CLfilename = null;
    private static HashMap<String, Class> parserMap;
}
