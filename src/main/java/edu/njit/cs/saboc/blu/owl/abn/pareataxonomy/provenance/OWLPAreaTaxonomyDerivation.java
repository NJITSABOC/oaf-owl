package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.ArrayList;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyDerivation extends SimplePAreaTaxonomyDerivation {
    
    private final Set<PropertyTypeAndUsage> typesAndUsages;
    private final SimplePAreaTaxonomyDerivation base;
    
    public OWLPAreaTaxonomyDerivation(
            SimplePAreaTaxonomyDerivation base, 
            Set<PropertyTypeAndUsage> typesAndUsages) {
        
        super(base);
        
        this.base = base;
        this.typesAndUsages = typesAndUsages;
    }
    
    public Set<PropertyTypeAndUsage> getSelectedTypesAndUsages() {
        return typesAndUsages;
    }

    @Override
    public String getDescription() {
        
        ArrayList<PropertyTypeAndUsage> sortedTypesAndUsages = new ArrayList<>(typesAndUsages);
        sortedTypesAndUsages.sort( (a, b) -> {
            return a.toString().compareTo(b.toString());
        });
        
        return String.format("Derived OWL PArea Taxonomy (%s)", sortedTypesAndUsages.size());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "OWLPAreaTaxonomyDerivation");       

        result.put("BaseDerivation", base.serializeToJSON());   

        JSONArray arr_typesAndUsages = new JSONArray();
        
        typesAndUsages.forEach(ty ->{
            arr_typesAndUsages.add(ty.toString());
        });
        
        result.put("typesAndUsages", arr_typesAndUsages);

        return result;
    }
}
