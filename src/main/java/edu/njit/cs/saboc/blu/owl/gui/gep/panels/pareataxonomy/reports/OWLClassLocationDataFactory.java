package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.ConceptLocationDataFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLClassLocationDataFactory implements ConceptLocationDataFactory<OWLConcept> {
    
    private final OAFOWLOntology ontology;
    
    public OWLClassLocationDataFactory(OAFOWLOntology ontology) {
        this.ontology = ontology;
    }

    @Override
    public Set<OWLConcept> getConceptsFromIds(ArrayList<String> ids) {
        System.out.println("ids: "+ ids.toString());

        Map<String, OWLConcept> conceptMap = new HashMap<>();
        
        ontology.getConceptHierarchy().getNodes().forEach( (concept) -> {
            conceptMap.put(concept.getIDAsString().toLowerCase(), concept);
        });
        
        Set<OWLConcept> foundConcepts = new HashSet<>();

        ids.forEach( (id) -> {
            if(conceptMap.containsKey(id.toLowerCase())) {
                foundConcepts.add(conceptMap.get(id.toLowerCase()));
            } else {
                
            }
        });
               
        return foundConcepts;
    }
}
