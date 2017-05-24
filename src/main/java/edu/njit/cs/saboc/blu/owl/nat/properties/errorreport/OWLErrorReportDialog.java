
package edu.njit.cs.saboc.blu.owl.nat.properties.errorreport;

import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class OWLErrorReportDialog extends ErrorReportDialog {
        
    public static void displayRemoveOtherRestrictionDialog(
            NATBrowserPanel<OWLConcept> browserPanel, 
            OWLConcept erroneousConcept,
            OWLClassExpression expr) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                new RemoveOtherRestrictionTypeInitializer(
                        (OAFOWLOntology)browserPanel.getDataSource().get().getOntology(), 
                        erroneousConcept,
                        expr));
    }
    
    public static void displayOtherErrorWithOtherRestrictionDialog(
            NATBrowserPanel<OWLConcept> browserPanel, 
            OWLConcept erroneousConcept,
            OWLClassExpression expr) {
       
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                new OtherErrorWithOtherRestrictionTypeInitializer(
                        (OAFOWLOntology)browserPanel.getDataSource().get().getOntology(), 
                        erroneousConcept,
                        expr));
    }
}
