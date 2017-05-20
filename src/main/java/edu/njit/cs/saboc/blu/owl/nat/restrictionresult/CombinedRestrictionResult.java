package edu.njit.cs.saboc.blu.owl.nat.restrictionresult;

import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CombinedRestrictionResult<T extends RestrictionResult> {
    
    private final ArrayList<T> allResults;
    
    public CombinedRestrictionResult(ArrayList<T> allResults) {
        this.allResults = allResults;
    }
    
    public ArrayList<T> getAllResults() {
        return allResults;
    }
    
    public Set<OWLConcept> getSourceConcepts() {
        return allResults.stream().map( 
                (result) -> result.getSourceConcept()).collect(Collectors.toSet());
    }
}
