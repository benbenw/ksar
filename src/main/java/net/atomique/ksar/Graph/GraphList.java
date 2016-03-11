

package net.atomique.ksar.Graph;

import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jfree.data.time.Second;

import net.atomique.ksar.kSar;
import net.atomique.ksar.UI.ParentNodeInfo;
import net.atomique.ksar.UI.SortedTreeNode;
import net.atomique.ksar.UI.TreeNodeInfo;
import net.atomique.ksar.XML.GraphConfig;

/**
 *
 * @author alex
 */
public class GraphList {

    public GraphList(kSar hissar, GraphConfig g, String stitle, String sheader, int i) {
        mysar = hissar;
        headerStr = sheader;
        graphconfig = g ;
        title = stitle;
        skipColumn = i;
        ParentNodeInfo tmp = new ParentNodeInfo(title, this);
        parentTreeNode = new SortedTreeNode(tmp);
        mysar.add2tree(mysar.graphtree, parentTreeNode);
    }

    public int parseLine(Second now, String s) {
        String[] cols = s.split("\\s+");
        Graph tmp = nodeHashList.get(cols[skipColumn]);
        if (tmp == null) {
            tmp = new Graph(mysar, graphconfig, title + " " + cols[skipColumn], headerStr, skipColumn+1, null);
            nodeHashList.put(cols[skipColumn], tmp);
            TreeNodeInfo infotmp = new TreeNodeInfo( cols[skipColumn], tmp);
            SortedTreeNode nodetmp = new SortedTreeNode(infotmp);
            mysar.add2tree(parentTreeNode, nodetmp);
        }

        return tmp.parseLine(now,s);
    }


    public JPanel run() {
        int graphnumber = nodeHashList.size();
        int linenum = (int) Math.floor(graphnumber / 2);
        if (graphnumber % 2 != 0) {
            linenum++;
        }
        
        LayoutManager tmplayout = new java.awt.GridLayout(linenum, 2);
        JPanel tmppanel = new JPanel();
        tmppanel.setLayout(tmplayout);


        SortedSet<String> sortedset = new TreeSet<String>(nodeHashList.keySet());

        Iterator<String> it = sortedset.iterator();

        while (it.hasNext()) {
            Graph tmpgraph = nodeHashList.get(it.next());
            tmppanel.add(tmpgraph.getChartPanel());
        }

        return tmppanel;
    }

    public boolean isPrintSelected() {
        boolean leaftoprint = false;
        SortedSet<String> sortedset = new TreeSet<String>(nodeHashList.keySet());

        Iterator<String> it = sortedset.iterator();

        while (it.hasNext()) {
            Graph tmpgraph = nodeHashList.get(it.next());
            if (tmpgraph.printSelected) {
                leaftoprint = true;
                break;
            }
        }

        return leaftoprint;
    }

    public String getTitle() {
        return title;
    }

    public JPanel getPrintForm() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(title));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.PAGE_AXIS));
        return panel;
    }


    private GraphConfig graphconfig = null;
    private SortedTreeNode parentTreeNode = null;
    private kSar mysar = null;
    private String headerStr = null;
    private Map<String, Graph> nodeHashList = new HashMap<>();
    private int skipColumn = 0;
    private String title = null;
    
}
