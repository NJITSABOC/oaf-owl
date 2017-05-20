
package edu.njit.cs.saboc.blu.owl.utils;

import java.util.Collection;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.search.EntitySearcher;


/**
 * A wrapper around the functionality that is different between OWL API versions 3.x and 4.x
 * 
 * @author cro3
 */
public class BLUEntityRetriever {
    
    public static Collection<OWLAnnotation> getAnnotations(OWLEntity entity, OWLOntology ontology) {
        return EntitySearcher.getAnnotations(entity, ontology);
    }
    
    public static Collection<OWLClassExpression> getSuperClasses(OWLClass cls, OWLOntology ontology) {
        return EntitySearcher.getSuperClasses(cls, ontology);
    }
    
    public static Collection<OWLClassExpression> getSubClasses(OWLClass cls, OWLOntology ontology) {
        return EntitySearcher.getSubClasses(cls, ontology);
    }
    
    public static Collection<OWLIndividual> getIndividuals(OWLClass cls, OWLOntology ontology) {
        return EntitySearcher.getIndividuals(cls, ontology);
    }
    
    public static boolean isDefined(OWLClass cls, OWLOntology ontology) {
        return EntitySearcher.isDefined(cls, ontology);
    }
    
    public static Collection<OWLClassExpression> getEquivalentClasses(OWLClass cls, OWLOntology ontology) {
        return EntitySearcher.getEquivalentClasses(cls, ontology);
    }
    
    public static Collection<OWLClassExpression> getDomains(OWLProperty property, OWLOntology ontology) {       
        if(property.isOWLObjectProperty()) {
            return getObjectPropertyDomains((OWLObjectProperty)property, ontology);
        } else{ 
            return getDataPropertyDomains((OWLDataProperty)property, ontology); 
        }
    }
    
    public static Collection<OWLClassExpression> getObjectPropertyDomains(OWLObjectProperty property, OWLOntology ontology) {
        return EntitySearcher.getDomains(property, ontology);
    }
    
    public static Collection<OWLClassExpression> getDataPropertyDomains(OWLDataProperty property, OWLOntology ontology) {
        return EntitySearcher.getDomains(property, ontology);
    }
    
    public static Collection<OWLClassExpression> getRanges(OWLObjectProperty objectProperty, OWLOntology ontology) {
        return EntitySearcher.getRanges(objectProperty, ontology);
    }
    
    public static Collection<OWLDataRange> getRanges(OWLDataProperty dataProperty, OWLOntology ontology) {
        return EntitySearcher.getRanges(dataProperty, ontology);
    }
    
    public static String getOntologyIRI(OWLOntology ontology) {
        return ontology.getOntologyID().toString();
    }
}
