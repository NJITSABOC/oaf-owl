package edu.njit.cs.saboc.blu.owl.abnhistory;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.OWLClassLocationDataFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.OWLPropertyLocationDataFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLPAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance.OWLImportSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance.OWLPAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.owl.abn.range.OWLRangeAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hao Liu
 */
public class OWLDerivationParser extends AbNDerivationParser {

    private final OAFOntologyDataManager manager;

    public OWLDerivationParser(OAFOntologyDataManager manager) {
        
        super(manager.getOntology(), 
                new OWLClassLocationDataFactory(manager.getOntology()), 
                new OWLPropertyLocationDataFactory(manager), 
                new OWLAbNDerivationFactory(manager));
        
        this.manager = manager;
    }

    @Override
    public AbNDerivation parseDerivationHistory(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("ClassName")) {
            throw new AbNParseException("Derivation history type not specified.");
        }
        
        String className = obj.get("ClassName").toString();

        if (className.equalsIgnoreCase("OWLPAreaTaxonomyDerivation")) {
            return parseOWLPAreaTaxonomyDerivation(obj);
        } else if (className.equalsIgnoreCase("OWLImportSubtaxonomyDerivation")) {
            return parseOWLImportSubtaxonomyDerivation(obj);
        } else {
           return super.parseDerivationHistory(obj);
        }
    }

    public OWLPAreaTaxonomyDerivation parseOWLPAreaTaxonomyDerivation(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("BaseDerivation")) {
            throw new AbNParseException("Base PArea Taxonomy Derivation not specified.");
        }
        
        Set<PropertyTypeAndUsage> typesAndUsages =  getTypesAndUsages(obj);
        
        OWLPAreaTaxonomyDerivation result = new OWLPAreaTaxonomyDerivation(
                super.parseSimplePAreaTaxonomyDerivation(
                        (JSONObject)obj.get("BaseDerivation"), 
                        new OWLPAreaTaxonomyFactory(manager, typesAndUsages)), 
                typesAndUsages);

        return result;
    }

    public OWLImportSubtaxonomyDerivation parseOWLImportSubtaxonomyDerivation(JSONObject obj) throws AbNParseException {

        if(!obj.containsKey("URIStrings")) {
            throw new AbNParseException("Import URIs not specified.");
        }
        
        JSONArray uriStringsJSON = (JSONArray)obj.get("URIStrings");
        
        Set<String> uriStrings = new HashSet<>();
        
        uriStringsJSON.forEach( (uriStringJSON) -> {
            uriStrings.add(uriStringJSON.toString());
        });

        if(uriStrings.isEmpty()) {
            throw new AbNParseException("No import URIs given.");
        }

        OWLImportSubtaxonomyDerivation result = new OWLImportSubtaxonomyDerivation(
                (PAreaTaxonomyDerivation)super.parseDerivationHistory(obj), 
                uriStrings);

        return result;
    }

    @Override
    public TargetAbNDerivation parseTargetAbNDerivation(JSONObject obj) throws AbNParseException {

        if (!obj.containsKey("PropertyID")) {
            throw new AbNParseException("Property ID not specified.");
        }

        Concept sourceHierarchyRoot = getRoot(obj, "SourceRootID");
        Concept targetHierarchyRoot = getRoot(obj, "TargetRootID");

        String propertyID = (String) obj.get("PropertyID");

        OWLPropertyLocationDataFactory propertyRetreiver = new OWLPropertyLocationDataFactory(manager);
        
        Set<InheritableProperty> property = propertyRetreiver.getPropertiesFromIds(new ArrayList<>(Arrays.asList(propertyID)));

        if (property.isEmpty()) {
            throw new AbNParseException("Property with specified ID not found.");
        }
        
        Hierarchy<OWLConcept> sourceHierarchy = manager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt((OWLConcept)sourceHierarchyRoot);
        Hierarchy<OWLConcept> targetHierarchy = manager.getOntology().getConceptHierarchy().getSubhierarchyRootedAt((OWLConcept)targetHierarchyRoot);
        
        OWLInheritableProperty owlProperty = (OWLInheritableProperty)property.iterator().next();
        
        OWLRangeAbstractionNetworkFactory factory = new OWLRangeAbstractionNetworkFactory(manager, sourceHierarchy, owlProperty, targetHierarchy);

        return new TargetAbNDerivation(
                manager.getOntology(),
                factory,
                sourceHierarchyRoot,
                property.iterator().next(),
                targetHierarchyRoot);

    }

    public Set<PropertyTypeAndUsage> getTypesAndUsages(JSONObject obj) throws AbNParseException {
        
        if(!obj.containsKey("typesAndUsages")) {
            throw new AbNParseException("Types and usages not set.");
        }
        
        JSONArray typesAndUsagesJSON = (JSONArray) obj.get("typesAndUsages");
        
        Set<String> usedTypesAndUsagesStrs = new HashSet<>();
        
        typesAndUsagesJSON.forEach( (entry) -> {
            usedTypesAndUsagesStrs.add(entry.toString().toLowerCase());
        });

        Set<PropertyTypeAndUsage> typesAndUsages = new HashSet<>();
        
        PropertyTypeAndUsage.getAll().stream().forEach( (p) -> {
            if(usedTypesAndUsagesStrs.contains(p.toString().toLowerCase())) {
                typesAndUsages.add(p);
            }
        });
        
        if(typesAndUsages.isEmpty()) {
            throw new AbNParseException("No types and usages selected.");
        }

        return typesAndUsages;
    }
}
