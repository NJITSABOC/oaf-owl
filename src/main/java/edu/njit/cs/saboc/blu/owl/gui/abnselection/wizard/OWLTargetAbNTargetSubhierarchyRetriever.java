
package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased.TargetAbNDerivationWizardPanel.TargetHierarchyRetriever;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class OWLTargetAbNTargetSubhierarchyRetriever implements TargetHierarchyRetriever {
    private final OAFOntologyDataManager dataManager;
    
    public OWLTargetAbNTargetSubhierarchyRetriever(OAFOntologyDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Hierarchy<Concept> getTargetSubhierarchy(Concept root, InheritableProperty propertyType) {
        return (Hierarchy<Concept>)(Hierarchy<?>)dataManager.getSimpleRestrictionTargetHierarchy((OWLConcept)root, 
                Collections.singleton((OWLInheritableProperty)propertyType));
    }
}
