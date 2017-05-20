package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public abstract class RestrictionResult {
    
    private final OWLClassExpression sourceExpression;
    
    private final OWLConcept sourceConcept;
    
    protected RestrictionResult(OWLClassExpression sourceExpression, OWLConcept sourceConcept) {
        this.sourceExpression = sourceExpression;
        this.sourceConcept = sourceConcept;
    }
    
    public OWLClassExpression getSourceExpression() {
        return sourceExpression;
    }
    
    public OWLConcept getSourceConcept() {
        return sourceConcept;
    }
}
