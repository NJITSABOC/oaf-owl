package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomy;
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
            OWLPAreaTaxonomy fromTaxonomy,
            OWLPAreaTaxonomy toTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {
        
        super(areaTaxonomy, fromTaxonomy, toTaxonomy, pareaHierarchy);
    }

    @Override
    public OWLPAreaTaxonomy getTo() {
        return (OWLPAreaTaxonomy)super.getTo();
    }

    @Override
    public OWLPAreaTaxonomy getFrom() {
        return (OWLPAreaTaxonomy)super.getFrom();
    }

    @Override
    public OWLDiffAreaTaxonomy getAreaTaxonomy() {
        return (OWLDiffAreaTaxonomy)super.getAreaTaxonomy();
    }
    
    public OAFOntologyDataManager getFromDataManager() {
        return getFrom().getDataManager();
    }
    
    public OAFOntologyDataManager getToDataManager() {
        return getTo().getDataManager();
    }

    public Set<PropertyTypeAndUsage> getFromTypesAndUsages() {
        return getFrom().getPropertyTypesAndUsages();
    }
    
    public Set<PropertyTypeAndUsage> getToTypesAndUsages() {
        return getTo().getPropertyTypesAndUsages();
    }
}
