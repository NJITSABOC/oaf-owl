package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import java.util.ArrayList;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class OtherCombinedRestrictionResult extends CombinedRestrictionResult<OtherRestrictionTypeResult> {
    
    public OtherCombinedRestrictionResult(ArrayList<OtherRestrictionTypeResult> allResults) {
        super(allResults);
    }
    
    public OWLClassExpression getRestriction() {
        return super.getAllResults().get(0).getRestriction();
    }
}
