
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLDisjointPAreaTaxonomyConfigurationFactory {
    public OWLDisjointPAreaTaxonomyConfiguration createConfiguration(
            DisjointAbstractionNetwork<DisjointNode<PArea>, PAreaTaxonomy<PArea>, PArea> disjointTaxonomy, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager) {
        
        OWLDisjointPAreaTaxonomyConfiguration disjointConfiguration = new OWLDisjointPAreaTaxonomyConfiguration(disjointTaxonomy);
        disjointConfiguration.setUIConfiguration(new OWLDisjointPAreaTaxonomyUIConfiguration(disjointConfiguration, displayListener, frameManager));
        disjointConfiguration.setTextConfiguration(new OWLDisjointPAreaTaxonomyTextConfiguration(disjointTaxonomy));
        
        return disjointConfiguration;
    }
}
