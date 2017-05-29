package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration;

import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.OWLBandOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.OWLClusterOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.OWLTANOptionsPanel;

/**
 *
 * @author Chris O
 */
public class OWLTANUIConfiguration extends TANUIConfiguration {

    private final OWLAbNFrameManager frameManager;
    
    public OWLTANUIConfiguration(
            OWLTANConfiguration config, 
            OWLTANListenerConfiguration listenerConfig,
            AbNDisplayManager displayListener, 
            OWLAbNFrameManager frameManager,
            boolean showingBandTAN) {
        
        super(config, displayListener, listenerConfig, showingBandTAN);
        
        this.frameManager = frameManager;
    }
    
    public OWLTANUIConfiguration(OWLTANConfiguration config, 
            AbNDisplayManager displayListener, 
            OWLAbNFrameManager frameManager,
            boolean showingBandTAN) {
        
        this(config, 
                new OWLTANListenerConfiguration(config), 
                displayListener, 
                frameManager, 
                showingBandTAN);
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
    
    @Override
    public OWLTANConfiguration getConfiguration() {
        return (OWLTANConfiguration)super.getConfiguration();
    }

    @Override
    public NodeOptionsPanel getPartitionedNodeOptionsPanel() {
        return new OWLBandOptionsPanel(getConfiguration(), getConfiguration().getAbstractionNetwork().isAggregated());
    }

    @Override
    public NodeOptionsPanel getNodeOptionsPanel() {
        return new OWLClusterOptionsPanel(getConfiguration(), getConfiguration().getAbstractionNetwork().isAggregated());
    }

    @Override
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new OWLTANOptionsPanel(getConfiguration());
    }

    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
}
