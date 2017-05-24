package edu.njit.cs.saboc.blu.owl.nat.properties.errorreport;

import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.error.OtherErrorWithOtherRestrictionType;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class OtherErrorWithOtherRestrictionTypeInitializer extends 
        ErrorReportPanelInitializer<OWLConcept, OtherErrorWithOtherRestrictionType> {
    
    private final OWLClassExpression erroneousRestriction;

    public OtherErrorWithOtherRestrictionTypeInitializer(
            OAFOWLOntology ontology, 
            OWLConcept erroneousConcept,
            OWLClassExpression erroneousRestriction) {
        
        super(ontology, erroneousConcept);
        
        this.erroneousRestriction = erroneousRestriction;
    }

    @Override
    public String getStyledErrorDescriptionText() {
        
        OAFOWLOntology owlOntology = (OAFOWLOntology)getOntology();
        
        String axiomStr = AxiomStringGenerator.getClassExpressionStr(
                owlOntology.getOntologyDataManager().getSourceOntology(), 
                erroneousRestriction, 
                true);
        
        return String.format("<html><font size = '5'>"
                + "<b>Other error: </b>"
                + "<br>%s", 
                axiomStr);
    }

    @Override
    public OntologyError.Severity getDefaultSeverity() {
        return OntologyError.Severity.Severe;
    }

    @Override
    public OtherErrorWithOtherRestrictionType generateError(String comment, OntologyError.Severity severity) {
        return new OtherErrorWithOtherRestrictionType((OAFOWLOntology)getOntology(), comment, severity, erroneousRestriction);
    }

    @Override
    public String getErrorTypeName() {
        return "Other error with restriction";
    }
}