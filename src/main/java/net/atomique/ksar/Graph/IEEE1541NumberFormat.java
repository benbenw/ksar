

package net.atomique.ksar.Graph;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 *
 * @author alex
 */
public class IEEE1541NumberFormat extends NumberFormat {

    private static final int KB = 1024;
    private static final int MB = KB * 1024;
    private static final int GB = MB * 1024;
    private static final long serialVersionUID = 5L;

    public IEEE1541NumberFormat() {
    }

    public IEEE1541NumberFormat(int value) {
        kilo = value;
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        if (kilo == 0) {
            return toAppendTo.append(number);
        }
        
        if ((number * kilo) < KB) {
            return toAppendTo.append(number);
        }
        
        if ((number * kilo) < MB) {
            DecimalFormat formatter = new DecimalFormat("#,##0.0");
            toAppendTo.append(formatter.format(number / KB)).append(" KB");
            return toAppendTo;
        }
        
        if ((number * kilo) < GB) {
            DecimalFormat formatter = new DecimalFormat("#,##0.0");
            toAppendTo.append(formatter.format(number * kilo / MB)).append(" MB");
            return toAppendTo;
        }

        DecimalFormat formatter = new DecimalFormat("#,##0.0");
        toAppendTo.append(formatter.format(number * kilo / GB)).append(" GB");
        return toAppendTo;
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return format((double) (number * kilo), toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return null;
    }
    
    private int kilo = 0;
}
