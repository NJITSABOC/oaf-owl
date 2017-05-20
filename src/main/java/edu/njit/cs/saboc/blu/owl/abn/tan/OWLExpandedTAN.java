package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.ExpandedClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLExpandedTAN extends ExpandedClusterTribalAbstractionNetwork implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLExpandedTAN(OAFOntologyDataManager dataManager, ExpandedClusterTribalAbstractionNetwork tan) {
        super(tan);
        
        this.dataManager = dataManager;
    }

    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    @Override
    public OWLBandTAN getBandTAN() {
        return (OWLBandTAN)super.getBandTAN();
    }

    @Override
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode) {
        
        ClusterTribalAbstractionNetwork tan = super.getAggregated(smallestNode);
        
        if(tan.isAggregated()) {
            return new OWLAggregateClusterTAN(this.getDataManager(), (AggregateClusterTribalAbstractionNetwork)tan);
        } else {
            return this;
        }
    }
}
