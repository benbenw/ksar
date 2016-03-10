
package net.atomique.ksar;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JDesktopPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.UI.DataView;
import net.atomique.ksar.UI.SortedTreeNode;
import net.atomique.ksar.UI.TreeNodeInfo;

/**
 *
 * @author Max
 */
public class kSar {

    private static final Logger LOGGER = LoggerFactory.getLogger(kSar.class);
    
    public kSar(JDesktopPane desktopPane) {
        dataview = new DataView(this);
        dataview.toFront();
        dataview.setVisible(true);
        dataview.setTitle("Empty");
        desktopPane.add(dataview);
        try {
            int num = desktopPane.getAllFrames().length;
            if (num != 1) {
                dataview.reshape(5 * num, 5 * num, 800, 600);
            } else {
                dataview.reshape(0, 0, 800, 600);
            }
            dataview.setSelected(true);
        } catch (PropertyVetoException vetoe) {
        }
        if (GlobalOptions.getCLfilename() != null) {
            do_fileread(GlobalOptions.getCLfilename());
        }
    }

    public kSar() {
    }

    public void do_fileread(String filename) {
        if (filename == null) {
            launched_action = new FileRead(this);
        } else {
            launched_action = new FileRead(this, filename);
        }
        reloadAction = ((FileRead) launched_action).get_action();
        doAction();
    }

    public void do_localcommand(String cmd) {
        if (cmd == null) {
            launched_action = new LocalCommand(this);
        } else {
            launched_action = new LocalCommand(this, cmd);
        }
        reloadAction = ((LocalCommand) launched_action).get_action();
        doAction();
    }

    public void do_sshread(String cmd) {
        if (cmd == null) {
            launched_action = new SSHCommand(this);
        } else {
            launched_action = new SSHCommand(this, cmd);
        }

        reloadAction = ((SSHCommand) launched_action).get_action();
        doAction();
    }

    private void doAction() {
        if (reloadAction == null ) {
            System.out.println("action is null");
            return;
        }
        if (launched_action != null) {
            if (dataview != null) {
                dataview.notifyrun(true);
            }
            launched_action.start();
        }
    }

    public int parse(BufferedReader br) {
        String currentLine = null;
        long parsingStart = 0L;
        long parsingEnd = 0L;
        String[] columns;
        int parserReturn = 0;

        parsingStart = System.currentTimeMillis();

        try {
            while ((currentLine = br.readLine()) != null && !action_interrupted) {
                parsing = true;

                linesParsed++;
                if (currentLine.length() == 0) {
                    continue;
                }
                columns = currentLine.split("\\s+");

                if (columns.length == 0) {
                    continue;
                }

                String parserType = columns[0];
                try {
                    Class classtmp = GlobalOptions.getParser(parserType);
                    if (classtmp != null) {
                        if (myparser == null) {
                            myparser = (AllParser) classtmp.newInstance();
                            myparser.init(this, currentLine);

                            continue;
                        } else {
                            if (myparser.getParserName().equals(parserType)) {
                                myparser.parse_header(currentLine);
                                continue;
                            }
                        }
                    }
                } catch (InstantiationException ex) {
                    LOGGER.error("", ex);
                } catch (IllegalAccessException ex) {
                    LOGGER.error("", ex);
                }


                if (myparser == null) {
                    System.out.println("unknown parser");
                    parsing = false;
                    return -1;
                }

                parserReturn = myparser.parse(currentLine, columns);
                if(LOGGER.isDebugEnabled()) {
                    if (parserReturn == 1) {
                        LOGGER.debug("### " + currentLine);
                    }
                    else if (parserReturn < 0) {
                        LOGGER.debug("ERR " + currentLine);
                    }
                }

                myparser.updateUITitle();
            }
        } catch (IOException ex) {
            LOGGER.error("", ex);
            parsing = false;
        }

        if (dataview != null) {
            dataview.treehome();
            dataview.notifyrun(false);
            dataview.setHasData(true);
        }

        parsingEnd = System.currentTimeMillis();
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("time to parse: " + (parsingEnd - parsingStart) + "ms ");
            if (myparser != null) {
                LOGGER.debug("number of datesamples: " + myparser.DateSamples.size());
            }
        }
        parsing = false;
        return -1;
    }

    public void cleared() {
        aborted();
    }

    public void aborted() {
        if (dataview != null) {
            System.out.println("reset menu");
            dataview.notifyrun(false);
        }
    }

    public boolean isAction_interrupted() {
        return action_interrupted;
    }

    public void interrupt_parsing() {
        if (isParsing()) {
            action_interrupted = true;
        }
    }

    public void add2tree(SortedTreeNode parent, SortedTreeNode newNode) {
        if (dataview != null) {
            dataview.add2tree(parent, newNode);
        }
    }

    public int getPageToPrint() {
        return count_printSelected(graphtree);
    }

    public int count_printSelected(SortedTreeNode node) {
        int page_to_print = 0;
        int num = node.getChildCount();

        if (num > 0) {
            Object obj1 = node.getUserObject();
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                page_to_print += count_printSelected(l);
            }
        } else {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                if (nodeobj.isPrintSelected()) {
                    page_to_print++;
                }
            }
        }
        
        return page_to_print;
    }

    public DataView getDataView() {
        return dataview;
    }

    public boolean isParsing() {
        return parsing;
    }
    
    private DataView dataview = null;
    private long linesParsed = 0L;
    private String reloadAction = "Empty";
    private Thread launched_action = null;
    private boolean action_interrupted = false;
    private boolean parsing = false;
    
    public AllParser myparser = null;
    public int total_graph = 0;
    public SortedTreeNode graphtree = new SortedTreeNode("kSar");
}
