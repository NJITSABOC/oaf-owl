package edu.njit.cs.saboc.blu.owl.gui.listeners;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class DisplayPAreaTaxonomyAction implements DisplayAbNAction<PAreaTaxonomy> {
    
    private final AbNDisplayManager displayListener;
    
    public DisplayPAreaTaxonomyAction(AbNDisplayManager displayListener) {
        this.displayListener = displayListener;
    }

    @Override
    public void displayAbstractionNetwork(PAreaTaxonomy abstractionNetwork) {
        displayListener.displayPAreaTaxonomy(abstractionNetwork);
    }
}
