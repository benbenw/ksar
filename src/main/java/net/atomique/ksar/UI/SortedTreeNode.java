

package net.atomique.ksar.UI;

import java.util.Collections;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author Max
 */
public class SortedTreeNode extends DefaultMutableTreeNode implements Comparable {

    public static final long serialVersionUID = 15071L;


    public SortedTreeNode(String name) {
        super(name);
    }

    public SortedTreeNode(TreeNodeInfo tmp) {
        super(tmp);
    }

    public SortedTreeNode(ParentNodeInfo tmp) {
        super(tmp);
    }
    
    public void countGraph(SortedTreeNode node) {
        int num = node.getChildCount();

        if (num > 0) {
            for (int i = 0; i < num; i++) {
                SortedTreeNode l = (SortedTreeNode) node.getChildAt(i);
                countGraph(l);
            }
        } else {
            Object obj1 = node.getUserObject();
            if (obj1 instanceof TreeNodeInfo) {
                this.leafNum++;
            }
        }
        
    }
    
    public int leafCount() {
        this.leafNum = 0;
        countGraph(this);
        return this.leafNum;
    }
    
    @Override
    public void insert(final MutableTreeNode newChild, final int childIndex) {
        super.insert(newChild, childIndex);
        Collections.sort(this.children);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.toString().compareToIgnoreCase(o.toString());
    }

    private int leafNum = 0;
}
