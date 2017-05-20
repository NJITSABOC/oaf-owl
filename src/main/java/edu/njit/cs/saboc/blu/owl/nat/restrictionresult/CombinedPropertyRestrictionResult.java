package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class CombinedPropertyRestrictionResult<
        T extends PropertyRestrictionResult, 
        V extends OWLProperty, 
        U extends OWLObject> extends CombinedRestrictionResult<T>{
    
    
    public CombinedPropertyRestrictionResult(ArrayList<T> allResults) {
        super(allResults);
    }
    
    public V getProperty() {
        return (V)super.getAllResults().get(0).getProperty();
    }
    
    public U getFiller() {
        return (U)super.getAllResults().get(0).getFiller();
    }
}
