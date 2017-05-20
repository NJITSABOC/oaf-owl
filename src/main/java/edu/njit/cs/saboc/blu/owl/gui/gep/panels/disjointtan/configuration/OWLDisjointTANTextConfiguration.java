package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan.DisjointTANTextConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLDisjointTANTextConfiguration extends DisjointTANTextConfiguration {

    public OWLDisjointTANTextConfiguration(
            DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN) {
        
        super(new OWLEntityNameConfiguration(), disjointTAN);
    }
}
