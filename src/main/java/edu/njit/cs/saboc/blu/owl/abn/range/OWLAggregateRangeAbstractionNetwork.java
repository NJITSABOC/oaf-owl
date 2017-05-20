package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
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
            int minBound) {
        
        TargetAbstractionNetwork baseAggregate = AggregateTargetAbN.createAggregated(sourceAbN, minBound);
        
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
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(this.getNonAggregateSourceAbN(), this, smallestNode);
    }
}
