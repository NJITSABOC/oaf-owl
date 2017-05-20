package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregateAncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregateRootSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLImportSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLImportSubtaxonomyGenerator;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLAggregatePAreaTaxonomy extends AggregatePAreaTaxonomy implements OWLTaxonomy {
    
    public static final PAreaTaxonomy createAggregatedOWLPAreaTaxonomy(
            PAreaTaxonomy sourceTaxonomy, 
            OWLTaxonomy owlTaxonomy, 
            int minBound) {
        
         PAreaTaxonomy taxonomy = AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(sourceTaxonomy, minBound);
        
        if(taxonomy.isAggregated()) {
            return new OWLAggregatePAreaTaxonomy(
                    owlTaxonomy.getDataManager(),
                    owlTaxonomy.getPropertyTypesAndUsages(),
                    (AggregatePAreaTaxonomy)taxonomy);
        } else {
            return new OWLPAreaTaxonomy(owlTaxonomy.getDataManager(), sourceTaxonomy);
        }
    }

    private final OAFOntologyDataManager dataManager;
    private final Set<PropertyTypeAndUsage> typesAndUsages;
 
    public OWLAggregatePAreaTaxonomy(
            OAFOntologyDataManager dataManager, 
            Set<PropertyTypeAndUsage> typesAndUsages,
            AggregatePAreaTaxonomy taxonomy) {
        
        super(taxonomy.getNonAggregateSourceAbN(), 
                taxonomy.getAggregateBound(),
                taxonomy);
        
        this.dataManager = dataManager;
        this.typesAndUsages = typesAndUsages;
    }
    
    @Override
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }

    @Override
    public Set<PropertyTypeAndUsage> getPropertyTypesAndUsages() {
        return typesAndUsages;
    }
    
    @Override
    public OWLAreaTaxonomy getAreaTaxonomy() {
        return (OWLAreaTaxonomy)super.getAreaTaxonomy();
    }
    
    @Override
    public PAreaTaxonomy getAggregated(int smallestNode) {
        return OWLAggregatePAreaTaxonomy.createAggregatedOWLPAreaTaxonomy(this.getNonAggregateSourceAbN(), this, smallestNode);
    }

    @Override
    public OWLExpandedSubtaxonomy expandAggregateNode(AggregatePArea parea) {
        return new OWLExpandedSubtaxonomy(dataManager, (ExpandedSubtaxonomy)super.expandAggregateNode(parea));
    }
    
    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();

        return generator.createImportSubtaxonomy(this.getNonAggregateSourceAbN(), this.getPAreaTaxonomyFactory(), uris);
    }

    @Override
    public OWLAggregateAncestorSubtaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        
        PAreaTaxonomy aggregateSource = AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
        
        return new OWLAggregateAncestorSubtaxonomy(
                dataManager, 
                this.getPropertyTypesAndUsages(),
                (AggregateAncestorSubtaxonomy)aggregateSource); 
    }
    
    @Override
    public OWLAggregateRootSubtaxonomy createRootSubtaxonomy(AggregatePArea root) {
        
        PAreaTaxonomy aggregateSource = AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this.getNonAggregateSourceAbN(), this, root);
        
        return new OWLAggregateRootSubtaxonomy(dataManager, 
                this.getPropertyTypesAndUsages(),
                (AggregateRootSubtaxonomy)aggregateSource);
    }
}
