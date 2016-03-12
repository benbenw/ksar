package net.atomique.ksar.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

import org.jfree.data.time.Second;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.kSar;
import net.atomique.ksar.UI.DataView;
import net.atomique.ksar.XML.OSConfig;

/**
 *
 * @author alex
 */
public abstract class BaseParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataView.class);
    
    protected Second startofstat = null;
    protected Second endofstat = null;
    
    protected Date sarStartDate = null;
    protected Date sarEndDate = null;

    protected Second startofgraph = null;
    protected Second endofgraph = null;
    protected TreeSet<Second> dateSamples = new TreeSet<Second>();
    protected int firstdatacolumn = 0;

    
    protected kSar mysar = null;
    protected OSConfig myosconfig = null;
    protected String parserName = null;

    protected Calendar cal = Calendar.getInstance();
    protected Date parsedate = null;
    protected int day = 0;
    protected int month = 0;
    protected int year = 0;
    protected String currentStat = "NONE";
    protected String dateFormat = "MM/dd/yy";
    protected String timeFormat = "HH:mm:ss";
    protected int timeColumn = 1;
    protected SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
    
    public BaseParser () {}

    public void init (kSar hissar, String header) {
        String[] s = header.split("\\s+");
        mysar = hissar;
        parserName = s[0];
        parseHeader(header);
    }

    public BaseParser(kSar hissar, String header) {
        init(hissar, header);
    }

    public int parse(String line, String[] columns) {
        LOGGER.error("not implemented");
        return -1;
    }

    public Second getStartofgraph() {
        return startofgraph;
    }
    
    public Second getEndofgraph() {
        return endofgraph;
    }

    public String getParserName() {
        return parserName;
    }

     public boolean setDate(String s) {
        Date dateSimple1;
        
        try {
            dateSimple1 = simpleDateFormat.parse(s);
            cal.setTime(dateSimple1);
            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH) + 1;
            year = cal.get(Calendar.YEAR);
            if (sarStartDate == null) {
                sarStartDate = dateSimple1;
            }
            
            if (sarEndDate == null) {
                sarEndDate = dateSimple1;
            }
        } catch (ParseException e) {
            return false;
        }
        
        if (dateSimple1.compareTo(sarStartDate) < 0) {
            sarStartDate = dateSimple1;
        }
        else if (dateSimple1.compareTo(sarEndDate) > 0) {
            sarEndDate = dateSimple1;
        }
        return true;
    }

     public String getDate() {
        if (sarStartDate.equals(sarEndDate)) {
            return sarStartDate.toString();
        } else {
            return sarStartDate + " to " + sarEndDate;
        }
    }

    public TreeSet<Second> getDateSamples() {
        return dateSamples;
    }

    public String getCurrentStat() {
        return currentStat;
    }
    
    protected boolean shouldIgnoreLine(String line) {
        boolean ignore = false;
        
        if (line == null || line.length() == 0) {
            ignore = true;
        }
        
        else if (line.indexOf("unix restarts") >= 0 || line.indexOf(" unix restarted") >= 0) {
            ignore = true;
        }

        // match the System [C|c]onfiguration line on AIX
        else if (line.indexOf("System Configuration") >= 0 || line.indexOf("System configuration") >= 0) {
            ignore = true;
        }

        else if (line.indexOf("State change") >= 0) {
            ignore = true;
        }
        
        return ignore;
    }
    
    abstract public String getInfo();
    abstract public void parseHeader(String s);

}
