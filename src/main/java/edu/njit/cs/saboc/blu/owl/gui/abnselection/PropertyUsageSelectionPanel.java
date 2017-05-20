package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyType;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class PropertyUsageSelectionPanel extends JPanel {
    
    public interface PropertUsagedChangedListener {
        public void propertyUsageChanged(PropertyTypeAndUsage usage, boolean included);
    }
    
    private final JCheckBox chkUseDomains = new JCheckBox("Domains");
    private final JCheckBox chkUseRestrictions = new JCheckBox("Class Restrictions");
    private final JCheckBox chkUseRestrictionsInEquiv = new JCheckBox("Equivalence Restrictions");
    
    private final ArrayList<PropertUsagedChangedListener> listeners = new ArrayList<>();
    
    private final PropertyType propertyType;
    
    public PropertyUsageSelectionPanel(PropertyType propertyType) {
        super(new BorderLayout());
        
        this.propertyType = propertyType;
        
        JPanel panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.setBorder(BorderFactory.createTitledBorder("Property Usage"));

        panel.add(chkUseDomains);
        panel.add(chkUseRestrictions);
        panel.add(chkUseRestrictionsInEquiv);
        
        chkUseDomains.addActionListener( (ae) -> {
            listeners.forEach( (listener) -> {
               if(propertyType.equals(PropertyType.Object)) {
                   listener.propertyUsageChanged(PropertyTypeAndUsage.OP_DOMAIN, chkUseDomains.isSelected());
               } else {
                   listener.propertyUsageChanged(PropertyTypeAndUsage.DP_DOMAIN, chkUseDomains.isSelected());
               }
            });
        });
        
        chkUseRestrictions.addActionListener((ae) -> {
            listeners.forEach((listener) -> {
                if (propertyType.equals(PropertyType.Object)) {
                    listener.propertyUsageChanged(PropertyTypeAndUsage.OP_RESTRICTION, chkUseRestrictions.isSelected());
                } else {
                    listener.propertyUsageChanged(PropertyTypeAndUsage.DP_RESTRICTION, chkUseRestrictions.isSelected());
                }
            });
        });
        
        chkUseRestrictionsInEquiv.addActionListener((ae) -> {
            listeners.forEach((listener) -> {
                if (propertyType.equals(PropertyType.Object)) {
                    listener.propertyUsageChanged(PropertyTypeAndUsage.OP_EQUIV, chkUseRestrictionsInEquiv.isSelected());
                } else {
                    listener.propertyUsageChanged(PropertyTypeAndUsage.DP_EQUIV, chkUseRestrictionsInEquiv.isSelected());
                }
            });
        });
        
        this.add(panel, BorderLayout.CENTER);
    }
    
    public void addPropertyUsageChangedListener(PropertUsagedChangedListener listener) {
        this.listeners.add(listener);
    }
    
    public void removePropertyUsageChangedListener(PropertUsagedChangedListener listener) {
        this.listeners.remove(listener);
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        chkUseDomains.setEnabled(enabled);
        chkUseRestrictions.setEnabled(enabled);
        chkUseRestrictionsInEquiv.setEnabled(enabled);
    }
    
    public void enableOptions(Set<PropertyTypeAndUsage> availableOptions) {
        
        chkUseDomains.setSelected(false);
        chkUseRestrictions.setSelected(false);
        chkUseRestrictionsInEquiv.setSelected(false);

        if (propertyType.equals(PropertyType.Object)) {
            if (availableOptions.contains(PropertyTypeAndUsage.OP_DOMAIN)) {
                chkUseDomains.setEnabled(true);
                chkUseDomains.setSelected(true);
            }

            if (availableOptions.contains(PropertyTypeAndUsage.OP_RESTRICTION)) {
                chkUseRestrictions.setEnabled(true);
                chkUseRestrictions.setSelected(true);
            }

            if (availableOptions.contains(PropertyTypeAndUsage.OP_EQUIV)) {
                chkUseRestrictionsInEquiv.setEnabled(true);
                chkUseRestrictionsInEquiv.setSelected(true);
            }
        } else {
            if (availableOptions.contains(PropertyTypeAndUsage.DP_DOMAIN)) {//Explicit Domain
                chkUseDomains.setEnabled(true);
                chkUseDomains.setSelected(true);
            }

            if (availableOptions.contains(PropertyTypeAndUsage.DP_RESTRICTION)) {//Restriction
                chkUseRestrictions.setEnabled(true);
                chkUseRestrictions.setSelected(true);
            }

            if (availableOptions.contains(PropertyTypeAndUsage.DP_EQUIV)) {//RestrictionEquivalence
                chkUseRestrictionsInEquiv.setEnabled(true);
                chkUseRestrictionsInEquiv.setSelected(true);
            }
        }
    }

    public void setSelectedUsageTypes(Set<PropertyTypeAndUsage> selectedTypes) {
        chkUseDomains.setSelected(false);
        chkUseRestrictions.setSelected(false);
        chkUseRestrictionsInEquiv.setSelected(false);
        
        if(this.propertyType.equals(PropertyType.Object)) {
            if(selectedTypes.contains(PropertyTypeAndUsage.OP_DOMAIN)) {
                chkUseDomains.setSelected(true);
            }
            
            if(selectedTypes.contains(PropertyTypeAndUsage.OP_RESTRICTION)) {
                chkUseRestrictions.setSelected(true);
            }
            
            if(selectedTypes.contains(PropertyTypeAndUsage.OP_EQUIV)) {
                chkUseRestrictionsInEquiv.setSelected(true);
            }
        } else {
            if(selectedTypes.contains(PropertyTypeAndUsage.DP_DOMAIN)) {
                chkUseDomains.setSelected(true);
            }
            
            if(selectedTypes.contains(PropertyTypeAndUsage.DP_RESTRICTION)) {
                chkUseRestrictions.setSelected(true);
            }
            
            if(selectedTypes.contains(PropertyTypeAndUsage.DP_EQUIV)) {
                chkUseRestrictionsInEquiv.setSelected(true);
            }
        }
    }
    
    public Set<PropertyTypeAndUsage> getSelectedUsageTypes() {
        Set<PropertyTypeAndUsage> result = new HashSet<>();
        
        if(this.propertyType.equals(PropertyType.Object)) {
            if(chkUseDomains.isSelected()) {
                result.add(PropertyTypeAndUsage.OP_DOMAIN);
            }

            if(chkUseRestrictions.isSelected()) {
                result.add(PropertyTypeAndUsage.OP_RESTRICTION);
            }

            if(chkUseRestrictionsInEquiv.isSelected()) {
                result.add(PropertyTypeAndUsage.OP_EQUIV);
            }
        } else {
            if(chkUseDomains.isSelected()) {
                result.add(PropertyTypeAndUsage.DP_DOMAIN);
            }

            if(chkUseRestrictions.isSelected()) {
                result.add(PropertyTypeAndUsage.DP_RESTRICTION);
            }

            if(chkUseRestrictionsInEquiv.isSelected()) {
                result.add(PropertyTypeAndUsage.DP_EQUIV);
            }
        }
        
        return result;
    }
    
    public void clear() {
        this.chkUseDomains.setSelected(false);
        this.chkUseRestrictions.setSelected(false);
        this.chkUseRestrictionsInEquiv.setSelected(false);
    }
    
    public void setFontSize(int fontSize) {
        Font font = chkUseDomains.getFont().deriveFont(Font.BOLD, fontSize);
        
        chkUseDomains.setFont(font);
        chkUseRestrictions.setFont(font);
        chkUseRestrictionsInEquiv.setFont(font);
    }
}
