package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFromPArea;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbstractionNetworkFromPArea extends TargetAbstractionNetworkFromPArea implements OWLAbstractionNetwork {
    
    public OWLRangeAbstractionNetworkFromPArea(TargetAbstractionNetworkFromPArea rangeAbN) {
        super(rangeAbN, rangeAbN.getSourceTaxonomy(), rangeAbN.getSourcePArea());
    }

    @Override
    public OAFOntologyDataManager getDataManager() {
        OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)super.getSourceTaxonomy();
        
        return owlAbN.getDataManager();
    }

    @Override
    public TargetAbstractionNetwork getAggregated(int smallestNode, boolean isWeighteAggregated) {
        
        return OWLAggregateRangeAbstractionNetwork.createAggregatedOWLRangeAbN(
                this, 
                this, 
                smallestNode,
                isWeighteAggregated);
    }
}
