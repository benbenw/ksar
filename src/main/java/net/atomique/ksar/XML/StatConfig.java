
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


    public void setHeaderStr(String s) {
        this.HeaderStr = s;
        this.Header = s.split("\\s+");
    }

    public String getStatName() {
        return this.statName;
    }

    public boolean checkHeader(String c, int i) {
        if (!compareHeader(i)) {
            return false;
        }

        if (this.HeaderStr.equals(c)) {
            return true;
        }
        
        return false;
    }

    private boolean compareHeader(int i) {
        if (i == this.Header.length) {
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
    

    private String statName = null;
    private String graphName = null;
    private String[] Header = null;
    private String HeaderStr = null;
    private boolean duplicatetime = false;
}
