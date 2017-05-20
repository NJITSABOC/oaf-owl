package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.AbNCreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy.OWLDiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy.OWLDiffPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class CreateAndDisplayOWLDiffPAreaTaxonomy extends AbNCreateAndDisplayDialog<DiffPAreaTaxonomy> {

    private final OWLConcept selectedRoot;
    
    private final OAFOntologyDataManager fromDataManager;
    
    private final OAFOntologyDataManager toDataManager;
    
    private final Set<PropertyTypeAndUsage> typesAndUsages;
    
    private final Set<OWLInheritableProperty> availableProperties;
    private final Set<OWLInheritableProperty> selectedProperties;

    public CreateAndDisplayOWLDiffPAreaTaxonomy(String text, 
            OWLConcept selectedRoot, 
            Set<PropertyTypeAndUsage> typesAndUsages,
            Set<OWLInheritableProperty> availableProperties,
            Set<OWLInheritableProperty> selectedProperties,
            OWLAbNFrameManager displayFrameListener, 
            OAFOntologyDataManager fromDataManager, 
            OAFOntologyDataManager toDataManager) {
        
        super(text, displayFrameListener);
        
        this.selectedRoot = selectedRoot;
        
        this.fromDataManager = fromDataManager;
        
        this.toDataManager = toDataManager;
        
        this.typesAndUsages = typesAndUsages;
        this.availableProperties = availableProperties;
        this.selectedProperties = selectedProperties;
        
    }

    @Override
    protected void display(DiffPAreaTaxonomy taxonomy) {
        super.getDisplayFrameListener().displayDiffPAreaTaxonomy(taxonomy);
    }

    @Override
    protected DiffPAreaTaxonomy create() {
        PAreaTaxonomyGenerator taxonomyGenerator = new PAreaTaxonomyGenerator();
        
        Hierarchy<OWLConcept> fromHierarchy = fromDataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(selectedRoot);
        
        OWLPAreaTaxonomyFactory fromFactory = new OWLPAreaTaxonomyFactory(fromDataManager, typesAndUsages);
        
        PAreaTaxonomy fromTaxonomy = taxonomyGenerator.derivePAreaTaxonomy(fromFactory, fromHierarchy);
        
        if(!availableProperties.equals(selectedProperties)) {
            fromTaxonomy = fromTaxonomy.getRelationshipSubtaxonomy(selectedProperties);
        }
        
        Hierarchy<OWLConcept> toHierarchy = toDataManager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(selectedRoot);
        
        OWLPAreaTaxonomyFactory toFactory = new OWLPAreaTaxonomyFactory(toDataManager, typesAndUsages);

        PAreaTaxonomy toTaxonomy = taxonomyGenerator.derivePAreaTaxonomy(toFactory, toHierarchy);

        if (!availableProperties.equals(selectedProperties)) {
            toTaxonomy = toTaxonomy.getRelationshipSubtaxonomy(selectedProperties);
        }
        
        DiffPAreaTaxonomyGenerator diffTaxonomyGenerator = new DiffPAreaTaxonomyGenerator();

        OWLDiffPAreaTaxonomy diffTaxonomy
                = (OWLDiffPAreaTaxonomy) diffTaxonomyGenerator.createDiffPAreaTaxonomy(
                        new OWLDiffPAreaTaxonomyFactory(fromTaxonomy, toTaxonomy),
                        fromDataManager.getOntology(),
                        fromTaxonomy,
                        toDataManager.getOntology(),
                        toTaxonomy);
        
        return diffTaxonomy;
    }
}