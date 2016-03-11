

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
        graphconfig =g ;
        title = stitle;
        skipColumn = i;
        ParentNodeInfo tmp = new ParentNodeInfo(title, this);
        parentTreeNode = new SortedTreeNode(tmp);
        mysar.add2tree(mysar.graphtree, parentTreeNode);
    }

    public int parseLine(Second now,String s) {
        String[] cols = s.split("\\s+");
        Graph tmp = null;
        if ( ! nodeHashList.containsKey(cols[skipColumn])) {
            tmp= new Graph(mysar, graphconfig,title + " " + cols[skipColumn], headerStr, skipColumn+1, null);
            nodeHashList.put(cols[skipColumn], tmp);
            TreeNodeInfo infotmp= new TreeNodeInfo( cols[skipColumn], tmp);
            SortedTreeNode nodetmp = new SortedTreeNode(infotmp);
            mysar.add2tree(parentTreeNode, nodetmp);
        } else {
            tmp = (Graph)nodeHashList.get(cols[skipColumn]);
        }

        return tmp.parse_line(now,s);
    }


    public JPanel run() {
        JPanel tmppanel = new JPanel();
        LayoutManager tmplayout = null;
        int graphnumber = nodeHashList.size();
        int linenum = (int) Math.floor(graphnumber / 2);
        if (graphnumber % 2 != 0) {
            linenum++;
        }
        tmplayout = new java.awt.GridLayout(linenum, 2);
        tmppanel.setLayout(tmplayout);


        SortedSet<String> sortedset = new TreeSet<String>(nodeHashList.keySet());

        Iterator<String> it = sortedset.iterator();

        while (it.hasNext()) {
            Graph tmpgraph = (Graph) nodeHashList.get(it.next());
            tmppanel.add(tmpgraph.get_ChartPanel());
        }

        return tmppanel;
    }

    public boolean isPrintSelected() {
        boolean leaftoprint = false;
        SortedSet<String> sortedset = new TreeSet<String>(nodeHashList.keySet());

        Iterator<String> it = sortedset.iterator();

        while (it.hasNext()) {
            Graph tmpgraph = (Graph) nodeHashList.get(it.next());
            if (tmpgraph.printSelected) {
                leaftoprint = true;
                break;
            }
        }
        if (leaftoprint) {
            return true;
        } else {
            return false;
        }
    }

    public String getTitle() {
        return title;
    }

    public JPanel getprintform() {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(title));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.PAGE_AXIS));
        return panel;
    }


    protected GraphConfig graphconfig = null;
    protected SortedTreeNode parentTreeNode = null;
    protected kSar mysar = null;
    protected String headerStr = null;
    protected Map<String, Graph> nodeHashList = new HashMap<String, Graph>();
    protected int skipColumn = 0;
    protected String title = null;
    
}
