package edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNTextConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbNTextConfiguration extends TargetAbNTextConfiguration {

    public OWLRangeAbNTextConfiguration(TargetAbstractionNetwork targetAbN) {
        super(new OWLEntityNameConfiguration(), targetAbN);
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Range Abstraction Networks";
        } else {
            return "Range Abstraction Network";
        }
    }
    
}
