package net.atomique.ksar.UI;

import net.atomique.ksar.Graph.List;

/**
 *
 * @author Max
 */
public class ParentNodeInfo {

    public ParentNodeInfo(String t, List list) {
        nodeTitle = t;
        nodeObject = list;
    }

    public List getNode_object() {
        return nodeObject;
    }

    public String getNode_title() {
        return nodeTitle;
    }

    public String toString() {
        return nodeTitle;
    }
    
    private String nodeTitle = null;
    private List nodeObject = null;
    
}
