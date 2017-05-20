package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;

/**
 *
 * @author Chris O
 */
public class OWLPropertyTableModel extends OAFAbstractTableModel<InheritableProperty> {
    
    private final boolean forArea;
    
    private static String [] getColumnNames(boolean forArea) {
        if(forArea) {
            return new String[]{"Name", "Type", "Usage"};
        } else {
            return new String[]{"Name", "Type", "Usage", "Inheritance"};
        }
    }
        
    public OWLPropertyTableModel(boolean forArea) {
        super(OWLPropertyTableModel.getColumnNames(forArea));
        
        this.forArea = forArea;
    }
    
    @Override
    protected Object[] createRow(InheritableProperty property) {
        OWLInheritableProperty owlProperty = (OWLInheritableProperty)property;

        if (forArea) {
            return new Object[]{
                owlProperty.getName(),
                owlProperty.getPropertyTypeAndUsage().getType().toString(),
                owlProperty.getPropertyTypeAndUsage().getUsage().toString()
            };
        } else {
            return new Object[]{
                owlProperty.getName(),
                owlProperty.getPropertyTypeAndUsage().getType().toString(),
                owlProperty.getPropertyTypeAndUsage().getUsage().toString(),
                owlProperty.getInheritanceType().toString()
            };
        }
    }
}
