package edu.njit.cs.saboc.blu.owl.nat.restrictions;

import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.DataPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherCombinedRestrictionResult;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class CombinedRestrictionsResults {

    private final ArrayList<ObjectPropertyCombinedRestrictionResult> opResults;
    private final ArrayList<DataPropertyCombinedRestrictionResult> dpResults;
    private final ArrayList<OtherCombinedRestrictionResult> otherResults;

    public CombinedRestrictionsResults(
            ArrayList<ObjectPropertyCombinedRestrictionResult> opResults,
            ArrayList<DataPropertyCombinedRestrictionResult> dpResults,
            ArrayList<OtherCombinedRestrictionResult> otherResults) {

        this.opResults = opResults;
        this.dpResults = dpResults;
        this.otherResults = otherResults;
    }
    
    public ArrayList<ObjectPropertyCombinedRestrictionResult> getObjectPropertyResults() {
        return opResults;
    }
    
    public ArrayList<DataPropertyCombinedRestrictionResult> getDataPropertyResults() {
        return dpResults;
    }
    
    public ArrayList<OtherCombinedRestrictionResult> getOtherResults() {
        return otherResults;
    }
}
