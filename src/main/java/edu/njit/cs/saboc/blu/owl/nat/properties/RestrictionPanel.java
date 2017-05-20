package edu.njit.cs.saboc.blu.owl.nat.properties;

import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.nat.OWLNATDataRetrievers;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.ObjectPropertyCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.panels.ResultListPanel;
import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.semanticweb.owlapi.model.ClassExpressionType;

/**
 *
 * @author Chris O
 */
public class RestrictionPanel extends ResultListPanel<OWLConcept, CombinedRestrictionResult> {
    
    private final JRadioButton btnShowAll;
    private final JRadioButton btnShowMostSpecific;
    private final JRadioButton btnShowStated;
    
    public RestrictionPanel(
            NATBrowserPanel<OWLConcept> mainPanel, 
            OWLBrowserDataSource dataSource) {
        
        super(mainPanel, 
                dataSource, 
                OWLNATDataRetrievers.getAllRestrictionsRetriever(dataSource), 
                new RestrictionRenderer(mainPanel, dataSource), 
                true, 
                true);
        
        this.btnShowAll = new JRadioButton("Show All");
        this.btnShowMostSpecific = new JRadioButton("Show Only Most Refined");
        this.btnShowStated = new JRadioButton("Show Stated Only");
        
        this.btnShowAll.addActionListener( (ae) -> {
            this.setDataRetriever(OWLNATDataRetrievers.getAllRestrictionsRetriever(dataSource));
        });
        
        this.btnShowMostSpecific.addActionListener( (ae) -> {
            this.setDataRetriever(OWLNATDataRetrievers.getMostSpecificRestrictionsRetriever(dataSource));
        });
        
        this.btnShowStated.addActionListener( (ae) -> {
            this.setDataRetriever(OWLNATDataRetrievers.getStatedRestrictionsRetriever(dataSource));
        });
        
        this.setRightClickMenuGenerator(new RestrictionRightClickMenu(mainPanel, dataSource));
        
        ButtonGroup bg = new ButtonGroup();
        bg.add(btnShowAll);
        bg.add(btnShowMostSpecific);
        bg.add(btnShowStated);
        
        this.btnShowAll.setSelected(true);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        btnPanel.add(btnShowAll);
        btnPanel.add(btnShowMostSpecific);
        btnPanel.add(btnShowStated);
        
        super.addOptionsComponent(btnPanel);

        super.addResultSelectedListener(new ResultSelectedListener<CombinedRestrictionResult>() {

            @Override
            public void resultSelected(CombinedRestrictionResult result) {
                
                if(result instanceof ObjectPropertyCombinedRestrictionResult) {
                    ObjectPropertyCombinedRestrictionResult opResult = (ObjectPropertyCombinedRestrictionResult)result;
                    
                    if(opResult.getFiller().getClassExpressionType() == ClassExpressionType.OWL_CLASS) {
                        OWLConcept concept = dataSource.getDataManager().getOntology().getOWLConceptFor(opResult.getFiller().asOWLClass());
                        
                        mainPanel.getFocusConceptManager().navigateTo(concept);
                    }
                }
            }

            @Override
            public void noResultSelected() {

            }
        });
    }
    
    @Override
    public OWLBrowserDataSource getDataSource() {
        return (OWLBrowserDataSource)super.getDataSource();
    }

    @Override
    protected Filterable<CombinedRestrictionResult> createFilterableEntry(CombinedRestrictionResult entry) {
        return new FilterableRestrictionEntry(getMainPanel(), getDataSource(), entry);
    }
}
