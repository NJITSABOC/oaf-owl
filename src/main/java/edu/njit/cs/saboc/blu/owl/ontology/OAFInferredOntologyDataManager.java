
package edu.njit.cs.saboc.blu.owl.ontology;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 *
 * @author Chris Ochs
 */
public class OAFInferredOntologyDataManager extends OAFOntologyDataManager {

    private final OAFOntologyDataManager sourceManager;

    private boolean initialized = false;
    
    public OAFInferredOntologyDataManager(OAFOntologyDataManager sourceManager) {

        super(sourceManager.getOAFStateFileManager(),
                sourceManager.getManager(),
                sourceManager.getOntologyFile(),
                sourceManager.getOntologyName(),
                sourceManager.getSourceOntology());
        
        this.sourceManager = sourceManager;
    }

    @Override
    protected OAFOWLOntology createOAFOntology() {
        return createInferredOntology();
    }

    private OAFOWLOntology createInferredOntology() {
        
        OWLOntology ontology = getSourceOntology();
        
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        
        Map<OWLClass, OWLConcept> clsToConceptMap = new HashMap<>();
             
        Hierarchy<OWLConcept> inferredHierarchy = new Hierarchy<>(
                sourceManager.getOntology().getOWLConceptFor(df.getOWLThing()));
        
        Configuration configuration = new Configuration();

        OWLReasoner reasoner;
        
        try {
            reasoner = new Reasoner(configuration, ontology);
        } catch(Exception e) {
            return new OAFOWLOntology(inferredHierarchy, this); 
        }
        
        reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
        
        if(!reasoner.isConsistent()) {
            return new OAFOWLOntology(inferredHierarchy, this); 
        }
        
        Queue<OWLConcept> queue = new ArrayDeque<>();
        
        Set<OWLConcept> processed = new HashSet<>();
        Set<OWLConcept> inQueue = new HashSet<>();

        queue.add(inferredHierarchy.getRoot());
        inQueue.add(inferredHierarchy.getRoot());
        
        // BFS inferred hierarchy to copy hierarchy into OAF hierarchy
        while (!queue.isEmpty()) {
            OWLConcept cls = queue.remove();
            
            processed.add(cls);
            inQueue.remove(cls);

            NodeSet<OWLClass> subclasses = reasoner.getSubClasses(cls.getCls(), true);

            Set<OWLConcept> childrenConcepts = subclasses.getFlattened().stream().map((childCls) -> {
                
                if(!clsToConceptMap.containsKey(childCls)) {
                    clsToConceptMap.put(childCls, new OWLConcept(childCls, this));
                }

                return clsToConceptMap.get(childCls);
                
            }).collect(Collectors.toSet());

            childrenConcepts.forEach( (child) -> {
                
                if(!child.getCls().isOWLNothing()) {
                    inferredHierarchy.addEdge(child, cls);

                    if (!inQueue.contains(child) && !processed.contains(child)) {
                        queue.add(child);
                        inQueue.add(child);
                    }
                }
            });
        }

        this.initialized = true;
        
        return new OAFOWLOntology(inferredHierarchy, this);
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
}
    
    
