package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import org.semanticweb.owlapi.model.OWLEntity;

/**
 *
 * @author Chris O
 */
public class OWLBaseReuseEntry<T extends OWLEntity> {
    public enum ReusedEntityType {
        Class,
        ObjectProperty,
        DataProperty
    }
    
    private final ReusedEntityType reuseType;
    private final T reusedEntity;
    
    public OWLBaseReuseEntry(ReusedEntityType type, T reusedEntity) {
        this.reuseType = type;
        this.reusedEntity = reusedEntity;
    }
    
    public ReusedEntityType getReusedEntityType() {
        return reuseType;
    }
    
    public T getReusedEntity() {
        return reusedEntity;
    }
}
