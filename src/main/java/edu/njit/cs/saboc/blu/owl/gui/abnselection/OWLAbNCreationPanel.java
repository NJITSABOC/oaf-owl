package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFStateFileManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OntologyManagementPanel.OntologyManagementListener;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLNAT;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class OWLAbNCreationPanel extends JPanel {
    
    private final OAFStateFileManager stateFileManager = new OAFStateFileManager("BLUOWL");
    
    private final BLUOntologyManager ontologyManager;
    
    private final OntologyManagementPanel ontologySelectionPanel;

    private final OWLAbNFrameManager frameManager;
    
    private final OWLAbNWizardPanel wizardPanel;
    
    private JButton openBrowserBtn;

    public OWLAbNCreationPanel(OWLAbNFrameManager frameManager) {
        
        super(new BorderLayout());
        
        this.frameManager = frameManager;
        
        this.ontologyManager = new BLUOntologyManager(stateFileManager);
        
        ontologySelectionPanel = new OntologyManagementPanel(ontologyManager);
        
        wizardPanel = new OWLAbNWizardPanel(stateFileManager, frameManager, true);
        wizardPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        
        ontologySelectionPanel.addOntologySelectionListener(new OntologyManagementListener() {
            
            @Override
            public void ontologySelected(OAFOntologyDataManager dataManager) {
               
                wizardPanel.setCurrentDataManager(dataManager);
                wizardPanel.setEnabled(true);
                
                openBrowserBtn.setEnabled(true);
            }
            
            @Override
            public void ontologyUnselected() {

                wizardPanel.clear();
                wizardPanel.setEnabled(false);
                
                openBrowserBtn.setEnabled(false);
            }
            
            @Override
            public void ontologyLoading() {
                
                wizardPanel.resetView();
                wizardPanel.setEnabled(false);
                               
                openBrowserBtn.setEnabled(false);
            }
            
            @Override
            public void ontologyLoaded(OAFOntologyDataManager loader) {
                
            }
        });

        this.add(ontologySelectionPanel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
                
        JPanel browserPanel = new JPanel(new BorderLayout());
        browserPanel.add(Box.createHorizontalStrut(10), BorderLayout.CENTER);
        browserPanel.add(createBrowserPanel(), BorderLayout.EAST);
                
        centerPanel.add(wizardPanel, BorderLayout.CENTER);
        centerPanel.add(browserPanel, BorderLayout.EAST);

        this.add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel createBrowserPanel() {
        
        
        JPanel browserPanel = new JPanel(new BorderLayout());
        browserPanel.setPreferredSize(new Dimension(200, -1));
        
        browserPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLUE, 2),
                "Class Browser"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        openBrowserBtn = new JButton("<html><div align='center'>Open<br>Class<br>Browser");

        openBrowserBtn.addActionListener((ae) -> {
            if (ontologySelectionPanel.getSelectedOntology().isPresent()) {
                CreateAndDisplayOWLNAT createAndDisplay = new CreateAndDisplayOWLNAT(
                        frameManager,
                        ontologySelectionPanel.getSelectedOntology().get());

                createAndDisplay.run();
            }
        });
        
        openBrowserBtn.setEnabled(false);

        topPanel.add(openBrowserBtn, BorderLayout.CENTER);
        topPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JEditorPane detailsPane = new JEditorPane();
        detailsPane.setContentType("text/html");

        String detailsString = "<html><div align='justify'>The OAF OWL "
                + "class browser allows you to browse individual classes and their "
                + "restrictions. ";

        detailsPane.setText(detailsString);

        bottomPanel.add(detailsPane, BorderLayout.CENTER);

        browserPanel.add(topPanel, BorderLayout.NORTH);
        browserPanel.add(bottomPanel, BorderLayout.CENTER);

        return browserPanel;
    }
}
