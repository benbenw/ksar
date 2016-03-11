
package net.atomique.ksar.XML;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author alex
 */
public class OSConfig {

    public OSConfig(String s) {
        this.OSname = s;
    }

    public void addStat(StatConfig s) {
        this.StatHash.put(s.getStatName(), s);
    }

    public void addGraph(GraphConfig s) {
        this.GraphHash.put(s.getName(), s);
    }

    public String getOSname() {
        return this.OSname;
    }

    public String getStat(String[] columns, int firstdatacolumn) {

        StringBuilder tmpbuf = new StringBuilder();
        int num = 0;
        for (int i = firstdatacolumn; i < columns.length; i++) {
            if (tmpbuf.length() != 0) {
                tmpbuf.append(" ");
            }
            tmpbuf.append(columns[i]);
            num++;
        }
        String header = tmpbuf.toString();
        

        Iterator<String> ite = this.StatHash.keySet().iterator();
        while (ite.hasNext()) {
            String tmptitle = ite.next();
            StatConfig tmp = this.StatHash.get(tmptitle);
            if (tmp.checkHeader(header, num)) {
                return tmp.getGraphName();
            }
        }
        
        return null;
    }

    public StatConfig getStat(String statName) {
        if (this.StatHash.isEmpty()) {
            return null;
        }
        Iterator<String> ite = this.StatHash.keySet().iterator();
        while (ite.hasNext()) {
            String tmptitle = ite.next();
            StatConfig tmp = this.StatHash.get(tmptitle);
            if ( tmp.getGraphName().equals(statName)) {
                return tmp;
            }
        }
        return null;
    }

    public GraphConfig getGraphConfig(String s) {
        return this.GraphHash.get(s);
    }


    private String OSname = null;
    private HashMap<String, StatConfig> StatHash = new HashMap<String, StatConfig>();
    private HashMap<String, GraphConfig> GraphHash = new HashMap<String, GraphConfig>();
}
