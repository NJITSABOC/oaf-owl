package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel.DataRetriever;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class OWLNATDataRetrievers {
    
    public static DataRetriever<OWLConcept, ArrayList<OWLConcept>> getEquivSuperclasses(OWLBrowserDataSource dataSource) {
        
        return new DataRetriever<OWLConcept, ArrayList<OWLConcept>>() {

            @Override
            public ArrayList<OWLConcept> getData(OWLConcept concept) {
                return dataSource.getSuperclassesInEquivalences(concept);
            }

            @Override
            public String getDataType() {
                return "Superclasses in Equivalences";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> getAllRestrictionsRetriever(OWLBrowserDataSource dataSource) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                return dataSource.getAllRestrictions(concept);
            }

            @Override
            public String getDataType() {
                return "All Restrictions";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> getStatedRestrictionsRetriever(OWLBrowserDataSource dataSource) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                return dataSource.getStatedRestrictions(concept);
            }

            @Override
            public String getDataType() {
                return "Stated Restrictions";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> getMostSpecificRestrictionsRetriever(OWLBrowserDataSource dataSource) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                return dataSource.getMostSpecificRestrictions(concept);
            }

            @Override
            public String getDataType() {
                return "Most Refined Restrictions";
            }
        };
    }
}
