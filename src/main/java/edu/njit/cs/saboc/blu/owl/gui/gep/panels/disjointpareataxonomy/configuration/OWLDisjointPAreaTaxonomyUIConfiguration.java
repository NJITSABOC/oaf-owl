package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy.DisjointPAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.OWLDisjointPAreaOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.OWLDisjointPAreaTaxonomyOptionsPanel;

/**
 *
 * @author Chris O
 */
public class OWLDisjointPAreaTaxonomyUIConfiguration extends DisjointPAreaTaxonomyUIConfiguration {
    
    private final OWLAbNFrameManager frameManager;
    
    public OWLDisjointPAreaTaxonomyUIConfiguration(
            OWLDisjointPAreaTaxonomyConfiguration config, 
            OWLDisjointPAreaTaxonomyListenerConfiguration listenerConfig,
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager) {
        
        super(config, displayListener, listenerConfig);
        
        this.frameManager = frameManager;
    }
    
    public OWLDisjointPAreaTaxonomyUIConfiguration(
            OWLDisjointPAreaTaxonomyConfiguration config, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager) {
        
        this(config, 
                new OWLDisjointPAreaTaxonomyListenerConfiguration(config), 
                displayListener, 
                frameManager);
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
    
    @Override
    public OWLDisjointPAreaTaxonomyConfiguration getConfiguration() {
        return (OWLDisjointPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    @Override
    public NodeOptionsPanel getNodeOptionsPanel() {
        return new OWLDisjointPAreaOptionsPanel(getConfiguration());
    }
    
    @Override
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new OWLDisjointPAreaTaxonomyOptionsPanel(getConfiguration());
    }

    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
}
