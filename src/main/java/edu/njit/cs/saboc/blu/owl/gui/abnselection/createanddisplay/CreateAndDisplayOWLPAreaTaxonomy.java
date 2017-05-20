package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.AbNCreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Kevyn
 */
public class CreateAndDisplayOWLPAreaTaxonomy extends AbNCreateAndDisplayDialog<PAreaTaxonomy> {

    private final OWLConcept selectedRoot;
    
    private final OAFOntologyDataManager dataManager;
    private final Set<PropertyTypeAndUsage> typesAndUsages;
    
    private final Set<OWLInheritableProperty> availableProperties;
    private final Set<OWLInheritableProperty> selectedProperties;

    public CreateAndDisplayOWLPAreaTaxonomy(String text, 
            OWLConcept selectedRoot, 
            Set<PropertyTypeAndUsage> typesAndUsages,
            Set<OWLInheritableProperty> availableProperties,
            Set<OWLInheritableProperty> selectedProperties,
            OWLAbNFrameManager displayFrameListener, 
            OAFOntologyDataManager dataManager) {
        
        super(text, displayFrameListener);
        
        this.selectedRoot = selectedRoot;
        
        this.dataManager = dataManager;
        this.typesAndUsages = typesAndUsages;
        this.availableProperties = availableProperties;
        this.selectedProperties = selectedProperties;
    }

    @Override
    protected void display(PAreaTaxonomy taxonomy) {
        super.getDisplayFrameListener().displayPAreaTaxonomy(taxonomy);
    }

    @Override
    protected PAreaTaxonomy create() {
        Hierarchy<OWLConcept> conceptHierarchy = dataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(selectedRoot);
        
        OWLPAreaTaxonomyFactory factory = new OWLPAreaTaxonomyFactory(dataManager, typesAndUsages);

        PAreaTaxonomyGenerator taxonomyGenerator = new PAreaTaxonomyGenerator();
        PAreaTaxonomy taxonomy = taxonomyGenerator.derivePAreaTaxonomy(factory, conceptHierarchy);
        
        if(availableProperties.equals(selectedProperties)) {
            return taxonomy;
        } else {
            return taxonomy.getRelationshipSubtaxonomy(selectedProperties);
        }
    }
}
