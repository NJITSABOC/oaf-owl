package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.nat.annotations.AnnotationDialog;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.FocusConceptRightClickMenu;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 *
 * @author Chris O
 */
public class OWLFocusConceptRightClickMenu extends FocusConceptRightClickMenu<OWLConcept> {
    
    public OWLFocusConceptRightClickMenu(NATBrowserPanel<OWLConcept> mainPanel) {
        
        super(mainPanel);
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(OWLConcept item) {
        
        if(!getMainPanel().getDataSource().isPresent()) {
            return new ArrayList<>();
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)getMainPanel().getDataSource().get();
        
        ArrayList<JComponent> components = new ArrayList<>();
       
        JMenuItem viewAnnotationsBtn = new JMenuItem("View annotations");
        viewAnnotationsBtn.setFont(viewAnnotationsBtn.getFont().deriveFont(14.0f));
        viewAnnotationsBtn.addActionListener((ae) -> {
            AnnotationDialog.displayAnnotationDialog(getMainPanel(), item);
        });
                
        viewAnnotationsBtn.setEnabled(!dataSource.getAnnotations(item).isEmpty());
        
        components.add(viewAnnotationsBtn);
        
        ArrayList<JComponent> parentComponents = super.buildRightClickMenuFor(item);
        
        if(!parentComponents.isEmpty()) {
            components.add(new JSeparator());
            components.addAll(parentComponents);
        }

        return components;
    }
}
