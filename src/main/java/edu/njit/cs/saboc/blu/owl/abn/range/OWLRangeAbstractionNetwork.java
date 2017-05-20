package edu.njit.cs.saboc.blu.owl.abn.range;

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
    public TargetAbstractionNetwork getAggregated(int smallestNode) {
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(this, this, smallestNode);
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
}
