package edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbNConfigurationFactory {
    
    public OWLRangeAbNConfiguration createConfiguration(
            TargetAbstractionNetwork targetAbN, 
            AbNDisplayManager displayListener, 
            OWLAbNFrameManager frameManager) {
        
        OWLRangeAbNConfiguration targetAbNConfiguration = new OWLRangeAbNConfiguration(targetAbN);
        targetAbNConfiguration.setUIConfiguration(new OWLRangeAbNUIConfiguration(targetAbNConfiguration, displayListener, frameManager));
        targetAbNConfiguration.setTextConfiguration(new OWLRangeAbNTextConfiguration(targetAbN));
        
        return targetAbNConfiguration;
    }
}
