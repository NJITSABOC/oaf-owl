package edu.njit.cs.saboc.blu.owl.gui.gep.panels.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.OpenBrowserButton;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.listeners.OWLDisplayNATListener;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class OWLOpenBrowserButton<T extends SinglyRootedNode> extends OpenBrowserButton<T> {
    
    private final OAFOntologyDataManager dataManager;
    private final OWLAbNFrameManager frameManager;
    
    public OWLOpenBrowserButton(
            AbNConfiguration config, 
            OAFOntologyDataManager dataManager,
            OWLAbNFrameManager frameManager) {
        
        super(config.getTextConfiguration().getNodeTypeName(false));
        
        this.dataManager = dataManager;
        this.frameManager = frameManager;
    }

    @Override
    public void displayBrowserWindowAction() {
        
        OWLDisplayNATListener displayNATListener = new OWLDisplayNATListener(
                frameManager, 
                dataManager);
        
        displayNATListener.entityDoubleClicked(this.getCurrentEntity().get().getRoot());
        
    }
}