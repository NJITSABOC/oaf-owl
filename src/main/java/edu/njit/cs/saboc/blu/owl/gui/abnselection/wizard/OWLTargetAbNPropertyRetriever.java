package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased.TargetAbNDerivationWizardPanel.InheritablePropertyRetriever;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLTargetAbNPropertyRetriever implements InheritablePropertyRetriever {
    private final OAFOntologyDataManager dataManager;
    
    public OWLTargetAbNPropertyRetriever(OAFOntologyDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Set<InheritableProperty> getInheritablePropertiesInSubhierarchy(Concept root) {
        
        Set<PropertyTypeAndUsage> typesAndUsages = new HashSet<>();
        typesAndUsages.add(PropertyTypeAndUsage.OP_RESTRICTION);
        typesAndUsages.add(PropertyTypeAndUsage.OP_EQUIV);
        
        return (Set<InheritableProperty>)(Set<?>)dataManager.getPropertiesInSubhierarchy((OWLConcept)root, typesAndUsages);
    }

}
