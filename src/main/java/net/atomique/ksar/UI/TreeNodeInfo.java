package net.atomique.ksar.UI;

import net.atomique.ksar.Graph.Graph;

/**
 *
 * @author Max
 */
public class TreeNodeInfo {

    public TreeNodeInfo(String t, Graph graph) {
        nodeTitle = t;
        nodeObject = graph;
    }

    public Graph getNode_object() {
        return nodeObject;
    }

    public String getNode_title() {
        return nodeTitle;
    }

    public String toString() {
        return nodeTitle;
    }
    
    private String nodeTitle = null;
    private Graph nodeObject = null;
    
}
