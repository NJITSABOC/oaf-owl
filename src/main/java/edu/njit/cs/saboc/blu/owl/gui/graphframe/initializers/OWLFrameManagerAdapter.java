package edu.njit.cs.saboc.blu.owl.gui.graphframe.initializers;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.DisjointPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointCluster;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLFrameManagerAdapter extends OWLAbNFrameManager {

    private final MultiAbNGraphFrame frame;

    public OWLFrameManagerAdapter(MultiAbNGraphFrame frame) {
        super(null, null);

        this.frame = frame;
    }

    @Override
    public void displayBandTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {
        frame.displayBandTAN(tan);
    }

    @Override
    public void displayAreaTaxonomy(PAreaTaxonomy taxonomy) {
        frame.displayAreaTaxonomy(taxonomy);
    }

    @Override
    public void displayTargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        frame.displayTargetAbstractionNetwork(targetAbN);
    }

    @Override
    public void displayDisjointTribalAbstractionNetwork(
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {

        frame.displayDisjointTAN(
                (DisjointAbstractionNetwork<DisjointCluster, ClusterTribalAbstractionNetwork<Cluster>, Cluster>) (DisjointAbstractionNetwork<?, ?, ?>) disjointTAN);
    }

    @Override
    public void displayDisjointPAreaTaxonomy(DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        frame.displayDisjointPAreaTaxonomy(
                (DisjointAbstractionNetwork<DisjointPArea, PAreaTaxonomy<PArea>, PArea>) (DisjointAbstractionNetwork<?, ?, ?>) disjointTaxonomy);
    }

    @Override
    public void displayTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {
        frame.displayTAN(tan);
    }

    @Override
    public void displayPAreaTaxonomy(PAreaTaxonomy taxonomy) {
        frame.displayPAreaTaxonomy(taxonomy);
    }
}
