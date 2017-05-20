package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyConfigurationFactory {
    
    public OWLPAreaTaxonomyConfiguration createConfiguration(
            PAreaTaxonomy taxonomy, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager,
            boolean showingAreaTaxonomy) {
        
        OWLPAreaTaxonomyConfiguration pareaTaxonomyConfiguration = new OWLPAreaTaxonomyConfiguration(taxonomy);
        
        pareaTaxonomyConfiguration.setUIConfiguration(
                new OWLPAreaTaxonomyUIConfiguration(
                        pareaTaxonomyConfiguration, 
                        displayListener, 
                        frameManager, 
                        showingAreaTaxonomy));
        
        pareaTaxonomyConfiguration.setTextConfiguration(new OWLPAreaTaxonomyTextConfiguration(taxonomy));
        
        return pareaTaxonomyConfiguration;
    }
}
