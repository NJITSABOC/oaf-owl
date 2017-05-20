package edu.njit.cs.saboc.blu.owl.nat.error;

import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import org.json.simple.JSONObject;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class RemoveOtherRestrictionTypeError extends OtherRestrictionTypeError {
    
    public RemoveOtherRestrictionTypeError(OAFOWLOntology ontology, OWLClassExpression restriction) {
        super(ontology, restriction);
    }
    
    public RemoveOtherRestrictionTypeError(
            OAFOWLOntology ontology, 
            String comment, 
            Severity severity, 
            OWLClassExpression restriction) {
        
        super(ontology, comment, severity, restriction);
    }
    
    @Override
    public String getSummaryText() {
        
        String axiomName = AxiomStringGenerator.getClassExpressionStr(
                getOntology().getOntologyDataManager().getSourceOntology(), 
                getRestriction(), 
                false);
        
        return String.format("Remove restriction: %s", axiomName);
    }

    @Override
    public String getTooltipText() {
        String text =  "<html><font color = 'RED'><b>Remove restriction</b></font>";
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }

        return text;
    }

    @Override
    public String getStyledText() {
        String axiomName = AxiomStringGenerator.getClassExpressionStr(
                getOntology().getOntologyDataManager().getSourceOntology(), 
                getRestriction(), 
                true);
        
        String text =  String.format("<html><font color = 'RED'>"
                + "<b>Remove restriction: </b></font> %s", 
                axiomName);
        
        text += "<br>";
        
        if(this.getComment().isEmpty()) {
            text += this.getStyledEmptyCommentText();
        } else {
            text += this.getStyledCommentText();
        }

        return text;
    }

    @Override
    public JSONObject toJSON() {
        return super.getBaseJSON("RemoveOtherRestrictionTypeError");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final RemoveOtherRestrictionTypeError other = (RemoveOtherRestrictionTypeError) obj;
        
        if(!this.getRestriction().equals(other.getRestriction())) {
            return false;
        }
        
        return true;
    }
}
