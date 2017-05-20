
package edu.njit.cs.saboc.blu.owl.nat.properties.errorreport;

import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
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
            OWLBrowserDataSource dataSource,
            OWLClassExpression expr) {
        
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                dataSource, 
                new RemoveOtherRestrictionTypeInitializer((OAFOWLOntology)dataSource.getOntology(), expr));
    }
    
    public static void displayOtherErrorWithOtherRestrictionDialog(
            NATBrowserPanel<OWLConcept> browserPanel, 
            OWLBrowserDataSource dataSource,
            OWLClassExpression expr) {
       
        ErrorReportDialog.displaySimpleErrorReportPanel(
                browserPanel, 
                dataSource, 
                new OtherErrorWithOtherRestrictionTypeInitializer((OAFOWLOntology)dataSource.getOntology(), expr));
    }
}
