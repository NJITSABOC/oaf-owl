package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.CreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class CreateAndDisplayOWLNAT extends CreateAndDisplayDialog<OWLBrowserDataSource> {
    
    private final OAFOntologyDataManager dataManager;
    private Optional<OWLConcept> focusConcept = Optional.empty();
    
    public CreateAndDisplayOWLNAT(
            OWLAbNFrameManager displayManager, 
            OAFOntologyDataManager dataManager) {
        
        super("Preparing Neighborhood Auditing Tool (NAT) for OWL", displayManager);
        
        this.dataManager = dataManager;
    }
    
    public CreateAndDisplayOWLNAT(
            OWLAbNFrameManager displayManager, 
            OAFOntologyDataManager dataManager,
            OWLConcept focusConcept) {
        
        this(displayManager, dataManager);
        
        this.focusConcept = Optional.of(focusConcept);
    } 

    @Override
    public OWLAbNFrameManager getDisplayFrameListener() {
        return (OWLAbNFrameManager)super.getDisplayFrameListener();
    }

    @Override
    protected void display(OWLBrowserDataSource dataSource) {
        getDisplayFrameListener().displayNATBrowser(dataSource, focusConcept);
    }

    @Override
    protected OWLBrowserDataSource create() {
        return new OWLBrowserDataSource(dataManager);
    }
}
