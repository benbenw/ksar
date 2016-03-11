
package net.atomique.ksar.XML;

import java.awt.Color;

/**
 *
 * @author Max
 */
public class ColumnConfig {

    public ColumnConfig(String s) {
        this.data_title=s;
    }

    public void setData_color(String data_color) {
        String[] color_indices = data_color.split(",");
        if (color_indices.length == 3) {
            try {
                Integer red = new Integer(color_indices[0]);
                Integer green = new Integer(color_indices[1]);
                Integer blue = new Integer(color_indices[2]);
                this.data_color = new Color(red, green, blue);
            } catch (NumberFormatException ee) {
            }
        }
    }

    public Color getDataColor() {
        return this.data_color;
    }

    public String getData_title() {
        return this.data_title;
    }

    public void setData_title(String dataTitle) {
        this.data_title = dataTitle;
    }

    public void setType(String s){
        if ( "gauge".equals(s)) {
            this.type=1;
        }
        if ( "counter".equals(s)) {
            this.type=2;
        }
    }

    public int getType() {
        return this.type;
    }
    
    
    private int type = 0;
    private Color data_color = null;
    private String data_title = null;
}
