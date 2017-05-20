package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyConfiguration extends PAreaTaxonomyConfiguration {

    public OWLPAreaTaxonomyConfiguration(PAreaTaxonomy taxonomy) {
        super(taxonomy);
    }
    
    public OWLTaxonomy getTaxonomy() {
        return (OWLTaxonomy)super.getAbstractionNetwork();
    }
  
    public void setUIConfiguration(OWLPAreaTaxonomyUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(OWLPAreaTaxonomyTextConfiguration config) {
        super.setTextConfiguration(config);
    }
    
    public OWLPAreaTaxonomyUIConfiguration getUIConfiguration() {
        return (OWLPAreaTaxonomyUIConfiguration)super.getUIConfiguration();
    }
    
    public OWLPAreaTaxonomyTextConfiguration getTextConfiguration() {
        return (OWLPAreaTaxonomyTextConfiguration)super.getTextConfiguration();
    }
}
