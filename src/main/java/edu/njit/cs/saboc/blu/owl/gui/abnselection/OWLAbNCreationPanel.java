package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLTAN;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan.TANDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased.TargetAbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OntologyManagementPanel.OntologyManagementListener;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLNAT;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayRangeAbN;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLDiffPAreaTaxonomyWizardPanel;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLPAreaTaxonomyWizardPanel;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLTargetAbNPropertyRetriever;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLTargetAbNTargetSubhierarchyRetriever;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class OWLAbNCreationPanel extends JPanel {
    
    private final BLUOntologyManager manager = new BLUOntologyManager();
    
    private final OntologyManagementPanel ontologySelectionPanel;

    private final OWLAbNFrameManager frameManager;
    
    private final OWLAbNWizardPanel wizardPanel;
    
    private JButton openNATButton;

    public OWLAbNCreationPanel(OWLAbNFrameManager frameManager) {
        super(new BorderLayout());
        
        this.frameManager = frameManager;
        
        ontologySelectionPanel = new OntologyManagementPanel(manager);
        
        wizardPanel = new OWLAbNWizardPanel(frameManager);
        
        ontologySelectionPanel.addOntologySelectionListener(new OntologyManagementListener() {
            
            @Override
            public void ontologySelected(OAFOntologyDataManager dataManager) {
               
                wizardPanel.setCurrentDataManager(dataManager);
                wizardPanel.setEnabled(true);
                
                openNATButton.setEnabled(true);
            }
            
            @Override
            public void ontologyUnselected() {

                wizardPanel.clear();
                wizardPanel.setEnabled(false);
                
                openNATButton.setEnabled(false);
            }
            
            @Override
            public void ontologyLoading() {
                
                wizardPanel.resetView();
                wizardPanel.setEnabled(false);
                               
                openNATButton.setEnabled(false);
            }
            
            @Override
            public void ontologyLoaded(OAFOntologyDataManager loader) {
                
            }
        });

        this.add(ontologySelectionPanel, BorderLayout.WEST);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        centerPanel.add(wizardPanel, BorderLayout.CENTER);
        
        centerPanel.add(createBrowserPanel(), BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel createBrowserPanel() {
        JPanel browserPanel = new JPanel();
        browserPanel.setLayout(new BoxLayout(browserPanel, BoxLayout.X_AXIS));
        browserPanel.setBorder(BorderFactory.createTitledBorder("Class Browser"));

        JPanel openBtnPanel = new JPanel(new BorderLayout());
        openBtnPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        openNATButton = new JButton("<html><div align='center'>Open<br>Class<br>Browser");

        openNATButton.addActionListener( (ae) -> {
            if(ontologySelectionPanel.getSelectedOntology().isPresent()) {
                CreateAndDisplayOWLNAT createAndDisplay = new CreateAndDisplayOWLNAT(
                        frameManager, 
                        ontologySelectionPanel.getSelectedOntology().get());
                
                createAndDisplay.run();
            }
        });
        
        openNATButton.setEnabled(false);
        
        openBtnPanel.add(openNATButton, BorderLayout.CENTER);

        JPanel descPannel = new JPanel(new BorderLayout());
        descPannel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JEditorPane detailsPane = new JEditorPane();
        detailsPane.setContentType("text/html");
        
        String detailsString = "<html>The BLUOWL class browser allows you to browse individual classes and their relationships.";
        
        detailsPane.setText(detailsString);
        
        descPannel.add(detailsPane, BorderLayout.CENTER);
        
        browserPanel.add(descPannel);
        browserPanel.add(Box.createHorizontalStrut(8));
        browserPanel.add(openBtnPanel);

        return browserPanel;
    }
}
