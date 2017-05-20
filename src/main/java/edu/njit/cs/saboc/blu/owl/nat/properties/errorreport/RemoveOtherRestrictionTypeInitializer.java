package edu.njit.cs.saboc.blu.owl.nat.properties.errorreport;

import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.error.RemoveOtherRestrictionTypeError;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class RemoveOtherRestrictionTypeInitializer implements ErrorReportPanelInitializer<RemoveOtherRestrictionTypeError> {
    
    private final OAFOWLOntology theOntology;
    private final OWLClassExpression erroneousRestriction;

    public RemoveOtherRestrictionTypeInitializer(OAFOWLOntology ontology, OWLClassExpression erroneousRestriction) {
        this.theOntology = ontology;
        this.erroneousRestriction = erroneousRestriction;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        
        String axiomStr = AxiomStringGenerator.getClassExpressionStr(
                theOntology.getOntologyDataManager().getSourceOntology(), 
                erroneousRestriction, 
                true);
        
        return String.format("<html><font size = '5'>"
                + "<b>Remove restriction: </b>"
                + "<br>%s", 
                axiomStr);
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Severe;
    }

    @Override
    public RemoveOtherRestrictionTypeError generateError(String comment, OntologyError.Severity severity) {
        return new RemoveOtherRestrictionTypeError(theOntology, comment, severity, erroneousRestriction);
    }

    @Override
    public String getErrorTypeName() {
        return "Remove restriction";
    }
}