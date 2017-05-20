
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;

/**
 *
 * @author Chris O
 */
public class OWLDisjointTANConfigurationFactory {
    
    public OWLDisjointTANConfiguration createConfiguration(
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN, 
            AbNDisplayManager displayListener,
            OWLAbNFrameManager frameManager) {
        
        OWLDisjointTANConfiguration disjointConfiguration = new OWLDisjointTANConfiguration(disjointTAN);
        disjointConfiguration.setUIConfiguration(new OWLDisjointTANUIConfiguration(disjointConfiguration, displayListener, frameManager));
        disjointConfiguration.setTextConfiguration(new OWLDisjointTANTextConfiguration(disjointTAN));
        
        return disjointConfiguration;
    }
}
