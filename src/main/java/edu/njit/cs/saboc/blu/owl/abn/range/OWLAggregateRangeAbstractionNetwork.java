package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateAncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateDescendantTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.ExpandedTargetAbN;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLAggregateRangeAbstractionNetwork extends AggregateTargetAbN implements OWLAbstractionNetwork {
    
     public static final TargetAbstractionNetwork createAggregatedOWLRangeAbN(
            TargetAbstractionNetwork sourceAbN, 
            OWLAbstractionNetwork owlAbN, 
            int minBound,
            boolean isWeighteAggregated) {
        
        TargetAbstractionNetwork baseAggregate = AggregateTargetAbN.createAggregated(sourceAbN, minBound, isWeighteAggregated);
        
        if(baseAggregate.isAggregated()) {
            return new OWLAggregateRangeAbstractionNetwork((AggregateTargetAbN)baseAggregate, owlAbN.getDataManager());
        } else {
            return new OWLRangeAbstractionNetwork(baseAggregate, owlAbN.getDataManager());
        }
    }
        
    private final OAFOntologyDataManager dataManager;
    
    public OWLAggregateRangeAbstractionNetwork(AggregateTargetAbN aggregateRangeAbN, OAFOntologyDataManager dataManager) {
        super(aggregateRangeAbN);
        
        this.dataManager = dataManager;
    }

    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeighteAggregated) {
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(this.getNonAggregateSourceAbN(), this, smallestNode, isWeighteAggregated);
    }

    @Override
    public AggregateDescendantTargetAbN createDescendantTargetAbN(AggregateTargetGroup root) {
        return new OWLAggregateDescendantRangeAbN(
                (AggregateDescendantTargetAbN) super.createDescendantTargetAbN(root), dataManager);
    }

    @Override
    public AggregateAncestorTargetAbN createAncestorTargetAbN(AggregateTargetGroup root) {
        return new OWLAggregateAncestorRangeAbN(
                (AggregateAncestorTargetAbN) super.createAncestorTargetAbN(root), dataManager);
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup targetGroup) {
        return new OWLExpandedRangeAbN((ExpandedTargetAbN)super.expandAggregateTargetGroup(targetGroup), dataManager);
    }
}
