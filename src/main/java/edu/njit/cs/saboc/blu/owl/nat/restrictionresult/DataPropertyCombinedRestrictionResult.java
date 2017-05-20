package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;

/**
 *
 * @author Chris O
 */
public class DataPropertyCombinedRestrictionResult extends CombinedPropertyRestrictionResult<
        DataPropertyRestrictionResult,
        OWLDataProperty,
        OWLDataRange> {
    
    public DataPropertyCombinedRestrictionResult(ArrayList<DataPropertyRestrictionResult> allResults) {
        super(allResults);
    }
}
