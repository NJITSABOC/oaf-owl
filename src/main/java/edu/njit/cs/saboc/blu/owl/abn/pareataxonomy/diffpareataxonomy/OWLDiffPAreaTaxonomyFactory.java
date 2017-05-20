package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomyConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.PropertyChangeDetailsFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomy;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyFactory extends DiffPAreaTaxonomyFactory {

    private final PAreaTaxonomy from;
    private final PAreaTaxonomy to;
    
    public OWLDiffPAreaTaxonomyFactory(PAreaTaxonomy from, PAreaTaxonomy to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy, 
            PAreaTaxonomy fromSourceTaxonomy, 
            PAreaTaxonomy toSourceTaxonomy, 
            Hierarchy<DiffPArea> pareaHierarchy) {
        
        OWLDiffAreaTaxonomy diffAreaTaxonomy = (OWLDiffAreaTaxonomy)areaTaxonomy;
        
        OWLPAreaTaxonomy fromTaxonomy = (OWLPAreaTaxonomy)fromSourceTaxonomy;
        OWLPAreaTaxonomy toTaxonomy = (OWLPAreaTaxonomy)toSourceTaxonomy;
        
        return new OWLDiffPAreaTaxonomy(diffAreaTaxonomy, fromTaxonomy, toTaxonomy, pareaHierarchy);
    }

    @Override
    public DiffAreaTaxonomy createDiffAreaTaxonomy(
            DiffPAreaTaxonomyConceptChanges ontDifferences,
            AreaTaxonomy fromSourceTaxonomy, 
            AreaTaxonomy toSourceTaxonomy, 
            Hierarchy<DiffArea> diffAreas) {
        
        OWLAreaTaxonomy fromTaxonomy = (OWLAreaTaxonomy)fromSourceTaxonomy;
        OWLAreaTaxonomy toTaxonomy = (OWLAreaTaxonomy)toSourceTaxonomy;
        
        return new OWLDiffAreaTaxonomy(this, ontDifferences, fromTaxonomy, toTaxonomy, diffAreas);
    }

    @Override
    public PropertyChangeDetailsFactory getPropertyChangeDetailsFactory() {
        return new OWLPropertyChangeDetailsFactory(from, to);
    }
}
