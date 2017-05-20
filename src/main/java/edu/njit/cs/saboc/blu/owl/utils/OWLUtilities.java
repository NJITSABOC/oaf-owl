package edu.njit.cs.saboc.blu.owl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris
 */
public class OWLUtilities {

    public static String getClassLabel(OWLOntology ontology, OWLClass cls) {

        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(cls, ontology);

        Optional<String> label = OWLUtilities.getLabel(annotations);

        if (label.isPresent()) {
            return label.get().replaceAll("_", " ");
        } else {
            String clsIri = cls.getIRI().toString();

            if (clsIri.contains("#")) {
                return clsIri.substring(clsIri.lastIndexOf("#") + 1);
            }
            return clsIri.substring(clsIri.lastIndexOf("/") + 1);
        }
    }
    
    public static String getEntityIdentifier(OWLEntity entity) {
        String iriStr = entity.getIRI().toString();
        
        return iriStr.substring(iriStr.lastIndexOf("/") + 1);
    }

    public static String getPropertyLabel(OWLOntology ontology, OWLProperty property) {
        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(property, ontology);

        Optional<String> label = OWLUtilities.getLabel(annotations);

        if (label.isPresent()) {
            return label.get().replaceAll("_", " ");
        } else {
            String propIri = property.getIRI().toString();

            return propIri.substring(propIri.lastIndexOf("#") + 1);
        }
    }
    
    public static String getAnnotationPropertyLabel(OWLOntology ontology, OWLAnnotationProperty property) {
        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(property, ontology);;
        
        Optional<String> label = OWLUtilities.getLabel(annotations);
        
        if(label.isPresent()) {
            return label.get();
        } else {
            String propIri = property.getIRI().toString();

            return propIri.substring(propIri.lastIndexOf("#") + 1);
        }
    }
    
    public static String getIndividualLabel(OWLOntology ontology, OWLIndividual individual) {

        if(individual.isNamed()) {
            OWLNamedIndividual namedIndividual = individual.asOWLNamedIndividual();
            
            Optional<String> label = OWLUtilities.getLabel(BLUEntityRetriever.getAnnotations(namedIndividual, ontology));
            
            if(label.isPresent()) {
                return label.get().replaceAll("_", " ");
            } else {
                String indivIRI = namedIndividual.getIRI().toString();

                return indivIRI.substring(indivIRI.lastIndexOf("#") + 1);
            }
        }
        
        return "(Anonymous Individual)";
    }

    private static Optional<String> getLabel(Collection<OWLAnnotation> annotations) {
        ArrayList<OWLLiteral> potentialLabels = new ArrayList<>();
        
        for (OWLAnnotation annotation : annotations) {
            if (annotation.getProperty().isLabel()) {

                if(annotation.getValue() instanceof OWLLiteral) {
                    potentialLabels.add((OWLLiteral)annotation.getValue());
                }
            }
        }
        
        if (potentialLabels.isEmpty()) {
            return Optional.empty();
        }

        if(potentialLabels.size() == 1) {
            return Optional.of(sanitizeLabel(potentialLabels.get(0).getLiteral()));
        }
        
        for(OWLLiteral label : potentialLabels) {
            if(label.getLang().equals("en")) {
                return Optional.of(sanitizeLabel(label.getLiteral()));
            }
        }
        
        return Optional.of(sanitizeLabel(potentialLabels.get(0).getLiteral()));
    }
    
    private static String sanitizeLabel(String label) {
        label = label.replaceAll("\r\n", "");
        label = label.replaceAll("\r", "");
        label = label.replaceAll("\n", "");
        label = label.replaceAll("\t", " ");
        
        return label;
    }

    public static boolean classIsObsolete(OWLOntology ontology, OWLClass cls) {
        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(cls, ontology);

        for (OWLAnnotation annotation : annotations) {

            if (annotation.getProperty().toString().contains("http://purl.obolibrary.org/obo/is_obsolete") && 
                    annotation.getValue().toString().contains("true")) {
                
                return true;
            }

            if (annotation.getProperty().toString().contains("owl:deprecated") && 
                    annotation.getValue().toString().contains("true")) {
                
                return true;
            }
        }

        return false;
    }
    
    public static Collection<OWLClassExpression> getDomains(OWLProperty property, OWLOntology ontology) {
        if(property.isOWLObjectProperty()) {
            OWLObjectProperty op = (OWLObjectProperty)property;
            
            return BLUEntityRetriever.getDomains(op, ontology);
        } else {
            OWLDataProperty dp = (OWLDataProperty)property;
            
            return BLUEntityRetriever.getDomains(dp, ontology);
        }
    }
}
