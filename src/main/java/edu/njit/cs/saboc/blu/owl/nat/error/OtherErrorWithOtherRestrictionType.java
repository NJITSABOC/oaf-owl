
package edu.njit.cs.saboc.blu.owl.nat.error;

import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import org.json.simple.JSONObject;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class OtherErrorWithOtherRestrictionType extends OtherRestrictionTypeError {
    
    public OtherErrorWithOtherRestrictionType(OAFOWLOntology ontology, OWLClassExpression restriction) {
        super(ontology, restriction);
    }
    
    public OtherErrorWithOtherRestrictionType(
            OAFOWLOntology ontology, 
            String comment, 
            Severity severity, 
            OWLClassExpression restriction) {
        
        super(ontology, comment, severity, restriction);
    }
    
    @Override
    public String getSummaryText() {
        String comment;
        
        if(this.getComment().isEmpty()) {
            comment = "[no comment specified]";
        } else {
            comment = this.getAbbridgedComment();
        }
        
        String axiomName = AxiomStringGenerator.getClassExpressionStr(
                getOntology().getOntologyDataManager().getSourceOntology(), 
                getRestriction(), 
                false);
        
        return String.format("Other error with %s: %s", axiomName, comment);
    }

    @Override
    public String getTooltipText() {
        String text =  "<html><font color = 'RED'><b>Other error</b></font>";
        
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
                + "<b>Other error with: </b></font> %s", 
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
        return super.getBaseJSON("OtherErrorWithOtherRestrictionType");
    }
}
