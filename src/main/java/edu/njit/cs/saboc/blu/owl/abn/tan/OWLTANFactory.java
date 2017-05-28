package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.BandTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTANFromPartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTANFromSinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLTANFactory extends TANFactory {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLTANFactory(OAFOntologyDataManager dataManager) {
        super(dataManager.getOntology());
        
        this.dataManager = dataManager;
    }

    @Override
    public <T extends Cluster> OWLClusterTAN createClusterTAN(
            BandTribalAbstractionNetwork bandTAN, 
            Hierarchy<T> clusterHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        return new OWLClusterTAN((OWLBandTAN)bandTAN, clusterHierarchy, sourceHierarchy);
    }

    @Override
    public OWLBandTAN createBandTAN(
            Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        return new OWLBandTAN(this, dataManager, bandHierarchy, sourceHierarchy);
    }

    @Override
    public <T extends Cluster, V extends PartitionedNode, U extends PartitionedAbstractionNetwork> 
        ClusterTribalAbstractionNetwork createTANFromPartitionedNode(ClusterTribalAbstractionNetwork<T> theTAN, V node, U sourceAbN) {
            
        return new OWLTANFromPartitionedNode((ClusterTANFromPartitionedNode)super.createTANFromPartitionedNode(theTAN, node, sourceAbN), dataManager);
    }

    @Override
    public <T extends Cluster, V extends SinglyRootedNode, U extends AbstractionNetwork<V>> 
        ClusterTribalAbstractionNetwork createTANFromSinglyRootedNode(ClusterTribalAbstractionNetwork<T> theTAN, V node, U sourceAbN) {
            
        return new OWLTANFromSinglyRootedNode((ClusterTANFromSinglyRootedNode)super.createTANFromSinglyRootedNode(theTAN, node, sourceAbN), dataManager);
    }
    
}
