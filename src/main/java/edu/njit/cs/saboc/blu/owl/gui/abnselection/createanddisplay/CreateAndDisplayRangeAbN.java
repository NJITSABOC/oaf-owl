package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.AbNCreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.range.OWLRangeAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;

/**
 *
 * @author Chris O
 */
public class CreateAndDisplayRangeAbN extends AbNCreateAndDisplayDialog<TargetAbstractionNetwork> {
    
    private final OWLConcept sourceHierarchyRoot;
    private final OWLConcept targetHierarchyRoot;
    
    private final OWLInheritableProperty propertyType;
    
    private final OAFOntologyDataManager dataManager;

    public CreateAndDisplayRangeAbN(
            String displayText, 
            OWLAbNFrameManager displayListener,
            OWLConcept sourceHierarchyRoot, 
            OWLInheritableProperty propertyType, 
            OWLConcept targetHierarchyRoot,
            OAFOntologyDataManager dataManager) {
        
        super(displayText, displayListener);
        
        this.sourceHierarchyRoot = sourceHierarchyRoot;
        this.targetHierarchyRoot = targetHierarchyRoot;
        this.propertyType = propertyType;
        this.dataManager = dataManager;
    }

    @Override
    protected void display(TargetAbstractionNetwork abn) {
        super.getDisplayFrameListener().displayTargetAbstractionNetwork(abn);
    }

    @Override
    protected TargetAbstractionNetwork create() {
        
        OWLRangeAbstractionNetworkFactory rangeFactory = new OWLRangeAbstractionNetworkFactory(
                dataManager, 
                sourceHierarchyRoot, 
                propertyType, 
                targetHierarchyRoot);
        
        Hierarchy<OWLConcept> sourceHierarchy = dataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(sourceHierarchyRoot);
        Hierarchy<OWLConcept> targetHierarchy = dataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(targetHierarchyRoot);
        
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork<TargetGroup> targetAbN = generator.deriveTargetAbstractionNetwork(
                rangeFactory, 
                (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy, 
                propertyType, 
                (Hierarchy<Concept>)(Hierarchy<?>)targetHierarchy);

        return targetAbN;
    }
}
