package edu.njit.cs.saboc.blu.owl.nat.properties.errorreport;

import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.error.RemoveOtherRestrictionTypeError;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.initializer.ErrorReportPanelInitializer;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class RemoveOtherRestrictionTypeInitializer extends ErrorReportPanelInitializer<OWLConcept, RemoveOtherRestrictionTypeError> {
    
    private final OWLClassExpression erroneousRestriction;

    public RemoveOtherRestrictionTypeInitializer(
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
        return new RemoveOtherRestrictionTypeError((OAFOWLOntology)getOntology(), comment, severity, erroneousRestriction);
    }

    @Override
    public String getErrorTypeName() {
        return "Remove restriction";
    }
}