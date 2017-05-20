package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 *
 * @author Chris O
 */
public class ObjectPropertyCombinedRestrictionResult extends CombinedPropertyRestrictionResult<
        ObjectPropertyRestrictionResult,
        OWLObjectProperty,
        OWLClassExpression> {
    
    public ObjectPropertyCombinedRestrictionResult(ArrayList<ObjectPropertyRestrictionResult> allResults) {
        super(allResults);
    }
}
