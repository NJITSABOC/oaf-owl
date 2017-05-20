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
    
    private final Hierarchy<OWLConcept> sourceHierarchy;
    private final Hierarchy<OWLConcept> targetHierarchy;
    
    private final OWLInheritableProperty propertyType;
    
    private final OAFOntologyDataManager dataManager;

    public CreateAndDisplayRangeAbN(
            String displayText, 
            OWLAbNFrameManager displayListener,
            Hierarchy<OWLConcept> sourceHierarchy, 
            OWLInheritableProperty propertyType, 
            Hierarchy<OWLConcept> targetHierarchy,
            OAFOntologyDataManager dataManager) {
        
        super(displayText, displayListener);
        
        this.sourceHierarchy = sourceHierarchy;
        this.targetHierarchy = targetHierarchy;
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
                sourceHierarchy, 
                propertyType, 
                targetHierarchy);
        
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork<TargetGroup> targetAbN = generator.deriveTargetAbstractionNetwork(
                rangeFactory, 
                (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy, 
                propertyType, 
                (Hierarchy<Concept>)(Hierarchy<?>)targetHierarchy);

        return targetAbN;
    }
}
