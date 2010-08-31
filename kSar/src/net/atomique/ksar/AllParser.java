/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.atomique.ksar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;
import net.atomique.ksar.Parser.Linux;
import net.atomique.ksar.XML.OSConfig;
import org.jfree.data.time.Second;

/**
 *
 * @author alex
 */
public abstract class AllParser {

     

    public AllParser () {

    }

    public void init (kSar hissar, String header) {
        String [] s = header.split("\\s+");
        mysar = hissar;
        ParserName = s[0];
        parse_header(header);
    }

    public AllParser(kSar hissar, String header) {
        init(hissar, header);
    }

    public int parse(String line, String[] columns) {
        System.err.println("not implemented");
        return -1;
    }

    public Second get_startofgraph() {
        return startofgraph;
    }
    public Second get_endofgraph() {
        return endofgraph;
    }

    public String getParserName() {
        return ParserName;
    }

     public void setDate(String s) {
        Date dateSimple1;
        Date dateSimple2;
        Date dateSimple3;
        String dateFormat = "MM/dd/yy";
        if (sarStartDate == null) {
            sarStartDate = s;
        }
        if (sarEndDate == null) {
            sarEndDate = s;
        }
        if ( this instanceof Linux ) {
            if ( "MM/DD/YYYY 23:59:59".equals(Config.getLinuxDateFormat()) ) {
                dateFormat = "MM/dd/yy";
            } else if ("MM/DD/YYYY 12:59:59 AM|PM".equals(Config.getLinuxDateFormat()) ) {
                dateFormat = "MM/dd/yy";
            } else if ( "DD/MM/YYYY 23:59:59".equals(Config.getLinuxDateFormat())) {
                dateFormat = "dd/MM/yy";
            } else if ( "YYYY/MM/DD 23:59:59".equals(Config.getLinuxDateFormat()) ) {
                dateFormat = "yy/MM/dd";
            } else if ( "Always ask".equals(Config.getLinuxDateFormat())) {
                dateFormat="MM/dd/yy";
            }
        }
        try {
            dateSimple1 = new SimpleDateFormat(dateFormat).parse(s);            
            cal.setTime(dateSimple1);
            day=cal.get(cal.DAY_OF_MONTH);
            month=cal.get(cal.MONTH)+1;
            year=cal.get(cal.YEAR);
            dateSimple2 = new SimpleDateFormat(dateFormat).parse(sarStartDate);
            dateSimple3 = new SimpleDateFormat(dateFormat).parse(sarEndDate);
        } catch (ParseException e) {
            return;
        }
        if (dateSimple1.compareTo(dateSimple2) < 0) {
            sarStartDate = s;
        }
        if (dateSimple1.compareTo(dateSimple3) > 0) {
            sarEndDate = s;
        }
    }

     public String getDate() {
        if (sarStartDate.equals(sarEndDate)) {
            return sarStartDate;
        } else {
            return sarStartDate + " to " + sarEndDate;
        }
    }

    public TreeSet<Second> getDateSamples() {
        return DateSamples;
    }

    public String getCurrentStat() {
        return currentStat;
    }


    protected Second startofstat = null;
    protected Second endofstat = null;
    protected String sarStartDate = null;
    protected String sarEndDate = null;

    protected Second startofgraph = null;
    protected Second endofgraph =null;
    protected TreeSet<Second> DateSamples = new TreeSet<Second>();
    protected int firstdatacolumn =0;

    abstract public String getInfo();
    abstract public void parse_header(String s);
    abstract public void updateUITitle();
     
    protected kSar mysar = null;
    protected OSConfig myosconfig = null;
    protected String ParserName = null;

    Calendar cal=Calendar.getInstance();
    protected int day = 0;
    protected int month = 0;
    protected int year = 0;
    protected String currentStat = "NONE";
}
