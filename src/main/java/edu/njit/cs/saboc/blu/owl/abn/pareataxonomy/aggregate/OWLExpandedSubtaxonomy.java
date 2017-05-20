package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.aggregate;



import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLAncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLImportSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLImportSubtaxonomyGenerator;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLRootSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLExpandedSubtaxonomy extends ExpandedSubtaxonomy implements OWLTaxonomy {

    private final OAFOntologyDataManager dataManager;

    public OWLExpandedSubtaxonomy(
            OAFOntologyDataManager dataManager, 
            ExpandedSubtaxonomy taxonomy) {
        
        super(taxonomy.getSuperAbN(), 
                taxonomy.getExpandedAggregatePArea(),
                taxonomy);
        
        this.dataManager = dataManager;
    }
    
    @Override
    public OWLAreaTaxonomy getAreaTaxonomy() {
        return (OWLAreaTaxonomy)super.getAreaTaxonomy();
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    @Override
    public Set<PropertyTypeAndUsage> getPropertyTypesAndUsages() {
        return getAreaTaxonomy().getPropertyTypesAndUsages();
    }
    
    @Override
    public PAreaTaxonomy getAggregated(int smallestNode) {
        return OWLAggregatePAreaTaxonomy.createAggregatedOWLPAreaTaxonomy(this, this, smallestNode);
    }
    
    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(PArea source) {
        return new OWLAncestorSubtaxonomy(dataManager, (AncestorSubtaxonomy)super.createAncestorSubtaxonomy(source));
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(PArea root) {
        return new OWLRootSubtaxonomy(dataManager, (RootSubtaxonomy)super.createRootSubtaxonomy(root));
    }
    
    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();

        return generator.createImportSubtaxonomy(this, this.getPAreaTaxonomyFactory(), uris);
    }
}