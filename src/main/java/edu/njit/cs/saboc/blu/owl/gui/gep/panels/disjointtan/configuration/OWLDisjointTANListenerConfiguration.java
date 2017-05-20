package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan.DisjointTANListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners.OWLDisplayNATListener;

/**
 *
 * @author Chris O
 */
public class OWLDisjointTANListenerConfiguration extends DisjointTANListenerConfiguration {
    public OWLDisjointTANListenerConfiguration(OWLDisjointTANConfiguration config) {
        super(config);
    }
    
    @Override
    public EntitySelectionListener<Concept> getGroupConceptListListener() {
        OWLDisjointTANConfiguration config = (OWLDisjointTANConfiguration)super.getConfiguration();
        OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getDisjointTAN().getParentAbstractionNetwork();
        
        return new OWLDisplayNATListener(
                config.getUIConfiguration().getFrameManager(), 
                owlAbN.getDataManager());
    }
}
