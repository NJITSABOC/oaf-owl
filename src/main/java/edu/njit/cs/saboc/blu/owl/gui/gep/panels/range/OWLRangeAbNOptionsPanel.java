package edu.njit.cs.saboc.blu.owl.gui.gep.panels.range;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.PopoutAbNDetailsButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.SavePNGButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons.TargetAbNHelpButton;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration.OWLRangeAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLRangeAbNOptionsPanel extends AbNOptionsPanel<TargetAbstractionNetwork> {
    
    public OWLRangeAbNOptionsPanel(OWLRangeAbNConfiguration config) {
        
        TargetAbNHelpButton helpBtn = new TargetAbNHelpButton(config);
        ExportAbNButton exportBtn = new ExportAbNButton("Export Range Abstraction Network", config);
        SavePNGButton pngBtn = new SavePNGButton(config);
        PopoutAbNDetailsButton popoutBtn = new PopoutAbNDetailsButton(config);
        
        helpBtn.setCurrentAbN(config.getAbstractionNetwork());
        exportBtn.setCurrentAbN(config.getAbstractionNetwork());
        pngBtn.setCurrentAbN(config.getAbstractionNetwork());
        popoutBtn.setCurrentEntity(config.getAbstractionNetwork());
        
        super.addOptionButton(helpBtn);
        super.addOptionButton(exportBtn);
        super.addOptionButton(pngBtn);
        super.addOptionButton(popoutBtn);
    }
}