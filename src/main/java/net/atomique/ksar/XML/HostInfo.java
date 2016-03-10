
package net.atomique.ksar.XML;

/**
 *
 * @author alex
 */
public class HostInfo {

    public HostInfo(String sarHostname) {
        this.setHostname(sarHostname);
    }

    public Integer getMemBlockSize() {
        return this.memBlockSize;
    }

    public void setMemBlockSize(Integer MemBlockSize) {
        this.memBlockSize = MemBlockSize;
    }

    public void setMemBlockSize(String MemBlockSizestr) {
        try {
        this.memBlockSize = Integer.parseInt(MemBlockSizestr);
        }  catch ( NumberFormatException nfe) {
        }
    }
    
    public String getAlias() {
        return this.aka_hostname;
    }

    public void setAlias(String aka_hostname) {
        this.aka_hostname = aka_hostname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostname() {
        return this.sarHostname;
    }

    public void setHostname(String sarHostname) {
        this.sarHostname = sarHostname;
    }

    public String save() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("\t\t<host name=\"" + this.sarHostname + "\">\n");
        tmp.append("\t\t\t<alias>" + this.aka_hostname + "</alias>\n");
        tmp.append("\t\t\t<description>" + this.description + "</description>\n");
        tmp.append("\t\t\t<memblocksize>" + this.memBlockSize + "</memblocksize>\n");
        tmp.append("\t\t</host>\n");
        return tmp.toString();
    }



    private String sarHostname =null;
    private String aka_hostname = null;
    private String description = null;
    private Integer memBlockSize = 1;
    
}
