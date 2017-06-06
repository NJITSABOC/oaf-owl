package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class OWLAbNSelectionPanel extends JPanel {
    
    public OWLAbNSelectionPanel(OWLAbNFrameManager displayFrameListener) {
        
        super(new BorderLayout());

        this.add(
                new OWLAbNCreationPanel(displayFrameListener), 
                BorderLayout.CENTER);
    }
}
