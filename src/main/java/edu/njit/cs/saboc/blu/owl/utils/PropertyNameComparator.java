package edu.njit.cs.saboc.blu.owl.utils;

import java.util.Comparator;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class PropertyNameComparator implements Comparator<OWLProperty> {

    private OWLOntology ontology;

    public PropertyNameComparator(OWLOntology ontology) {
        this.ontology = ontology;
    }

    public int compare(OWLProperty p1, OWLProperty p2) {
        String s1 = OWLUtilities.getPropertyLabel(ontology, p1);
        String s2 = OWLUtilities.getPropertyLabel(ontology, p2);
        
        return s1.compareToIgnoreCase(s2);
    }
}