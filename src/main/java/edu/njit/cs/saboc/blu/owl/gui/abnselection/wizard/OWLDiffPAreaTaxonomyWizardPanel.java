package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFStateFileManager;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.BLUOntologyManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLFileFilter;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLDiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLPAreaTaxonomyWizardPanel.OWLPAreaTaxonomyDerivationAction;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyWizardPanel extends AbNDerivationWizardPanel implements OWLAbNDerivationWizardPanel {

    private final DiffOntologySelectionPanel fromOntologySelectionPanel;

    private final OWLPAreaTaxonomyWizardPanel taxonomyDerivationPanel;
    
    private final OWLAbNFrameManager displayFrameListener;
    
    private Optional<OAFOntologyDataManager> optCurrentOntologyDataManager = Optional.empty();
    
    public OWLDiffPAreaTaxonomyWizardPanel(
            OAFStateFileManager stateFileManager,
            OWLAbNFrameManager displayFrameListener) {
        
        this.displayFrameListener = displayFrameListener;
        
        this.fromOntologySelectionPanel = new DiffOntologySelectionPanel(stateFileManager, "From");
        
        OWLPAreaTaxonomyDerivationAction action = (
                dataManager, 
                root, 
                typesAndUsages, 
                availableProperties, 
                selectedProperties) -> {
                    
                    CreateAndDisplayOWLDiffPAreaTaxonomy createAndDisplay = new CreateAndDisplayOWLDiffPAreaTaxonomy(
                        "Creating Diff Partial-area Taxonomy",
                        root,
                        typesAndUsages,
                        availableProperties,
                        selectedProperties, 
                        displayFrameListener,
                        fromOntologySelectionPanel.getSelectedOntologyDataManager().get(), 
                        optCurrentOntologyDataManager.get());
                    
                    createAndDisplay.run();
                };
        
        this.taxonomyDerivationPanel = new OWLPAreaTaxonomyWizardPanel(
                action,
                displayFrameListener) {
                    
            @Override
            public Set<OWLInheritableProperty> getAvailableProperties(OWLConcept root, Set<PropertyTypeAndUsage> typesAndUsages) {
                
                if(optCurrentOntologyDataManager.isPresent() && 
                        fromOntologySelectionPanel.getSelectedOntologyDataManager().isPresent()) {
   
                    OAFOntologyDataManager fromManager = fromOntologySelectionPanel.getSelectedOntologyDataManager().get();
                    OAFOntologyDataManager toManager = optCurrentOntologyDataManager.get();
                    
                    Set<OWLInheritableProperty> fromProperties = fromManager.getPropertiesInSubhierarchy(root, typesAndUsages);
                    Set<OWLInheritableProperty> toProperties = toManager.getPropertiesInSubhierarchy(root, typesAndUsages);
                    
                    return SetUtilities.getSetUnion(fromProperties, toProperties);

                } else {
                    return Collections.emptySet();
                }
            }
        };
        
        this.taxonomyDerivationPanel.setDerivationButtonText("Derive Diff Partial-area Taxonomy");
        
        this.fromOntologySelectionPanel.addOntologySelectedListener( (ontology) -> {
            
            OAFOntologyDataManager toOntology = optCurrentOntologyDataManager.get();
            
            this.taxonomyDerivationPanel.initialize(toOntology);
            
            this.taxonomyDerivationPanel.setEnabled(true);
        });
        
        this.fromOntologySelectionPanel.setEnabled(false);
        this.taxonomyDerivationPanel.setEnabled(false);

        this.setLayout(new BorderLayout());

        this.add(fromOntologySelectionPanel, BorderLayout.NORTH);
        
        this.add(taxonomyDerivationPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.fromOntologySelectionPanel.setEnabled(value);
    }

    @Override
    public void initialize(OAFOntologyDataManager manager) {
        super.initialize(manager.getOntology());
        
        optCurrentOntologyDataManager = Optional.of(manager);
        
        this.fromOntologySelectionPanel.initialize(manager);
    }

    @Override
    public void displaySelectedOptionsFor(OWLAbstractionNetwork abn) {
        
    }

    @Override
    public void resetView() {
        this.fromOntologySelectionPanel.resetView();
        this.taxonomyDerivationPanel.resetView();
        
        this.optCurrentOntologyDataManager = Optional.empty();
        
        this.taxonomyDerivationPanel.setEnabled(false);
    }
}

/**
 * Panel that contains options for selecting the from and to ontologies
 * @author Chris
 */
class DiffOntologySelectionPanel extends AbNDerivationWizardPanel implements OWLAbNDerivationWizardPanel {
    
    public interface OntologySelectedListener {
        public void ontologySelected(OAFOntologyDataManager result);
    }
    
    private Optional<OAFOntologyDataManager> optCurrentToOntology = Optional.empty(); 
    
    private final BLUOntologyManager manager;
    
    private Optional<OAFOntologyDataManager> optSelectedFromOntology = Optional.empty();
    
    private final JButton btnOpenLocal;

    private final JLabel selectedOntologyLabel = new JLabel();
    
    private final ArrayList<OntologySelectedListener> ontologySelectedListeners = new ArrayList<>();
    
    public DiffOntologySelectionPanel(OAFStateFileManager stateFileManager, String type) {
        
        this.manager = new BLUOntologyManager(stateFileManager);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK), 
                String.format("Select %s Ontology", type.toUpperCase()),
                TitledBorder.CENTER, 
                TitledBorder.ABOVE_TOP));
        
        btnOpenLocal = new JButton("<html><div align='center'>Local Ontology File<br>"
                + "(Select Earlier Ontology Release)");
        
        btnOpenLocal.addActionListener((ae) -> {
            promptForFile();
        });

        this.add(btnOpenLocal);
        
        JPanel selectedOntologyPanel = new JPanel();
        selectedOntologyPanel.add(new JLabel("Selected Ontology File: "));
        selectedOntologyPanel.add(selectedOntologyLabel);
        
        this.add(selectedOntologyPanel);
    }
    
    public void addOntologySelectedListener(OntologySelectedListener listener) {
        ontologySelectedListeners.add(listener);
    }
    
    public void removeOntologySelectedListener(OntologySelectedListener listener) {
        ontologySelectedListeners.remove(listener);
    }
    
    public void clear() {
        closeSelectedOntology();

        this.selectedOntologyLabel.setText(" ");
    }
    
    /**
     * Returns the selected ontology
     * @return 
     */
    public Optional<OAFOntologyDataManager> getSelectedOntologyDataManager() {
        return this.optSelectedFromOntology;
    }
    
    /**
     * Prompts the user to select an ontology file
     */
    private void promptForFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new OWLFileFilter());

        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            closeSelectedOntology();

            String filePath = chooser.getSelectedFile().getAbsolutePath();
            
            try {
                 OAFOntologyDataManager userSelectedOntology = manager.loadOntology(filePath);
                 
                 if (canDeriveDiffTaxonomy(userSelectedOntology, optCurrentToOntology.get())) {
                    
                    this.optSelectedFromOntology = Optional.of(userSelectedOntology);
                    this.selectedOntologyLabel.setText(userSelectedOntology.getOntologyName());

                    ontologySelectedListeners.forEach((listener) -> {
                        listener.ontologySelected(userSelectedOntology);
                    });
                    
                } else {
                    // TODO: Display error
                }
                 
            } catch(BLUOntologyManager.FailedToOpenOntologyException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeSelectedOntology() {
        if (optSelectedFromOntology.isPresent()) {
            manager.closeOntology(optSelectedFromOntology.get());
            optSelectedFromOntology = Optional.empty();
        }
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.btnOpenLocal.setEnabled(value);
    }

    @Override
    public void resetView() {
        clear();
        optCurrentToOntology = Optional.empty();
    }

    @Override
    public void initialize(OAFOntologyDataManager manager) {
        this.optCurrentToOntology = Optional.of(manager);
    }

    @Override
    public void displaySelectedOptionsFor(OWLAbstractionNetwork abn) {
        
    }
    
    private boolean canDeriveDiffTaxonomy(OAFOntologyDataManager from, OAFOntologyDataManager to) {
        Set<OWLConcept> toConcepts = from.getOntology().getConceptHierarchy().getNodes();
        Set<OWLConcept> fromConcepts = to.getOntology().getConceptHierarchy().getNodes();

        return !SetUtilities.getSetIntersection(toConcepts, fromConcepts).isEmpty();
    }
}
