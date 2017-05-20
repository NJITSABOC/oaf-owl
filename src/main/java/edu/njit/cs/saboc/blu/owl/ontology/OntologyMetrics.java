
package edu.njit.cs.saboc.blu.owl.ontology;

/**
 *
 * @author Chris O
 */
public class OntologyMetrics {
    public int hierarchyCount = -1;
    
    public int totalOP = -1;
    public int totalDP = -1;
    
    public int totalOPWithDomainCount = -1;
    public int totalDPWithDomainCount = -1;
    
    public int totalUniqueOPExplicitDomains = -1;
    public int totalUniqueDPExplicitDomains = -1;
    
    public int totalOPWithRestrictionCount = -1;
    public int totalDPWithRestrictionCount = -1;
    
    public int totalUniqueOPRestrictionDomains = -1;
    public int totalUniqueDPRestrictionDomains = -1;
    
    public int totalOPRestrictionEquivCount = -1;
    public int totalDPRestrictionEquivCount = -1;
    
    public int totalUniqueOPEquivDomains = -1;
    public int totalUniqueDPEquivDomains = -1;
    
    public int totalNumberOfClasses = -1;
    
    public int totalMultiParentClasses = -1;
    
    public boolean objectPropertiesAvailable() {
        return totalOPWithDomainCount > 0 || totalOPWithRestrictionCount > 0 || totalOPRestrictionEquivCount > 0;
    }
    
    public boolean dataPropertiesAvailable() {
        return totalDPWithDomainCount > 0 || totalDPWithRestrictionCount > 0 || totalDPRestrictionEquivCount > 0;
    }
    
    public boolean propertiesAvailable() {
        return objectPropertiesAvailable() || dataPropertiesAvailable();
    }
}
