package edu.njit.cs.saboc.blu.owl.gui.listeners;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class DisplayTANAction implements DisplayAbNAction<ClusterTribalAbstractionNetwork> {
    
    private final AbNDisplayManager displayListener;
    
    public DisplayTANAction(AbNDisplayManager displayListener) {
        this.displayListener = displayListener;
    }

    @Override
    public void displayAbstractionNetwork(ClusterTribalAbstractionNetwork abstractionNetwork) {
        displayListener.displayTribalAbstractionNetwork(abstractionNetwork);
    }
}
