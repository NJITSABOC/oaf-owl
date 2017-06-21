package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.aggregate.OWLAggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RelationshipSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance.OWLPAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OWLPAreaTaxonomy<T extends PArea> extends PAreaTaxonomy<T> implements OWLTaxonomy {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLPAreaTaxonomy(
            OAFOntologyDataManager dataManager,
            OWLAreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy,
            Hierarchy<Concept> concepts,
            PAreaTaxonomyDerivation derivation) {

        super(areaTaxonomy, 
                pareaHierarchy, 
                concepts, 
                derivation);
        
        this.dataManager = dataManager;
    }
    
    public OWLPAreaTaxonomy(OAFOntologyDataManager dataManager,
            OWLAreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy,
            Hierarchy<Concept> concepts) {

        this(dataManager,
                areaTaxonomy, 
                pareaHierarchy, 
                concepts, 
                new OWLPAreaTaxonomyDerivation(
                        (SimplePAreaTaxonomyDerivation)areaTaxonomy.getDerivation(), 
                        areaTaxonomy.getPropertyTypesAndUsages()));
    }
    
    public OWLPAreaTaxonomy(OAFOntologyDataManager dataManager, PAreaTaxonomy taxonomy) {
        this(dataManager, 
                (OWLAreaTaxonomy)taxonomy.getAreaTaxonomy(),
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy());
    }
    
    public OWLPAreaTaxonomy(
            OAFOntologyDataManager dataManager, 
            PAreaTaxonomy taxonomy, 
            PAreaTaxonomyDerivation derivation) {
        
        this(dataManager, 
                (OWLAreaTaxonomy)taxonomy.getAreaTaxonomy(),
                taxonomy.getPAreaHierarchy(), 
                taxonomy.getSourceHierarchy(),
                derivation);
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
    public OWLAreaTaxonomy getAreaTaxonomy() {
        return (OWLAreaTaxonomy)super.getAreaTaxonomy();
    }

    @Override
    public PAreaTaxonomy getAggregated(int smallestNode, boolean isWeighteAggregated) {
        return OWLAggregatePAreaTaxonomy.createAggregatedOWLPAreaTaxonomy(this, this, new AggregatedProperty(smallestNode, isWeighteAggregated));
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
    public PAreaTaxonomy getRelationshipSubtaxonomy(Set<InheritableProperty> allowedRelTypes) {
        
        PAreaTaxonomy taxonomy = super.getRelationshipSubtaxonomy(allowedRelTypes);
        
        if(taxonomy instanceof RelationshipSubtaxonomy) {
            return new OWLRelationshipSubtaxonomy(dataManager, (RelationshipSubtaxonomy)taxonomy);
        } else {
            return new OWLPAreaTaxonomy(dataManager, taxonomy);
        }
    }
    
    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();
        
        return generator.createImportSubtaxonomy(this, this.getPAreaTaxonomyFactory(), uris);
    }
}
