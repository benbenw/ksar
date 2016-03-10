
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
    public String getData_colorstr() {
        return this.data_colorstr;
    }

    public void setData_color(String data_color) {
        String[] color_indices = data_color.split(",");
        this.data_colorstr = data_color;
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

    public Color getData_color() {
        return this.data_color;
    }

    public String getData_title() {
        return this.data_title;
    }

    public void setData_title(String data_title) {
        this.data_title = data_title;
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
    
    public boolean is_valid() {
        if (this.data_title == null) {
            this.error_message = "Column header name not found";
            return false;
        }
        if (this.data_colorstr == null) {
            this.error_message = "color info missing for " + this.data_title;
            return false;
        }
        if (this.data_color == null) {
            this.error_message = "color " + this.data_colorstr + " is not a valid color";
            return false;
        }
        return true;
    }

    public String getError_message() {
        return this.error_message;
    }

    private int type = 0;
    private Color data_color = null;
    private String error_message = null;
    private String data_title = null;
    private String data_colorstr = null;
}
