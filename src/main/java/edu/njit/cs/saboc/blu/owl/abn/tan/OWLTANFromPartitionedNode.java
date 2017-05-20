package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTANFromPartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLTANFromPartitionedNode<
        T extends PartitionedNode, 
        V extends PartitionedAbstractionNetwork> extends ClusterTANFromPartitionedNode<T, V> implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLTANFromPartitionedNode(ClusterTANFromPartitionedNode<T, V> source, OAFOntologyDataManager dataManager) {
        super(source);
        
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
