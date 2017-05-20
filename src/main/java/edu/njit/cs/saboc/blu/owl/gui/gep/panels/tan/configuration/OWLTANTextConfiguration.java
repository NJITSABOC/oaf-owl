package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANTextConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLTANTextConfiguration extends TANTextConfiguration {

    public OWLTANTextConfiguration(ClusterTribalAbstractionNetwork tan) {
        super(new OWLEntityNameConfiguration(), tan);
    }
}
