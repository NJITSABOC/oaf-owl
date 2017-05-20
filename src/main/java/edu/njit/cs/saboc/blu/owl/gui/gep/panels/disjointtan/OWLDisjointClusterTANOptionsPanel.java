package edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.AbNHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.PopoutAbNDetailsButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.SavePNGButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ShowSourceHierarchyButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ViewDisjointAbNSubsetButton;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration.OWLDisjointTANConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfigurationFactory;

/**
 *
 * @author Chris O
 */
public class OWLDisjointClusterTANOptionsPanel extends AbNOptionsPanel<DisjointAbstractionNetwork> {
    
    public OWLDisjointClusterTANOptionsPanel(OWLDisjointTANConfiguration config) {
        
        AbNHelpButton helpBtn = new AbNHelpButton(config);
        ExportAbNButton exportBtn = new ExportAbNButton("Export Disjoint Cluster TAN", config);
        SavePNGButton pngBtn = new SavePNGButton(config);
        PopoutAbNDetailsButton popoutBtn = new PopoutAbNDetailsButton(config);
        
        ClusterTribalAbstractionNetwork tan = (ClusterTribalAbstractionNetwork)config.getAbstractionNetwork().getParentAbstractionNetwork();
                
        OWLTANConfiguration parentConfig = new OWLTANConfigurationFactory().createConfiguration(
                                tan, 
                                config.getUIConfiguration().getAbNDisplayManager(), 
                                config.getUIConfiguration().getFrameManager(),
                                false);
        
        ViewDisjointAbNSubsetButton viewSubsetBtn = new ViewDisjointAbNSubsetButton(config,  
                (disjointAbN) -> {
                    config.getUIConfiguration().getAbNDisplayManager().displayDisjointPAreaTaxonomy(disjointAbN);
                },
                parentConfig);
        
                
        ShowSourceHierarchyButton showHierarchyBtn = new ShowSourceHierarchyButton();

        showHierarchyBtn.setCurrentAbN(config.getAbstractionNetwork());
        viewSubsetBtn.setCurrentAbN(config.getAbstractionNetwork());
        helpBtn.setCurrentAbN(config.getAbstractionNetwork());
        exportBtn.setCurrentAbN(config.getAbstractionNetwork());
        pngBtn.setCurrentAbN(config.getAbstractionNetwork());
        popoutBtn.setCurrentEntity(config.getAbstractionNetwork());
        
        super.addOptionButton(showHierarchyBtn);
        super.addOptionButton(viewSubsetBtn);
        super.addOptionButton(helpBtn);
        super.addOptionButton(exportBtn);
        super.addOptionButton(pngBtn);
        super.addOptionButton(popoutBtn);
    }
}