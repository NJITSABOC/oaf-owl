package edu.njit.cs.saboc.blu.owl.nat.properties;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.DataPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.List;
import org.semanticweb.owlapi.model.ClassExpressionType;

/**
 *
 * @author Chris O
 */
public class FilterableRestrictionEntry extends Filterable<CombinedRestrictionResult> {
    
    private final NATBrowserPanel<OWLConcept> mainPanel;
    
    private final CombinedRestrictionResult<?> result;
    
    public FilterableRestrictionEntry(
            NATBrowserPanel<OWLConcept> mainPanel, 
            CombinedRestrictionResult<?> result) {
        
        this.mainPanel = mainPanel;
        
        this.result = result;
    }

    @Override
    public boolean containsFilter(String filter) {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return false;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
        
        filter = filter.toLowerCase();
        
        if(result instanceof ObjectPropertyCombinedRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult)result;
            
            String propertyName = OWLUtilities.getPropertyLabel(dataSource.getDataManager().getSourceOntology(), opResult.getProperty());
            
            if(propertyName.toLowerCase().contains(filter)) {
                return true;
            }
            
            if(opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                OWLConcept concept = dataSource.getDataManager().getOntology().getOWLConceptFor(opResult.getFiller().asOWLClass());
                
                return concept.getName().toLowerCase().contains(filter);
            } else {
                String str = AxiomStringGenerator.getClassExpressionStr(dataSource.getDataManager().getSourceOntology(), opResult.getFiller(), false);
                
                return str.toLowerCase().contains(filter);
            }
            
        } else if(result instanceof DataPropertyCombinedRestrictionResult) {
            DataPropertyCombinedRestrictionResult dpResult = (DataPropertyCombinedRestrictionResult)result;
            
            String propertyName = OWLUtilities.getPropertyLabel(dataSource.getDataManager().getSourceOntology(), dpResult.getProperty());
            
            if (propertyName.toLowerCase().contains(filter)) {
                return true;
            }
            
            return dpResult.getFiller().toString().toLowerCase().contains(filter);
            
        } else {
            OtherCombinedRestrictionResult otherResult = (OtherCombinedRestrictionResult)result;
            
            String str = AxiomStringGenerator.getClassExpressionStr(dataSource.getDataManager().getSourceOntology(), otherResult.getRestriction(), false);
            
            return str.toLowerCase().contains(filter);
        }
    }

    @Override
    public CombinedRestrictionResult getObject() {
        return result;
    }

    @Override
    public String getToolTipText() {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return null;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
        
        StringBuilder builder = new StringBuilder("<html><font size= '4'>");
        
        if(result instanceof CombinedPropertyRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult)result;
            
            String propertyName = OWLUtilities.getPropertyLabel(dataSource.getDataManager().getSourceOntology(), opResult.getProperty());
            
            String fillerStr;
            
            if(opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                OWLConcept concept = dataSource.getDataManager().getOntology().getOWLConceptFor(opResult.getFiller().asOWLClass());
                
                fillerStr = concept.getName();
                
            } else {
                fillerStr = AxiomStringGenerator.getClassExpressionStr(dataSource.getDataManager().getSourceOntology(), opResult.getFiller(), true);
            }
            
            builder.append(String.format("<font color = 'RED'>==</font> <b>%s</b> <font color='RED'> ==> </font> %s", 
                    propertyName, 
                    fillerStr));
        } else {
            OtherCombinedRestrictionResult otherResult = (OtherCombinedRestrictionResult)result;
            
            String str = AxiomStringGenerator.getClassExpressionStr(dataSource.getDataManager().getSourceOntology(), otherResult.getRestriction(), true);
            
            builder.append(str);
        }

        builder.append("<ul>");

        result.getAllResults().forEach((individualResult) -> {
            builder.append(String.format("<li>%s (From: <i>%s</i>)</li>",
                    individualResult.getSourceConcept().getName(),
                    AxiomStringGenerator.getAxiomTypeName(individualResult.getSourceExpression())));
        });
        
        builder.append("</ul>");
        
        if(mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            builder.append(getErrorReportText(result));
        }

        return builder.toString();
    }
    
    private String getErrorReportText(CombinedRestrictionResult combinedResult) {
        
        if (!mainPanel.getDataSource().isPresent()) {
            return "";
        }
        
        if(!mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            return "";
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource) mainPanel.getDataSource().get();

        AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

        List<? extends OntologyError<OWLConcept>> reportedErrors;

        if (combinedResult instanceof ObjectPropertyCombinedRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult) combinedResult;

            if (opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {

                OWLInheritableProperty property = new OWLInheritableProperty(
                        opResult.getProperty(),
                        InheritableProperty.InheritanceType.Introduced,
                        PropertyTypeAndUsage.OP_RESTRICTION,
                        dataSource.getDataManager());

                OWLConcept range = dataSource.getDataManager().getOntology().getOWLConceptFor(opResult.getFiller().asOWLClass());

                reportedErrors = auditSet.getRelatedSemanticRelationshipErrors(focusConcept, property, range);
            } else {
                reportedErrors = dataSource.getRelatedOtherRestrictionTypeErrors(auditSet, focusConcept, result);
            }
        } else {
            reportedErrors = dataSource.getRelatedOtherRestrictionTypeErrors(auditSet, focusConcept, result);
        }

        String text = "";

        if (!reportedErrors.isEmpty()) {
            text += String.format("<p><p><font size = '4'><b>Reported Errors (%d):</b></font><br>", reportedErrors.size());

            for (OntologyError<OWLConcept> error : reportedErrors) {
                text += error.getTooltipText();
                text += "<p>";
            }
        }

        return text;
    }

    @Override
    public String getClipboardText() {
        return "";
    }
}
