package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.tan.TANDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased.TargetAbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLPAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayOWLTAN;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayRangeAbN;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLDiffPAreaTaxonomyWizardPanel;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLPAreaTaxonomyWizardPanel;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLTargetAbNPropertyRetriever;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard.OWLTargetAbNTargetSubhierarchyRetriever;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration.OWLRangeAbNConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration.OWLRangeAbNConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Optional;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class OWLAbNWizardPanel extends JPanel {
    
    private Optional<OAFOntologyDataManager> optCurrentDataManager = Optional.empty();

    private final OWLPAreaTaxonomyWizardPanel pareaTaxonomyWizardPanel;

    private final OWLDiffPAreaTaxonomyWizardPanel diffPAreaTaxonomyWizardPanel;

    private final TANDerivationWizardPanel tanDerivationWizardPanel;

    private final TargetAbNDerivationWizardPanel targetAbNDerivationWizardPanel;

    private final JTabbedPane abnSelectionTabs;
    
    private final OWLAbNFrameManager frameManager;
    
    public OWLAbNWizardPanel(OWLAbNFrameManager frameManager, boolean includeDiff) {
        
        this.frameManager = frameManager;
        
        pareaTaxonomyWizardPanel = new OWLPAreaTaxonomyWizardPanel(
                (dataManager, root, typesAndUsages, availableProperties, selectedProperties) -> {
                    
                    CreateAndDisplayOWLPAreaTaxonomy createTaxonomy = new CreateAndDisplayOWLPAreaTaxonomy(
                            "Creating Partial-area Taxonomy",
                            root,
                            typesAndUsages,
                            availableProperties,
                            selectedProperties,
                            frameManager,
                            dataManager);

                    createTaxonomy.run();

                },
                frameManager);
        
        diffPAreaTaxonomyWizardPanel = new OWLDiffPAreaTaxonomyWizardPanel(frameManager);
        
        OWLTANConfigurationFactory tanConfigFactory = new OWLTANConfigurationFactory();
        
        OWLTANConfiguration dummyTANConfig = tanConfigFactory.createConfiguration(null,
                frameManager, 
                null, 
                false);        

        tanDerivationWizardPanel = new TANDerivationWizardPanel(dummyTANConfig, (patriarchs) -> {
            CreateAndDisplayOWLTAN creatingTANDialog = new CreateAndDisplayOWLTAN(
                    "Creating Tribal Abstraction Network", 
                    (Set<OWLConcept>)(Set<?>)patriarchs,
                    frameManager,
                    optCurrentDataManager.get()
                );
            
            creatingTANDialog.run();
        });
        
        OWLRangeAbNConfigurationFactory rangeConfigFactory = new OWLRangeAbNConfigurationFactory();
        
        OWLRangeAbNConfiguration dummyRangeConfig = rangeConfigFactory.createConfiguration(null,
                frameManager, 
                null);        
        
        targetAbNDerivationWizardPanel = new TargetAbNDerivationWizardPanel(dummyRangeConfig,
                (sourceHierarchy, type, targetHierarchy) -> {
                    CreateAndDisplayRangeAbN createRangeAbN = new CreateAndDisplayRangeAbN(
                       "Creating Range Abstraction Network",
                        frameManager, 
                            (Hierarchy<OWLConcept>)(Hierarchy<?>)sourceHierarchy,
                            (OWLInheritableProperty)type,
                            (Hierarchy<OWLConcept>)(Hierarchy<?>)targetHierarchy, 
                            optCurrentDataManager.get()
                    );
                    
                    createRangeAbN.run();
                });

        pareaTaxonomyWizardPanel.setEnabled(false);
        tanDerivationWizardPanel.setEnabled(false);
        targetAbNDerivationWizardPanel.setEnabled(false);
        diffPAreaTaxonomyWizardPanel.setEnabled(false);
        
        abnSelectionTabs = new JTabbedPane();
        abnSelectionTabs.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Derive an Abstraction Network"));
        
        JPanel pareaPanel = new JPanel(new BorderLayout());
        pareaPanel.add(pareaTaxonomyWizardPanel, BorderLayout.CENTER);
        
        JPanel diffPAreaPanel = new JPanel(new BorderLayout());
        diffPAreaPanel.add(diffPAreaTaxonomyWizardPanel, BorderLayout.CENTER);
        
        JPanel tanPanel = new JPanel(new BorderLayout());
        tanPanel.add(tanDerivationWizardPanel, BorderLayout.CENTER);
        
        JPanel targetPanel = new JPanel(new BorderLayout());
        targetPanel.add(targetAbNDerivationWizardPanel, BorderLayout.CENTER);
        
        abnSelectionTabs.addTab("Partial-area Taxonomy", pareaPanel);
        
        if(includeDiff) {
            abnSelectionTabs.addTab("Diff Partial-area Taxonomy (Under development)", diffPAreaTaxonomyWizardPanel);
        }
        
        abnSelectionTabs.addTab("Tribal Abstraction Network (TAN)", tanPanel);
        abnSelectionTabs.addTab("Range Abstraction Network", targetPanel);

        this.setLayout(new BorderLayout());
        
        this.add(abnSelectionTabs, BorderLayout.CENTER);
    }
    
    public void setCurrentDataManager(OAFOntologyDataManager dataManager) {
        optCurrentDataManager = Optional.of(dataManager);

        pareaTaxonomyWizardPanel.initialize(dataManager);
        diffPAreaTaxonomyWizardPanel.initialize(dataManager);
        tanDerivationWizardPanel.initialize(dataManager.getOntology(), dataManager);

        targetAbNDerivationWizardPanel.initialize(
                dataManager.getOntology(),
                dataManager,
                new OWLTargetAbNPropertyRetriever(dataManager),
                new OWLTargetAbNTargetSubhierarchyRetriever(dataManager));
    }
    
    @Override
    public void setEnabled(boolean value) {
        
        super.setEnabled(value);

        pareaTaxonomyWizardPanel.setEnabled(value);
        diffPAreaTaxonomyWizardPanel.setEnabled(value);
        tanDerivationWizardPanel.setEnabled(value);
        targetAbNDerivationWizardPanel.setEnabled(value);
        
        abnSelectionTabs.setEnabled(value);
    }
    
    public void resetView() {
        pareaTaxonomyWizardPanel.resetView();
        diffPAreaTaxonomyWizardPanel.resetView();
        tanDerivationWizardPanel.resetView();
        targetAbNDerivationWizardPanel.resetView();
    }
    
    public void clear() {
        this.optCurrentDataManager = Optional.empty();
        
        this.resetView();
    }
}
