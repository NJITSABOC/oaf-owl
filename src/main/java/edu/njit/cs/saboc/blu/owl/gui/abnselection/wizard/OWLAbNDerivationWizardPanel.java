package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public interface OWLAbNDerivationWizardPanel<ABN_T extends OWLAbstractionNetwork> {
    public void initialize(OAFOntologyDataManager manager);
    public void displaySelectedOptionsFor(ABN_T abn);
}
