
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.OWLPropertyTableModel;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyUIConfiguration extends DiffPAreaTaxonomyUIConfiguration {
    

    public OWLDiffPAreaTaxonomyUIConfiguration(
            OWLDiffPAreaTaxonomyConfiguration config, 
            OWLDiffPAreaTaxonomyListenerConfiguration listenerConfig,
            AbNDisplayManager displayListener) {

        super(config, displayListener, listenerConfig, new OWLDiffPAreaChangeExplanationFactory(config));
    }
    
    public OWLDiffPAreaTaxonomyUIConfiguration(OWLDiffPAreaTaxonomyConfiguration config, AbNDisplayManager displayListener) {
        this(config, new OWLDiffPAreaTaxonomyListenerConfiguration(config), displayListener);
    }
    
    @Override
    public OWLDiffPAreaTaxonomyConfiguration getConfiguration() {
        return (OWLDiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public OAFAbstractTableModel<InheritableProperty> getPropertyTableModel(boolean forArea) {
        return new OWLPropertyTableModel(forArea);
    }

    @Override
    public NodeOptionsPanel getPartitionedNodeOptionsPanel() {
        return new NodeOptionsPanel();
    }

    @Override
    public NodeOptionsPanel getNodeOptionsPanel() {
        return new NodeOptionsPanel();
    }

    @Override
    public ConceptPainter getConceptHierarchyPainter() {
        return new ConceptPainter();
    }
}
