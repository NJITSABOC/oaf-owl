package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan.DisjointTANConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLDisjointTANConfiguration extends DisjointTANConfiguration {
    
    public OWLDisjointTANConfiguration(DisjointAbstractionNetwork disjointAbN) {
        super(disjointAbN);
    }
    
    public void setUIConfiguration(OWLDisjointTANUIConfiguration uiConfiguation) {
        super.setUIConfiguration(uiConfiguation);
    }
    
    public void setTextConfiguration(OWLDisjointTANTextConfiguration uiConfiguation) {
        super.setTextConfiguration(uiConfiguation);
    }
    
    public OWLDisjointTANUIConfiguration getUIConfiguration() {
        return (OWLDisjointTANUIConfiguration)super.getUIConfiguration();
    }
    
    public OWLDisjointTANTextConfiguration getTextConfiguration() {
        return (OWLDisjointTANTextConfiguration)super.getTextConfiguration();
    }
}
