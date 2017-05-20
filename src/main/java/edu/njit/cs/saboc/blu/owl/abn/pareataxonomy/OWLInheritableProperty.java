package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class OWLInheritableProperty extends InheritableProperty<IRI, OWLProperty> {
    
    private final OAFOntologyDataManager manager;
    
    private final PropertyTypeAndUsage typeAndUsage;
    
    public OWLInheritableProperty(OWLProperty property, 
            InheritanceType inheritance, 
            PropertyTypeAndUsage typeAndUsage,
            OAFOntologyDataManager manager) {

        super(property.getIRI(), property, inheritance);
        
        this.manager = manager;
        
        this.typeAndUsage = typeAndUsage;
    }
    
    public OAFOntologyDataManager getManager() {
        return manager;
    }
    
    public PropertyTypeAndUsage getPropertyTypeAndUsage() {
        return typeAndUsage;
    }

    @Override
    public String getName() {
        return OWLUtilities.getPropertyLabel(manager.getSourceOntology(), getPropertyType());
    }

    @Override
    public String getIDAsString() {
        return super.getID().toString().toLowerCase();
    }
}
