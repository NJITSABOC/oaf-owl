package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.aggregate.OWLAggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RelationshipSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLRelationshipSubtaxonomy<T extends PArea> extends RelationshipSubtaxonomy<T> implements OWLTaxonomy {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLRelationshipSubtaxonomy(
            OAFOntologyDataManager dataManager, 
            RelationshipSubtaxonomy subtaxonomy) {
        
        super(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAllowedProperties(),
                subtaxonomy);
                
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
    public PAreaTaxonomy getAggregated(int smallestNode, boolean isWeighteAggregated) {
        return OWLAggregatePAreaTaxonomy.createAggregatedOWLPAreaTaxonomy(this, this, smallestNode, isWeighteAggregated);
    }

    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(T source) {
        return new OWLAncestorSubtaxonomy(dataManager, (AncestorSubtaxonomy)super.createAncestorSubtaxonomy(source)); 
    }

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(T root) {
        return new OWLRootSubtaxonomy(dataManager, (RootSubtaxonomy)super.createRootSubtaxonomy(root));
    }
    
    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();
        
        return generator.createImportSubtaxonomy(this.getSuperAbN(), this.getPAreaTaxonomyFactory(), uris);
    }
}
