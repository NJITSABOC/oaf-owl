package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyConfiguration extends DiffPAreaTaxonomyConfiguration {

    public OWLDiffPAreaTaxonomyConfiguration(DiffPAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
  
    public void setUIConfiguration(OWLDiffPAreaTaxonomyUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(OWLDiffPAreaTaxonomyTextConfiguration config) {
        super.setTextConfiguration(config);
    }
    
    public OWLDiffPAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (OWLDiffPAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }
    
    public OWLDiffPAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (OWLDiffPAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }
}
