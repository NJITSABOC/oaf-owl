package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLTANConfigurationFactory {
    
    public OWLTANConfiguration createConfiguration(
            ClusterTribalAbstractionNetwork tan, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager,
            boolean showingBandTAN) {

        OWLTANConfiguration tanConfiguration = new OWLTANConfiguration(tan);
        
        tanConfiguration.setUIConfiguration(new OWLTANUIConfiguration(
                tanConfiguration, 
                displayListener, 
                frameManager, 
                showingBandTAN));
        
        tanConfiguration.setTextConfiguration(new OWLTANTextConfiguration(tan));

        return tanConfiguration;
    }
}
