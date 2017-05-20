package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.diffpareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.PropertyChangeDetailsFactory;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.ontology.OWLPropertyUtilities;
import edu.njit.cs.saboc.blu.owl.utils.BLUEntityRetriever;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyType;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class OWLPropertyChangeDetailsFactory implements PropertyChangeDetailsFactory {
    
    private class UsageEqualsOWLInheritableProperty extends OWLInheritableProperty {
        
        public UsageEqualsOWLInheritableProperty(OWLInheritableProperty source) {
            super(source.getPropertyType(), 
                    source.getInheritanceType(), 
                    source.getPropertyTypeAndUsage(), 
                    source.getManager());
        }
        
        public boolean equals(Object o) {
            if(o instanceof OWLInheritableProperty) {
                OWLInheritableProperty inheritableProperty = (OWLInheritableProperty)o;
                
                return super.equals(o) && this.getPropertyTypeAndUsage().equals(inheritableProperty.getPropertyTypeAndUsage());
            }
            
            return false;
        }
    }
    
    private final PAreaTaxonomy fromTaxonomy;
    private final PAreaTaxonomy toTaxonomy;
    
    private final Map<OWLInheritableProperty, Set<OWLConcept>> beforeDomains = new HashMap<>();
    private final Map<OWLInheritableProperty, Set<OWLConcept>> afterDomains = new HashMap<>();
    
    public OWLPropertyChangeDetailsFactory(PAreaTaxonomy fromTaxonomy, PAreaTaxonomy toTaxonomy) {
        this.fromTaxonomy = fromTaxonomy;
        this.toTaxonomy = toTaxonomy;
        
        initializeStatedPropertyDomains(fromTaxonomy, toTaxonomy);
    }

    @Override
    public Set<InheritableProperty> getFromOntProperties() {
        return fromTaxonomy.getAreaTaxonomy().getPropertiesInTaxonomy();
    }

    @Override
    public Set<InheritableProperty> getToOntProperties() {
        return toTaxonomy.getAreaTaxonomy().getPropertiesInTaxonomy();
    }

    @Override
    public Map<InheritableProperty, Set<Concept>> getFromDomains() {
        return (Map<InheritableProperty, Set<Concept>>)(Map<?, ?>)beforeDomains;
    }

    @Override
    public Map<InheritableProperty, Set<Concept>> getToDomains() {
        return (Map<InheritableProperty, Set<Concept>>)(Map<?, ?>)afterDomains;
    }
    

    private void initializeStatedPropertyDomains(PAreaTaxonomy fromTaxonomy, PAreaTaxonomy toTaxonomy) {
        
        Set<OWLInheritableProperty> fromProperties = fromTaxonomy.getAreaTaxonomy().getPropertiesInTaxonomy();
        Set<OWLInheritableProperty> toProperties = toTaxonomy.getAreaTaxonomy().getPropertiesInTaxonomy();
       
        Set<OWLInheritableProperty> addedProperties = SetUtilities.getSetDifference(toProperties, fromProperties);
        Set<OWLInheritableProperty> removedProperties = SetUtilities.getSetDifference(fromProperties, toProperties);
        Set<OWLInheritableProperty> transferredProperties = SetUtilities.getSetIntersection(fromProperties, toProperties);

        OWLTaxonomy fromOWLTaxonomy = (OWLTaxonomy)fromTaxonomy;
        OWLTaxonomy toOWLTaxonomy = (OWLTaxonomy)toTaxonomy;
        
        Set<PropertyTypeAndUsage> propertyTypesAndUsages = toOWLTaxonomy.getPropertyTypesAndUsages();
        
        OAFOntologyDataManager fromManager = fromOWLTaxonomy.getDataManager();
        OAFOntologyDataManager toManager = toOWLTaxonomy.getDataManager();

        if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_DOMAIN)) {
            
            Set<OWLInheritableProperty> removedOPs = removedProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Object);
            }).collect(Collectors.toSet());
            
            Set<OWLInheritableProperty> addedOPs = addedProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Object);
            }).collect(Collectors.toSet());
            
            Set<OWLInheritableProperty> transferredOPs = transferredProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Object);
            }).collect(Collectors.toSet());
            
            removedOPs.forEach((removedProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getNonComplementClassesInSet(BLUEntityRetriever.getDomains(
                        removedProperty.getPropertyType(), 
                        fromManager.getSourceOntology()),
                    fromManager);
                
                beforeDomains.put(new UsageEqualsOWLInheritableProperty(removedProperty), domain);
            });
            
            addedOPs.forEach((addedProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(addedProperty.getPropertyType(),
                                toManager.getSourceOntology()),
                            toManager);
                
                afterDomains.put(new UsageEqualsOWLInheritableProperty(addedProperty), domain);
            });

            transferredOPs.forEach((transferredProperty) -> {
                Set<OWLConcept> beforeDomain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(
                                transferredProperty.getPropertyType(),
                                fromManager.getSourceOntology()),
                            fromManager);

                Set<OWLConcept> afterDomain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(
                                transferredProperty.getPropertyType(),
                                toManager.getSourceOntology()),
                            toManager);

                beforeDomains.put(new UsageEqualsOWLInheritableProperty(transferredProperty), beforeDomain);
                afterDomains.put(new UsageEqualsOWLInheritableProperty(transferredProperty), afterDomain);
            });
        }
                
        if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_DOMAIN)) {
            
            Set<OWLInheritableProperty> removedDPs = removedProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Data);
            }).collect(Collectors.toSet());
            
            Set<OWLInheritableProperty> addedDPs = addedProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Data);
            }).collect(Collectors.toSet());
            
            Set<OWLInheritableProperty> transferredDPs = transferredProperties.stream().filter( (property) -> {
                return property.getPropertyTypeAndUsage().getType().equals(PropertyType.Data);
            }).collect(Collectors.toSet());
            
            removedDPs.forEach((removedProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getNonComplementClassesInSet(BLUEntityRetriever.getDomains(
                        removedProperty.getPropertyType(), 
                        fromManager.getSourceOntology()),
                    fromManager);
                
                beforeDomains.put(new UsageEqualsOWLInheritableProperty(removedProperty), domain);
            });
            
            addedDPs.forEach((addedProperty) -> {
                Set<OWLConcept> domain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(addedProperty.getPropertyType(),
                                toManager.getSourceOntology()),
                        toManager);
                
                afterDomains.put(new UsageEqualsOWLInheritableProperty(addedProperty), domain);
            });

            transferredDPs.forEach((transferredProperty) -> {
                Set<OWLConcept> beforeDomain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(
                                transferredProperty.getPropertyType(), 
                                fromManager.getSourceOntology()),
                        fromManager);
                
                Set<OWLConcept> afterDomain = OWLPropertyUtilities.getNonComplementClassesInSet(
                        BLUEntityRetriever.getDomains(
                                transferredProperty.getPropertyType(), 
                                toManager.getSourceOntology()),
                        toManager);
                
                beforeDomains.put(new UsageEqualsOWLInheritableProperty(transferredProperty), beforeDomain);
                afterDomains.put(new UsageEqualsOWLInheritableProperty(transferredProperty), afterDomain);
            });
        }
        
        Set<OWLConcept> fromClses = fromTaxonomy.getSourceHierarchy().getNodes();
        Set<OWLConcept> toClses = toTaxonomy.getSourceHierarchy().getNodes();
        
        fromClses.forEach( (concept) -> {
            
            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_RESTRICTION)) {
                
                Set<OWLProperty> fromOPRestrictions = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getOPUsedInClassRestriction(concept,
                        fromManager.getSourceOntology());

                Set<OWLInheritableProperty> fromOPRestictionInheritableProperties = this.toUsageEqualsInheritableProperties(
                        fromOPRestrictions,
                        PropertyTypeAndUsage.OP_RESTRICTION,
                        fromManager);
                
                fromOPRestictionInheritableProperties.forEach((property) -> {
                    if (!beforeDomains.containsKey(property)) {
                        beforeDomains.put(property, new HashSet<>());
                    }

                    beforeDomains.get(property).add(concept);
                });
            }
            
            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_RESTRICTION)) {

                Set<OWLProperty> fromDPRestrictions = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getDPUsedInClassRestriction(concept,
                        fromManager.getSourceOntology());

                Set<OWLInheritableProperty> fromDPRestictionInheritableProperties = this.toUsageEqualsInheritableProperties(
                        fromDPRestrictions,
                        PropertyTypeAndUsage.DP_RESTRICTION,
                        fromManager);

                fromDPRestictionInheritableProperties.forEach((property) -> {
                    if (!beforeDomains.containsKey(property)) {
                        beforeDomains.put(property, new HashSet<>());
                    }

                    beforeDomains.get(property).add(concept);
                });
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_EQUIV)) {
                Set<OWLProperty> fromOPEquivs = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getOPUsedInClassEquivRestriction(concept,
                        fromManager.getSourceOntology());

                Set<OWLInheritableProperty> fromOPEquivInheritableProperties = this.toUsageEqualsInheritableProperties(
                        fromOPEquivs,
                        PropertyTypeAndUsage.OP_EQUIV,
                        fromManager);

                fromOPEquivInheritableProperties.forEach((property) -> {
                    if (!beforeDomains.containsKey(property)) {
                        beforeDomains.put(property, new HashSet<>());
                    }

                    beforeDomains.get(property).add(concept);
                });
            }
            
            
            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_EQUIV)) {
                Set<OWLProperty> fromDPEquivs = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getDPUsedInClassEquivRestriction(concept,
                        fromManager.getSourceOntology());

                Set<OWLInheritableProperty> fromDPEquivInheritableProperties = this.toUsageEqualsInheritableProperties(
                        fromDPEquivs,
                        PropertyTypeAndUsage.DP_EQUIV,
                        fromManager);

                fromDPEquivInheritableProperties.forEach((property) -> {
                    if (!beforeDomains.containsKey(property)) {
                        beforeDomains.put(property, new HashSet<>());
                    }

                    beforeDomains.get(property).add(concept);
                });
            }
            
        });
        
        toClses.forEach( (concept) -> {

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_RESTRICTION)) {
                Set<OWLProperty> toOPRestrictions = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getOPUsedInClassRestriction(concept,
                        toManager.getSourceOntology());

                Set<OWLInheritableProperty> toOPRestictionInheritableProperties = this.toUsageEqualsInheritableProperties(
                        toOPRestrictions,
                        PropertyTypeAndUsage.OP_RESTRICTION,
                        toManager);

                toOPRestictionInheritableProperties.forEach((property) -> {

                    if (!afterDomains.containsKey(property)) {
                        afterDomains.put(property, new HashSet<>());
                    }

                    afterDomains.get(property).add(concept);
                });
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_RESTRICTION)) {
                Set<OWLProperty> toDPRestrictions = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getDPUsedInClassRestriction(concept,
                        toManager.getSourceOntology());

                Set<OWLInheritableProperty> toDPRestictionInheritableProperties = this.toUsageEqualsInheritableProperties(
                        toDPRestrictions,
                        PropertyTypeAndUsage.DP_RESTRICTION,
                        toManager);

                toDPRestictionInheritableProperties.forEach((property) -> {

                    if (!afterDomains.containsKey(property)) {
                        afterDomains.put(property, new HashSet<>());
                    }

                    afterDomains.get(property).add(concept);
                });
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.OP_EQUIV)) {
                Set<OWLProperty> toOPEquivs = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getOPUsedInClassEquivRestriction(concept,
                        toManager.getSourceOntology());

                Set<OWLInheritableProperty> toOPEquivInheritableProperties = this.toUsageEqualsInheritableProperties(
                        toOPEquivs,
                        PropertyTypeAndUsage.OP_EQUIV,
                        toManager);

                toOPEquivInheritableProperties.forEach((property) -> {

                    if (!afterDomains.containsKey(property)) {
                        afterDomains.put(property, new HashSet<>());
                    }

                    afterDomains.get(property).add(concept);
                });
            }

            if (propertyTypesAndUsages.contains(PropertyTypeAndUsage.DP_EQUIV)) {
                Set<OWLProperty> toDPEquivs = (Set<OWLProperty>) (Set<?>) OWLPropertyUtilities.getDPUsedInClassEquivRestriction(concept,
                        toManager.getSourceOntology());

                Set<OWLInheritableProperty> toDPEquivInheritableProperties = this.toUsageEqualsInheritableProperties(
                        toDPEquivs,
                        PropertyTypeAndUsage.DP_EQUIV,
                        toManager);

                toDPEquivInheritableProperties.forEach((property) -> {

                    if (!afterDomains.containsKey(property)) {
                        afterDomains.put(property, new HashSet<>());
                    }

                    afterDomains.get(property).add(concept);
                });
            }
        });
    }
    
    private Set<OWLInheritableProperty> toUsageEqualsInheritableProperties(
            Set<OWLProperty> properties, 
            PropertyTypeAndUsage typeAndUsage,
            OAFOntologyDataManager dataManager) {
        
        Set<OWLInheritableProperty> inheritableProperties = new HashSet<>();
        
        properties.forEach( (property) -> {
            inheritableProperties.add(
                    new UsageEqualsOWLInheritableProperty(
                            new OWLInheritableProperty(
                                property, 
                                InheritanceType.Introduced, 
                                typeAndUsage, 
                                dataManager))
            );
        });
        
        return inheritableProperties;
    }
}
