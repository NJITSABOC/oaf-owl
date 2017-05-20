package edu.njit.cs.saboc.blu.owl.utils.owlproperties;

/**
 *
 * @author Chris O
 */
public enum PropertyType {
    Object,
    Data;
    
    public String toString() {
        
        if(this == Object) {
            return "object property";
        } else {
            return "data property";
        }
    }
}
