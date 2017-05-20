package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyFactory extends PAreaTaxonomyFactory {
    
    private final OAFOntologyDataManager manager;

    private final Map<OWLConcept, Set<OWLInheritableProperty>> inferredProperties;
    
    private final Set<PropertyTypeAndUsage> propertyTypesAndUsages;
 
    public OWLPAreaTaxonomyFactory(
            OAFOntologyDataManager manager,
            Set<PropertyTypeAndUsage> propertyTypesAndUsages) {
        
        super(manager.getOntology());

        this.manager = manager;
        this.propertyTypesAndUsages = propertyTypesAndUsages;
        
        this.inferredProperties = manager.getOntologyInferredProperties(propertyTypesAndUsages);
    }
    
    @Override
    public Set<InheritableProperty> getRelationships(Concept c) {
        return (Set<InheritableProperty>)(Set<?>)inferredProperties.get((OWLConcept)c);
    }

    @Override
    public <T extends PArea> PAreaTaxonomy createPAreaTaxonomy(AreaTaxonomy areaTaxonomy, Hierarchy<T> pareaHierarchy, Hierarchy<Concept> conceptHierarchy) {
        OWLAreaTaxonomy owlAreaTaxonomy = (OWLAreaTaxonomy) areaTaxonomy;

        return new OWLPAreaTaxonomy(manager, owlAreaTaxonomy, pareaHierarchy, conceptHierarchy);
    }

   
    @Override
    public AreaTaxonomy createAreaTaxonomy(Hierarchy<Area> areaHierarchy, Hierarchy<Concept> sourceHierarchy) {
        return new OWLAreaTaxonomy(this, manager, propertyTypesAndUsages, areaHierarchy, sourceHierarchy);
    }
}
