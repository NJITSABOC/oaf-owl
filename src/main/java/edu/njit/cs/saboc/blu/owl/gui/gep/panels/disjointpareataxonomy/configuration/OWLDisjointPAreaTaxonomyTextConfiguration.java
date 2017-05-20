package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy.DisjointPAreaTaxonomyTextConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLDisjointPAreaTaxonomyTextConfiguration extends DisjointPAreaTaxonomyTextConfiguration {

    public OWLDisjointPAreaTaxonomyTextConfiguration(DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy) {
        super(new OWLEntityNameConfiguration(), disjointTaxonomy);
    }
}
