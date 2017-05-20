package edu.njit.cs.saboc.blu.owl.gui.abnselection.wizard;

import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.PropertyUsageSelectionPanel;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OntologyMetrics;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyType;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class PropertyTypeAndUsagePanel extends AbNDerivationWizardPanel {
    
    public interface PropertyTypeAndUsageSelectionListener {
        public void typesAndUsagesChanged(Set<PropertyTypeAndUsage> typesAndUsages);
    }
    
    public enum EnableType {
        ObjectProperties,
        DataProperties,
        Both
    }

    private PropertyUsageSelectionPanel opTypePanel = new PropertyUsageSelectionPanel(PropertyType.Object);
    private PropertyUsageSelectionPanel dpTypePanel = new PropertyUsageSelectionPanel(PropertyType.Data);
    
    private final JCheckBox chkUseOp = new JCheckBox("Use Object Properties");
    private final JCheckBox chkUseDp = new JCheckBox("Use Data Properties");
    
    private final ArrayList<PropertyTypeAndUsageSelectionListener> typeAndUsageSelectionListeners = new ArrayList<>();
    
    private Optional<Set<PropertyTypeAndUsage>> availableTypesAndUsages = Optional.empty();
    
    private Optional<OAFOntologyDataManager> optDataManager = Optional.empty();
    
    public PropertyTypeAndUsagePanel() {
        
        this.setLayout(new GridLayout(2, 1));
        
        this.opTypePanel.addPropertyUsageChangedListener( (typeAndUsage, enabled) -> {
            propertyTypeAndUsageChanged();
        });
        
        this.dpTypePanel.addPropertyUsageChangedListener( (typeAndUsage, enabled) -> {
            propertyTypeAndUsageChanged();
        });
                
        opTypePanel.setEnabled(false);
        chkUseOp.setEnabled(false);      
        
        dpTypePanel.setEnabled(false);
        chkUseDp.setEnabled(false);      
        
        JPanel opSelectionPanel = new JPanel(new BorderLayout());
        opSelectionPanel.add(chkUseOp, BorderLayout.NORTH);
        opSelectionPanel.add(opTypePanel);
        
        this.add(opSelectionPanel, BorderLayout.WEST);

        JPanel dpSelectionPanel = new JPanel(new BorderLayout());
        dpSelectionPanel.add(chkUseDp, BorderLayout.NORTH);
        dpSelectionPanel.add(dpTypePanel, BorderLayout.CENTER);
        
        this.add(dpSelectionPanel);
        
        chkUseOp.addActionListener((ae) -> {
            if (chkUseOp.isSelected()) {
                opTypePanel.setEnabled(true);
                opTypePanel.clear();
                
                if (availableTypesAndUsages.isPresent()) {
                    opTypePanel.enableOptions(availableTypesAndUsages.get());
                }
                
            } else {
                opTypePanel.clear();
                opTypePanel.setEnabled(false);
            }
            
            propertyTypeAndUsageChanged();
        });
        
        chkUseDp.addActionListener((ae) -> {
            if (chkUseDp.isSelected()) {
                dpTypePanel.setEnabled(true);
                dpTypePanel.clear();
                
                if (availableTypesAndUsages.isPresent()) {
                    dpTypePanel.enableOptions(availableTypesAndUsages.get());
                }
                
            } else {
                dpTypePanel.clear();
                dpTypePanel.setEnabled(false);
            }
            
            propertyTypeAndUsageChanged();
        });
    }
    
    public Optional<Set<PropertyTypeAndUsage>> getAvailableTypesAndUsages() {
        return availableTypesAndUsages;
    }
    
    private void propertyTypeAndUsageChanged() {
        typeAndUsageSelectionListeners.forEach( (listener) -> {
            listener.typesAndUsagesChanged(this.getSelectedUsageTypes());
        });
    }
    
    public void addTypeAndUsageSelectionListener(PropertyTypeAndUsageSelectionListener listener) {
        typeAndUsageSelectionListeners.add(listener);
    }
    
    public void removeTypeAndUsageSelectionListener(PropertyTypeAndUsageSelectionListener listener) {
        typeAndUsageSelectionListeners.remove(listener);
    }
    
    public void enableOptionsByMetrics(OntologyMetrics metrics, EnableType enableType) {
        
        Set<PropertyTypeAndUsage> availableUsages = new HashSet<>();

        if (enableType == EnableType.Both || enableType == EnableType.ObjectProperties) {

            if (metrics.totalOPWithDomainCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.OP_DOMAIN);
            }

            if (metrics.totalOPWithRestrictionCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.OP_RESTRICTION);
            }

            if (metrics.totalOPRestrictionEquivCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.OP_EQUIV);
            }
        }

        if (enableType == EnableType.Both || enableType == EnableType.DataProperties) {

            if (metrics.totalDPWithDomainCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.DP_DOMAIN);//originally ExplicitDDomain
            }

            if (metrics.totalDPWithRestrictionCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.DP_RESTRICTION);//originally Restriction
            }

            if (metrics.totalDPRestrictionEquivCount > 0) {
                availableUsages.add(PropertyTypeAndUsage.DP_EQUIV); //originally RestrictionEquivalence
            }
        }
        
        enableOptions(availableUsages);
    }
    
    public void enableOptions(Set<PropertyTypeAndUsage> typesAndUsages) {
        
        this.availableTypesAndUsages = Optional.of(typesAndUsages);
        
        Set<PropertyTypeAndUsage> opTypes = typesAndUsages.stream().filter( (typeAndUsage) -> {
           return typeAndUsage.getType().equals(PropertyType.Object);
        }).collect(Collectors.toSet());
        
        Set<PropertyTypeAndUsage> dpTypes = typesAndUsages.stream().filter( (typeAndUsage) -> {
           return typeAndUsage.getType().equals(PropertyType.Data);
        }).collect(Collectors.toSet());
        
        if(opTypes.isEmpty()) {
            chkUseOp.setEnabled(false);
            chkUseOp.setSelected(false);
            
            opTypePanel.clear();
            opTypePanel.setEnabled(false);
        } else {
            chkUseOp.setEnabled(true);
            chkUseOp.setSelected(true);
            
            opTypePanel.enableOptions(opTypes);
        }
        
        if(dpTypes.isEmpty()) {
            chkUseDp.setEnabled(false);
            chkUseDp.setSelected(false);
            
            dpTypePanel.clear();
            dpTypePanel.setEnabled(false);
        } else {
            chkUseDp.setEnabled(true);
            chkUseDp.setSelected(true);
            
            dpTypePanel.enableOptions(dpTypes);
        }
    }
    
    public boolean typeSelected() {
        if(chkUseOp.isSelected()) {
            return !opTypePanel.getSelectedUsageTypes().isEmpty();
        }
        
        if(chkUseDp.isSelected()) {
            return !dpTypePanel.getSelectedUsageTypes().isEmpty();
        }
        
        return false;
    }

    public Set<PropertyTypeAndUsage> getSelectedUsageTypes() {
        Set<PropertyTypeAndUsage> usageTypes = new HashSet<>();
        
        if(chkUseOp.isSelected()) {
            usageTypes.addAll(opTypePanel.getSelectedUsageTypes());
        }
        
        if(chkUseDp.isSelected()) {
            usageTypes.addAll(dpTypePanel.getSelectedUsageTypes());
        }
        
        return usageTypes;
    }
    
    public void setSelectedUsageTypes(Set<PropertyTypeAndUsage> selectedTypes) {
        opTypePanel.setSelectedUsageTypes(selectedTypes);
        dpTypePanel.setSelectedUsageTypes(selectedTypes);
    }
    
    public void initialize(OAFOntologyDataManager dataManager) {
        super.initialize((Ontology<Concept>)(Ontology<?>)dataManager.getOntology());
        
        this.optDataManager = Optional.of(dataManager);
    }
    
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        opTypePanel.setEnabled(value);
        dpTypePanel.setEnabled(value);
        
        this.chkUseOp.setEnabled(value);
        this.chkUseDp.setEnabled(value);
    }
    
    public void clearContents() {
        super.clearContents();
        
        this.optDataManager = Optional.empty();
        this.availableTypesAndUsages = Optional.empty();
        
        opTypePanel.clear();
        dpTypePanel.clear();
        
        this.chkUseDp.setSelected(false);
        this.chkUseOp.setSelected(false);
    }
    
    public void resetView() {
        
        if(availableTypesAndUsages.isPresent()) {
            enableOptions(availableTypesAndUsages.get());
        }
    }
    
    public void setFontSize(int fontSize) {
        Font font = chkUseDp.getFont().deriveFont(Font.BOLD, fontSize);
        
        chkUseDp.setFont(font);
        chkUseOp.setFont(font);
        
        opTypePanel.setFontSize(fontSize);
        dpTypePanel.setFontSize(fontSize);
    }
}
