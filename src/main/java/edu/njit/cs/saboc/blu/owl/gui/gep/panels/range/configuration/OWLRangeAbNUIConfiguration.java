
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration;

import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.OWLRangeAbNOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.OWLTargetGroupOptionsPanel;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbNUIConfiguration extends TargetAbNUIConfiguration {
    
    private final OWLAbNFrameManager frameManager;
    
    public OWLRangeAbNUIConfiguration(
            OWLRangeAbNConfiguration config, 
            OWLRangeAbNListenerConfiguration listenerConfig,
            AbNDisplayManager displayListener, 
            OWLAbNFrameManager frameManager) {

        super(config, displayListener, listenerConfig);
        
        this.frameManager = frameManager;
    }
    
    public OWLRangeAbNUIConfiguration(
            OWLRangeAbNConfiguration config, 
            AbNDisplayManager displayListener, 
            OWLAbNFrameManager frameManager) {
        
        this(config, 
                new OWLRangeAbNListenerConfiguration(config), 
                displayListener, 
                frameManager);
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
    
    @Override
    public OWLRangeAbNConfiguration getConfiguration() {
        return (OWLRangeAbNConfiguration)super.getConfiguration();
    }
    
    @Override
    public NodeOptionsPanel getNodeOptionsPanel() {
        return new OWLTargetGroupOptionsPanel(this.getConfiguration());
    }

    @Override
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new OWLRangeAbNOptionsPanel(this.getConfiguration());
    }
    
    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
}
