package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTANFromSinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class OWLTANFromSinglyRootedNode<T extends SinglyRootedNode,
        V extends AbstractionNetwork<T>> 

        extends ClusterTANFromSinglyRootedNode implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLTANFromSinglyRootedNode(ClusterTANFromSinglyRootedNode source, OAFOntologyDataManager dataManager) {
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
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode, boolean isWeighteAggregated) {
        
        ClusterTribalAbstractionNetwork tan = super.getAggregated(smallestNode, isWeighteAggregated);
        
        if(tan.isAggregated()) {
            return new OWLAggregateClusterTAN(this.getDataManager(), (AggregateClusterTribalAbstractionNetwork)tan);
        } else {
            return this;
        }
    }
}
