package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLAreaTaxonomy extends AreaTaxonomy implements OWLTaxonomy {
    
    private final OAFOntologyDataManager dataManager;
    
    private final Set<PropertyTypeAndUsage> propertyUsages;
    
    public OWLAreaTaxonomy(
            OWLPAreaTaxonomyFactory factory, 
            OAFOntologyDataManager dataManager,
            Set<PropertyTypeAndUsage> propertyUsages, 
            Hierarchy<Area> areaHierarchy, 
            Hierarchy<Concept> concepts) {
        
        super(factory, areaHierarchy, concepts);
        
        this.dataManager = dataManager;
        
        this.propertyUsages = propertyUsages;
    }
    
    public OWLPAreaTaxonomyFactory getPAreaTaxonomyFactory() {
        return (OWLPAreaTaxonomyFactory)super.getPAreaTaxonomyFactory();
    }
    
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    public Set<PropertyTypeAndUsage> getPropertyTypesAndUsages() {
        return propertyUsages;
    }

    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> selectedURIs) {
        throw new UnsupportedOperationException("Not supported for area taxonomies yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
