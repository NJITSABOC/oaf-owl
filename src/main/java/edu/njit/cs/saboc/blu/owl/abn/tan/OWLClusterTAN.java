package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OWLClusterTAN<T extends Cluster> extends ClusterTribalAbstractionNetwork<T> implements OWLAbstractionNetwork {
    
    public OWLClusterTAN(OWLBandTAN bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            ClusterTANDerivation derivation) {
        
        super(bandTan, clusterHierarchy, sourceHierarchy, derivation);
    }
    
    public OWLClusterTAN(OWLBandTAN bandTan,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {
        
        super(bandTan, clusterHierarchy, sourceHierarchy);
    }
    
    public OWLClusterTAN(ClusterTribalAbstractionNetwork<T> tan) {
        this((OWLBandTAN)tan.getBandTAN(), 
                tan.getClusterHierarchy(), 
                tan.getSourceHierarchy(), 
                tan.getDerivation());
    }

    @Override
    public OWLTANFactory getSourceFactory() {
        return (OWLTANFactory)super.getSourceFactory();
    }

    @Override
    public OWLBandTAN getBandTAN() {
        return (OWLBandTAN)super.getBandTAN();
    }

    @Override
    public OAFOntologyDataManager getDataManager() {
        return getBandTAN().getDataManager();
    }

    @Override
    public ClusterTribalAbstractionNetwork getAggregated(int smallestNode) {
        
        ClusterTribalAbstractionNetwork tan = super.getAggregated(smallestNode);
        
        if(tan.isAggregated()) {
            return new OWLAggregateClusterTAN(
                    this.getDataManager(), 
                    (AggregateClusterTribalAbstractionNetwork)tan);
        } else {
            return this;
        }
    }
}
