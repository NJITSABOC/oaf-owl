/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.PropertyLocationDataFactory;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 *
 * @author Hao Liu
 */
public class OWLPropertyLocationDataFactory implements PropertyLocationDataFactory{

    private final OAFOntologyDataManager manager;
    

    public OWLPropertyLocationDataFactory(OAFOntologyDataManager manager) {
        this.manager = manager;
    }
    
    @Override
    public Set<InheritableProperty> getPropertiesFromIds(ArrayList<String> ids) {
        Set<InheritableProperty> result = new HashSet<>();
        
        Set<OWLDataProperty> dataPropertySet = manager.getSourceOntology().getDataPropertiesInSignature();        
        
        Map<String, OWLDataProperty> dataPropertyMap = new HashMap<>();
        
        for (OWLDataProperty dataProperty : dataPropertySet) {
            dataPropertyMap.put(dataProperty.getIRI().toString().toLowerCase(), dataProperty);
        } 
        
        for (String id : ids) {
            if (dataPropertyMap.containsKey(id)) {
                OWLInheritableProperty owlProperty = new OWLInheritableProperty(dataPropertyMap.get(id), InheritableProperty.InheritanceType.Inherited, PropertyTypeAndUsage.DP_DOMAIN , manager);
                result.add(owlProperty);
            }
        }
        
        Set<OWLObjectProperty> objPropertySet = manager.getSourceOntology().getObjectPropertiesInSignature();
        Map<String, OWLObjectProperty> objPropertyMap = new HashMap<>();
        
        for (OWLObjectProperty objProperty : objPropertySet) {
            objPropertyMap.put(objProperty.getIRI().toString().toLowerCase(), objProperty);
        }        
        
        for (String id : ids) {
            if (objPropertyMap.containsKey(id)) {
                OWLInheritableProperty owlProperty = new OWLInheritableProperty(objPropertyMap.get(id), InheritableProperty.InheritanceType.Inherited, PropertyTypeAndUsage.DP_DOMAIN , manager);
                result.add(owlProperty);
            }
        }

        return result;
    }
}
