package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomy extends DiffPAreaTaxonomy {
    
    public OWLDiffPAreaTaxonomy(
            OWLDiffAreaTaxonomy areaTaxonomy,
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {
        
        super(areaTaxonomy, fromTaxonomy, toTaxonomy, pareaHierarchy);
    }

    public OWLTaxonomy getToOWLTaxonomy() {
        return (OWLTaxonomy)super.getTo();
    }

    public OWLTaxonomy getFromOWLTaxonomy() {
        return (OWLTaxonomy)super.getFrom();
    }

    @Override
    public OWLDiffAreaTaxonomy getAreaTaxonomy() {
        return (OWLDiffAreaTaxonomy)super.getAreaTaxonomy();
    }
    
    public OAFOntologyDataManager getFromDataManager() {
        return getFromOWLTaxonomy().getDataManager();
    }
    
    public OAFOntologyDataManager getToDataManager() {
        return getToOWLTaxonomy().getDataManager();
    }

    public Set<PropertyTypeAndUsage> getFromTypesAndUsages() {
        return getFromOWLTaxonomy().getPropertyTypesAndUsages();
    }
    
    public Set<PropertyTypeAndUsage> getToTypesAndUsages() {
        return getToOWLTaxonomy().getPropertyTypesAndUsages();
    }
}
