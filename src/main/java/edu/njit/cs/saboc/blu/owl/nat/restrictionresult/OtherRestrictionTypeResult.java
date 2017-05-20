package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Den
 */
public class OtherRestrictionTypeResult extends RestrictionResult {
    
    private final OWLClassExpression expr;
    
    public OtherRestrictionTypeResult(
            OWLClassExpression expr,
            OWLClassExpression sourceExpression, 
            OWLConcept sourceConcept) {
        
        super(sourceExpression, sourceConcept);
        
        this.expr = expr;
    }
    
    public OWLClassExpression getRestriction() { 
        return expr;
    }
}
