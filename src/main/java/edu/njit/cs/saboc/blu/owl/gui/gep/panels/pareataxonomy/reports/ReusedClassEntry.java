package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLClass;

/**
 *
 * @author Chris O
 */
public class ReusedClassEntry extends OWLBaseReuseEntry<OWLClass> {
    
    private final OWLConcept concept;
    
    private final Set<PArea> pareas;
    
    public ReusedClassEntry(OWLConcept concept, Set<PArea> pareas) {
        super(ReusedEntityType.Class, concept.getCls());
        
        this.concept = concept;
        
        this.pareas = pareas;
    }
    
    public OWLConcept getConcept() {
        return concept;
    }
    
    public Set<PArea> getPAreas() {
        return pareas;
    }
}
