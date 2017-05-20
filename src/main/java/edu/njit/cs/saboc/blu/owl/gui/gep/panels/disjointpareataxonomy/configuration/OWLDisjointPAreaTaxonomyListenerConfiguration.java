package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy.DisjointPAreaTaxonomyListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners.OWLDisplayNATListener;

/**
 *
 * @author Chris O
 */
public class OWLDisjointPAreaTaxonomyListenerConfiguration extends DisjointPAreaTaxonomyListenerConfiguration {
    
    public OWLDisjointPAreaTaxonomyListenerConfiguration(OWLDisjointPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public EntitySelectionListener<Concept> getGroupConceptListListener() {
        OWLDisjointPAreaTaxonomyConfiguration config = (OWLDisjointPAreaTaxonomyConfiguration)super.getConfiguration();
        
        OWLTaxonomy parentAbN = (OWLTaxonomy)config.getDisjointPAreaTaxonomy().getParentAbstractionNetwork();
        
        return new OWLDisplayNATListener(
                config.getUIConfiguration().getFrameManager(), 
                parentAbN.getDataManager());
    }
}
