package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.CreateDisjointAbNFromPartitionNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.CreateTANFromPartitionedNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.ExportPartitionedNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.PopoutDetailsButton;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.tan.OWLTANFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.listeners.DisplayDisjointTANAction;
import edu.njit.cs.saboc.blu.owl.gui.listeners.DisplayTANAction;

/**
 *
 * @author Chris O
 */
public class OWLBandOptionsPanel extends NodeOptionsPanel {


    public OWLBandOptionsPanel(OWLTANConfiguration config, boolean forAggregate) {

        OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getTribalAbstractionNetwork();
        
        super.addOptionButton(new CreateDisjointAbNFromPartitionNodeButton(
                config,
                new DisplayDisjointTANAction(config.getUIConfiguration().getAbNDisplayManager())));
        
        
        CreateTANFromPartitionedNodeButton tanBtn = new CreateTANFromPartitionedNodeButton(
                new OWLTANFactory(owlAbN.getDataManager()),
                config, 
            new DisplayTANAction(config.getUIConfiguration().getAbNDisplayManager()));
        
        super.addOptionButton(tanBtn);
        
        ExportPartitionedNodeButton exportBtn = new ExportPartitionedNodeButton(config);
        
        super.addOptionButton(exportBtn);
        
        PopoutDetailsButton popoutBtn = new PopoutDetailsButton("band", () -> {
            Band band = (Band)super.getCurrentNode().get();
            
            NodeDashboardPanel anp = config.getUIConfiguration().createPartitionedNodeDetailsPanel();
            anp.setContents(band);

            return anp;
        });

        super.addOptionButton(popoutBtn);
        
        
        NodeHelpButton helpBtn = new NodeHelpButton(config);

        super.addOptionButton(helpBtn);
    }
}
