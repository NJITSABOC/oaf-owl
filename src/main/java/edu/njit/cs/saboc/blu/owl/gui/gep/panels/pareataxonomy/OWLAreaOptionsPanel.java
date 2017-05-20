package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.CreateDisjointAbNFromPartitionNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.CreateTANFromPartitionedNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.ExportPartitionedNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.PopoutDetailsButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons.CreateDisjointSubjectSubtaxonomyButton;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.tan.OWLTANFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.listeners.DisplayDisjointTaxonomyAction;
import edu.njit.cs.saboc.blu.owl.gui.listeners.DisplayTANAction;

/**
 *
 * @author Chris O
 */
public class OWLAreaOptionsPanel extends NodeOptionsPanel<Area> {

    public OWLAreaOptionsPanel(OWLPAreaTaxonomyConfiguration config, boolean forAggregate) {

        super.addOptionButton(new CreateDisjointAbNFromPartitionNodeButton(
                config,
                new DisplayDisjointTaxonomyAction(config.getUIConfiguration().getAbNDisplayManager())));

        CreateDisjointSubjectSubtaxonomyButton createDisjointSubjectSubtaxonomyBtn
                = new CreateDisjointSubjectSubtaxonomyButton(config,
                        new DisplayDisjointTaxonomyAction(config.getUIConfiguration().getAbNDisplayManager()));
        
        super.addOptionButton(createDisjointSubjectSubtaxonomyBtn);


        OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getAbstractionNetwork();
        
        CreateTANFromPartitionedNodeButton createTANBtn = new CreateTANFromPartitionedNodeButton(
                new OWLTANFactory(owlAbN.getDataManager()),
                config,
            new DisplayTANAction(config.getUIConfiguration().getAbNDisplayManager()));
        
        super.addOptionButton(createTANBtn);

        PopoutDetailsButton popoutBtn = new PopoutDetailsButton("area", () -> {
            
            Area area = (Area)super.getCurrentNode().get();
            
            NodeDashboardPanel anp = config.getUIConfiguration().createPartitionedNodeDetailsPanel();
            anp.setContents(area);

            return anp;
        });

        super.addOptionButton(popoutBtn);
        
        
        ExportPartitionedNodeButton exportBtn = new ExportPartitionedNodeButton(config);
        
        super.addOptionButton(exportBtn);
        
        
        NodeHelpButton helpBtn = new NodeHelpButton(config);

        super.addOptionButton(helpBtn);
    }
}
