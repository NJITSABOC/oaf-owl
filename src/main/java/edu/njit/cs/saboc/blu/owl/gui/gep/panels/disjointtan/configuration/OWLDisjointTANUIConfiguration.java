package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan.DisjointTANUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.OWLDisjointClusterOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.OWLDisjointClusterTANOptionsPanel;

/**
 *
 * @author Chris O
 */
public class OWLDisjointTANUIConfiguration extends DisjointTANUIConfiguration {
    
    private final OWLAbNFrameManager frameManager;

    public OWLDisjointTANUIConfiguration(
            OWLDisjointTANConfiguration config,
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager) {
        
        super(config, displayListener, new OWLDisjointTANListenerConfiguration(config));
        
        this.frameManager = frameManager;
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
    
    @Override
    public OWLDisjointTANConfiguration getConfiguration() {
        return (OWLDisjointTANConfiguration)super.getConfiguration();
    }
    
    @Override
    public NodeOptionsPanel<DisjointNode<Cluster>> getNodeOptionsPanel() {
        return new OWLDisjointClusterOptionsPanel(getConfiguration(), getConfiguration().getAbstractionNetwork().isAggregated());
    }

    @Override
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new OWLDisjointClusterTANOptionsPanel(getConfiguration());
    }
    
    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
}
