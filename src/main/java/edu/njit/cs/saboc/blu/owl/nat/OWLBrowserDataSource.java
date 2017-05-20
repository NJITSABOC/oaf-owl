package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.OWLClassLocationDataFactory;
import edu.njit.cs.saboc.blu.owl.nat.error.OtherRestrictionTypeError;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.DataPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.DataPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.RestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherRestrictionTypeResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictions.CombinedRestrictionsResults;
import edu.njit.cs.saboc.blu.owl.nat.restrictions.IntermediatePropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.BLUEntityRetriever;
import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import edu.njit.cs.saboc.nat.generic.data.ConceptBrowserDataSource;
import edu.njit.cs.saboc.nat.generic.data.NATConceptSearchResult;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.ErrorParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLRestriction;
import uk.ac.manchester.cs.owl.owlapi.OWLClassImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;

/**
 *
 * @author Chris O
 */
public class OWLBrowserDataSource extends ConceptBrowserDataSource<OWLConcept> {
    
    private final OAFOntologyDataManager dataManager;
    
    public OWLBrowserDataSource(OAFOntologyDataManager dataManager) {
        super(dataManager.getOntology());
        
        this.dataManager = dataManager;
    }
    
    public OAFOntologyDataManager getDataManager() {
        return dataManager;
    }
    
    @Override
    public String getFocusConceptText(OWLConcept concept) {
        
        return String.format("<html><font face='Arial' size = '5'>"
                + "<b>%s</b></font>"
                + "<font face='Arial' size = '3'>"
                + "<br>%s",
                concept.getName(), 
                concept.getIDAsString());
    }
    
    @Override
    public String getFocusConceptText(AuditSet<OWLConcept> auditSet, OWLConcept concept) {
        
        return String.format("<html><font face='Arial' size = '5'>"
                + "<b>%s</b></font>"
                + "<font face='Arial' size = '3'"
                + "<br>%s"
                + "<br>(%s)",
                concept.getName(), 
                concept.getIDAsString(),
                super.getStyledAuditStatusText(auditSet, concept));
    }

    @Override
    public ErrorParser<OWLConcept, InheritableProperty> getErrorParser() {
        return (ErrorParser<OWLConcept, InheritableProperty>)(ErrorParser<?, ?>)new OWLErrorParser(this);
    }

    @Override
    public ArrayList<NATConceptSearchResult<OWLConcept>> searchStarting(String str) {
        
        Set<OWLConcept> results = dataManager.searchStarting(str);
        
        ArrayList<NATConceptSearchResult<OWLConcept>> searchResults = new ArrayList<>();

        results.forEach((result) -> {
            searchResults.add(new NATConceptSearchResult<>(result, Collections.singleton(result.getName()), str));
        });

        searchResults.sort((a, b) -> {
            return a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName());
        });

        return searchResults;
    }

    @Override
    public ArrayList<NATConceptSearchResult<OWLConcept>> searchExact(String str) {
        Set<OWLConcept> results = dataManager.searchExact(str);

        ArrayList<NATConceptSearchResult<OWLConcept>> searchResults = new ArrayList<>();

        results.forEach((result) -> {
            searchResults.add(new NATConceptSearchResult<>(result, Collections.singleton(result.getName()), str));
        });

        searchResults.sort((a, b) -> {
            return a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName());
        });

        return searchResults;
    }

    @Override
    public ArrayList<NATConceptSearchResult<OWLConcept>> searchAnywhere(String str) {
        Set<OWLConcept> results = dataManager.searchAnywhere(str);

        ArrayList<NATConceptSearchResult<OWLConcept>> searchResults = new ArrayList<>();

        results.forEach((result) -> {
            searchResults.add(new NATConceptSearchResult<>(result, Collections.singleton(result.getName()), str));
        });

        searchResults.sort((a, b) -> {
            return a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName());
        });

        return searchResults;
    }

    @Override
    public ArrayList<NATConceptSearchResult<OWLConcept>> searchID(String str) {
        
        Set<OWLConcept> results = dataManager.searchID(str);
        
        ArrayList<NATConceptSearchResult<OWLConcept>> searchResults = new ArrayList<>();
        
        results.forEach( (result) -> {
            searchResults.add(new NATConceptSearchResult<>(result, Collections.singleton(result.getIDAsString()), str));
        });
        
        searchResults.sort( (a, b) -> {
            return a.getConcept().getName().compareToIgnoreCase(b.getConcept().getName());
        });
        
        return searchResults;
    }
    
    
    public ArrayList<CombinedRestrictionResult> getAllRestrictions(OWLConcept concept) {
        
        ArrayList<RestrictionResult> restrictions = new ArrayList<>();
        
        Set<OWLConcept> ancestors = this.getOntology().getConceptHierarchy().getAncestors(concept);
        
        restrictions.addAll(getConceptRestrictions(concept));
        
        ancestors.forEach( (ancestor) -> {
            restrictions.addAll(getConceptRestrictions(ancestor));
        });
                
        CombinedRestrictionsResults combinedResults = this.getUniqueRestrictions(restrictions);
        
        sortCombinedResults(combinedResults);
       
        ArrayList<CombinedRestrictionResult> sortedResults = new ArrayList<>(combinedResults.getObjectPropertyResults());
        sortedResults.addAll(combinedResults.getDataPropertyResults());
        sortedResults.addAll(combinedResults.getOtherResults());
        
        return sortedResults;
    }
    
    private void sortCombinedResults(CombinedRestrictionsResults combinedResults) {
        Set<OWLConcept> uniqueRangeClses = new HashSet<>();

        combinedResults.getObjectPropertyResults().forEach((combinedResult) -> {
            if (combinedResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                OWLClass cls = combinedResult.getFiller().asOWLClass();

                uniqueRangeClses.add(dataManager.getOntology().getOWLConceptFor(cls));
            }
        });

        Hierarchy<OWLConcept> targetAncestorHierarchy
                = dataManager.getOntology().getConceptHierarchy().getAncestorHierarchy(uniqueRangeClses);

        Map<OWLConcept, Integer> hierarchicalDepths = targetAncestorHierarchy.getAllLongestPathDepths();

        combinedResults.getObjectPropertyResults().sort((a, b) -> {
            String aName = OWLUtilities.getPropertyLabel(dataManager.getSourceOntology(), a.getProperty());
            String bName = OWLUtilities.getPropertyLabel(dataManager.getSourceOntology(), b.getProperty());

            if (aName.equals(bName)) {
                int aDepth;
                int bDepth;

                if (a.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                    OWLConcept aConcept = dataManager.getOntology().getOWLConceptFor(a.getFiller().asOWLClass());

                    aDepth = hierarchicalDepths.getOrDefault(aConcept, Integer.MAX_VALUE);
                } else {
                    aDepth = Integer.MAX_VALUE;
                }

                if (b.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                    OWLConcept bConcept = dataManager.getOntology().getOWLConceptFor(b.getFiller().asOWLClass());

                    bDepth = hierarchicalDepths.getOrDefault(bConcept, Integer.MAX_VALUE);
                } else {
                    bDepth = Integer.MAX_VALUE;
                }

                if (aDepth == bDepth) {
                    String aFillerName = AxiomStringGenerator.getClassExpressionStr(dataManager.getSourceOntology(), a.getFiller(), false);
                    String bFillerName = AxiomStringGenerator.getClassExpressionStr(dataManager.getSourceOntology(), b.getFiller(), false);

                    return aFillerName.compareToIgnoreCase(bFillerName);
                } else {
                    return aDepth - bDepth;
                }
            } else {
                return aName.compareToIgnoreCase(bName);
            }
        });

        combinedResults.getDataPropertyResults().sort((a, b) -> {
            String aName = OWLUtilities.getPropertyLabel(dataManager.getSourceOntology(), a.getProperty());
            String bName = OWLUtilities.getPropertyLabel(dataManager.getSourceOntology(), b.getProperty());

            return aName.compareToIgnoreCase(bName);
        });

        combinedResults.getOtherResults().sort((a, b) -> {
            String aStr = AxiomStringGenerator.getClassExpressionStr(dataManager.getSourceOntology(), a.getRestriction(), false);
            String bStr = AxiomStringGenerator.getClassExpressionStr(dataManager.getSourceOntology(), b.getRestriction(), false);

            return aStr.compareToIgnoreCase(bStr);
        });
    }
    
    public ArrayList<CombinedRestrictionResult> getStatedRestrictions(OWLConcept concept) {
        ArrayList<RestrictionResult> restrictions = new ArrayList<>(getConceptRestrictions(concept));

        CombinedRestrictionsResults combinedResults = this.getUniqueRestrictions(restrictions);

        sortCombinedResults(combinedResults);

        ArrayList<CombinedRestrictionResult> sortedResults = new ArrayList<>(combinedResults.getObjectPropertyResults());
        sortedResults.addAll(combinedResults.getDataPropertyResults());
        sortedResults.addAll(combinedResults.getOtherResults());

        return sortedResults;
    }

    public ArrayList<CombinedRestrictionResult> getMostSpecificRestrictions(OWLConcept concept) {
        ArrayList<RestrictionResult> restrictions = new ArrayList<>();
        
        Set<OWLConcept> ancestors = this.getOntology().getConceptHierarchy().getAncestors(concept);
        
        restrictions.addAll(getConceptRestrictions(concept));
        
        ancestors.forEach( (ancestor) -> {
            restrictions.addAll(getConceptRestrictions(ancestor));
        });
                
        CombinedRestrictionsResults combinedResults = this.getUniqueRestrictions(restrictions);
        
        Map<OWLObjectProperty, Set<OWLConcept>> uniqueTargets = new HashMap<>();
        
        combinedResults.getObjectPropertyResults().forEach( (result) -> {
            if(result.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                
                if(!uniqueTargets.containsKey(result.getProperty())) {
                    uniqueTargets.put(result.getProperty(), new HashSet<>());
                }
                
                OWLConcept targetConcept = dataManager.getOntology().getOWLConceptFor(result.getFiller().asOWLClass());
                
                uniqueTargets.get(result.getProperty()).add(targetConcept);
            }
        });
        
        Map<OWLObjectProperty, Set<OWLConcept>> allowedTargets = new HashMap<>();
        
        uniqueTargets.forEach( (property, targets) -> {
            Hierarchy<OWLConcept> targetHierarchy = dataManager.getOntology().getConceptHierarchy().getAncestorHierarchy(targets);
            
            allowedTargets.put(property, targetHierarchy.getLeaves());
        });
        
        ArrayList<ObjectPropertyCombinedRestrictionResult> filteredOPResults = new ArrayList<>();
        
        combinedResults.getObjectPropertyResults().forEach( (result) -> {
            if(result.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                OWLConcept targetConcept = dataManager.getOntology().getOWLConceptFor(result.getFiller().asOWLClass());
                
                if(allowedTargets.get(result.getProperty()).contains(targetConcept)) {
                    filteredOPResults.add(result);
                }
            } else {
                filteredOPResults.add(result);
            }
        });
        
        CombinedRestrictionsResults filteredCombinedResults = new CombinedRestrictionsResults(
                filteredOPResults, 
                combinedResults.getDataPropertyResults(),
                combinedResults.getOtherResults());
        
        sortCombinedResults(filteredCombinedResults);
       
        ArrayList<CombinedRestrictionResult> sortedResults = new ArrayList<>(filteredCombinedResults.getObjectPropertyResults());
        sortedResults.addAll(filteredCombinedResults.getDataPropertyResults());
        sortedResults.addAll(filteredCombinedResults.getOtherResults());
        
        return sortedResults;
    }
    
    
    private ArrayList<RestrictionResult> getConceptRestrictions(OWLConcept concept) {
        Collection<OWLClassExpression> parents = BLUEntityRetriever.getSuperClasses(concept.getCls(), dataManager.getSourceOntology());

        Set<OWLClassExpression> restrictions = parents.stream().filter((parent) -> {
            return parent.getClassExpressionType() != ClassExpressionType.OWL_CLASS;
        }).collect(Collectors.toSet());
        
        restrictions.addAll(BLUEntityRetriever.getEquivalentClasses(concept.getCls(), dataManager.getSourceOntology()));

        ArrayList<RestrictionResult> restrictionResults = new ArrayList<>();

        restrictions.forEach((expr) -> {
            restrictionResults.addAll(getRestrictionsFrom(expr, concept));
        });
        
        return restrictionResults;
    }

    @Override
    public Set<? extends InheritableProperty> getPropertiesFromIds(Set<String> ids) {
        Map<String, OWLProperty> propertyIRIMap = new HashMap<>();
        
        OWLOntology owlOnt = dataManager.getSourceOntology();
        
        owlOnt.getObjectPropertiesInSignature().forEach( (property) -> {
            propertyIRIMap.put(property.getIRI().toString().toLowerCase(), property);
        });
        
        owlOnt.getDataPropertiesInSignature().forEach( (property) -> {
            propertyIRIMap.put(property.getIRI().toString().toLowerCase(), property);
        });
        
        Set<OWLInheritableProperty> results = new HashSet<>();
        
        ids.forEach( (id) -> {
            if (propertyIRIMap.containsKey(id)) {
                results.add(new OWLInheritableProperty(
                                propertyIRIMap.get(id),
                                InheritanceType.Introduced,
                                PropertyTypeAndUsage.OP_DOMAIN,
                                dataManager)
                );
            }
        });
        
        return results;
    }
    
    private CombinedRestrictionsResults getUniqueRestrictions(ArrayList<RestrictionResult> allRestrictions) {
        
        ArrayList<ObjectPropertyRestrictionResult> individualOPResults = new ArrayList<>();
        ArrayList<DataPropertyRestrictionResult> individualDPResults = new ArrayList<>();
        ArrayList<OtherRestrictionTypeResult> individualOtherResults = new ArrayList<>();
        
        allRestrictions.forEach((restriction) -> {
           if(restriction instanceof ObjectPropertyRestrictionResult) {
               individualOPResults.add((ObjectPropertyRestrictionResult)restriction);
           } else if(restriction instanceof DataPropertyRestrictionResult) {
               individualDPResults.add((DataPropertyRestrictionResult)restriction);
           } else {
               individualOtherResults.add((OtherRestrictionTypeResult)restriction);
           }
        });
        
        Set<IntermediatePropertyRestrictionResult<OWLObjectProperty, OWLClassExpression>> opTypes = new HashSet<>();
        
        Set<IntermediatePropertyRestrictionResult<OWLDataProperty, OWLDataRange>> dpTypes = new HashSet<>();
        
        individualOPResults.forEach((result) -> {
            opTypes.add(new IntermediatePropertyRestrictionResult<>(result.getProperty(), result.getFiller()));
        });
        
        individualDPResults.forEach((result) -> {
            dpTypes.add(new IntermediatePropertyRestrictionResult<>(result.getProperty(), result.getFiller()));
        });
        
        Map<IntermediatePropertyRestrictionResult<OWLObjectProperty, OWLClassExpression>, 
                ArrayList<ObjectPropertyRestrictionResult>> uniqueOPResults = new HashMap<>();
        
        Map<IntermediatePropertyRestrictionResult<OWLDataProperty, OWLDataRange>, 
                ArrayList<DataPropertyRestrictionResult>> uniqueDPResults = new HashMap<>();
        
        Set<OWLClassExpression> uniqueOtherRestrictions = new HashSet<>();
        
        opTypes.forEach((opType) -> {
            uniqueOPResults.put(opType, getSameObjectPropertyRestrictions(individualOPResults, opType.getProperty(), opType.getFiller()));
        });

        dpTypes.forEach((dpType) -> {
            uniqueDPResults.put(dpType, getSameDataPropertyRestrictions(individualDPResults, dpType.getProperty(), dpType.getFiller()));
        });
        
        individualOtherResults.forEach( (result) -> {
            uniqueOtherRestrictions.add(result.getRestriction());
        });
        
        ArrayList<ObjectPropertyCombinedRestrictionResult> combinedOPResults = new ArrayList<>();
        ArrayList<DataPropertyCombinedRestrictionResult> combinedDPResults = new ArrayList<>();
        ArrayList<OtherCombinedRestrictionResult> combinedOtherResults = new ArrayList<>();
        
        uniqueOPResults.forEach( (type, restrictions) -> {
            combinedOPResults.add(new ObjectPropertyCombinedRestrictionResult(restrictions));
        });
        
        uniqueDPResults.forEach( (type, restrictions) -> {
            combinedDPResults.add(new DataPropertyCombinedRestrictionResult(restrictions));
        });
        
        uniqueOtherRestrictions.forEach((restriction) -> {
            combinedOtherResults.add(new OtherCombinedRestrictionResult(getSameRestrictions(individualOtherResults, restriction)));
        });

        return new CombinedRestrictionsResults(
                combinedOPResults, 
                combinedDPResults, 
                combinedOtherResults);
    }
    
    private ArrayList<ObjectPropertyRestrictionResult> getSameObjectPropertyRestrictions(
            ArrayList<ObjectPropertyRestrictionResult> allObjectPropertyRestrictionResults,
            OWLObjectProperty property, 
            OWLClassExpression filler) {
        
        ArrayList<ObjectPropertyRestrictionResult> allResults = new ArrayList<>();
        
        allObjectPropertyRestrictionResults.forEach( (restriction) -> {
            if(restriction.getProperty().equals(property) && restriction.getFiller().equals(filler)) {
                allResults.add(restriction);
            }
        });
        
        return allResults;
    }
    
    private ArrayList<DataPropertyRestrictionResult> getSameDataPropertyRestrictions(
            ArrayList<DataPropertyRestrictionResult> allDataPropertyRestrictionResults,
            OWLDataProperty property, 
            OWLDataRange filler) {
        
        ArrayList<DataPropertyRestrictionResult> allResults = new ArrayList<>();
        
        allDataPropertyRestrictionResults.forEach( (restriction) -> {
            if(restriction.getProperty().equals(property) && restriction.getFiller().equals(filler)) {
                allResults.add(restriction);
            }
        });
        
        return allResults;
    }
    
    private ArrayList<OtherRestrictionTypeResult> getSameRestrictions(
            ArrayList<OtherRestrictionTypeResult> allOtherTypeRestrictions,
            OWLClassExpression restriction) {
        
        ArrayList<OtherRestrictionTypeResult> matchedResults = new ArrayList<>();
        
        allOtherTypeRestrictions.forEach( (result) -> {
            if(result.getRestriction().equals(restriction)) {
                matchedResults.add(result);
            }
        });
        
        return matchedResults;
    }
    
    private ArrayList<RestrictionResult> getRestrictionsFrom(OWLClassExpression expr, OWLConcept sourceConcept) {
        
        if(expr instanceof OWLObjectIntersectionOf) {
            return getRestrictionsFromIntersection((OWLObjectIntersectionOf)expr, sourceConcept);
        } else if(expr instanceof OWLObjectUnionOf) {
            return getRestrictionsFromUnion((OWLObjectUnionOf)expr, sourceConcept);
        } else {
            return new ArrayList<>(Arrays.asList(getRestrictionFrom(expr, expr, sourceConcept)));
        }
    }
    
    private ArrayList<RestrictionResult> getRestrictionsFromIntersection(OWLObjectIntersectionOf objectIntersectionOf, OWLConcept sourceConcept) {
        
        ArrayList<RestrictionResult> restrictions = new ArrayList<>();
        
        Set<OWLClassExpression> exprs = objectIntersectionOf.asConjunctSet();
        
        exprs.forEach( (expr) -> {
            if(expr.getClassExpressionType() != ClassExpressionType.OWL_CLASS) {
                restrictions.add(getRestrictionFrom(expr, objectIntersectionOf, sourceConcept));
            }
        });
        
        return restrictions;
    }
    
    private ArrayList<RestrictionResult> getRestrictionsFromUnion(OWLObjectUnionOf objectUnionOf, OWLConcept sourceConcept) {
        
        ArrayList<RestrictionResult> restrictions = new ArrayList<>();
        
        Set<OWLClassExpression> exprs = objectUnionOf.asDisjunctSet();
        
        exprs.forEach( (expr) -> {
            if(expr.getClassExpressionType() != ClassExpressionType.OWL_CLASS) {
                restrictions.add(getRestrictionFrom(expr, objectUnionOf, sourceConcept));
            }
        });
        
        return restrictions;
    }
    
    private RestrictionResult getRestrictionFrom(
            OWLClassExpression expr, 
            OWLClassExpression sourceExpression, 
            OWLConcept sourceConcept) {
        
        if(expr instanceof OWLRestriction) {
            OWLRestriction restriction = (OWLRestriction)expr;
            
            if(restriction.getProperty() instanceof OWLObjectProperty) {
                return new ObjectPropertyRestrictionResult(
                    (OWLQuantifiedRestriction<OWLClassExpression>) restriction,
                        sourceExpression,
                        sourceConcept);
            } else {
                 return new DataPropertyRestrictionResult(
                    (OWLQuantifiedRestriction<OWLDataRange>) restriction,
                        sourceExpression,
                        sourceConcept);
            }
        } else {
            return new OtherRestrictionTypeResult(expr, sourceExpression, sourceConcept);
        }
    }

    @Override
    public Set<OWLConcept> getConceptsFromIds(Set<String> idStrs) {
        OWLClassLocationDataFactory factory = new OWLClassLocationDataFactory(this.getDataManager().getOntology());

        return factory.getConceptsFromIds(new ArrayList<>(idStrs));
    }

    @Override
    public String getOntologyID() {
        return dataManager.getSourceOntology().getOntologyID().toString();
    }

    @Override
    public ArrayList<InheritableProperty> getAvailableProperties() {
        ArrayList<InheritableProperty> properties = new ArrayList<>();
        
        Set<PropertyTypeAndUsage> allowedUsages = new HashSet<>();
        allowedUsages.add(PropertyTypeAndUsage.OP_DOMAIN);
        allowedUsages.add(PropertyTypeAndUsage.OP_RESTRICTION);
        allowedUsages.add(PropertyTypeAndUsage.OP_EQUIV);
        
        Set<OWLInheritableProperty> allProperties = dataManager.getPropertiesInSubhierarchy(
                this.getOntology().getConceptHierarchy().getRoot(), 
                allowedUsages);

        properties.addAll(allProperties);
        
        properties.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        return properties;
    }
    
    public ArrayList<OWLConcept> getSuperclassesInEquivalences(OWLConcept concept) {
        
        Set<OWLConcept> resultSet = new HashSet<>();
        
        Collection<OWLClassExpression> equivClasses = BLUEntityRetriever.getEquivalentClasses(
                concept.getCls(), 
                this.getDataManager().getSourceOntology());

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
                        resultSet.add(this.getDataManager().getOntology().getOWLConceptFor(expandedExpr.asOWLClass()));
                    }
                });
            }
        });
        
        ArrayList<OWLConcept> sortedResults = new ArrayList<>(resultSet);
        sortedResults.sort(new ConceptNameComparator());
        
        return sortedResults;
    }
    
    public List<OtherRestrictionTypeError> getRelatedOtherRestrictionTypeErrors(
            AuditSet<OWLConcept> auditSet, 
            OWLConcept concept,
            CombinedRestrictionResult<?> result) {

        OWLClassExpression restrictionExpr;

        if (result instanceof CombinedPropertyRestrictionResult) {
            CombinedPropertyRestrictionResult<?, ?, ?> propertyResult = (CombinedPropertyRestrictionResult) result;

            restrictionExpr = propertyResult.getAllResults().get(0).getRestriction();
        } else {
            OtherCombinedRestrictionResult otherResult = (OtherCombinedRestrictionResult) result;

            restrictionExpr = otherResult.getRestriction();
        }

        List<OtherRestrictionTypeError> otherTypeErrors = auditSet.
                getSemanticRelationshipErrors(concept).
                stream().
                filter((error) -> {
                    return (error instanceof OtherRestrictionTypeError);
                }).map((error) -> {
                    return (OtherRestrictionTypeError) error;
                }).collect(Collectors.toList());

        List<OtherRestrictionTypeError> relatedErrors = otherTypeErrors.stream().filter((error) -> {
            return error.getRestriction().equals(restrictionExpr);
        }).collect(Collectors.toList());
        
        return relatedErrors;
    }
    
    public Map<String, Set<String>> getAnnotations(OWLConcept concept) {
        
        
        Collection<OWLAnnotation> annotations = BLUEntityRetriever.getAnnotations(
                concept.getCls(), 
                this.dataManager.getSourceOntology());
        
        Map<String, Set<String>> result = new HashMap<>();
        
        annotations.forEach( (annotation) -> {
            
            String propertyTypeName = OWLUtilities.getAnnotationPropertyLabel(
                    this.getDataManager().getSourceOntology(), 
                    annotation.getProperty()).toLowerCase();
            
            if(!result.containsKey(propertyTypeName)) {
                result.put(propertyTypeName, new HashSet<>());
            }
            
            if(annotation.getValue() instanceof OWLLiteral) {
                OWLLiteral literal = (OWLLiteral)annotation.getValue();

                result.get(propertyTypeName).add( literal.getLiteral());
            }
        });
        
        return result;
    }

    @Override
    public OAFRecentlyOpenedFileManager getRecentlyOpenedAuditSets() {
        
        try {
            return dataManager.getOAFStateFileManager().getRecentlyOpenedAuditSets(dataManager.getOntologyFile());
        } catch (RecentlyOpenedFileException rofe) {
            
        }
        
        return null;
    }

    @Override
    public OAFRecentlyOpenedFileManager getRecentlyOpenedWorkspaces() {
        
        try {
            return dataManager.getOAFStateFileManager().getRecentNATWorkspaces(dataManager.getOntologyFile());
        } catch (RecentlyOpenedFileException rofe) {
            
        }
        
        return null;
    }
}
