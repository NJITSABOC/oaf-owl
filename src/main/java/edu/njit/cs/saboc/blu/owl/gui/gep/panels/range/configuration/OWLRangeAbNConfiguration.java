package edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbNConfiguration extends TargetAbNConfiguration {

    public OWLRangeAbNConfiguration(TargetAbstractionNetwork targetAbN) {
        super(targetAbN);
    }
    
    public OAFOntologyDataManager getDataManager() {
        return ((OWLAbstractionNetwork)super.getAbstractionNetwork()).getDataManager();
    }
    
    public void setUIConfiguration(OWLRangeAbNUIConfiguration config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(OWLRangeAbNTextConfiguration config) {
        super.setTextConfiguration(config);
    }
    
    @Override
    public OWLRangeAbNUIConfiguration getUIConfiguration() {
        return (OWLRangeAbNUIConfiguration)super.getUIConfiguration();
    }
    
    @Override
    public OWLRangeAbNTextConfiguration getTextConfiguration() {
        return (OWLRangeAbNTextConfiguration)super.getTextConfiguration();
    }
}
