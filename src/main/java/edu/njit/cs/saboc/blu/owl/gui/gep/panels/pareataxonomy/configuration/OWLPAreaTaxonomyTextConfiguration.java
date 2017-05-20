package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyTextConfiguration;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyTextConfiguration extends PAreaTaxonomyTextConfiguration {

    public OWLPAreaTaxonomyTextConfiguration(PAreaTaxonomy taxonomy) {
        super(new OWLEntityNameConfiguration(), taxonomy);
    }
    
    public OWLTaxonomy getTaxonomy() {
        return (OWLTaxonomy)super.getPAreaTaxonomy();
    }
    
    public String getPropertyUsageStr(Set<PropertyTypeAndUsage> propertyTypes) {
        
        if(propertyTypes.isEmpty()) {
            return "No Property Types and Usages Selected";
        }
        
        ArrayList<String> propertyTypeStrs = new ArrayList<>();

        propertyTypes.forEach( (propertyType) -> {
            propertyTypeStrs.add(getTypeAndUsageStr(propertyType));
        });
        
        Collections.sort(propertyTypeStrs);
        
        String result = propertyTypeStrs.get(0);
        
        for(int c = 1; c < propertyTypeStrs.size(); c++) {
            result += String.format("<br>%s", propertyTypeStrs.get(c));
        }
        
        return result;        
    }
    
    private String getTypeAndUsageStr(PropertyTypeAndUsage typeAndUsage) {
        if(typeAndUsage.equals(PropertyTypeAndUsage.OP_DOMAIN)) {
            return "Object Property Domains";
        } else if(typeAndUsage.equals(PropertyTypeAndUsage.OP_RESTRICTION)) {
            return "Object Properties in Class Restrictions";
        } else if(typeAndUsage.equals(PropertyTypeAndUsage.OP_EQUIV)) {
            return "Object Properties in Class Equivalence Restrictions";
        } else if(typeAndUsage.equals(PropertyTypeAndUsage.DP_DOMAIN)) {
            return "Data Property Domains";
        } else if(typeAndUsage.equals(PropertyTypeAndUsage.DP_RESTRICTION)) {
            return "Data Properties in Class Restrictions";
        } else if(typeAndUsage.equals(PropertyTypeAndUsage.DP_EQUIV)) {
            return "Data Properties in Class Equivalence Restrictions";
        } else {
            return "[UNKNOWN TYPE AND USAGE]";
        }
    }
    
    @Override
    public String getAbNSummary() {
        String summary = super.getAbNSummary();
                
        summary += "<p><b>Derived using:</b><br>";
        summary += getPropertyUsageStr(this.getTaxonomy().getPropertyTypesAndUsages());
        
        return summary;
    }
}
