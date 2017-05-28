package edu.njit.cs.saboc.blu.owl.gui.graphframe.initializers;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.DisjointAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.DerivationSelectionButton;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.AreaTaxonomyInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.BandTANInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.DisjointAbNInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.PAreaTaxonomyInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.TANInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers.TargetAbNInitializer;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.abnhistory.OWLDerivationParser;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNFrameManager;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.OWLAbNWizardPanel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointpareataxonomy.configuration.OWLDisjointPAreaTaxonomyConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.disjointtan.configuration.OWLDisjointTANConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.range.configuration.OWLRangeAbNConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfigurationFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class OWLMultiAbNGraphFrameInitializers implements AbNGraphFrameInitializers {

    private final OAFOntologyDataManager dataManager;
    
    private final OWLAbNFrameManager frameManager;
    
    private final AbNWarningManager warningManager;
    
    public OWLMultiAbNGraphFrameInitializers(
            OAFOntologyDataManager ontologyManager, 
            OWLAbNFrameManager frameManager,
            AbNWarningManager warningManager) {
        
        this.dataManager = ontologyManager;
        this.frameManager = frameManager;
        this.warningManager = warningManager;
    }
    
    protected DerivationSelectionButton createDerivationSelectionButton(
            MultiAbNGraphFrame graphFrame,
            OAFOntologyDataManager dataManager) {

        OWLAbNWizardPanel wizardPanel = new OWLAbNWizardPanel(
                new OWLFrameManagerAdapter(graphFrame),
                false);

        SwingUtilities.invokeLater( () -> {
            wizardPanel.setCurrentDataManager(dataManager);
            wizardPanel.setEnabled(true);
        });
        
        return new DerivationSelectionButton(graphFrame, wizardPanel);
    }
    
    
    @Override
    public GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> getPAreaTaxonomyInitializer() {
        
        return new PAreaTaxonomyInitializer(warningManager) {

            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, PAreaTaxonomyConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getPAreaTaxonomy();
                
                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(PAreaTaxonomy abn, AbNDisplayManager displayManager) {
                return new OWLPAreaTaxonomyConfigurationFactory().createConfiguration(abn, displayManager, frameManager, false);
            }
        };
    }

    @Override
    public GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> getAreaTaxonomyInitializer() {
        
        return new AreaTaxonomyInitializer(warningManager) {
            
            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, PAreaTaxonomyConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config); 
                
                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork)config.getPAreaTaxonomy();
                
                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                
                return panel;
            }
            
            @Override
            public AbNConfiguration getConfiguration(PAreaTaxonomy abn, AbNDisplayManager displayManager) {
                return new OWLPAreaTaxonomyConfigurationFactory().createConfiguration(abn, displayManager, frameManager, true);
            }
        };
    }

    @Override
    public GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> getTANInitializer() {
        
        return new TANInitializer(warningManager) {
            
            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TANConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork) config.getAbstractionNetwork();

                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(ClusterTribalAbstractionNetwork abn, AbNDisplayManager displayManager) {
                return new OWLTANConfigurationFactory().createConfiguration(abn, displayManager, frameManager, false);
            }
        };
    }
    
    
    @Override
    public GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> getBandTANInitializer() {
        
        return new BandTANInitializer(warningManager) {
            
            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TANConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork) config.getAbstractionNetwork();

                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(ClusterTribalAbstractionNetwork abn, AbNDisplayManager displayManager) {
                return new OWLTANConfigurationFactory().createConfiguration(abn, displayManager, frameManager, true);
            }
        };
    }
    
    @Override
    public GraphFrameInitializer<TargetAbstractionNetwork, TargetAbNConfiguration> getTargetAbNInitializer() {
        
        return new TargetAbNInitializer(warningManager) {

            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TargetAbNConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork) config.getAbstractionNetwork();

                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(TargetAbstractionNetwork abn, AbNDisplayManager displayManager) {
                return new OWLRangeAbNConfigurationFactory().createConfiguration(abn, displayManager, frameManager);
            }
        };
    }

    @Override
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointPAreaTaxonomyInitializer() {
        
        return new DisjointAbNInitializer() {
            
            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, DisjointAbNConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork) config.getAbstractionNetwork().getParentAbstractionNetwork();

                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(DisjointAbstractionNetwork abn, AbNDisplayManager displayManager) {
                return new OWLDisjointPAreaTaxonomyConfigurationFactory().createConfiguration(abn, displayManager, frameManager);
            }

            @Override
            public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(DisjointAbNConfiguration config) {
                
                PAreaTaxonomy taxonomy = (PAreaTaxonomy)config.getAbstractionNetwork().getParentAbstractionNetwork();
                
                return new DisjointAbNExplorationPanelInitializer(
                        config,
                        new OWLPAreaTaxonomyConfigurationFactory().createConfiguration(
                                taxonomy, 
                                config.getUIConfiguration().getAbNDisplayManager(), 
                                frameManager,
                                false),
                        
                        (bound) -> {
                            DisjointAbstractionNetwork disjointAbN = config.getAbstractionNetwork().getAggregated(bound);
                            config.getUIConfiguration().getAbNDisplayManager().displayDisjointPAreaTaxonomy(disjointAbN);
                        },
                        warningManager);
            }
        };
    }

    @Override
    public GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> getDisjointTANInitializer() {
        return new DisjointAbNInitializer() {
            
            @Override
            public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, DisjointAbNConfiguration config) {
                TaskBarPanel panel = super.getTaskBar(graphFrame, config);

                OWLAbstractionNetwork owlAbN = (OWLAbstractionNetwork) config.getAbstractionNetwork().getParentAbstractionNetwork();

                panel.addToggleableButtonToMenu(
                        createDerivationSelectionButton(graphFrame, owlAbN.getDataManager()));

                return panel;
            }

            @Override
            public AbNConfiguration getConfiguration(DisjointAbstractionNetwork abn, AbNDisplayManager displayManager) {
                return new OWLDisjointTANConfigurationFactory().createConfiguration(abn, displayManager, frameManager);
            }

            @Override
            public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(DisjointAbNConfiguration config) {

                ClusterTribalAbstractionNetwork tan = (ClusterTribalAbstractionNetwork)config.getAbstractionNetwork().getParentAbstractionNetwork();

                return new DisjointAbNExplorationPanelInitializer(
                        config,
                        new OWLTANConfigurationFactory().createConfiguration(
                                tan, 
                                config.getUIConfiguration().getAbNDisplayManager(), 
                                frameManager,
                                false),
                        (bound) -> {
                            DisjointAbstractionNetwork disjointAbN = config.getAbstractionNetwork().getAggregated(bound);
                            config.getUIConfiguration().getAbNDisplayManager().displayDisjointTribalAbstractionNetwork(disjointAbN);
                        },
                        warningManager);
            }
        };
    }

    @Override
    public OAFRecentlyOpenedFileManager getRecentAbNWorkspaceFiles() {
        
        try {
            return dataManager.getOAFStateFileManager().getRecentAbNWorkspaces(dataManager.getOntologyFile());
        } catch (RecentlyOpenedFileException rofe) {
            
        }
        
        return null;
    }

    @Override
    public Ontology getSourceOntology() {
        return dataManager.getOntology();
    }

    @Override
    public AbNDerivationParser getAbNParser() {
        return new OWLDerivationParser(dataManager);
    }
    
    public AbNWarningManager getWarningManager() {
        return warningManager;
    }
    
    public OWLAbNFrameManager getFrameManager() {
        return frameManager;
    }
}
