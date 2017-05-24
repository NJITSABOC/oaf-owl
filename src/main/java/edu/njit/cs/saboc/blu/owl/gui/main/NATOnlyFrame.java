package edu.njit.cs.saboc.blu.owl.gui.main;

import edu.njit.cs.saboc.blu.owl.gui.abnselection.BLUOntologyManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OntologyManagementPanel;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OntologyManagementPanel.OntologyManagementListener;
import edu.njit.cs.saboc.blu.owl.nat.OWLNATLayout;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.workspace.NATWorkspace;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author Chris Ochs
 */
public class NATOnlyFrame extends JFrame {
    
    private final BLUOntologyManager ontologyManager;
    private final OntologyManagementPanel managementPanel;
    
    private final NATBrowserPanel<OWLConcept> browserPanel;
    
    private final Map<File, NATWorkspace> workspaces;
    
    public NATOnlyFrame() {

        this.ontologyManager = new BLUOntologyManager();
        this.managementPanel = new OntologyManagementPanel(ontologyManager);

        this.browserPanel = new NATBrowserPanel<>(this, new OWLNATLayout());
        this.browserPanel.setEnabled(false);
        
        this.workspaces = new HashMap<>();
          
        this.managementPanel.addOntologySelectionListener(new OntologyManagementListener() {
            
            @Override
            public void ontologySelected(OAFOntologyDataManager dataManager) {
                browserPanel.reset();
                
                browserPanel.setDataSource(dataManager.getClassBrowserDataSource());
                
                if (workspaces.containsKey(dataManager.getOntologyFile())) {
                    
                    NATWorkspace<OWLConcept> workspace = workspaces.get(dataManager.getOntologyFile());
                    
                    browserPanel.setWorkspace(workspace);
                    
                } else {
                    browserPanel.getFocusConceptManager().navigateToRoot();

                    NATWorkspace workspace = NATWorkspace.createNewWorkspaceFromCurrent(
                            browserPanel,
                            null,
                            String.format("%s Default Workspace", dataManager.getOntologyFile().getName()));

                    workspaces.put(dataManager.getOntologyFile(), workspace);
                    
                    browserPanel.setWorkspace(workspace);
                }
                
                browserPanel.setEnabled(true);
            }

            @Override
            public void ontologyUnselected() {
                browserPanel.setEnabled(false);
                browserPanel.reset();
            }

            @Override
            public void ontologyLoading() {
                browserPanel.setEnabled(false);
            }

            @Override
            public void ontologyLoaded(OAFOntologyDataManager ontology) {
                browserPanel.setEnabled(true);
            }
        });
        
        

        JMenuBar menuBar = new JMenuBar();
                
        JCheckBox chkShowOntologyManagement = new JCheckBox("Show Ontology Managerment Panel");
        chkShowOntologyManagement.setSelected(true);
        
        chkShowOntologyManagement.addActionListener( (ae) -> {
            managementPanel.setVisible(chkShowOntologyManagement.isSelected());
        });
        
        menuBar.add(chkShowOntologyManagement);
        
        setJMenuBar(menuBar);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        contentPanel.add(managementPanel, BorderLayout.WEST);
        contentPanel.add(browserPanel, BorderLayout.CENTER);
                
        this.add(contentPanel);
        
        setTitle("Ontology Abstraction Framework (OAF) Class Browser by SABOC at NJIT");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
        
        setVisible(true);
    }
}
