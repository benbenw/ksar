
package net.atomique.ksar.Export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.atomique.ksar.kSar;
import net.atomique.ksar.Graph.Graph;
import net.atomique.ksar.Graph.GraphList;
import net.atomique.ksar.UI.ParentNodeInfo;
import net.atomique.ksar.UI.SortedTreeNode;
import net.atomique.ksar.UI.TreeNodeInfo;

/**
 *
 * @author Max
 */
public class FileCSV implements Runnable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FileCSV.class);
    
    private GraphExportCallback exportCallback = null;

    public FileCSV(String filename, kSar hissar) {
        csvfilename = filename;
        mysar = hissar;
    }

    public FileCSV(String filename, kSar hissar, GraphExportCallback exportCallback) {
        csvfilename = filename;
        mysar = hissar;
        this.exportCallback = exportCallback;
    }

    public void run() {
        // open file
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(csvfilename));
        } catch (IOException e) {
            out = null;
        }

        // print header
        tmpcsv.append("Date;");
        
        export_treenode_header(mysar.graphtree);
        tmpcsv.append("\n");
        Iterator<Second> ite = mysar.parser.getDateSamples().iterator();
        while (ite.hasNext()) {
            Second tmp = ite.next();
            export_treenode_data(mysar.graphtree, tmp);
            tmpcsv.append("\n");
        }
        try {
            out.write(tmpcsv.toString());
            out.close();
        } catch (IOException ex) {
            LOGGER.error("", ex);
        }
        
        
        if (exportCallback != null) {
            exportCallback.onEnd();
        }
    }


    public void export_treenode_header(SortedTreeNode node) {
        int num = node.getChildCount();

        if (num > 0) {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof ParentNodeInfo) {
                ParentNodeInfo tmpnode = (ParentNodeInfo) obj1;
                GraphList nodeobj = tmpnode.getNode_object();                
            }
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                export_treenode_header(l);
            }
        } else {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                if ( nodeobj.doPrint()) {
                    tmpcsv.append(nodeobj.getCsvHeader());
                    
                }
            }
        }
    }

     public void export_treenode_data(SortedTreeNode node, RegularTimePeriod time) {
        int num = node.getChildCount();

        if (num > 0) {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof ParentNodeInfo) {
                ParentNodeInfo tmpnode = (ParentNodeInfo) obj1;
                GraphList nodeobj = tmpnode.getNode_object();
            }
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                export_treenode_data(l, time);
            }
        } else {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                TreeNodeInfo tmpnode = (TreeNodeInfo) obj1;
                Graph nodeobj = tmpnode.getNode_object();
                if ( nodeobj.doPrint()) {
                    tmpcsv.append(nodeobj.getCsvLine(time));
                    updateUi();

                }
            }
        }
    }
     
    private void updateUi() {
        if (exportCallback != null) {
            exportCallback.onGraphExported();
        }
    }

    
    private StringBuilder tmpcsv = new StringBuilder();
    private String csvfilename = null;
    private kSar mysar = null;
}
