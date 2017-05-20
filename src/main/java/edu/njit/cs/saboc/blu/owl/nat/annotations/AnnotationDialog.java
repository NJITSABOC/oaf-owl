package edu.njit.cs.saboc.blu.owl.nat.annotations;

import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class AnnotationDialog {
    
    public static void displayAnnotationDialog(
            NATBrowserPanel<OWLConcept> mainPanel, 
            OWLBrowserDataSource dataSource, 
            OWLConcept concept) {
        
        JDialog dialog = new JDialog();
        
        dialog.setModal(true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(mainPanel.getParentFrame());
        
        AnnotationPanel annotationPanel = new AnnotationPanel(mainPanel, dataSource, concept);
        dialog.add(annotationPanel);
        
        dialog.setTitle(String.format("Annotations for %s", concept.getName()));

        dialog.setVisible(true);
    }
}
