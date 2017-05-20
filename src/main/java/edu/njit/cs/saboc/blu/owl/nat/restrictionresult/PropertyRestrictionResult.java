package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLRestriction;

/**
 *
 * @author Chris
 * @param <T>
 * @param <V>
 */
public abstract class PropertyRestrictionResult<T extends OWLProperty, V extends OWLObject> extends RestrictionResult {
    
    private final OWLRestriction restriction;
    
    public PropertyRestrictionResult(
            OWLRestriction restriction, 
            OWLClassExpression sourceExpression, 
            OWLConcept source) {
        
        super(sourceExpression, source);
        
        this.restriction = restriction;
    }
    
    public OWLRestriction getRestriction() {
        return restriction;
    }
    
    public T getProperty() {
        return (T)restriction.getProperty();
    }
    
    public abstract V getFiller();
}
