package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.AncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.DescendantTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbstractionNetwork extends TargetAbstractionNetwork<TargetGroup> implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLRangeAbstractionNetwork(TargetAbstractionNetwork source, OAFOntologyDataManager dataManager) {
        super(source);
        
        this.dataManager = dataManager;
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeighteAggregated) {
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(this, this, smallestNode, isWeighteAggregated);
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }

    @Override
    public TargetAbstractionNetwork createDescendantTargetAbN(TargetGroup root) {
        return new OWLDescendantRangeAbN((DescendantTargetAbN)super.createDescendantTargetAbN(root), dataManager);
    }

    @Override
    public TargetAbstractionNetwork createAncestorTargetAbN(TargetGroup root) {
        return new OWLAncestorRangeAbN((AncestorTargetAbN)super.createAncestorTargetAbN(root), dataManager);
    }
}
