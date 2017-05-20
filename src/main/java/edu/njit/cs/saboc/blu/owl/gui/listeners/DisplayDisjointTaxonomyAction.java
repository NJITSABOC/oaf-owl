package edu.njit.cs.saboc.blu.owl.gui.listeners;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class DisplayDisjointTaxonomyAction implements DisplayAbNAction<DisjointAbstractionNetwork> {
    
    private final AbNDisplayManager listener;
    
    public DisplayDisjointTaxonomyAction(AbNDisplayManager listener) {
        this.listener = listener;
    }

    @Override
    public void displayAbstractionNetwork(DisjointAbstractionNetwork abstractionNetwork) {
        DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy = 
                (DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea>)abstractionNetwork;
        
        listener.displayDisjointPAreaTaxonomy(disjointTaxonomy);
    }
}
