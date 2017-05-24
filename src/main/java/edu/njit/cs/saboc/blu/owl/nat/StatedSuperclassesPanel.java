package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.filterable.list.renderer.SimpleConceptRenderer;
import edu.njit.cs.saboc.nat.generic.gui.panels.ConceptListPanel;

/**
 *
 * @author Chris O
 */
public class StatedSuperclassesPanel extends ConceptListPanel<OWLConcept> {
    
    public StatedSuperclassesPanel(NATBrowserPanel<OWLConcept> mainPanel) {
        
        super(mainPanel, 
                OWLNATDataRetrievers.getEquivSuperclasses(mainPanel),
                new SimpleConceptRenderer<>(mainPanel), 
                true,
                true,
                true);
    }
}