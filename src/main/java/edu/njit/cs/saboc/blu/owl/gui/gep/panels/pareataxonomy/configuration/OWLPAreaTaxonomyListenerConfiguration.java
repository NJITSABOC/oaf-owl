
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyListenerConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners.OWLDisplayNATListener;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyListenerConfiguration extends PAreaTaxonomyListenerConfiguration {

    public OWLPAreaTaxonomyListenerConfiguration(OWLPAreaTaxonomyConfiguration config) {
        super(config);
    }

    @Override
    public EntitySelectionListener<Concept> getGroupConceptListListener() {
        OWLPAreaTaxonomyConfiguration config = (OWLPAreaTaxonomyConfiguration)super.getConfiguration();
        
        return new OWLDisplayNATListener(
                config.getUIConfiguration().getFrameManager(), 
                config.getTaxonomy().getDataManager());
    }
    
}
