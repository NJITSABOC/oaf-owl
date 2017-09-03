package edu.njit.cs.saboc.blu.owl.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff.DiffPAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff.DiffPAreaTaxonomySubviewLayout;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff.DiffTaxonomySubsetOptions;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.DiffAbNGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DiffTaxonomySubsetSelectionButton.DiffTaxonomySubsetCreationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy.DiffTaxonomyPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration.OWLDiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration.OWLDiffPAreaTaxonomyConfigurationFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class OWLDiffTaxonomyGraphFrame extends GenericInternalGraphFrame<DiffPAreaTaxonomy> {

    private final OWLAbNFrameManager displayListener;
    
    private final DiffTaxonomySubsetCreationAction subsetCreationAction = (options) -> {
        this.setDiffTaxonomySubsetView(options);
    };
    
    public OWLDiffTaxonomyGraphFrame(
            JFrame parentFrame,
            OWLAbNFrameManager displayListener) {

        super(parentFrame, "OWL Diff Partial-area Taxonomy");
        
        this.displayListener = displayListener;

        this.setTitle("OWL Diff Partial-area Taxonomy");
    }
    
    private void updateHierarchyInfoLabel(DiffPAreaTaxonomy data) {
        setHierarchyInfoText("*** OWL DIFF TAXONOMY UI IN DEVELOPMENT ***");
    }

    public void displayDiffPAreaTaxonomy(DiffPAreaTaxonomy diffPAreaTaxonomy) {
        
        Thread loadThread = new Thread(() -> {
            getAbNExplorationPanel().showLoading();
            
            OWLDiffPAreaTaxonomyConfigurationFactory configFactory = new OWLDiffPAreaTaxonomyConfigurationFactory();
            OWLDiffPAreaTaxonomyConfiguration config = configFactory.createConfiguration(diffPAreaTaxonomy, displayListener);

            DiffPAreaTaxonomyGraph newGraph = new DiffPAreaTaxonomyGraph(
                    getParentFrame(), 
                    diffPAreaTaxonomy, 
                    new SinglyRootedNodeLabelCreator(), 
                    config);
                        
            SwingUtilities.invokeLater(() -> {
                displayAbstractionNetwork(
                        newGraph, 
                        new DiffTaxonomyPainter(), 
                        config, 
                        new DiffAbNGUIInitializer(null, subsetCreationAction));

                updateHierarchyInfoLabel(diffPAreaTaxonomy);
            });
        });
        
        loadThread.start();
    }
    
    public void setDiffTaxonomySubsetView(DiffTaxonomySubsetOptions subsetOptions) {
        
        DiffPAreaTaxonomyGraph graph = (DiffPAreaTaxonomyGraph)super.getGraph().get();
        
        OWLDiffPAreaTaxonomyConfigurationFactory configFactory = new OWLDiffPAreaTaxonomyConfigurationFactory();
        OWLDiffPAreaTaxonomyConfiguration config = configFactory.createConfiguration(graph.getPAreaTaxonomy(), displayListener);
        
        SwingUtilities.invokeLater( () -> {
            graph.setAbstractionNetworkLayout(
                    new DiffPAreaTaxonomySubviewLayout(
                            graph, 
                            graph.getPAreaTaxonomy(),
                            config,
                            subsetOptions));
            
            this.getAbNExplorationPanel().getDisplayPanel().reset();
        });
    }
}