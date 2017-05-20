package edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLNAT;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;

/**
 *
 * @author Chris O
 */
public class OWLDisplayNATListener extends EntitySelectionAdapter<Concept> {

    private final OWLAbNFrameManager frameManager;
    private final OAFOntologyDataManager dataManager;
    
    public OWLDisplayNATListener(
            OWLAbNFrameManager frameManager,
            OAFOntologyDataManager dataManager) {
        
        this.frameManager = frameManager;
        this.dataManager = dataManager;
    }
    
    @Override
    public void entityDoubleClicked(Concept entity) {
        OWLConcept concept = (OWLConcept) entity;

        CreateAndDisplayOWLNAT createAndDisplay = new CreateAndDisplayOWLNAT(
                frameManager, dataManager, concept);

        createAndDisplay.run();
    }
    
}
