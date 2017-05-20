package edu.njit.cs.saboc.blu.owl.gui.graphframe;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff.DiffPAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy.DiffTaxonomyPainter;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search.diff.DiffPartitionedAbNSearchButton;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration.OWLDiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration.OWLDiffPAreaTaxonomyConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration.OWLDiffPAreaTaxonomyTextConfiguration;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class OWLDiffTaxonomyGraphFrame extends GenericInternalGraphFrame<DiffPAreaTaxonomy> {

    private final OWLAbNFrameManager displayListener;
    
    //private final DiffPartitionedAbNSearchButton searchButton;
    
    public OWLDiffTaxonomyGraphFrame(
            JFrame parentFrame,
            OWLAbNFrameManager displayListener) {

        super(parentFrame, "OWL Diff Partial-area Taxonomy");
        
        this.displayListener = displayListener;

        this.setTitle("OWL Diff Partial-area Taxonomy");
        
        //this.searchButton = new DiffPartitionedAbNSearchButton(parentFrame, new OWLDiffPAreaTaxonomyTextConfiguration(null));
        
        //this.addToggleableButtonToMenu(searchButton);
    }
    
    private void updateHierarchyInfoLabel(DiffPAreaTaxonomy data) {
        setHierarchyInfoText("*** OWL DIFF TAXONOMY UI IN DEVELOPMENT ***");
    }

    public final void displayDiffPAreaTaxonomy(DiffPAreaTaxonomy diffPAreaTaxonomy) {
        
        Thread loadThread = new Thread(() -> {
            getAbNExplorationPanel().showLoading();
            
            OWLDiffPAreaTaxonomyConfigurationFactory configFactory = new OWLDiffPAreaTaxonomyConfigurationFactory();
            OWLDiffPAreaTaxonomyConfiguration config = configFactory.createConfiguration(diffPAreaTaxonomy, displayListener);
            
            //searchButton.initialize(config);

            AbstractionNetworkGraph newGraph = new DiffPAreaTaxonomyGraph(
                    getParentFrame(), 
                    diffPAreaTaxonomy, 
                    new SinglyRootedNodeLabelCreator(), 
                    config);
            
            SwingUtilities.invokeLater(() -> {
                displayAbstractionNetwork(newGraph, new DiffTaxonomyPainter(), config);

                updateHierarchyInfoLabel(diffPAreaTaxonomy);
            });
        });
        
        loadThread.start();
    }
}