package edu.njit.cs.saboc.blu.owl.ontology;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;

/**
 *
 * @author Chris O
 */
public class OWLConcept extends Concept<IRI> {
    private final OWLClass cls;
    private final OAFOntologyDataManager manager;
    
    public OWLConcept(OWLClass cls, OAFOntologyDataManager manager) {
        super(cls.getIRI());
        
        this.cls = cls;
        this.manager = manager;
    }

    @Override
    public String getName() {
        return OWLUtilities.getClassLabel(manager.getSourceOntology(), cls);
    }

    @Override
    public String getIDAsString() {
        return super.getID().toString().toLowerCase();
    }
    
    public OWLClass getCls() {
        return cls;
    }
    
    public OAFOntologyDataManager getOntologyDataManager() {
        return manager;
    }
}
