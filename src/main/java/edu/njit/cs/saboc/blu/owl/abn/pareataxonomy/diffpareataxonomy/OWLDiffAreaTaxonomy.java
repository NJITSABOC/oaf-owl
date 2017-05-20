package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomyConceptChanges;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLDiffAreaTaxonomy extends DiffAreaTaxonomy {
    
    public OWLDiffAreaTaxonomy(
            OWLDiffPAreaTaxonomyFactory diffFactory,
            DiffPAreaTaxonomyConceptChanges ontDifferences,
            OWLAreaTaxonomy fromAreaTaxonomy,
            OWLAreaTaxonomy toAreaTaxonomy,
            Hierarchy<DiffArea> areaHierarchy) {
        
        super(diffFactory, ontDifferences, fromAreaTaxonomy, toAreaTaxonomy, areaHierarchy);
    }

    @Override
    public OWLAreaTaxonomy getTo() {
        return (OWLAreaTaxonomy)super.getTo();
    }

    @Override
    public OWLAreaTaxonomy getFrom() {
        return (OWLAreaTaxonomy)super.getFrom();
    }

    @Override
    public OWLDiffPAreaTaxonomyFactory getDiffFactory() {
        return (OWLDiffPAreaTaxonomyFactory)super.getDiffFactory();
    }
    
    public Set<PropertyTypeAndUsage> getFromTypesAndUsages() {
        return getFrom().getPropertyTypesAndUsages();
    }
    
    public Set<PropertyTypeAndUsage> getToTypesAndUsages() {
        return getTo().getPropertyTypesAndUsages();
    }
    
    public OAFOntologyDataManager getFromDataManager() {
        return getFrom().getDataManager();
    }
    
    public OAFOntologyDataManager getToDataManager() {
        return getTo().getDataManager();
    }
}
