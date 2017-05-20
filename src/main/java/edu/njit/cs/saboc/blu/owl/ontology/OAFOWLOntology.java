package edu.njit.cs.saboc.blu.owl.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashMap;
import java.util.Map;
import org.semanticweb.owlapi.model.OWLClass;

/**
 *
 * @author Chris O
 */
public class OAFOWLOntology extends Ontology<OWLConcept> {

    private final OAFOntologyDataManager manager;
    private final Map<OWLClass, OWLConcept> map = new HashMap<>();

    public OAFOWLOntology(Hierarchy<OWLConcept> conceptHierarchy,
            OAFOntologyDataManager manager) {

        super(conceptHierarchy);

        this.manager = manager;

        conceptHierarchy.getNodes().forEach((concept) -> {
            map.put(concept.getCls(), concept);
        });
    }

    public OWLConcept getOWLConceptFor(OWLClass cls) {

        if (!map.containsKey(cls)) {
            OWLConcept concept = new OWLConcept(cls, manager);
            map.put(cls, concept);
            return concept;
        }

        return map.get(cls);
    }

    public OAFOntologyDataManager getOntologyDataManager() {
        return manager;
    }

}
