package edu.njit.cs.saboc.blu.owl.abn.range;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.targetbased.RelationshipTriple;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFromPArea;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbstractionNetworkFactory extends TargetAbstractionNetworkFactory {

    private final OAFOntologyDataManager dataManager;
    
    private final Map<Concept, Set<RelationshipTriple>> conceptTriples = new HashMap<>();
    
    public OWLRangeAbstractionNetworkFactory(
            OAFOntologyDataManager dataManager, 
            Hierarchy<OWLConcept> sourceHierarchy,
            OWLInheritableProperty propertyType,
            Hierarchy<OWLConcept> targetHierarchy) {
        
        super(dataManager.getOntology());
        
        this.dataManager = dataManager;
        
        // Precompute triples for OWL ontologies...
        
        Set<RelationshipTriple> triples = dataManager.getSimpleRestrictionTriplesFromHierarchy(
                sourceHierarchy.getRoot(), 
                Collections.singleton(propertyType));
        
        triples.forEach((triple) -> {
            OWLConcept sourceConcept = (OWLConcept) triple.getSource();
            OWLConcept targetConcept = (OWLConcept) triple.getTarget();

            if (targetHierarchy.contains(targetConcept)) {
                if (!conceptTriples.containsKey(sourceConcept)) {
                    conceptTriples.put(sourceConcept, new HashSet<>());
                }
                
                conceptTriples.get(sourceConcept).add(triple);
            }
        });
    }

    @Override
    public TargetAbstractionNetwork createTargetAbstractionNetwork(
            Hierarchy<TargetGroup> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy, 
            TargetAbNDerivation derivation) {
        
        TargetAbstractionNetwork base = super.createTargetAbstractionNetwork(groupHierarchy, sourceHierarchy, derivation);
        
        return new OWLRangeAbstractionNetwork(base, dataManager);
    }

    @Override
    public Set<RelationshipTriple> getRelationshipsToTargetHierarchyFor(
            Concept concept, 
            Set<InheritableProperty> relationshipTypes, 
            Hierarchy<Concept> targetHierarchy) {

        return conceptTriples.getOrDefault(concept, Collections.emptySet());
    }

    @Override
    public TargetAbstractionNetworkFromPArea createTargetAbNFromPArea(
            TargetAbstractionNetwork targetAbN, 
            PAreaTaxonomy sourceTaxonomy,
            PArea sourcePArea) {
        
        return new OWLRangeAbstractionNetworkFromPArea(
                super.createTargetAbNFromPArea(
                        targetAbN, 
                        sourceTaxonomy, 
                        sourcePArea));
    }
}
