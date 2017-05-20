package edu.njit.cs.saboc.blu.owl.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.BandTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLBandTAN extends BandTribalAbstractionNetwork implements OWLAbstractionNetwork {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLBandTAN(
            OWLTANFactory sourceFactory,
            OAFOntologyDataManager dataManager,
            Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            ClusterTANDerivation derivation) {
        
        super(sourceFactory, bandHierarchy, sourceHierarchy, derivation);
        
        this.dataManager = dataManager;
    }
    
    
     public OWLBandTAN(
            OWLTANFactory sourceFactory,
            OAFOntologyDataManager dataManager,
            Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        super(sourceFactory, bandHierarchy, sourceHierarchy);
        
        this.dataManager = dataManager;
    }

    @Override
    public OWLTANFactory getSourceFactory() {
        return (OWLTANFactory)super.getSourceFactory();
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
}
