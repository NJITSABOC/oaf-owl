
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANListenerConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners.OWLDisplayNATListener;

/**
 *
 * @author Chris O
 */
public class OWLTANListenerConfiguration extends TANListenerConfiguration {

    public OWLTANListenerConfiguration(OWLTANConfiguration config) {
        super(config);
    }
    
    @Override
    public EntitySelectionListener<Concept> getGroupConceptListListener() {
        OWLTANConfiguration config = (OWLTANConfiguration)super.getConfiguration();
        OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getAbstractionNetwork();
        
        return new OWLDisplayNATListener(
                config.getUIConfiguration().getFrameManager(), 
                owlAbN.getDataManager());
    }
}
