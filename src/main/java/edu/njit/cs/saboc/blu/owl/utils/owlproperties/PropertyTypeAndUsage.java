package edu.njit.cs.saboc.blu.owl.utils.owlproperties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PropertyTypeAndUsage {
 
    public static final PropertyTypeAndUsage OP_DOMAIN = new PropertyTypeAndUsage(PropertyType.Object, PropertyUsage.Domain);
    public static final PropertyTypeAndUsage OP_RESTRICTION = new PropertyTypeAndUsage(PropertyType.Object, PropertyUsage.Restriction);
    public static final PropertyTypeAndUsage OP_EQUIV = new PropertyTypeAndUsage(PropertyType.Object, PropertyUsage.Equivalence);

    public static final PropertyTypeAndUsage DP_DOMAIN = new PropertyTypeAndUsage(PropertyType.Data, PropertyUsage.Domain);
    public static final PropertyTypeAndUsage DP_RESTRICTION = new PropertyTypeAndUsage(PropertyType.Data, PropertyUsage.Restriction);
    public static final PropertyTypeAndUsage DP_EQUIV = new PropertyTypeAndUsage(PropertyType.Data, PropertyUsage.Equivalence);
        
    public static final Set<PropertyTypeAndUsage> getAll() {
        return new HashSet<>(
                Arrays.asList(
                        OP_DOMAIN, 
                        OP_RESTRICTION, 
                        OP_EQUIV, 
                        DP_DOMAIN, 
                        DP_RESTRICTION, 
                        DP_EQUIV)
                );
    }
    
    private final PropertyType type;
    private final PropertyUsage usage;
    
    private PropertyTypeAndUsage(PropertyType type, PropertyUsage usage) {
        this.type = type;
        this.usage = usage;
    }
    
    public PropertyType getType() {
        return type;
    }
    
    public PropertyUsage getUsage() {
        return usage;
    }
    
    @Override
    public String toString() {
        if(usage == PropertyUsage.Domain) {
            return String.format("%s %s",
                    type.toString(),
                    usage.toString());
        } else {
            return String.format("%s in %s", 
                    type.toString(), 
                    usage.toString());
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof PropertyTypeAndUsage) {
            PropertyTypeAndUsage other = (PropertyTypeAndUsage)o;
            
            return this.type == other.type && this.usage == other.usage;
        }
        
        return false;
    }
    
    public int hashCode() {
        return Integer.hashCode(this.type.ordinal() + this.usage.ordinal());
    }
}
