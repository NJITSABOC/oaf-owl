
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.OWLAreaOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.OWLPAreaOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.OWLPAreaTaxonomyOptionsPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.OWLPropertyTableModel;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyUIConfiguration extends PAreaTaxonomyUIConfiguration {
    
    private final OWLAbNFrameManager frameManager;
    
    public OWLPAreaTaxonomyUIConfiguration(
            OWLPAreaTaxonomyConfiguration config, 
            OWLPAreaTaxonomyListenerConfiguration listenerConfig,
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager,
            boolean showingAreaTaxonomy) {

        super(config, displayListener, listenerConfig, showingAreaTaxonomy);
        
        this.frameManager = frameManager;
    }
    
    public OWLPAreaTaxonomyUIConfiguration(
            OWLPAreaTaxonomyConfiguration config, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager,
            boolean showingAreaTaxonomy) {
        
        this(config, 
                new OWLPAreaTaxonomyListenerConfiguration(config), 
                displayListener, 
                frameManager, 
                showingAreaTaxonomy);
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
    
    @Override
    public OWLPAreaTaxonomyConfiguration getConfiguration() {
        return (OWLPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    @Override
    public OAFAbstractTableModel<InheritableProperty> getPropertyTableModel(boolean forArea) {
        return new OWLPropertyTableModel(forArea);
    }

    @Override
    public NodeOptionsPanel getPartitionedNodeOptionsPanel() {
        OWLPAreaTaxonomyConfiguration owlConfig = getConfiguration();

        return new OWLAreaOptionsPanel(owlConfig, owlConfig.getPAreaTaxonomy().isAggregated());
    }

    @Override
    public NodeOptionsPanel getNodeOptionsPanel() {
        OWLPAreaTaxonomyConfiguration owlConfig = getConfiguration();
        
        return new OWLPAreaOptionsPanel(owlConfig, owlConfig.getPAreaTaxonomy().isAggregated());
    }

    @Override
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new OWLPAreaTaxonomyOptionsPanel(getConfiguration());
    }

    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
    
}
