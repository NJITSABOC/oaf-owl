package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyConfigurationFactory {
    
    public OWLDiffPAreaTaxonomyConfiguration createConfiguration(
            DiffPAreaTaxonomy taxonomy, 
            AbNDisplayManager displayListener) {
        
        OWLDiffPAreaTaxonomyConfiguration pareaTaxonomyConfiguration = new OWLDiffPAreaTaxonomyConfiguration(taxonomy);
        pareaTaxonomyConfiguration.setUIConfiguration(new OWLDiffPAreaTaxonomyUIConfiguration(pareaTaxonomyConfiguration, displayListener));
        pareaTaxonomyConfiguration.setTextConfiguration(new OWLDiffPAreaTaxonomyTextConfiguration(taxonomy));
        
        return pareaTaxonomyConfiguration;
    }
}
