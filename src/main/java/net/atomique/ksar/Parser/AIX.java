
package net.atomique.ksar.Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jfree.data.time.Second;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.Graph.GraphList;
import net.atomique.ksar.UI.DataView;
import net.atomique.ksar.XML.GraphConfig;

/**
 *
 * @author Max
 */
public class AIX extends OSParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataView.class);
    
    @Override
    public void parseHeader(String s) {
        String [] columns = s.split("\\s+");
        setOstype(columns[0]);

        setHostname(columns[1]);
        setOSversion(columns[2]+ "." + columns[3]);
        setMacAddress(columns[4]);
        setDate(columns[5]);
    }

    @Override
    public int parse(String line, String[] columns) {
        int hour = 0;
        int minute = 0;
        int seconde = 0;


        if ("Average".equals(columns[0])) {
            underAverage = true;
            return 0;
        }

        if (shouldIgnoreLine(line)) {
            return 0;
        }


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
            parsedate = simpleDateFormat.parse(columns[0]);
            cal.setTime(parsedate);
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            seconde = cal.get(Calendar.SECOND);
            now = new Second(seconde, minute, hour, day, month, year);
            if (startofstat == null) {
                startofstat = now;
                startofgraph =now;
            }
            if ( endofstat == null) {
                endofstat = now;
                endofgraph = now;
            }
            if (now.compareTo(endofstat) > 0) {
                endofstat = now;
                endofgraph = now;
            }
            firstdatacolumn = 1;
        } catch (ParseException ex) {
            if (! "DEVICE".equals(currentStat) || "CPUS".equals(currentStat)) {
                System.out.println("unable to parse time " + columns[0]);
                return -1;
            }
            firstdatacolumn = 0;
        }


        /** XML COLUMN PARSER **/
        String checkStat = myosconfig.getStat(columns, firstdatacolumn);

        if (checkStat != null) {
            Object obj = listofGraph.get(checkStat);
            if (obj == null) {
                GraphConfig mygraphinfo = myosconfig.getGraphConfig(checkStat);
                if (mygraphinfo != null) {
                    if ("unique".equals(mygraphinfo.getType())) {
                        obj = new Graph(mysar, mygraphinfo, mygraphinfo.getTitle(), line, firstdatacolumn, mysar.graphtree);
                        listofGraph.put(checkStat, obj);
                        currentStat = checkStat;
                        return 0;
                    }
                    if ("multiple".equals(mygraphinfo.getType())) {
                        obj = new GraphList(mysar, mygraphinfo, mygraphinfo.getTitle(), line, firstdatacolumn);
                        listofGraph.put(checkStat, obj);
                        currentStat = checkStat;
                        return 0;
                    }
                } else {
                    // no graph associate
                    currentStat = checkStat;
                    return 0;
                }
            } else {
                currentStat = checkStat;
                return 0;
            }
        }

      
        if (lastStat != null) {
            if (!lastStat.equals(currentStat) ) {
                LOGGER.debug("Stat change from " + lastStat + " to " + currentStat);
                lastStat = currentStat;
                underAverage = false;
            }
        } else {
            lastStat = currentStat;
        }
        
        if ("IGNORE".equals(currentStat)) {
            return 1;
        }
        if ("NONE".equals(currentStat)) {
            return -1;
        }

        if (underAverage) {
            return 0;
        }
        currentStatObj = listofGraph.get(currentStat);
        if (currentStatObj == null) {
            return -1;
        } else {
            dateSamples.add(now);
            if (currentStatObj instanceof Graph) {
                Graph ag = (Graph) currentStatObj;
                return ag.parseLine(now, line);
            }
            if (currentStatObj instanceof GraphList) {
                GraphList ag = (GraphList) currentStatObj;
                return ag.parseLine(now, line);
            }
        }
        return -1;
    }


    private Second now = null;
    private boolean underAverage = false;
}
