package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy.DisjointPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLDisjointPAreaTaxonomyConfiguration extends DisjointPAreaTaxonomyConfiguration {
    
    public OWLDisjointPAreaTaxonomyConfiguration(
            DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        
        super(disjointTaxonomy);
    }
    
    @Override
    public OWLDisjointPAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (OWLDisjointPAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }
    
    @Override
    public OWLDisjointPAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (OWLDisjointPAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }
}
