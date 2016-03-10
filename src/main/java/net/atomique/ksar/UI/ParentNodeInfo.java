package net.atomique.ksar.UI;

import net.atomique.ksar.Graph.GraphList;

/**
 *
 * @author Max
 */
public class ParentNodeInfo {

    public ParentNodeInfo(String t, GraphList list) {
        nodeTitle = t;
        nodeObject = list;
    }

    public GraphList getNode_object() {
        return nodeObject;
    }

    public String getNode_title() {
        return nodeTitle;
    }

    public String toString() {
        return nodeTitle;
    }
    
    private String nodeTitle = null;
    private GraphList nodeObject = null;
    
}
