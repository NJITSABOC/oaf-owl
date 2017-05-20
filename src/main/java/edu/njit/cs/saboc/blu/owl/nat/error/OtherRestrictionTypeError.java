package edu.njit.cs.saboc.blu.owl.nat.error;

import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.SemanticRelationshipError;
import org.json.simple.JSONObject;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public abstract class OtherRestrictionTypeError extends SemanticRelationshipError<OWLConcept> {
    
    private final OWLClassExpression restriction;
    
    public OtherRestrictionTypeError(OAFOWLOntology ontology, OWLClassExpression restriction) {
        super(ontology);
        
        this.restriction = restriction;
    }
    
    public OtherRestrictionTypeError(
            OAFOWLOntology ontology, 
            String comment, 
            Severity severity, 
            OWLClassExpression restriction) {
        
        super(ontology, comment, severity);
        
        this.restriction = restriction;
    }

    @Override
    public OAFOWLOntology getOntology() {
        return (OAFOWLOntology)super.getOntology();
    }
    
    public OWLClassExpression getRestriction() {
        return restriction;
    }
    
    @Override
    protected JSONObject getBaseJSON(String type) {
        JSONObject json = super.getBaseJSON(type);
        json.put("restriction", restriction.toString());
        
        return json;
    }
    
}
