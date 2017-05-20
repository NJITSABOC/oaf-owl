package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 */
public class OWLImportSubtaxonomyGenerator {
    
    public OWLImportSubtaxonomy createImportSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy, 
            PAreaTaxonomyFactory factory,
            Set<String> selectedURIs) {
        
        Set<Concept> concepts = sourceTaxonomy.getSourceHierarchy().getNodes();

        Set<Concept> conceptsInSubtaxonomy = concepts.stream().filter( (concept) -> {
            String clsUri = concept.getIDAsString();
            
            Predicate<String> uriMatchPredicate = (selectedUri) -> {
                return clsUri.toLowerCase().contains(selectedUri);
            };
            
            return selectedURIs.stream().anyMatch(uriMatchPredicate);
            
        }).collect(Collectors.toSet());
        
        Hierarchy<Concept> ancestorHierarchy = sourceTaxonomy.getSourceHierarchy().getAncestorHierarchy(conceptsInSubtaxonomy);
        
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        PAreaTaxonomy subtaxonomy = generator.derivePAreaTaxonomy(factory, ancestorHierarchy);
        
        OAFOntologyDataManager dataManager = ((OWLTaxonomy)sourceTaxonomy).getDataManager();
        
        return new OWLImportSubtaxonomy(sourceTaxonomy, dataManager, subtaxonomy, selectedURIs);
    }
}
