package edu.njit.cs.saboc.blu.owl.ontology;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.core.abn.targetbased.RelationshipTriple;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFStateFileManager;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.utils.BLUEntityRetriever;
import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.util.SimpleRootClassChecker;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLQuantifiedObjectRestrictionImpl;

/**
 *
 * @author Chris
 */
public class OAFOntologyDataManager implements OntologySearcher<OWLConcept> {
    
    private final String ontologyFileName;
    
    private File ontologyFile;
    
    private final OWLOntologyManager manager;
    
    private final OWLOntology sourceOntology;
    
    private final OAFStateFileManager stateFileManager;
    
    private OWLBrowserDataSource browserDataSource;
    
    private OAFOWLOntology oafOntology;
    
    private OAFInferredOntologyDataManager inferredManager = null;
    
    public OAFOntologyDataManager(
            OAFStateFileManager stateFileManager,
            OWLOntologyManager manager, 
            File ontologyFile, 
            String ontologyName, 
            OWLOntology ontology) {
        
        this.stateFileManager = stateFileManager;
        this.manager = manager;
        
        this.sourceOntology = ontology;
        this.ontologyFileName = ontologyName;
        this.ontologyFile = ontologyFile;
    }
    
    public final void initialize() {
        
        setOAFOntology(createOAFOntology());
                
        this.browserDataSource = new OWLBrowserDataSource(this);
        
    }
    
    // Get a version of this manager based on the inferred hierarchy of the ontology
    public OAFInferredOntologyDataManager getInferredOntologyDataManager() throws OAFOntologyReasoningException {
        
        if(inferredManager == null) {
            inferredManager = new OAFInferredOntologyDataManager(this);
            inferredManager.initialize();
        }
        
        if(!inferredManager.isInitialized()) {
            throw new OAFOntologyReasoningException("Ontology did not reason properly...");
        }
        
        return inferredManager;
    }
    
    protected OAFOWLOntology createOAFOntology() {
        OWLClass thing = manager.getOWLDataFactory().getOWLThing();

        OWLConcept thingConcept = new OWLConcept(thing, this);

        Map<OWLClass, OWLConcept> conceptMap = new HashMap<>();
        conceptMap.put(thing, thingConcept);

        Set<OWLClass> clses = sourceOntology.getClassesInSignature(true);

        clses.forEach( (cls) -> {
            conceptMap.put(cls, new OWLConcept(cls, this));
        });

        Hierarchy<OWLConcept> conceptHierarchy = new Hierarchy<>(thingConcept);

        Set<OWLConcept> currentOntologyRoots = new HashSet<>();

        SimpleRootClassChecker checker = new SimpleRootClassChecker(Collections.singleton(sourceOntology));

        clses.forEach((cls) -> {
            if (!OWLUtilities.classIsObsolete(sourceOntology, cls)
                    && !cls.isOWLNothing()
                    && !cls.isOWLThing()) { // Ignore obsolete classes, nothing, and thing for specific roots

                if (checker.isRootClass(cls)) {
                    currentOntologyRoots.add(conceptMap.get(cls));
                }
            }
        });

        currentOntologyRoots.forEach((concept) -> {
            conceptHierarchy.addEdge(concept, conceptHierarchy.getRoot());
        });

        for (OWLClass cls : clses) {
            OWLConcept concept = conceptMap.get(cls);

            Collection<OWLClassExpression> superclasses = BLUEntityRetriever.getSuperClasses(cls, sourceOntology);

            superclasses.forEach((superCls) -> {
                if (superCls instanceof OWLClassImpl) {
                    OWLClass superclassAsCls = superCls.asOWLClass();
                    conceptHierarchy.addEdge(concept, conceptMap.get(superclassAsCls));
                }
            });

            // If there are no superclasses, use concept in equivalence
            if (conceptHierarchy.getParents(concept).isEmpty()) {
                Collection<OWLClassExpression> equivClasses = BLUEntityRetriever.getEquivalentClasses(cls, sourceOntology);

                equivClasses.forEach((expr) -> {
                    if (expr instanceof OWLObjectIntersectionOfImpl) {
                        Set<OWLClassExpression> exprs = expr.asConjunctSet();

                        for (OWLClassExpression clsExpr : exprs) {
                            if (!clsExpr.getObjectPropertiesInSignature().isEmpty()) {
                                break;
                            }

                            if (!clsExpr.getDataPropertiesInSignature().isEmpty()) {
                                break;
                            }
                        }

                        exprs.forEach((expandedExpr) -> {
                            if (expandedExpr instanceof OWLClassImpl) {
                                conceptHierarchy.addEdge(concept, conceptMap.get(expandedExpr.asOWLClass()));
                            }
                        });
                    }
                });
            }
        }
        
        return new OAFOWLOntology(conceptHierarchy, this);
    }
    
    public File getOntologyFile() {
        return ontologyFile;
    }

    public final void setOntologyFile(File file) {
        this.ontologyFile = file;
    }
    
    public OWLOntologyManager getManager() {
        return manager;
    }

    public OWLOntology getSourceOntology() {
        return sourceOntology;
    }
    
    public String getOntologyName() {
        return ontologyFileName;
    }
    
    protected void setOAFOntology(OAFOWLOntology oafOntology) {
        this.oafOntology = oafOntology;
    }
    
    public OAFOWLOntology getOntology() {
        return oafOntology;
    }
    
    public OAFStateFileManager getOAFStateFileManager() {
        return stateFileManager;
    }
    
    public OWLBrowserDataSource getClassBrowserDataSource() {
        return browserDataSource;
    }
    
    public boolean allClassesHaveDefinedSuperclass() {
        
        Set<OWLClass> clses = sourceOntology.getClassesInSignature();
        
        SimpleRootClassChecker checker = new SimpleRootClassChecker(Collections.singleton(sourceOntology));
        
        for (OWLClass cls : clses) {
            
            if (OWLUtilities.classIsObsolete(sourceOntology, cls) || cls.isOWLNothing() || cls.isOWLThing()) {
                continue;
            }
            
            if(checker.isRootClass(cls)) {
                continue;
            }

            Collection<OWLClassExpression> parentExprs = BLUEntityRetriever.getSuperClasses(cls, sourceOntology);
            
            if (parentExprs.isEmpty()) {
                return false;
            } else {
                
                boolean parentFound = false;
                
                for (OWLClassExpression parentExpr : parentExprs) {
                    if (parentExpr instanceof OWLClassImpl) {
                        parentFound = true;
                    }
                }
                
                if(!parentFound) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public Map<OWLConcept, Set<OWLInheritableProperty>> getOntologyInferredProperties(Set<PropertyTypeAndUsage> propertyTypesAndUsages) {
        return getPropertyInheritance(getOntology().getConceptHierarchy(), getStatedProperties(propertyTypesAndUsages));
    }
    
    private Map<OWLConcept, Set<OWLInheritableProperty>> getStatedProperties(Set<PropertyTypeAndUsage> propertyTypesAndUsages) {
        Hierarchy<OWLConcept> conceptHierarchy = getOntology().getConceptHierarchy();
        
        // Compute for every class in the ontology, regardless of the selected subhierarchy.
        // This ensures we know which properties are used, regardless of the chosen
        // subhierarchy.
        Set<OWLConcept> concepts = conceptHierarchy.getNodes();

        Map<OWLConcept, Set<OWLInheritableProperty>> statedProperties = new HashMap<>();

        concepts.forEach((concept) -> {
            statedProperties.put(concept, new HashSet<>());
        });
        
        concepts.forEach((concept) -> {

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_RESTRICTION)) {
                Set<OWLProperty> properties = (Set<OWLProperty>)(Set<?>)OWLPropertyUtilities.getOPUsedInClassRestriction(concept, sourceOntology);
                
                statedProperties.get(concept).addAll(batchCreatePropertyDetails(properties, PropertyTypeAndUsage.OP_RESTRICTION, InheritanceType.Introduced));
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_EQUIV)) {
                Set<OWLProperty> properties = (Set<OWLProperty>)(Set<?>)OWLPropertyUtilities.getOPUsedInClassEquivRestriction(concept, sourceOntology);
                statedProperties.get(concept).addAll(batchCreatePropertyDetails(properties, PropertyTypeAndUsage.OP_EQUIV, InheritanceType.Introduced));
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_RESTRICTION)) {
                Set<OWLProperty> properties = (Set<OWLProperty>)(Set<?>)OWLPropertyUtilities.getDPUsedInClassRestriction(concept, sourceOntology);
                statedProperties.get(concept).addAll(batchCreatePropertyDetails(properties, PropertyTypeAndUsage.DP_RESTRICTION, InheritanceType.Introduced));
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_EQUIV)) {
                Set<OWLProperty> properties = (Set<OWLProperty>)(Set<?>)OWLPropertyUtilities.getDPUsedInClassEquivRestriction(concept, sourceOntology);
                statedProperties.get(concept).addAll(batchCreatePropertyDetails(properties, PropertyTypeAndUsage.DP_EQUIV, InheritanceType.Introduced));
            }
        });

        if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_DOMAIN)) {
            Set<OWLObjectProperty> objectProperties = sourceOntology.getObjectPropertiesInSignature(true);
            
            objectProperties.forEach( (objectProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getDomainForProperty(objectProperty, this);
                
                OWLInheritableProperty statedProperty = new OWLInheritableProperty(
                        objectProperty, 
                        InheritableProperty.InheritanceType.Introduced, 
                        PropertyTypeAndUsage.OP_DOMAIN, 
                        this);

                domain.forEach((concept) -> {
                    statedProperties.get(concept).add(statedProperty);
                });
            });
        }

        if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_DOMAIN)) {
            
            Set<OWLDataProperty> dataProperties = sourceOntology.getDataPropertiesInSignature(true);
            
            dataProperties.forEach( (dataProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getDomainForProperty(dataProperty, this);

                OWLInheritableProperty statedProperty = new OWLInheritableProperty(
                        dataProperty, 
                        InheritableProperty.InheritanceType.Introduced, 
                        PropertyTypeAndUsage.DP_DOMAIN, 
                        this);

                domain.forEach((concept) -> {
                    statedProperties.get(concept).add(statedProperty);
                });
            });
        }
        
        return statedProperties;
    }
    
    private Map<OWLConcept, Set<OWLInheritableProperty>> getPropertyInheritance(
            Hierarchy<OWLConcept> conceptHierarchy,
            Map<OWLConcept, Set<OWLInheritableProperty>> explicitProperties) {
        
        Set<OWLConcept> subhierarchyConcepts = conceptHierarchy.getNodes();

        Map<OWLConcept, Integer> remainingParentCount = new HashMap<>();
        remainingParentCount.put(conceptHierarchy.getRoot(), 0);

        Map<OWLConcept, Set<OWLInheritableProperty>> currentInferredProperties = new HashMap<>();
        
        OWLConcept hierarchyRoot = conceptHierarchy.getRoot();

        subhierarchyConcepts.forEach( (concept) -> {
            if (!concept.equals(hierarchyRoot)) {
                Set<OWLConcept> parents = conceptHierarchy.getParents(concept);

                int parentCount = 0;

                for (OWLConcept parent : parents) {
                    if (subhierarchyConcepts.contains(parent)) {
                        parentCount++;
                    }
                }

                remainingParentCount.put(concept, parentCount);
            }

            currentInferredProperties.put(concept, new HashSet<>());
        });
        
        Stack<OWLConcept> pendingConcepts = new Stack<>();
        pendingConcepts.push(hierarchyRoot);

        // Do a topological traversal of the class hierarchy.
        while (!pendingConcepts.isEmpty()) {
            
            OWLConcept concept = pendingConcepts.pop();

            if (concept.equals(hierarchyRoot)) {
                Set<OWLInheritableProperty> rootProperties = explicitProperties.get(concept);

                rootProperties.forEach((detail) -> {
                    currentInferredProperties.get(concept).add(
                            new OWLInheritableProperty(
                                    detail.getPropertyType(),
                                    InheritableProperty.InheritanceType.Introduced,
                                    detail.getPropertyTypeAndUsage(), 
                                    detail.getManager()));
                });
            } else {
                Set<OWLInheritableProperty> parentProperties = new HashSet<>();

                Set<OWLConcept> parents = conceptHierarchy.getParents(concept);

                parents.forEach((parent) -> {
                    parentProperties.addAll(currentInferredProperties.get(parent));
                });

                Set<OWLInheritableProperty> allProperties = new HashSet<>();

                allProperties.addAll(explicitProperties.get(concept));
                allProperties.addAll(parentProperties);

                for (OWLInheritableProperty property : allProperties) {
                    if (parentProperties.contains(property)) {
                        currentInferredProperties.get(concept).add(
                                new OWLInheritableProperty(
                                        property.getPropertyType(), 
                                        InheritableProperty.InheritanceType.Inherited,
                                        property.getPropertyTypeAndUsage(),
                                        property.getManager()));
                        
                    } else {
                        currentInferredProperties.get(concept).add(
                                new OWLInheritableProperty(
                                        property.getPropertyType(),
                                        InheritableProperty.InheritanceType.Introduced,
                                        property.getPropertyTypeAndUsage(),
                                        property.getManager()));
                    }
                }
            }

            Set<OWLConcept> children = conceptHierarchy.getChildren(concept);

            for (OWLConcept child : children) {
                int parentCount = remainingParentCount.get(child) - 1;

                if (parentCount == 0) {
                    pendingConcepts.add(child);
                } else {
                    remainingParentCount.put(child, parentCount);
                }
            }
        }

        return currentInferredProperties; 
    }
    
    private Set<OWLInheritableProperty> batchCreatePropertyDetails(
            Set<OWLProperty> properties, 
            PropertyTypeAndUsage typeAndUsage,
            InheritableProperty.InheritanceType inheritanceType) {
        
        Set<OWLInheritableProperty> details = new HashSet<>();
        
        properties.forEach((property) -> {
            details.add(new OWLInheritableProperty(property, inheritanceType, typeAndUsage, this));
        });
        
        return details;
    }
    
    public Set<PropertyTypeAndUsage> getAvailablePropertyTypesInSubhierarchy(OWLConcept root) {
        Set<PropertyTypeAndUsage> typesAndUsages = new HashSet<>();

        Hierarchy<OWLConcept> subhierarchy = this.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(root);
        
        Map<OWLConcept, Set<OWLInheritableProperty>> properties = getOntologyInferredProperties(PropertyTypeAndUsage.getAll());
        
        subhierarchy.getNodes().forEach( (concept) -> {
            properties.get(concept).forEach( (property) -> {
                typesAndUsages.add(property.getPropertyTypeAndUsage());
            });
        });
        
        return typesAndUsages;
    }
    
    public Set<OWLInheritableProperty> getPropertiesInSubhierarchy(OWLConcept root, Set<PropertyTypeAndUsage> usages) {
        Set<OWLInheritableProperty> properties = new HashSet<>();
        
        Hierarchy<OWLConcept> subhierarchy = this.getOntology().getConceptHierarchy().getSubhierarchyRootedAt(root);
        
        Map<OWLConcept, Set<OWLInheritableProperty>> conceptProperties = getOntologyInferredProperties(usages);
        
        subhierarchy.getNodes().forEach( (concept) -> {
            properties.addAll(conceptProperties.get(concept));
        });
        
        return properties;
    }
    
    public Set<RelationshipTriple> getSimpleRestrictionTriplesFromHierarchy(OWLConcept root, Set<OWLInheritableProperty> types) {        
        return getSimpleRestrictionTriplesFromHierarchy(getOntology().getConceptHierarchy().getSubhierarchyRootedAt(root), types);
    }
    
    public Set<RelationshipTriple> getSimpleRestrictionTriplesFromHierarchy(Hierarchy<OWLConcept> hierarchy, Set<OWLInheritableProperty> types) {
        Set<RelationshipTriple> triples = new HashSet<>();

        hierarchy.getNodes().forEach( (concept) -> {
            triples.addAll(getSimpleRestrictionsTriplesFor(concept, types));
        });
        
        return triples;
    }
    
    private Set<RelationshipTriple> getSimpleRestrictionsTriplesFor(OWLConcept concept, Set<OWLInheritableProperty> types) {
        
        Collection<OWLClassExpression> exprs = BLUEntityRetriever.getSuperClasses(concept.getCls(), this.getSourceOntology());
        
        Set<RelationshipTriple> result = new HashSet<>();
        
        exprs.forEach((expr) -> {
            if (expr instanceof OWLQuantifiedObjectRestrictionImpl) {
                OWLQuantifiedObjectRestrictionImpl restriction = (OWLQuantifiedObjectRestrictionImpl) expr;

                try {
                    RelationshipTriple triple = convertToSimpleRestrictionTriple(concept, restriction, PropertyTypeAndUsage.OP_RESTRICTION);
                    
                    if(types.contains((OWLInheritableProperty)triple.getRelationship())) {
                        result.add(triple);
                    }
                    
                } catch (PropertyAnonymousException pae) {

                } catch (UnsupportedFillerTypeException ufte) {

                }
            }
        });
        
        Collection<OWLClassExpression> equivExprs = BLUEntityRetriever.getEquivalentClasses(concept.getCls(), this.getSourceOntology());

        equivExprs.forEach((expr) -> {

            if (expr instanceof OWLObjectIntersectionOfImpl) {
                OWLObjectIntersectionOfImpl intersection = ((OWLObjectIntersectionOfImpl) expr);

                Set<OWLClassExpression> conjunctExprs = intersection.asConjunctSet();

                for (OWLClassExpression conjunctExpr : conjunctExprs) {
                    if (conjunctExpr instanceof OWLQuantifiedObjectRestrictionImpl) {
                        OWLQuantifiedObjectRestrictionImpl restriction = (OWLQuantifiedObjectRestrictionImpl) conjunctExpr;

                        try {
                            RelationshipTriple triple = convertToSimpleRestrictionTriple(concept, restriction, PropertyTypeAndUsage.OP_EQUIV);

                            if (types.contains((OWLInheritableProperty) triple.getRelationship())) {
                                result.add(triple);
                            }
                        } catch (PropertyAnonymousException pae) {

                        } catch (UnsupportedFillerTypeException ufte) {

                        }
                    }
                }
            }
        });

        return result;
    }
    
    public Hierarchy<OWLConcept> getSimpleRestrictionTargetHierarchy(OWLConcept root, Set<OWLInheritableProperty> types) {
        Set<RelationshipTriple> triples = this.getSimpleRestrictionTriplesFromHierarchy(root, types);
        
        Set<OWLConcept> targets = new HashSet<>();
        
        triples.forEach( (triple) -> {
            targets.add((OWLConcept)triple.getTarget());
        });
        
        return getOntology().getConceptHierarchy().getAncestorHierarchy(targets);
    }
    
    private class PropertyAnonymousException extends Exception {
        public PropertyAnonymousException(OWLObjectPropertyExpression expr) {
            super("Property is anonymous: " + expr.toString());
        }
    }
    
    private class UnsupportedFillerTypeException extends Exception {
        public UnsupportedFillerTypeException(OWLClassExpression expr) {
            super("Unsupported Filler Type: " + expr.toString());
        }
    }
    
    private RelationshipTriple convertToSimpleRestrictionTriple(
            OWLConcept source, 
            OWLQuantifiedObjectRestrictionImpl restriction, 
            PropertyTypeAndUsage typeAndUsage) throws PropertyAnonymousException, UnsupportedFillerTypeException {
        
        OWLObjectPropertyExpression opExpr = restriction.getProperty();

        if (opExpr.isAnonymous()) {
            throw new PropertyAnonymousException(opExpr);
        }

        OWLObjectProperty property = opExpr.asOWLObjectProperty();

        OWLClassExpression filler = restriction.getFiller();

        if (filler.getClassExpressionType().equals(ClassExpressionType.OWL_CLASS)) {
            OWLInheritableProperty inheritableProperty = asIntroducedInheritableProperty(property, typeAndUsage);

            return new RelationshipTriple(source, inheritableProperty, getOntology().getOWLConceptFor(filler.asOWLClass()));
        } else {
            throw new UnsupportedFillerTypeException(filler);
        }
    }
    
    private OWLInheritableProperty asIntroducedInheritableProperty(OWLProperty property, PropertyTypeAndUsage typeAndUsage) {
        return new OWLInheritableProperty(
                                property,
                                InheritanceType.Introduced,
                                typeAndUsage,
                                this);
    }
    
    public Set<OWLConcept> getConceptsByLabels(Set<String> conceptNames) {
        Set<String> lowerCase = new HashSet<>();

        conceptNames.forEach((concept) -> {
            lowerCase.add(concept.toLowerCase());
        });

        Set<OWLConcept> result = new HashSet<>();

        this.getOntology().getConceptHierarchy().getNodes().forEach((concept) -> {
            OWLDataFactory dataFactory = OWLManager.getOWLDataFactory();

            Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(dataFactory.getRDFSLabel(), this.getSourceOntology());
            
            Set<OWLAnnotation> labelAnnotations = annotations.stream().filter( (annotation) -> {
                return annotation.getProperty().isLabel();  
            }).collect(Collectors.toSet());

            for (OWLAnnotation annotation : labelAnnotations) {
                
                if (annotation.getValue() instanceof OWLLiteral) {
                    OWLLiteral val = (OWLLiteral) annotation.getValue();

                    String literal = val.getLiteral().replaceAll("_", " ");

                    if (lowerCase.contains(literal.toLowerCase())) {
                        result.add(concept);
                    }
                }
            }
        });

        return result;
    }
    
    @Override
    public Set<OWLConcept> searchStarting(String str) {
        Predicate<OWLConcept> startingFilter = (concept) -> {
            return labelStartsWith(concept, str);
        };
        
        return doSearch(startingFilter);
    }
    
    @Override
    public Set<OWLConcept> searchExact(String str) {
        Predicate<OWLConcept> exactFilter = (concept) -> {
            return labelEquals(concept, str);
        };
        
        return doSearch(exactFilter);
    }
    
    @Override
    public Set<OWLConcept> searchAnywhere(String str) {
        Predicate<OWLConcept> anywhereFilter = (concept) -> {
            return labelContains(concept, str);
        };

        return doSearch(anywhereFilter);
    }
    
    @Override
    public Set<OWLConcept> searchID(String str) {
        return searchByIRI(str);
    }
    
    public Set<OWLConcept> searchByIRI(String str) {
        String lowerStr = str.toLowerCase();

        Predicate<OWLConcept> iriFilter = (concept) -> {
            return concept.getID().toString().toLowerCase().equals(lowerStr);
        };

        return doSearch(iriFilter);
    }
       
    private boolean labelEquals(OWLConcept concept, String str) {
        OWLClass cls = concept.getCls();

        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(cls, this.getSourceOntology());
        
        Set<OWLAnnotation> labelAnnotations = annotations.stream().filter((annotation) -> {
            return annotation.getProperty().isLabel();
        }).collect(Collectors.toSet());

        for (OWLAnnotation annotation : labelAnnotations) {
            if (annotation.getValue() instanceof OWLLiteral) {
                OWLLiteral val = (OWLLiteral) annotation.getValue();

                String literal = val.getLiteral().replaceAll("_", " ");
                
                if (literal.equalsIgnoreCase(str)) {
                    return true;
                }
            }
        }

        return concept.getName().equalsIgnoreCase(str);
    }
    
    private boolean labelStartsWith(OWLConcept concept, String str) {
        OWLClass cls = concept.getCls();

        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(cls, this.getSourceOntology());
        
        Set<OWLAnnotation> labelAnnotations = annotations.stream().filter((annotation) -> {
            return annotation.getProperty().isLabel();
        }).collect(Collectors.toSet());

        for (OWLAnnotation annotation : labelAnnotations) {
            if (annotation.getValue() instanceof OWLLiteral) {
                OWLLiteral val = (OWLLiteral) annotation.getValue();

                String literal = val.getLiteral().replaceAll("_", " ");

                if (literal.toLowerCase().startsWith(str.toLowerCase())) {
                    return true;
                }
            }
        }
        
        return concept.getName().toLowerCase().startsWith(str);
    }

    private boolean labelContains(OWLConcept concept, String str) {
        OWLClass cls = concept.getCls();

        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(cls, this.getSourceOntology());
        
        Set<OWLAnnotation> labelAnnotations = annotations.stream().filter((annotation) -> {
            return annotation.getProperty().isLabel();
        }).collect(Collectors.toSet());

        for (OWLAnnotation annotation : labelAnnotations) {
            OWLLiteral val = (OWLLiteral) annotation.getValue();

            String literal = val.getLiteral().replaceAll("_", " ");

            if (literal.toLowerCase().contains(str.toLowerCase())) {
                return true;
            }
        }

        return concept.getName().toLowerCase().contains(str);
    }
    
    private Set<OWLConcept> doSearch(Predicate<OWLConcept> filter) {
        Set<OWLConcept> result = oafOntology.getConceptHierarchy().getNodes().
                stream().filter(filter).collect(Collectors.toSet());
        
        return result;
    }

    @Override
    public String toString() {
        return ontologyFileName;
    }
}
