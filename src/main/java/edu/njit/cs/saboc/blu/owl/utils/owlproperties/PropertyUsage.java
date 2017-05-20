package edu.njit.cs.saboc.blu.owl.utils.owlproperties;

/**
 *
 * @author Chris O
 */
public enum PropertyUsage {
    Domain,
    Restriction,
    Equivalence;
    
    public String toString() {
        switch(this) {
            case Domain: 
                return "domain";
            case Restriction:
                return "class restriction";
            case Equivalence: 
                return "class equivalence";
                
            default:
                return "[UNKNOWN PROPERTY USAGE TYPE]";
        }
    }
}
