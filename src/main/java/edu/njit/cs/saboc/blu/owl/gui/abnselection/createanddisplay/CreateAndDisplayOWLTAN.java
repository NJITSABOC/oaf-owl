package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.AbNCreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.tan.OWLClusterTAN;
import edu.njit.cs.saboc.blu.owl.abn.tan.OWLTANFactory;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Set;

/**
 *
 * @author Kevyn
 */
public class CreateAndDisplayOWLTAN extends AbNCreateAndDisplayDialog<ClusterTribalAbstractionNetwork> {

    private final OAFOntologyDataManager dataManager;
    
    private final Set<OWLConcept> roots;

    public CreateAndDisplayOWLTAN(
            String text, 
            Set<OWLConcept> roots,
            OWLAbNFrameManager displayFrameListener, 
            OAFOntologyDataManager dataManager) {
        
        super(text, displayFrameListener);

        this.dataManager = dataManager;
        
        this.roots = roots;
    }

    @Override
    protected void display(ClusterTribalAbstractionNetwork abn) {
        super.getDisplayFrameListener().displayTribalAbstractionNetwork((OWLClusterTAN) abn);
    }

    @Override
    protected ClusterTribalAbstractionNetwork create() {
        Hierarchy<OWLConcept> conceptHierarchy = 
                dataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(roots);

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.deriveTANFromMultiRootedHierarchy(conceptHierarchy, new OWLTANFactory(dataManager));
    }
}
