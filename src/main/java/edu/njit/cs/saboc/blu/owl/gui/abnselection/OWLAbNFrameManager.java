package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointCluster;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.FrameCreationAction;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abnhistory.OWLDerivationParser;
import edu.njit.cs.saboc.blu.owl.gui.graphframe.OWLDiffTaxonomyGraphFrame;
import edu.njit.cs.saboc.blu.owl.gui.graphframe.initializers.OWLMultiAbNGraphFrameInitializers;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.nat.OWLNATLayout;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserFrame;
import java.util.Optional;
import javax.swing.JFrame;

/**
 *
 * @author Chris
 */
public class OWLAbNFrameManager extends AbNDisplayManager {
    private final JFrame mainFrame;

    public OWLAbNFrameManager(JFrame mainFrame, FrameCreationAction fca) {
        super(fca);

        this.mainFrame = mainFrame;
    }
    
    @Override
    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) taxonomy;
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this),
                owlParser);

        graphFrame.displayPAreaTaxonomy(taxonomy);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }
    
    @Override
    public void displayTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) tan;
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);

        graphFrame.displayTAN(tan);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }

    @Override
    public void displayDisjointPAreaTaxonomy(
        DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) disjointTaxonomy.getParentAbstractionNetwork();
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);

        graphFrame.displayDisjointPAreaTaxonomy(
                (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>)(DisjointAbstractionNetwork<?, ?, ?>)disjointTaxonomy);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }
    
    @Override
    public void displayDisjointTribalAbstractionNetwork(
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) disjointTAN.getParentAbstractionNetwork();
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);
        
        graphFrame.displayDisjointTAN(
                (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)(DisjointAbstractionNetwork<?, ?, ?>)
                        disjointTAN);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }

    
    @Override
    public void displayDiffPAreaTaxonomy(DiffPAreaTaxonomy diffTaxonomy) {
        OWLDiffTaxonomyGraphFrame igf = new OWLDiffTaxonomyGraphFrame(mainFrame, this);
        
        igf.displayDiffPAreaTaxonomy(diffTaxonomy);
        
        this.getFrameCreationAction().displayFrame(igf);
    }
    
    @Override
    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) targetAbN;
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);

        graphFrame.displayTargetAbstractionNetwork(targetAbN);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }

    @Override
    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) taxonomy;
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);

        graphFrame.displayAreaTaxonomy(taxonomy);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }

    @Override
    public void displayBandTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {

        OWLAbstractionNetwork owlan = (OWLAbstractionNetwork) tan;
        OWLDerivationParser owlParser = new OWLDerivationParser(owlan.getDataManager());
        MultiAbNGraphFrame graphFrame = new MultiAbNGraphFrame(
                mainFrame, 
                new OWLMultiAbNGraphFrameInitializers(owlan.getDataManager(), this), 
                owlParser);

        graphFrame.displayBandTAN(tan);

        this.getFrameCreationAction().displayFrame(graphFrame);
    }
    
    public void displayNATBrowser(OWLBrowserDataSource dataSource, Optional<OWLConcept> focusConcept) {

        NATBrowserFrame<OWLConcept> browserFrame = new NATBrowserFrame<>(
                mainFrame,
                dataSource,
                new OWLNATLayout(dataSource));

        if(focusConcept.isPresent()) {
            browserFrame.nagivateTo(focusConcept.get());
        } else {
            browserFrame.nagivateTo(dataSource.getOntology().getConceptHierarchy().getRoot());
        }

        super.getFrameCreationAction().displayFrame(browserFrame);
    }
}