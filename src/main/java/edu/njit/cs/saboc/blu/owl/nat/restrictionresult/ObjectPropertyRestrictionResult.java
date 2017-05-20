package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;

/**
 *
 * @author Chris O
 */
public class ObjectPropertyRestrictionResult extends PropertyRestrictionResult<OWLObjectProperty, OWLClassExpression> {
    
    public ObjectPropertyRestrictionResult(
            OWLQuantifiedRestriction<OWLClassExpression>  restriction, 
            OWLClassExpression sourceExpression, 
            OWLConcept source) {
        
        super(restriction, sourceExpression, source);
    }
    
    @Override
    public OWLQuantifiedRestriction<OWLClassExpression> getRestriction() {
        return (OWLQuantifiedRestriction)super.getRestriction();
    }
    
    @Override
    public OWLClassExpression getFiller() {
        return getRestriction().getFiller();
    }
}
