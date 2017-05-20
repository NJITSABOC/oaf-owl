package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.GenericRootSelectionOptionsPanel;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <ABN_T>
 */
public abstract class OWLRootSelectionPanel<ABN_T extends OWLAbstractionNetwork> extends GenericRootSelectionOptionsPanel 
        implements OWLAbNDerivationWizardPanel<ABN_T> {
    
    private Optional<OAFOntologyDataManager> optDataManager = Optional.empty();

    public OWLRootSelectionPanel(AbNConfiguration config) {
        super(config);
    }
    
    @Override
    public Optional<OWLConcept> getSelectedRoot() {
        return super.getSelectedRoot();
    }
    
    @Override
    public void initialize(OAFOntologyDataManager dataManager) {
        this.optDataManager = Optional.of(dataManager);
        
        super.initialize(optDataManager.get().getOntology(), dataManager);
    }
    
}
