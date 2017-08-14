package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateRootSubTAN;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.ExpandedClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLAggregateRootSubTAN extends AggregateRootSubTAN implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLAggregateRootSubTAN(
            OAFOntologyDataManager dataManager, 
            AggregateRootSubTAN tan) {
        
        super(tan);
        
        this.dataManager = dataManager;
    }

    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    @Override
    public OWLTANFactory getSourceFactory() {
        return (OWLTANFactory)super.getSourceFactory();
    }
    
    @Override
    public ClusterTribalAbstractionNetwork getAggregated(AggregatedProperty ap) {
        ClusterTribalAbstractionNetwork tan = AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(
                this.getNonAggregateSourceAbN(), 
                ap);
        
        if(tan.isAggregated()) {
            return new OWLAggregateClusterTAN(dataManager, (AggregateClusterTribalAbstractionNetwork)tan);
        } else {
            return new OWLClusterTAN(tan);
        }
    }

    @Override
    public OWLExpandedTAN expandAggregateNode(AggregateCluster cluster) {
        ExpandedClusterTribalAbstractionNetwork clusterTAN = (ExpandedClusterTribalAbstractionNetwork)super.expandAggregateNode(cluster);

        return new OWLExpandedTAN(dataManager, clusterTAN);
    }

    @Override
    public OWLAggregateRootSubTAN createRootSubTAN(AggregateCluster root) {
        return new OWLAggregateRootSubTAN(dataManager, super.createRootSubTAN(root));
    }

    @Override
    public OWLAggregateAncestorSubTAN createAncestorTAN(AggregateCluster source) {
        return new OWLAggregateAncestorSubTAN(dataManager, super.createAncestorTAN(source));
    }
}
