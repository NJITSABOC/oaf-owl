package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateAncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateDescendantTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.ExpandedTargetAbN;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris Ochs
 */
public class OWLAggregateAncestorRangeAbN extends AggregateAncestorTargetAbN implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLAggregateAncestorRangeAbN(
            AggregateAncestorTargetAbN source, 
            OAFOntologyDataManager dataManager) {
        
        super(source);
        
        this.dataManager = dataManager;
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeighteAggregated) {
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(
                this.getNonAggregateSourceAbN(),
                this, 
                smallestNode,
                isWeighteAggregated);
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    @Override
    public AggregateDescendantTargetAbN createDescendantTargetAbN(AggregateTargetGroup root) {
        return new OWLAggregateDescendantRangeAbN(
                (AggregateDescendantTargetAbN)super.createDescendantTargetAbN(root), dataManager);
    }

    @Override
    public AggregateAncestorTargetAbN createAncestorTargetAbN(AggregateTargetGroup root) {
        return new OWLAggregateAncestorRangeAbN(
                (AggregateAncestorTargetAbN)super.createAncestorTargetAbN(root), dataManager);
    }
    
    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup targetGroup) {
        return new OWLExpandedRangeAbN((ExpandedTargetAbN)super.expandAggregateNode(targetGroup), dataManager);
    }
}
