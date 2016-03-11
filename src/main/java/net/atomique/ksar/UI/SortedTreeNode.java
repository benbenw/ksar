

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
    
    @Override
    public void insert(final MutableTreeNode newChild, final int childIndex) {
        super.insert(newChild, childIndex);
        Collections.sort(this.children);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.toString().compareToIgnoreCase(o.toString());
    }

}
