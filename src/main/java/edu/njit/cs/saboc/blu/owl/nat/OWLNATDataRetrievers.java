package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultPanel.DataRetriever;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class OWLNATDataRetrievers {
    
    public static DataRetriever<OWLConcept, ArrayList<OWLConcept>> getEquivSuperclasses(
            NATBrowserPanel<OWLConcept> mainPanel) {
        
        return new DataRetriever<OWLConcept, ArrayList<OWLConcept>>() {

            @Override
            public ArrayList<OWLConcept> getData(OWLConcept concept) {
                
                if(mainPanel.getDataSource().isPresent()) {
                    OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
                    
                    return dataSource.getSuperclassesInEquivalences(concept);
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public String getDataType() {
                return "Superclasses in Equivalences";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> 
        getAllRestrictionsRetriever(NATBrowserPanel<OWLConcept> mainPanel) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                
                if(mainPanel.getDataSource().isPresent()) {
                    OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
                    
                    return dataSource.getAllRestrictions(concept);
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public String getDataType() {
                return "All Restrictions";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> 
        getStatedRestrictionsRetriever(NATBrowserPanel<OWLConcept> mainPanel) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                
                if(mainPanel.getDataSource().isPresent()) {
                    OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
                    
                    return dataSource.getStatedRestrictions(concept);
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public String getDataType() {
                return "Stated Restrictions";
            }
        };
    }
    
    public static DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>> 
        getMostSpecificRestrictionsRetriever(NATBrowserPanel<OWLConcept> mainPanel) {
        
        return new DataRetriever<OWLConcept, ArrayList<CombinedRestrictionResult>>() {

            @Override
            public ArrayList<CombinedRestrictionResult> getData(OWLConcept concept) {
                
                if(mainPanel.getDataSource().isPresent()) {
                    OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
                    
                    return dataSource.getMostSpecificRestrictions(concept);
                } else {
                    return new ArrayList<>();
                }
            }

            @Override
            public String getDataType() {
                return "Most Refined Restrictions";
            }
        };
    }
}
