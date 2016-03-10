
package net.atomique.ksar;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;

import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.UI.DataView;
import net.atomique.ksar.UI.SortedTreeNode;
import net.atomique.ksar.UI.TreeNodeInfo;

/**
 *
 * @author Max
 */
public class kSar {

    private static final Logger LOGGER = Logger.getLogger(kSar.class.getName());
    public kSar(JDesktopPane DesktopPane) {
        dataview = new DataView(this);
        dataview.toFront();
        dataview.setVisible(true);
        dataview.setTitle("Empty");
        DesktopPane.add(dataview);
        try {
            int num = DesktopPane.getAllFrames().length;
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
        reload_action = ((FileRead) launched_action).get_action();
        doAction();
    }

    public void do_localcommand(String cmd) {
        if (cmd == null) {
            launched_action = new LocalCommand(this);
        } else {
            launched_action = new LocalCommand(this, cmd);
        }
        reload_action = ((LocalCommand) launched_action).get_action();
        doAction();
    }

    public void do_sshread(String cmd) {
        if (cmd == null) {
            launched_action = new SSHCommand(this);
            //mysar.reload_command=t.get_command();
        } else {
            launched_action = new SSHCommand(this, cmd);
        }

        reload_action = ((SSHCommand) launched_action).get_action();
        doAction();
    }

    private void doAction() {
        if (reload_action == null ) {
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
                    LOGGER.log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }


                if (myparser == null) {
                    System.out.println("unknown parser");
                    parsing = false;
                    return -1;
                }

                parserReturn = myparser.parse(currentLine, columns);
                if (parserReturn == 1 && GlobalOptions.isDodebug()) {
                    System.out.println("### " + currentLine);
                }
                if (parserReturn < 0 && GlobalOptions.isDodebug()) {
                    System.out.println("ERR " + currentLine);
                }

                myparser.updateUITitle();
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            parsing = false;
        }

        if (dataview != null) {
            dataview.treehome();
            dataview.notifyrun(false);
            dataview.setHasData(true);
        }

        parsingEnd = System.currentTimeMillis();
        if (GlobalOptions.isDodebug()) {
            System.out.println("time to parse: " + (parsingEnd - parsingStart) + "ms ");
            if (myparser != null) {
                System.out.println("number of datesamples: " + myparser.DateSamples.size());
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

    public int get_page_to_print() {
        page_to_print = 0;
        count_printSelected(graphtree);
        return page_to_print;
    }

    public void count_printSelected(SortedTreeNode node) {
        int num = node.getChildCount();

        if (num > 0) {
            Object obj1 = node.getUserObject();
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                count_printSelected(l);
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
    }

    public DataView getDataView() {
        return dataview;
    }

    public boolean isParsing() {
        return parsing;
    }
    
    DataView dataview = null;
    private long linesParsed = 0L;
    private String reload_action = "Empty";
    private Thread launched_action = null;
    private boolean action_interrupted = false;
    private boolean parsing = false;
    
    public AllParser myparser = null;
    public int total_graph = 0;
    public SortedTreeNode graphtree = new SortedTreeNode("kSar");
    public int page_to_print = 0;
}
