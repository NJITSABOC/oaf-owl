package edu.njit.cs.saboc.blu.owl.nat.properties;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.nat.AxiomStringGenerator;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.nat.properties.errorreport.OWLErrorReportDialog;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.errorreport.AuditSet;
import edu.njit.cs.saboc.nat.generic.errorreport.error.semanticrel.SemanticRelationshipError;
import edu.njit.cs.saboc.nat.generic.gui.panels.errorreporting.errorreport.dialog.ErrorReportDialog;
import edu.njit.cs.saboc.nat.generic.gui.panels.focusconcept.rightclickmenu.AuditReportRightClickMenu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class RestrictionRightClickMenu extends AuditReportRightClickMenu<OWLConcept, CombinedRestrictionResult>  {
    
    private final NATBrowserPanel<OWLConcept> mainPanel;

    public RestrictionRightClickMenu(NATBrowserPanel<OWLConcept> mainPanel) {

        this.mainPanel = mainPanel;
    }

    @Override
    public ArrayList<JComponent> buildRightClickMenuFor(CombinedRestrictionResult item) {
        ArrayList<JComponent> components = new ArrayList<>();
        
        if (item instanceof ObjectPropertyCombinedRestrictionResult) {
            ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult) item;

            if (opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                components.addAll(buildSimpleRestrictionMenu(opResult));
            } else {
                components.addAll(buildOtherRestrictionTypeMenu(item));
            }
        } else {
            components.addAll(buildOtherRestrictionTypeMenu(item));
        }
        
        return components;
    }

    @Override
    public ArrayList<JComponent> buildEmptyListRightClickMenu() {
        
        ArrayList<JComponent> components = new ArrayList<>();
        
        if (mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {

            AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
            OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();
            
            JMenuItem reportMissingRel = new JMenuItem("Report missing restriction");
            reportMissingRel.setFont(reportMissingRel.getFont().deriveFont(14.0f));
            reportMissingRel.addActionListener((ae) -> {
                ErrorReportDialog.displayMissingSemanticRelationshipDialog(mainPanel);
            });

            components.add(reportMissingRel);
            
            List<SemanticRelationshipError<OWLConcept>> errors = auditSet.getSemanticRelationshipErrors(focusConcept);
            
            if(!errors.isEmpty()) {
                components.add(new JSeparator());
                
                components.add(super.generateRemoveErrorMenu(auditSet, focusConcept, errors));
            }
        }
        return components;
    }
    
    private ArrayList<JComponent> buildSimpleRestrictionMenu(ObjectPropertyCombinedRestrictionResult result) {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return new ArrayList<>();
        }
        
        if(!mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            return new ArrayList<>();
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource) mainPanel.getDataSource().get();

        ArrayList<JComponent> components = new ArrayList<>();

        OWLInheritableProperty propertyType = new OWLInheritableProperty(
                result.getProperty(),
                InheritanceType.Introduced,
                PropertyTypeAndUsage.OP_EQUIV,
                dataSource.getDataManager());

        OWLConcept target = dataSource.getDataManager().getOntology().getOWLConceptFor(result.getFiller().asOWLClass());

        AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.WEST);
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.EAST);

        JLabel nameLabel = new JLabel(
                SemanticRelationshipError.generateStyledRelationshipText(
                        propertyType.getName(),
                        target.getName()));

        nameLabel.setFont(nameLabel.getFont().deriveFont(16.0f));

        namePanel.setBackground(Color.WHITE);
        namePanel.setOpaque(true);

        namePanel.add(nameLabel, BorderLayout.CENTER);

        components.add(namePanel);

        components.add(new JSeparator());

        JMenuItem removeRelBtn = new JMenuItem("Remove erroneous restriction");
        removeRelBtn.setFont(removeRelBtn.getFont().deriveFont(14.0f));
        removeRelBtn.addActionListener((ae) -> {
            ErrorReportDialog.displayErroneousSemanticRelationshipDialog(
                    mainPanel,
                    propertyType,
                    target);
        });

        JMenuItem changeTargetBtn = new JMenuItem("Change restriction target");
        changeTargetBtn.setFont(changeTargetBtn.getFont().deriveFont(14.0f));
        changeTargetBtn.addActionListener((ae) -> {
            ErrorReportDialog.displayReplaceTargetDialog(
                    mainPanel,
                    propertyType,
                    target);
        });

        JMenuItem replaceRelBtn = new JMenuItem("Replace restriction");
        replaceRelBtn.setFont(replaceRelBtn.getFont().deriveFont(14.0f));
        replaceRelBtn.addActionListener((ae) -> {
            ErrorReportDialog.displayReplaceSemanticRelationshipDialog(
                    mainPanel,
                    propertyType,
                    target);
        });

        JMenuItem otherErrorBtn = new JMenuItem("Other error with restriction");
        otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
        otherErrorBtn.addActionListener((ae) -> {
            ErrorReportDialog.displayOtherSemanticRelationshipErrorDialog(
                    mainPanel,
                    propertyType,
                    target);
        });

        components.add(removeRelBtn);
        components.add(changeTargetBtn);
        components.add(replaceRelBtn);
        components.add(otherErrorBtn);

        components.add(new JSeparator());

        JMenuItem reportMissingRel = new JMenuItem("Report missing attribute relationship");
        reportMissingRel.setFont(reportMissingRel.getFont().deriveFont(14.0f));
        reportMissingRel.addActionListener((ae) -> {
            ErrorReportDialog.displayMissingSemanticRelationshipDialog(mainPanel);
        });

        components.add(reportMissingRel);

        List<SemanticRelationshipError<OWLConcept>> errors = auditSet.getSemanticRelationshipErrors(focusConcept);

        if (!errors.isEmpty()) {
            components.add(new JSeparator());

            components.add(super.generateRemoveErrorMenu(auditSet, focusConcept, errors));
        }

        return components;
    }
    
    private ArrayList<JComponent> buildOtherRestrictionTypeMenu(CombinedRestrictionResult<?> result) {
        
        if(!mainPanel.getDataSource().isPresent()) {
            return new ArrayList<>();
        }
        
        if(!mainPanel.getAuditDatabase().getLoadedAuditSet().isPresent()) {
            return new ArrayList<>();
        }
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource) mainPanel.getDataSource().get();
        
        ArrayList<JComponent> components = new ArrayList<>();

        AuditSet<OWLConcept> auditSet = mainPanel.getAuditDatabase().getLoadedAuditSet().get();
        OWLConcept focusConcept = mainPanel.getFocusConceptManager().getActiveFocusConcept();

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.WEST);
        namePanel.add(Box.createHorizontalStrut(8), BorderLayout.EAST);

        OWLClassExpression restrictionExpression;

        if (result instanceof CombinedPropertyRestrictionResult) {
            CombinedPropertyRestrictionResult<?, ?, ?> propertyResult = (CombinedPropertyRestrictionResult<?, ?, ?>) result;

            restrictionExpression = propertyResult.getAllResults().get(0).getRestriction();
        } else {
            OtherCombinedRestrictionResult otherResult = (OtherCombinedRestrictionResult) result;

            restrictionExpression = otherResult.getRestriction();
        }

        String axiomName = AxiomStringGenerator.getClassExpressionStr(
                dataSource.getDataManager().getSourceOntology(),
                restrictionExpression,
                true);

        JLabel nameLabel = new JLabel(axiomName);

        nameLabel.setFont(nameLabel.getFont().deriveFont(16.0f));

        namePanel.setBackground(Color.WHITE);
        namePanel.setOpaque(true);

        namePanel.add(nameLabel, BorderLayout.CENTER);

        components.add(namePanel);

        components.add(new JSeparator());

        JMenuItem removeRelBtn = new JMenuItem("Remove erroneous restriction");
        removeRelBtn.setFont(removeRelBtn.getFont().deriveFont(14.0f));
        removeRelBtn.addActionListener((ae) -> {
            OWLErrorReportDialog.displayRemoveOtherRestrictionDialog(mainPanel, restrictionExpression);
        });

        JMenuItem otherErrorBtn = new JMenuItem("Other error with restriction");
        otherErrorBtn.setFont(otherErrorBtn.getFont().deriveFont(14.0f));
        otherErrorBtn.addActionListener((ae) -> {
            OWLErrorReportDialog.displayOtherErrorWithOtherRestrictionDialog(mainPanel, restrictionExpression);
        });

        components.add(removeRelBtn);
        components.add(otherErrorBtn);

        components.add(new JSeparator());

        JMenuItem reportMissingRel = new JMenuItem("Report missing attribute relationship");
        reportMissingRel.setFont(reportMissingRel.getFont().deriveFont(14.0f));
        reportMissingRel.addActionListener((ae) -> {
            ErrorReportDialog.displayMissingSemanticRelationshipDialog(mainPanel);
        });

        components.add(reportMissingRel);

        List<SemanticRelationshipError<OWLConcept>> errors = auditSet.getSemanticRelationshipErrors(focusConcept);

        if (!errors.isEmpty()) {
            components.add(new JSeparator());

            components.add(super.generateRemoveErrorMenu(auditSet, focusConcept, errors));
        }


        return components;
    }
}
