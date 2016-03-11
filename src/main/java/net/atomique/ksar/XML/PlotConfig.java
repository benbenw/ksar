
package net.atomique.ksar.XML;

import java.text.NumberFormat;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.Range;

import net.atomique.ksar.Graph.IEEE1541Number;

/**
 *
 * @author Max
 */
public class PlotConfig {

    public PlotConfig(String s) {
        this.Title = s;
    }

    public String[] getHeader() {
        return this.Header;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setHeaderStr(String s) {
        this.Header = s.split("\\s+");
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSize(String s) {
        Integer tmp = new Integer(s);
        
        this.size = tmp.intValue();
    }


    public NumberAxis getAxis() {
        NumberAxis tmp = new NumberAxis(this.Title);
        if ("1024".equals(this.base)) {
            NumberFormat decimalformat1 = new IEEE1541Number(this.factor.intValue());
            tmp.setNumberFormatOverride(decimalformat1);
        }

        if (this.range != null) {
            tmp.setRange(this.range);
        }
        return tmp;
    }

    public void setBase(String s) {
        if (s == null) {
            return;
        }
        this.base = s;
    }

    public void setFactor(String s) {
        this.factor = Double.parseDouble(s);
    }

    public void setRange(String s) {
        String[] t = s.split(",");
        if (t.length == 2) {
            Double min = Double.parseDouble(t[0]);
            Double max = Double.parseDouble(t[1]);
            this.range = new Range(min, max);
        }
    }
    

    private Double factor = null;
    private String base = null;
    private Range range = null;
    private int size = 1;
    private String Title = null;
    private String[] Header = null;
}
