package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons.PAreaTaxonomyHelpButton;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.InheritablePropertySelectionPanel.SelectionType;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyWizardPanel extends AbNDerivationWizardPanel implements 
        OWLAbNDerivationWizardPanel<OWLPAreaTaxonomy> {
    
    public interface OWLPAreaTaxonomyDerivationAction {

        public void derivePAreaTaxonomy(
                OAFOntologyDataManager dataManager,
                OWLConcept root,
                Set<PropertyTypeAndUsage> typesAndUsages,
                Set<OWLInheritableProperty> availableProperties,
                Set<OWLInheritableProperty> selectedProperties);
    }

    private Optional<OAFOntologyDataManager> optDataManager = Optional.empty();
    
    private final OWLRootSelectionPanel<OWLPAreaTaxonomy> rootSelectionPanel;
    
    private final PropertyTypeAndUsagePanel propertyTypeAndUsagePanel;
    
    private final InheritablePropertySelectionPanel propertySelectionPanel;
    
    private final JButton deriveBtn;
    
    private final PAreaTaxonomyHelpButton pareaHelpBtn;
    
    private final JLabel statusLabel;
    
    private final OWLPAreaTaxonomyDerivationAction taxonomyDerivationAction;
    
    public OWLPAreaTaxonomyWizardPanel(
            OWLPAreaTaxonomyDerivationAction taxonomyDerivationAction,
            OWLAbNFrameManager displayFrameListener) {
        
        this.taxonomyDerivationAction = taxonomyDerivationAction;

        this.setLayout(new BorderLayout());
        
        OWLPAreaTaxonomyConfigurationFactory dummyFactory = new OWLPAreaTaxonomyConfigurationFactory();
        PAreaTaxonomyConfiguration config = dummyFactory.createConfiguration(null, null, null, false);

        this.rootSelectionPanel = new OWLRootSelectionPanel<OWLPAreaTaxonomy>(config) {

            @Override
            public void displaySelectedOptionsFor(OWLPAreaTaxonomy abn) {
                
            }
        };
        
        this.rootSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Select Partial-area Taxonomy Root Class"));
        
        this.rootSelectionPanel.addRootSelectionListener(new RootSelectionListener() {

            @Override
            public void rootSelected(Concept root) {
                doRootSelectedAction((OWLConcept)root);
            }

            @Override
            public void rootDoubleClicked(Concept root) {
                rootSelected(root);
            }

            @Override
            public void noRootSelected() {
                propertyTypeAndUsagePanel.setEnabled(false);
                propertySelectionPanel.setEnabled(false);
                
                
                propertySelectionPanel.resetView();
                
                displayStatus();
            }
        });
        
        this.propertyTypeAndUsagePanel = new PropertyTypeAndUsagePanel();
        this.propertyTypeAndUsagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Select Property Types and Usages"));
        
        this.propertyTypeAndUsagePanel.setPreferredSize(new Dimension(250, -1));
        
        this.propertyTypeAndUsagePanel.addTypeAndUsageSelectionListener( (currentTypesAndUsages) -> {
            if(optDataManager.isPresent()) {
                if(rootSelectionPanel.getSelectedRoot().isPresent()) {
                    doPropertyTypesChangedAction(rootSelectionPanel.getSelectedRoot().get(), currentTypesAndUsages);
                }
            }
        });

        this.propertySelectionPanel = new InheritablePropertySelectionPanel(SelectionType.Multiple, false);
        this.propertySelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Select Properties"));
        
        this.propertySelectionPanel.setPreferredSize(new Dimension(350, -1));
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.X_AXIS));
        
        optionsPanel.add(rootSelectionPanel);
        optionsPanel.add(propertyTypeAndUsagePanel);
        optionsPanel.add(propertySelectionPanel);
        
        this.add(optionsPanel, BorderLayout.CENTER);
        
        this.statusLabel = new JLabel(" ");
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.add(statusLabel);
        
        JPanel derivationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        this.deriveBtn = new JButton("<html><div align ='center'>Derive Partial-area<br>Taxonomy");
        this.deriveBtn.addActionListener( (ae) -> {
            performedDerivationAction();
        });
        
        this.pareaHelpBtn = new PAreaTaxonomyHelpButton(config);
        
        derivationPanel.add(deriveBtn);
        derivationPanel.add(Box.createHorizontalStrut(10));
        derivationPanel.add(pareaHelpBtn);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(statusPanel, BorderLayout.CENTER);
        
        southPanel.add(derivationPanel, BorderLayout.EAST);
        
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    public void setDerivationButtonText(String derivationBtnStr) {
        this.deriveBtn.setText(derivationBtnStr);
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.rootSelectionPanel.setEnabled(value);
        this.propertyTypeAndUsagePanel.setEnabled(value);
        this.propertySelectionPanel.setEnabled(value);
        
        this.deriveBtn.setEnabled(value);
        this.pareaHelpBtn.setEnabled(value);
    }
    
    @Override
    public void initialize(OAFOntologyDataManager dataManager) {
        this.resetView();
        
        super.initialize((Ontology<Concept>)(Ontology<?>)dataManager.getOntology());
        
        this.optDataManager = Optional.of(dataManager);

        this.rootSelectionPanel.initialize(dataManager);
                        
        this.deriveBtn.setEnabled(true);
    }
    
    private void doRootSelectedAction(OWLConcept root) {
        propertyTypeAndUsagePanel.clearContents();
        propertySelectionPanel.resetView();
        
        rootSelectionPanel.setEnabled(false);
        propertyTypeAndUsagePanel.setEnabled(false);
        propertySelectionPanel.setEnabled(false);

        displayLoadingData();

        Thread loaderThread = new Thread(() -> {
            
            if (optDataManager.isPresent()) {
                Set<PropertyTypeAndUsage> typesAndUsages = optDataManager.get().getAvailablePropertyTypesInSubhierarchy(root);
                
                ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(
                        getAvailableProperties(root, typesAndUsages));

                sortedProperties.sort((a, b) -> {
                    return a.getName().compareToIgnoreCase(b.getName());
                });

                SwingUtilities.invokeLater( () -> {
                    
                    propertyTypeAndUsagePanel.initialize(typesAndUsages);
                    
                    propertySelectionPanel.initialize(sortedProperties);

                    propertySelectionPanel.setEnabled(true);
                    propertyTypeAndUsagePanel.setEnabled(true);
                    rootSelectionPanel.setEnabled(true);
                    
                    displayStatus();
                });
            }
        });
        
        loaderThread.start();
    }
    
    private void doPropertyTypesChangedAction(
            OWLConcept root, 
            Set<PropertyTypeAndUsage> typesAndUsages) {
        
        propertySelectionPanel.resetView();
        
        propertySelectionPanel.setEnabled(false);

        displayLoadingData();

        Thread loaderThread = new Thread(() -> {
            
            if (optDataManager.isPresent()) {
                ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(
                        getAvailableProperties(root, typesAndUsages));

                sortedProperties.sort((a, b) -> {
                    return a.getName().compareToIgnoreCase(b.getName());
                });

                SwingUtilities.invokeLater( () -> {
                    propertySelectionPanel.initialize(sortedProperties);

                    propertySelectionPanel.setEnabled(true);
                    
                    displayStatus();
                });
            }
            
        });
        
        loaderThread.start();
    }
    
    public Set<OWLInheritableProperty> getAvailableProperties(
            OWLConcept root, 
            Set<PropertyTypeAndUsage> typesAndUsages) {
        
        if (optDataManager.isPresent()) {
            return optDataManager.get().getPropertiesInSubhierarchy(root, typesAndUsages);
        } else {
            return Collections.emptySet();
        }
    }
    
    private void displayLoadingData() {
        statusLabel.setText("Loading data, please wait...");
        statusLabel.setForeground(Color.RED);
    }
    
    private void displayStatus() {
        statusLabel.setForeground(Color.BLUE);
        
        if(optDataManager.isPresent()) {
            if(rootSelectionPanel.getSelectedRoot().isPresent()) {
                statusLabel.setText(String.format("Ready to derive the %s Partial-area Taxonomy", 
                        rootSelectionPanel.getSelectedRoot().get().getName()));
            } else {
                statusLabel.setText("Please select a root class...");
            }
        } else {
            statusLabel.setText("Please select an ontology...");
        }
    }
    
    @Override
    public void displaySelectedOptionsFor(OWLPAreaTaxonomy abn) {
        
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
        this.optDataManager = Optional.empty();
        
        this.rootSelectionPanel.clearContents();
        this.propertySelectionPanel.clearContents();
        this.propertyTypeAndUsagePanel.clearContents();
    }

    @Override
    public void resetView() {
        this.rootSelectionPanel.resetView();
        this.propertyTypeAndUsagePanel.resetView();
        this.propertySelectionPanel.resetView();
    }
    
    private void performedDerivationAction() {
        
        if (!optDataManager.isPresent()) {
            
            JOptionPane.showMessageDialog(null,
                    "Cannot derive partial-area taxonomy. No ontology loaded.",
                    "Error: No Ontology Loaded",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (!rootSelectionPanel.getSelectedRoot().isPresent()) {

            JOptionPane.showMessageDialog(null,
                    "Cannot derive partial-area taxonomy. No root class selected.",
                    "Error: No Root Class Selected",
                    JOptionPane.ERROR_MESSAGE);

            return;
        }
        
        if(propertyTypeAndUsagePanel.getSelectedUsageTypes().isEmpty()) {
            
            JOptionPane.showMessageDialog(null,
                    "Cannot derive partial-area taxonomy. No property types and usages selected.",
                    "Error: No Property Type and Usage Selected",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        if(propertySelectionPanel.getUserSelectedProperties().isEmpty()) {
            
              JOptionPane.showMessageDialog(null,
                    "Cannot derive partial-area taxonomy. No attribute relationships selected.",
                    "Error: No Attribute Relationships Selected",
                    JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        OAFOntologyDataManager dataManager = optDataManager.get();
        OWLConcept root = rootSelectionPanel.getSelectedRoot().get();
        Set<PropertyTypeAndUsage> typesAndUsages = propertyTypeAndUsagePanel.getSelectedUsageTypes();
        Set<OWLInheritableProperty> availableProperties = (Set<OWLInheritableProperty>)(Set<?>)propertySelectionPanel.getAvailableProperties();
        Set<OWLInheritableProperty> selectedProperties = (Set<OWLInheritableProperty>)(Set<?>)propertySelectionPanel.getUserSelectedProperties();
        
        this.taxonomyDerivationAction.derivePAreaTaxonomy(dataManager, root, typesAndUsages, availableProperties, selectedProperties);
    }
}

