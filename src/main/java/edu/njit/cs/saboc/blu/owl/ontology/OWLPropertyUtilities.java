package edu.njit.cs.saboc.blu.owl.ontology;

import edu.njit.cs.saboc.blu.owl.utils.BLUEntityRetriever;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLDataSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;

/**
 *
 * @author Chris O
 */
public class OWLPropertyUtilities {
    
    public static Set<OWLObjectProperty> getOPUsedInClassRestriction(OWLConcept concept, OWLOntology ontology) {
        
        Set<OWLObjectProperty> clsProperties = new HashSet<>();
        
        Collection<OWLClassExpression> parents = BLUEntityRetriever.getSuperClasses(concept.getCls(), ontology);

        parents.forEach( (parent) -> {
            if (parent instanceof OWLObjectSomeValuesFromImpl) {
                OWLObjectSomeValuesFromImpl someVals = ((OWLObjectSomeValuesFromImpl) parent);
                
                if(!someVals.getProperty().isAnonymous()) {
                    clsProperties.add(someVals.getProperty().asOWLObjectProperty());
                }
                
            } else if (parent instanceof OWLObjectAllValuesFromImpl) {
                OWLObjectAllValuesFromImpl allVals = ((OWLObjectAllValuesFromImpl) parent);
                
                if (!allVals.getProperty().isAnonymous()) {
                    clsProperties.add(allVals.getProperty().asOWLObjectProperty());
                }
            }
        });
        
        return clsProperties;
    }
    
    //this will give you the relationships of the class in question.
    public static Set<OWLObjectProperty> getOPUsedInClassEquivRestriction(OWLConcept concept, OWLOntology ontology) {
        HashSet<OWLObjectProperty> clsProperties = new HashSet<>();

        Collection<OWLClassExpression> equiv = BLUEntityRetriever.getEquivalentClasses(concept.getCls(), ontology);

        for (OWLClassExpression expr : equiv) {
            if (expr instanceof OWLObjectIntersectionOfImpl) {
                OWLObjectIntersectionOfImpl intersection = ((OWLObjectIntersectionOfImpl) expr);

                Set<OWLClassExpression> conjunctExprs = intersection.asConjunctSet();

                for (OWLClassExpression conjunctExpr : conjunctExprs) {
                    if (conjunctExpr instanceof OWLObjectSomeValuesFromImpl) {
                        OWLObjectSomeValuesFromImpl someValuesFrom = (OWLObjectSomeValuesFromImpl) conjunctExpr;

                        if(!someValuesFrom.getProperty().isAnonymous()) {
                            clsProperties.add(someValuesFrom.getProperty().asOWLObjectProperty());
                        }
                        
                    } else if (conjunctExpr instanceof OWLObjectAllValuesFromImpl) {
                        OWLObjectAllValuesFromImpl allVals = ((OWLObjectAllValuesFromImpl) conjunctExpr);

                        if(!allVals.getProperty().isAnonymous()) {
                            clsProperties.add(allVals.getProperty().asOWLObjectProperty());
                        }
                    }
                }
            }
        }

        return clsProperties;
    }
    
    //get DataProperty information:
    public static HashSet<OWLDataProperty> getDPUsedInClassRestriction(OWLConcept concept, OWLOntology ontology) {
        HashSet<OWLDataProperty> clsProperties = new HashSet<>();
        
        Collection<OWLClassExpression> parents = BLUEntityRetriever.getSuperClasses(concept.getCls(), ontology);

        parents.forEach((parent) -> {
            if (parent instanceof OWLDataSomeValuesFromImpl) {
                OWLDataSomeValuesFromImpl someVals = ((OWLDataSomeValuesFromImpl) parent);

                if(!someVals.getProperty().isAnonymous()) {
                    clsProperties.add(someVals.getProperty().asOWLDataProperty());
                }
                
            } else if (parent instanceof OWLDataAllValuesFromImpl) {
                OWLDataAllValuesFromImpl allVals = ((OWLDataAllValuesFromImpl) parent);

                if(!allVals.getProperty().isAnonymous()) {
                    clsProperties.add(allVals.getProperty().asOWLDataProperty());
                }
            }
        });
        
        return clsProperties;
    }

    public static HashSet<OWLDataProperty> getDPUsedInClassEquivRestriction(OWLConcept concept, OWLOntology ontology) {
        HashSet<OWLDataProperty> clsProperties = new HashSet<>();

        Collection<OWLClassExpression> equiv = BLUEntityRetriever.getEquivalentClasses(concept.getCls(), ontology);

        for (OWLClassExpression expr : equiv) {
            if (expr instanceof OWLObjectIntersectionOfImpl) {
                OWLObjectIntersectionOfImpl intersection = ((OWLObjectIntersectionOfImpl) expr);

                Set<OWLClassExpression> conjunctExprs = intersection.asConjunctSet();

                for (OWLClassExpression conjunctExpr : conjunctExprs) {
                    if (conjunctExpr instanceof OWLDataSomeValuesFromImpl) {
                        OWLDataSomeValuesFromImpl someValuesFrom = (OWLDataSomeValuesFromImpl) conjunctExpr;

                        if(!someValuesFrom.getProperty().isAnonymous()) {
                            clsProperties.add(someValuesFrom.getProperty().asOWLDataProperty());
                        }
                        
                    } else if(conjunctExpr instanceof OWLDataAllValuesFromImpl) {
                        OWLDataAllValuesFromImpl allValuesFrom = (OWLDataAllValuesFromImpl) conjunctExpr;

                        if(!allValuesFrom.getProperty().isAnonymous()) {
                            clsProperties.add(allValuesFrom.getProperty().asOWLDataProperty());
                        }
                    }
                }
            }
        }

        return clsProperties;
    }
    
    public static Set<OWLConcept> getNonComplementClassesInSet(Collection<OWLClassExpression> expressions, OAFOntologyDataManager dataManager) {
        Set<OWLConcept> clses = new HashSet<>();

        for (OWLClassExpression expression : expressions) {
            if (expression instanceof OWLClassImpl) {
                clses.add(dataManager.getOntology().getOWLConceptFor(expression.asOWLClass()));
                
            } else if (expression instanceof OWLObjectComplementOfImpl) {
                // Do nothing with complements.
                
            } else if (expression instanceof OWLObjectUnionOfImpl) {
                OWLObjectUnionOfImpl asUnion = ((OWLObjectUnionOfImpl) expression);
                clses.addAll(getNonComplementClassesInSet(asUnion.asDisjunctSet(), dataManager));
                
            } else if (expression instanceof OWLObjectIntersectionOfImpl) {
                OWLObjectIntersectionOfImpl asIntersection = ((OWLObjectIntersectionOfImpl) expression);
                clses.addAll(getNonComplementClassesInSet(asIntersection.asConjunctSet(), dataManager));
                
            } else if (expression instanceof OWLObjectSomeValuesFromImpl) {
                // Do nothing for now...
                
            } else if (expression instanceof OWLObjectAllValuesFromImpl) {
                // Do nothing for now...
                
            } else {
                
            }
        }

        return clses;
    }

    public static Set<OWLConcept> getDomainForProperty(OWLProperty property, OAFOntologyDataManager dataManager) {
        Collection<OWLClassExpression> domains = BLUEntityRetriever.getDomains(property, dataManager.getSourceOntology());
        
        return OWLPropertyUtilities.getNonComplementClassesInSet(domains, dataManager);
    }
}
