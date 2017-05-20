
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLTANConfiguration extends TANConfiguration {
    
    public OWLTANConfiguration(ClusterTribalAbstractionNetwork tan) {
        super(tan);
    }
    
    public void setUIConfiguration(OWLTANUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(OWLTANTextConfiguration config) {
        super.setTextConfiguration(config);
    }

    public OWLTANUIConfiguration getUIConfiguration() {
        return (OWLTANUIConfiguration)super.getUIConfiguration();
    }
    
    public OWLTANTextConfiguration getTextConfiguration() {
        return (OWLTANTextConfiguration)super.getTextConfiguration();
    }
}
