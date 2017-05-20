package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.nat.properties.RestrictionPanel;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.layout.BasicNATAdjustableLayout;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLNATLayout extends BasicNATAdjustableLayout<OWLConcept> {
    
    private RestrictionPanel restrictionPanel;
   
    public OWLNATLayout(OWLBrowserDataSource dataSource) {
        super(dataSource);
    }
    
    @Override
    public OWLBrowserDataSource getDataSource() {
        return (OWLBrowserDataSource)super.getDataSource();
    }

    @Override
    public void createLayout(NATBrowserPanel mainPanel) {
        super.createLayout(mainPanel);
        
        restrictionPanel = new RestrictionPanel(mainPanel, getDataSource());
        
        super.setRightPanelContents(restrictionPanel);
        
        StatedSuperclassesPanel statedSuperclassPanel = new StatedSuperclassesPanel(mainPanel, getDataSource());
        
        super.getAncestorPanel().addResultListPanel(statedSuperclassPanel, "Equiv. Superclasses");
        
        super.getFocusConceptPanel().setRightClickMenuGenerator(new OWLFocusConceptRightClickMenu(mainPanel, getDataSource()));
    }
}