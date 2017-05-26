package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class OWLImportSubtaxonomyDerivation extends PAreaTaxonomyDerivation {
    
    private final PAreaTaxonomyDerivation base;
    private final Set<String> importURIs;
    
    public OWLImportSubtaxonomyDerivation(PAreaTaxonomyDerivation base, Set<String> importURIs) {
        
        super(base.getFactory());
        
        this.base = base;
        this.importURIs = importURIs;
    }
    
    public Set<String> getImportURIs() {
        return importURIs;
    }

    @Override
    public String getDescription() {
        return "Derived OWL Import Subtaxonomy";
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy baseTaxonomy = base.getAbstractionNetwork(ontology);
        
        OWLTaxonomy owlTaxonomy = (OWLTaxonomy)baseTaxonomy;
        
        return owlTaxonomy.createImportSubtaxonomy(importURIs);
    }
    
    @Override
    public String getName() {
        return String.format("%s (%s)", 
                base.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Import Subtaxonomy";
    }

    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName","OWLImportSubtaxonomyDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   

        JSONArray arr = new JSONArray();
        
        importURIs.forEach(URI ->{
            arr.add(URI);
        });
        
        result.put("URIs", arr);
        
        return result;
        
    }    
}
