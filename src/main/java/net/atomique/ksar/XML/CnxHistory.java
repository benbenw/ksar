
package net.atomique.ksar.XML;

import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author alex
 */
public class CnxHistory {

    public CnxHistory(String link) {
        this.link=link;
        this.commandList = new TreeSet<String>();
        String[] s = link.split("@", 2);
        if (s.length != 2) {
            return;
        }
        this.setUsername(s[0]);
        String[] t = s[1].split(":");
        if (t.length == 2) {
            this.setHostname(t[0]);
            this.setPort(t[1]);
        } else {
            this.setHostname(s[1]);
            this.setPort("22");
        }
    }

    public void addCommand(String s) {
        this.commandList.add(s);
    }

    public TreeSet<String> getCommandList() {
        return this.commandList;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPort() {
        return this.port;
    }

    public int getPortInt() {
        Integer tmp = Integer.parseInt(this.port);
        return tmp.intValue();
    }
    
    public void setPort(String port) {
        this.port = port;
    }

    public String getLink() {
        return this.link;
    }
    
    public boolean isValid() {
        if (this.username == null) {
            return false;
        }
        if (this.hostname == null) {
            return false;
        }
        if (this.commandList.isEmpty()) {
            return false;
        }
        return true;
    }

    public void dump() {
        System.out.println(this.username + "@" + this.hostname + ":" + this.commandList);
    }

    public String save() {
        StringBuilder tmp = new StringBuilder();
        if ( "22".equals(this.port)) {
            tmp.append("\t\t<cnx link=\"" + this.username + "@" + this.hostname + "\">\n");
        } else {
            tmp.append("\t\t<cnx link=\"" + this.username + "@" + this.hostname + ":" + this.port + "\">\n");
        }
        Iterator<String> ite = this.commandList.iterator();
        while (ite.hasNext()) {
            tmp.append("\t\t\t<command>").append(ite.next()).append("</command>\n");
        }
        tmp.append("\t\t</cnx>\n");
        return tmp.toString();
    }
    
    private String username = null;
    private String hostname = null;
    private TreeSet<String> commandList = null;
    private String port = null;
    private String link = null;
}
