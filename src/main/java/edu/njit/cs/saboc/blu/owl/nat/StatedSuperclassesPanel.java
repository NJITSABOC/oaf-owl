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
    
    public StatedSuperclassesPanel(
            NATBrowserPanel<OWLConcept> mainPanel, 
            OWLBrowserDataSource dataSource) {
        
        super(mainPanel, 
                dataSource,
                OWLNATDataRetrievers.getEquivSuperclasses(dataSource),
                new SimpleConceptRenderer<>(mainPanel, dataSource), 
                true,
                true,
                true);
    }
}