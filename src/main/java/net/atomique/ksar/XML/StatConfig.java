
package net.atomique.ksar.XML;

/**
 *
 * @author alex
 */
public class StatConfig {

    public StatConfig(String s) {
        this.statName = s;
    }

    public String getGraphName() {
        return this.graphName;
    }

    public void setGraphName(String GraphName) {
        this.graphName = GraphName;
    }

    public String[] getHeader() {
        return this.Header;
    }

    public String getHeaderStr() {
        return this.HeaderStr;
    }

    public void setHeaderStr(String s) {
        this.HeaderStr = s;
        this.Header = this.HeaderStr.split("\\s+");
        this.headerNum = this.Header.length;
    }

    public String getStatName() {
        return this.statName;
    }

    public boolean check_Header(String c, int i) {
        if (!compare_Header(i)) {
            return false;
        }

        if (this.HeaderStr.equals(c)) {
            return true;
        }
        return false;
    }

    public boolean compare_Header(int i) {
        if (i == this.headerNum) {
            return true;
        }
        return false;
    }

    public boolean canDuplicateTime() {
        return this.duplicatetime;
    }

    public void setDuplicateTime(String s) {
        if ( "yes".equals(s) || "true".equals(s) ){
            this.duplicatetime=true;
        }
    }

    

    private int headerNum = 0;
    private String statName = null;
    private String graphName = null;
    private String[] Header = null;
    private String HeaderStr = null;
    private boolean duplicatetime = false;
}
