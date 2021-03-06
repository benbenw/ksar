
package net.atomique.ksar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import net.atomique.ksar.XML.CnxHistory;
import net.atomique.ksar.XML.ColumnConfig;
import net.atomique.ksar.XML.GraphConfig;
import net.atomique.ksar.XML.HostInfo;
import net.atomique.ksar.XML.OSConfig;
import net.atomique.ksar.XML.PlotConfig;
import net.atomique.ksar.XML.StackConfig;
import net.atomique.ksar.XML.StatConfig;

/**
 *
 * @author Max
 */
public class XMLConfig extends DefaultHandler {

    public XMLConfig(String filename) {
        load_config(filename);
        
    }

    public XMLConfig(InputStream is) {
        load_config(is);
    }

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

            InputSource inputSource = null;
            try {
                String dtdFile = systemId.substring(systemId.lastIndexOf("/"));
                InputStream inputStream = EntityResolver.class.getResourceAsStream(dtdFile);
                inputSource = new InputSource(inputStream);
            } catch (Exception e) {
                // No action; just let the null InputSource pass through
            }

            // If nothing found, null is returned, for normal processing
            return inputSource;
        }
    
    public void load_config(InputStream is) {
        SAXParserFactory fabric = null;
        SAXParser parser = null;
        try {
            fabric = SAXParserFactory.newInstance();
            parser = fabric.newSAXParser();
            parser.parse(is, this);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(1);
        }
        
        try {
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(XMLConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load_config(String xmlfile) {
        SAXParserFactory fabric = null;
        SAXParser parser = null;
        try {
            fabric = SAXParserFactory.newInstance();
            parser = fabric.newSAXParser();
            parser.parse(xmlfile, this);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

   
    public void characters(char[] ch, int start, int length) throws SAXException {
        tempval = new String(ch, start, length);
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ("ConfiG".equals(qName)) {
            // config found
        }
        if ("colors".equals(qName)) {
            inColors = true;
        }
        if ("OS".equals(qName)) {
            inOS = true;
        }

        if ("History".equals(qName)) {
            inHistory = true;
        }

        if ( "HostInfo".equals(qName)) {
            inHostinfo=true;
        }
        
        // COLORS
        if (inColors) {
            if ("itemcolor".equals(qName)) {
                currentColor = new ColumnConfig(attributes.getValue("name"));
                inColor = true;
            }
        }

        // history
        if (inHistory) {
            if ("cnx".equals(qName)) {
                currentCnx = new CnxHistory(attributes.getValue("link"));
                inCnx = true;
            }
        }
        // hostinfo
        if (inHostinfo) {
            if ("host".equals(qName)) {
                currentHost = new HostInfo(attributes.getValue("name"));
            }
        }

        // OS
        if (inOS) {
            if ("OSType".equals(qName)) {
                currentOS = GlobalOptions.getOSlist().get(attributes.getValue("name"));
                if (currentOS == null) {
                    currentOS = new OSConfig(attributes.getValue("name"));
                    GlobalOptions.getOSlist().put(currentOS.getOSname(), currentOS);
                }
            }
            if (currentOS != null) {
                if ("Stat".equals(qName)) {
                    currentStat = new StatConfig(attributes.getValue("name"));
                    currentOS.addStat(currentStat);
                }
                if ("Graph".equals(qName)) {
                    currentGraph = new GraphConfig(attributes.getValue("name"), attributes.getValue("Title"), attributes.getValue("type"));
                    currentOS.addGraph(currentGraph);
                }
                if (currentGraph != null) {
                    if ("Plot".equals(qName)) {
                        currentPlot = new PlotConfig(attributes.getValue("Title"));
                        String size_tmp = attributes.getValue("size");
                        if (size_tmp != null) {
                            currentPlot.setSize(size_tmp);
                        }
                        currentGraph.addPlot(currentPlot);
                    }
                    if ("Stack".equals(qName)) {
                        currentStack = new StackConfig(attributes.getValue("Title"));
                        String size_tmp = attributes.getValue("size");
                        if (size_tmp != null) {
                            currentStack.setSize(size_tmp);
                        }
                        currentGraph.addStack(currentStack);
                    }

                    if (currentPlot != null) {
                        if ("format".equals(qName)) {
                            currentPlot.setBase(attributes.getValue("base"));
                            currentPlot.setFactor(attributes.getValue("factor"));
                        }
                    }
                    if (currentStack != null) {
                        if ("format".equals(qName)) {
                            currentStack.setBase(attributes.getValue("base"));
                            currentStack.setFactor(attributes.getValue("factor"));
                        }
                    }
                }

            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        // clean up tempval;
        tempval = tempval.trim();
        if ("ConfiG".equals(qName)) {
            beenparse = true;
        }
        if ("colors".equals(qName)) {
            inColors = false;
        }
        if ("OSType".equals(qName)) {
            currentOS = null;
        }
        if ("Stat".equals(qName)) {
            currentStat = null;
        }
        if ("Graph".equals(qName)) {
            currentGraph = null;
        }
        if ("Cnx".equals(qName)) {
            currentCnx = null;
        }
        if ("Plot".equals(qName)) {
            currentPlot = null;
        }
        if ("Stack".equals(qName)) {
            currentStack = null;
        }
        if ("HostInfo".equals(qName)) {
            inHostinfo = false;
        }
        


        if (currentStat != null) {
            if ("headerstr".equals(qName)) {
                currentStat.setHeaderStr(tempval);
            }
            if ("graphname".equals(qName)) {
                currentStat.setGraphName(tempval);
            }
            if ("duplicate".equals(qName)) {
                currentStat.setDuplicateTime(tempval);
            }
        }
        
        if ("cols".equals(qName)) {
            if (currentPlot != null) {
                currentPlot.setHeaderStr(tempval);

            }
            if (currentStack != null) {
                currentStack.setHeaderStr(tempval);
            }
        }
        if ("range".equals(qName)) {
            if (currentPlot != null) {
                currentPlot.setRange(tempval);

            }
            if (currentStack != null) {
                currentStack.setRange(tempval);
            }
        }


        if ("itemcolor".equals(qName)) {
            GlobalOptions.getColorlist().put(currentColor.getData_title(), currentColor);
            inColor = false;
        }

        if (inColor) {
            if ("color".equals(qName) && currentColor != null) {
                currentColor.setData_color(tempval);
            }
        }

        if (inCnx) {
            if ("command".equals(qName) && currentCnx != null) {
                currentCnx.addCommand(tempval);
            }
        }
        if ("cnx".equals(qName)) {
            if (currentCnx.isValid()) {
                GlobalOptions.getHistoryList().put(currentCnx.getLink(), currentCnx);
            } else {
                System.err.println("Err cnx is not valid");
                currentCnx = null;
            }
        }
        if ( inHostinfo ) {
            if ( "alias".equals(qName)) {
                currentHost.setAlias(tempval);
            }
            if ( "description".equals(qName)) {
                currentHost.setDescription(tempval);
            }
            if ( "memblocksize".equals(qName)) {
                currentHost.setMemBlockSize(tempval);
            }
        }
        if ( "host".equals(qName)) {
            GlobalOptions.getHostInfoList().put(currentHost.getHostname(), currentHost);
            currentHost=null;
        }
    }

    
    public boolean beenparse = false;
    private String tempval;
    private boolean inColor = false;
    private boolean inColors = false;
    private boolean inOS = false;
    private boolean inHistory = false;
    private boolean inCnx = false;
    private boolean inHostinfo = false;
    
    private ColumnConfig currentColor = null;
    private OSConfig currentOS = null;
    private StatConfig currentStat = null;
    private GraphConfig currentGraph = null;
    private PlotConfig currentPlot = null;
    private StackConfig currentStack = null;
    private CnxHistory currentCnx = null;
    private HostInfo currentHost = null;
    
}

