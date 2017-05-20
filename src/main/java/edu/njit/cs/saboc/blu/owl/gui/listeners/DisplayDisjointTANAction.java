package edu.njit.cs.saboc.blu.owl.gui.listeners;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class DisplayDisjointTANAction implements DisplayAbNAction<DisjointAbstractionNetwork> {
    
    private final AbNDisplayManager displayListener;
    
    public DisplayDisjointTANAction(AbNDisplayManager displayListener) {
        this.displayListener = displayListener;
    }

    @Override
    public void displayAbstractionNetwork(DisjointAbstractionNetwork abstractionNetwork) {
        DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointAbN = 
                (DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)abstractionNetwork;
        
        displayListener.displayDisjointTribalAbstractionNetwork(disjointAbN);
    }
}
