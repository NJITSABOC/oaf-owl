package edu.njit.cs.saboc.blu.owl.utils;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;

/**
 *
 * @author Chris O
 */
public class OWLEntityNameConfiguration implements OntologyEntityNameConfiguration {
    
    @Override
    public String getConceptTypeName(boolean plural) {
        if(plural) {
            return "Classes";
        } else {
            return "Class";
        }
    }

    @Override
    public String getPropertyTypeName(boolean plural) {
        if(plural) {
            return "Properties";
        } else {
            return "Property";
        }
    }

    @Override
    public String getParentConceptTypeName(boolean plural) {
        if(plural) {
            return "Superclasses";
        } else {
            return "Superclass";
        }
    }

    @Override
    public String getChildConceptTypeName(boolean plural) {
        if(plural) {
            return "Subclasses";
        } else {
            return "Subclass";
        }
    }
}
