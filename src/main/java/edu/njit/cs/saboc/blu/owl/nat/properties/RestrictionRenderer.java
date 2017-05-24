package edu.njit.cs.saboc.blu.owl.nat.properties;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.nat.error.OtherRestrictionTypeError;
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
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.IncorrectSemanticRelationshipError;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.BaseFilterableRenderer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import org.semanticweb.owlapi.model.ClassExpressionType;

/**
 *
 * @author Chris O
 */
public class RestrictionRenderer extends BaseFilterableRenderer<CombinedRestrictionResult> {

    private final NATBrowserPanel<OWLConcept> mainPanel;

    private final JLabel inheritanceLabel;
    private final JPanel restrictionPanel;
    
    private final JLabel errorStatusLabel;
    
    public RestrictionRenderer(NATBrowserPanel<OWLConcept> mainPanel) {
        
        this.mainPanel = mainPanel;
        
        this.inheritanceLabel = new JLabel();
        this.inheritanceLabel.setOpaque(false);
        this.inheritanceLabel.setFont(this.inheritanceLabel.getFont().deriveFont(Font.PLAIN, 12));
        this.inheritanceLabel.setForeground(new Color(255, 157, 10));
        
        this.errorStatusLabel = new JLabel();
        this.errorStatusLabel.setOpaque(false);
        this.errorStatusLabel.setFont(this.errorStatusLabel.getFont().deriveFont(Font.BOLD, 12));
        this.errorStatusLabel.setForeground(Color.RED);
        
                
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.restrictionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.restrictionPanel.setOpaque(false);
        
        this.add(restrictionPanel);
        this.add(inheritanceLabel);
        
        this.add(Box.createHorizontalStrut(10));
        
        this.add(errorStatusLabel);
        
        this.setPreferredSize(new Dimension(-1, 50));

        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Filterable<CombinedRestrictionResult>> list,
            Filterable<CombinedRestrictionResult> value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        super.getListCellRendererComponent(
                list,
                value,
                index,
                isSelected,
                cellHasFocus);
        
        showDetailsFor(value);

        return this;
    }

    @Override
    public void showDetailsFor(Filterable<CombinedRestrictionResult> value) {
        CombinedRestrictionResult<?> restrictionResult = value.getObject();
        
        restrictionPanel.removeAll();
        
        if(restrictionResult instanceof CombinedPropertyRestrictionResult) {
            PropertyRestrictionPanel detailsPanel = new PropertyRestrictionPanel(mainPanel);
            detailsPanel.display((Filterable<CombinedPropertyRestrictionResult>)(Filterable<?>)value);
            
            this.restrictionPanel.add(detailsPanel, BorderLayout.CENTER);
        } else {
            OtherRestrictionTypePanel detailsPanel = new OtherRestrictionTypePanel(mainPanel);
            detailsPanel.display((Filterable<OtherCombinedRestrictionResult>)(Filterable<?>)value);
            
            this.restrictionPanel.add(detailsPanel, BorderLayout.CENTER);
        }
        
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();
        
        String inheritanceStr;
        
        if(restrictionResult.getSourceConcepts().contains(focusConcept)) {
            if(restrictionResult.getSourceConcepts().size() > 1) {
                inheritanceStr = String.format("Stated / Inherited from %d", (restrictionResult.getSourceConcepts().size() - 1));
            } else {
                inheritanceStr = "Stated";
            }
        } else {
            inheritanceStr = String.format("Inherited from %d", restrictionResult.getSourceConcepts().size());
        }

        this.inheritanceLabel.setText(inheritanceStr);
        
        this.errorStatusLabel.setText("");
        
        if(restrictionResult instanceof ObjectPropertyCombinedRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult)restrictionResult;
            
            if(opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                setSimpleOPRestrictionErrorText(opResult);
            } else {
                setOtherRestrictionErrorText(opResult);
            }
        } else {
            setOtherRestrictionErrorText(restrictionResult);
        }
    }
    
    private void setSimpleOPRestrictionErrorText(ObjectPropertyCombinedRestrictionResult opResult) {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return;
        }
        
        if(!mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            return;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();

        AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

        OWLInheritableProperty property = new OWLInheritableProperty(
                opResult.getProperty(),
                InheritanceType.Introduced,
                PropertyTypeAndUsage.OP_RESTRICTION,
                dataSource.getDataManager());

        OWLConcept range = dataSource.getDataManager().getOntology().getOWLConceptFor(opResult.getFiller().asOWLClass());

        List<IncorrectSemanticRelationshipError<OWLConcept, InheritableProperty>> relatedErrors
                = auditSet.getRelatedSemanticRelationshipErrors(focusConcept, property, range);

        if (relatedErrors.isEmpty()) {
            return;
        }

        if (relatedErrors.size() == 1) {
            this.errorStatusLabel.setText("Error");
        } else {
            this.errorStatusLabel.setText(String.format("Errors (%d)", relatedErrors.size()));
        }
    }
    
    private void setOtherRestrictionErrorText(CombinedRestrictionResult<?> result) {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return;
        }
        
        if(!mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            return;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
        
        AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

        List<OtherRestrictionTypeError> relatedErrors = dataSource.getRelatedOtherRestrictionTypeErrors(auditSet, focusConcept, result);

        if (relatedErrors.isEmpty()) {
            return;
        }

        if (relatedErrors.size() == 1) {
            this.errorStatusLabel.setText("Error");
        } else {
            this.errorStatusLabel.setText(String.format("Errors (%d)", relatedErrors.size()));
        }

    }
}

abstract class RestrictionDetailsPanel<T extends CombinedRestrictionResult> extends JPanel {
    
    private final NATBrowserPanel<OWLConcept> mainPanel;
    
    public RestrictionDetailsPanel(NATBrowserPanel<OWLConcept> mainPanel) {
        this.mainPanel = mainPanel;
        
        this.setOpaque(false);
    }
    
    public NATBrowserPanel<OWLConcept> getMainPanel() {
        return mainPanel;
    }
    
    public abstract void display(Filterable<T> result);
}

class PropertyRestrictionPanel extends RestrictionDetailsPanel<CombinedPropertyRestrictionResult> {
    
    private final JLabel propertyTypeLabel;
    private final JLabel fillerLabel;

    public PropertyRestrictionPanel(NATBrowserPanel<OWLConcept> mainPanel) {
        
        super(mainPanel);

        this.setOpaque(false);
        
        this.propertyTypeLabel = new JLabel();
        this.fillerLabel = new JLabel();
        
        this.propertyTypeLabel.setOpaque(false);
        this.fillerLabel.setOpaque(false);

        this.propertyTypeLabel.setFont(this.propertyTypeLabel.getFont().deriveFont(Font.BOLD, 16));
        this.fillerLabel.setFont(this.fillerLabel.getFont().deriveFont(Font.PLAIN, 16));
        
        JPanel propertyNamePanel = new JPanel();
        propertyNamePanel.setOpaque(false);

        JLabel relStartLabel = new JLabel(" == ");
        relStartLabel.setFont(relStartLabel.getFont().deriveFont(Font.BOLD, 16));

        JLabel relEndLabel = new JLabel(" ==> ");
        relEndLabel.setFont(relEndLabel.getFont().deriveFont(Font.BOLD, 16));

        relStartLabel.setOpaque(false);
        relEndLabel.setOpaque(false);

        relStartLabel.setForeground(Color.RED);
        relEndLabel.setForeground(Color.RED);

        propertyNamePanel.add(relStartLabel);
        propertyNamePanel.add(propertyTypeLabel);
        propertyNamePanel.add(relEndLabel);

        JPanel fillerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fillerPanel.add(fillerLabel);

        fillerPanel.setOpaque(false);

        JPanel entryPanel = new JPanel();
        entryPanel.setOpaque(false);

        entryPanel.add(propertyNamePanel);
        entryPanel.add(fillerPanel);
        
        this.setLayout(new BorderLayout());
        
        this.add(entryPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void display(Filterable<CombinedPropertyRestrictionResult> result) {
        
        if(!getMainPanel().getDataSource().isPresent()) {
            return;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)getMainPanel().getDataSource().get();
        
        CombinedPropertyRestrictionResult restrictionResult = result.getObject();
        
        String propertyName = OWLUtilities.getPropertyLabel(
                dataSource.getDataManager().getSourceOntology(), 
                restrictionResult.getProperty());
        
        if(result.getCurrentFilter().isPresent()) {
            propertyName = Filterable.filter(propertyName, result.getCurrentFilter().get());
        } 
        
        this.propertyTypeLabel.setText(propertyName);
        
        String fillerText;
        
        if (restrictionResult instanceof ObjectPropertyCombinedRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opRestrictionResult = (ObjectPropertyCombinedRestrictionResult) restrictionResult;

            if(result.getCurrentFilter().isPresent()) {
                fillerText = AxiomStringGenerator.getStyledClassExpressionStrFiltered(
                        dataSource.getDataManager().getSourceOntology(),
                        opRestrictionResult.getFiller(),
                        result.getCurrentFilter().get());
            } else {
                fillerText = AxiomStringGenerator.getClassExpressionStr(
                        dataSource.getDataManager().getSourceOntology(),
                        opRestrictionResult.getFiller(),
                        true);
            }
        } else {
            DataPropertyCombinedRestrictionResult dpRestrictionResult = (DataPropertyCombinedRestrictionResult) restrictionResult;

            fillerText = dpRestrictionResult.getFiller().toString();
            
            if(result.getCurrentFilter().isPresent()) {
                fillerText = Filterable.filter(fillerText, result.getCurrentFilter().get());
            }
        }

        this.fillerLabel.setText(fillerText);
    }
}

class OtherRestrictionTypePanel extends RestrictionDetailsPanel<OtherCombinedRestrictionResult> {
    
    private final JLabel restrictionLabel;
    
    public OtherRestrictionTypePanel(NATBrowserPanel<OWLConcept> mainPanel) {
        super(mainPanel);
        
        this.restrictionLabel = new JLabel();
        this.restrictionLabel.setOpaque(false);
        this.restrictionLabel.setFont(this.restrictionLabel.getFont().deriveFont(Font.PLAIN, 16));
        
        this.setLayout(new BorderLayout());
        
        this.add(restrictionLabel, BorderLayout.CENTER);
    }

    @Override
    public void display(Filterable<OtherCombinedRestrictionResult> result) {
   
        if(!getMainPanel().getDataSource().isPresent()) {
            return;
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)getMainPanel().getDataSource().get();
        
        OtherCombinedRestrictionResult unknownTypeResult = result.getObject();
        
        String str;
        
        if(result.getCurrentFilter().isPresent()) {
            str = AxiomStringGenerator.getStyledClassExpressionStrFiltered(
                    dataSource.getDataManager().getSourceOntology(), 
                    unknownTypeResult.getRestriction(), 
                    result.getCurrentFilter().get());
        } else {
            str = AxiomStringGenerator.getClassExpressionStr(
                    dataSource.getDataManager().getSourceOntology(), 
                    unknownTypeResult.getRestriction(), 
                    true);
        }
        
        this.restrictionLabel.setText(str);
    }
}
