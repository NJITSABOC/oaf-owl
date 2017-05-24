package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.nat.properties.RestrictionPanel;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.layout.BasicNATAdjustableLayout;

/**
 *
 * @author Chris O
 */
public class OWLNATLayout extends BasicNATAdjustableLayout<OWLConcept> {
    
    private RestrictionPanel restrictionPanel;
   
    public OWLNATLayout() {
        
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        super.createLayout(mainPanel);
        
        restrictionPanel = new RestrictionPanel(mainPanel);
        
        super.setRightPanelContents(restrictionPanel);
        
        StatedSuperclassesPanel statedSuperclassPanel = new StatedSuperclassesPanel(mainPanel);
        
        super.getAncestorPanel().addResultListPanel(statedSuperclassPanel, "Equiv. Superclasses");
        
        super.getFocusConceptPanel().setRightClickMenuGenerator(new OWLFocusConceptRightClickMenu(mainPanel));
    }

    @Override
    public void reset() {
        super.reset();
        
        restrictionPanel.reset();
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.restrictionPanel.setEnabled(value);
    }
    
}