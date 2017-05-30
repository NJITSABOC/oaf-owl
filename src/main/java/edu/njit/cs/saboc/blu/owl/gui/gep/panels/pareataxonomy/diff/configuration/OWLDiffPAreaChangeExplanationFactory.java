package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.ConceptHierarchicalChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange.ChangeInheritanceType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange.DomainModificationType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyDomainChange.PropertyState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyHierarchyChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.HierarchicalChangeExplanationFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.ChangeExplanationRowEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyType;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyUsage;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaChangeExplanationFactory implements ChangeExplanationRowEntryFactory {
    
    private final DiffPAreaTaxonomyConfiguration config;
    
    private final HierarchicalChangeExplanationFactory hierarchicalChangeFactory;
    
    public OWLDiffPAreaChangeExplanationFactory(DiffPAreaTaxonomyConfiguration config) {
        this.config = config;
        
        this.hierarchicalChangeFactory = new HierarchicalChangeExplanationFactory(config);
    }

    @Override
    public ChangeExplanationRowEntry getChangeEntry(DiffAbNConceptChange item) {
        
        if(item instanceof ConceptHierarchicalChange) {
            
            return hierarchicalChangeFactory.getChangeEntry(item);
            
        } else if(item instanceof InheritablePropertyChange) {
            
            ChangeInheritanceType inheritance = item.getChangeInheritanceType();

            String inheritanceTypeStr;

            if (inheritance == DiffAbNConceptChange.ChangeInheritanceType.Direct) {
                inheritanceTypeStr = ChangeExplanationRowEntry.DIRECT;
            } else {
                inheritanceTypeStr = ChangeExplanationRowEntry.INDIRECT;
            }

            String changeTypeStr = "[UNKNOWN CHANGE TYPE]";
            String changeSummaryStr = "[NO SUMMARY SET]";
            
            String changeDescriptionStr = "";

            if(item instanceof InheritablePropertyDomainChange) {
                
                InheritablePropertyDomainChange domainChange = (InheritablePropertyDomainChange)item;
                
                OWLInheritableProperty inheritableProperty = (OWLInheritableProperty)domainChange.getProperty();
                
                String propertyTypeStr;
                
                if(inheritableProperty.getPropertyTypeAndUsage().getType() == PropertyType.Object) {
                    propertyTypeStr = "object property";
                } else {
                    propertyTypeStr = "data property";
                }
                
                String summaryDesc;
                
                if(inheritableProperty.getPropertyTypeAndUsage().getUsage() == PropertyUsage.Domain) {
                    changeTypeStr = "property domain";
                } else if (inheritableProperty.getPropertyTypeAndUsage().getUsage() == PropertyUsage.Restriction) {
                    changeTypeStr = "class restriction";
                } else {
                    changeTypeStr = "equivalence restriction";
                }
                
                InheritableProperty property = domainChange.getProperty();
                Concept domainConcept = domainChange.getPropertyDomain();
                
                String domainStateStr;
                
                if (domainChange.getModificationState() == PropertyState.Added) {
                    
                    changeSummaryStr = String.format("%s (%s) was added to the ontology "
                            + "and is now used to model %s (in %s).",
                            property.getName(), 
                            propertyTypeStr,
                            domainConcept.getName(), 
                            changeTypeStr);
                    
                } else if (domainChange.getModificationState() == PropertyState.Removed) {
                    
                    changeSummaryStr = String.format("%s (%s) was removed from the ontology "
                            + "and was previously used to model %s (in %s).",
                            property.getName(),
                            propertyTypeStr,
                            domainConcept.getName(),
                            changeTypeStr);

                } else if (domainChange.getModificationState() == PropertyState.Modified) {

                    if (domainChange.getModificationType() == DomainModificationType.Added) {
                        
                        changeSummaryStr = String.format("%s (%s) was added to %s (in %s).",
                            property.getName(),
                            propertyTypeStr,
                            domainConcept.getName(),
                            changeTypeStr);
                        
                    } else {
                        changeSummaryStr = String.format("%s (%s) was removed from %s (in %s).",
                            property.getName(),
                            propertyTypeStr,
                            domainConcept.getName(),
                            changeTypeStr);
                    }
                    
                }

            } else if(item instanceof InheritablePropertyHierarchyChange) {
                InheritablePropertyHierarchyChange inheritanceChange = (InheritablePropertyHierarchyChange)item;
                
                OWLInheritableProperty inheritableProperty = (OWLInheritableProperty)inheritanceChange.getProperty();

                if(inheritableProperty.getPropertyTypeAndUsage().getUsage() == PropertyUsage.Domain) {
                    changeTypeStr = "property domain inheritance";
                } else if (inheritableProperty.getPropertyTypeAndUsage().getUsage() == PropertyUsage.Restriction) {
                    changeTypeStr = "restriction inheritance";
                } else {
                    changeTypeStr = "equivalance inheritance";
                }
                
                String propertyTypeStr;
                
                if(inheritableProperty.getPropertyTypeAndUsage().getType() == PropertyType.Object) {
                    propertyTypeStr = "object property";
                } else {
                    propertyTypeStr = "data property";
                }

                if(inheritanceChange.getHierarchicalConnectionState() == InheritablePropertyHierarchyChange.HierarchicalConnectionState.Added) {
                    changeSummaryStr = String.format("Superclass added from %s to %s, %s (%s) now inherited.", 
                            inheritanceChange.getChild().getName(),
                            inheritanceChange.getParent().getName(),
                            inheritableProperty.getName(),
                            propertyTypeStr);
                    
                } else {
                    changeSummaryStr = String.format("Superclass removed between %s to %s, %s (%s) no longer inherited.", 
                            inheritanceChange.getChild().getName(),
                            inheritanceChange.getParent().getName(),
                            inheritableProperty.getName(),
                            propertyTypeStr);
                }

            }
            
            return new ChangeExplanationRowEntry(changeTypeStr, inheritanceTypeStr, changeSummaryStr, changeDescriptionStr);
        }
        
        return new ChangeExplanationRowEntry("[UNSET]", "[UNSET]", "[UNSET]", "[UNSET]");
    }
}
