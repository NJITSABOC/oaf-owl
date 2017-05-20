package edu.njit.cs.saboc.blu.owl.nat.restrictions;

import java.util.Objects;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 *
 * @param <T>
 * @param <V>
 */
public class IntermediatePropertyRestrictionResult<T extends OWLProperty, V extends OWLObject> {

    private final T property;
    private final V filler;

    public IntermediatePropertyRestrictionResult(T property, V filler) {
        this.property = property;
        this.filler = filler;
    }
    
    public T getProperty() {
        return property;
    }
    
    public V getFiller() {
        return filler;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 23 * hash + Objects.hashCode(this.property);
        hash = 23 * hash + Objects.hashCode(this.filler);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final IntermediatePropertyRestrictionResult<?, ?> other = (IntermediatePropertyRestrictionResult<?, ?>) obj;

        if (!Objects.equals(this.property, other.property)) {
            return false;
        }

        if (!Objects.equals(this.filler, other.filler)) {
            return false;
        }

        return true;
    }
}
