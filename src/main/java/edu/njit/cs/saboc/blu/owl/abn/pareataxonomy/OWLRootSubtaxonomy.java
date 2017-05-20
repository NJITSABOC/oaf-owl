package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.aggregate.OWLAggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLRootSubtaxonomy extends RootSubtaxonomy implements OWLTaxonomy {
    
    private final OAFOntologyDataManager dataManager;

    public OWLRootSubtaxonomy(OAFOntologyDataManager dataManager, RootSubtaxonomy taxonomy) {
        super(taxonomy.getSuperAbN(), taxonomy);
        
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
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();

        return generator.createImportSubtaxonomy(this, this.getPAreaTaxonomyFactory(), uris);
    }

    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(PArea source) {
        return new OWLAncestorSubtaxonomy(dataManager, (AncestorSubtaxonomy)super.createAncestorSubtaxonomy(source));
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(PArea root) {
        return new OWLRootSubtaxonomy(dataManager, (RootSubtaxonomy)super.createRootSubtaxonomy(root));
    }
}
