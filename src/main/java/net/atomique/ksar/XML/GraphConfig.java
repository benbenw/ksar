package net.atomique.ksar.XML;

import java.util.HashMap;

/**
 *
 * @author Max
 */
public class GraphConfig {

    public GraphConfig(String name, String title, String type) {
        this.name = name;
        this.title = title;
        this.type = type;
    }
    
    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
    
    public void addPlot(PlotConfig s) {
        this.plotlist.put(s.getTitle(), s);
    }

    public HashMap<String, PlotConfig> getPlotlist() {
        return this.plotlist;
    }

    public void addStack(StackConfig s) {
        this.stacklist.put(s.getTitle(), s);
    }

    public HashMap<String, StackConfig> getStacklist() {
        return this.stacklist;
    }

    private String name =null;
    private String title = null;
    private String type = null;
    HashMap<String, PlotConfig> plotlist = new HashMap<>();
    HashMap<String, StackConfig> stacklist = new HashMap<>();
}
