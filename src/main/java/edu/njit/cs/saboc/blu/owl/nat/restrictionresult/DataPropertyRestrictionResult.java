package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;

/**
 *
 * @author Chris O
 */
public class DataPropertyRestrictionResult extends PropertyRestrictionResult<OWLDataProperty, OWLDataRange> {
    
    public DataPropertyRestrictionResult(
            OWLQuantifiedRestriction<OWLDataRange> restriction, 
            OWLClassExpression sourceExpression, 
            OWLConcept source) {
        
        super(restriction, sourceExpression, source);
    }
    
    @Override
    public OWLQuantifiedRestriction<OWLDataRange> getRestriction() {
        return (OWLQuantifiedRestriction)super.getRestriction();
    }
    
    @Override
    public OWLDataRange getFiller() {
        return getRestriction().getFiller();
    }
}
