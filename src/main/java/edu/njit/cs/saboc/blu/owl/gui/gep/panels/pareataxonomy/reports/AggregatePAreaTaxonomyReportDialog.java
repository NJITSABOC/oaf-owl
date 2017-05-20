package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNConceptLocationReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.PartitionedAbNImportReportTableModel;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaTaxonomyReportDialog extends JDialog {

    private final AbNConceptLocationReportPanel importClassPanel;

    private final JTabbedPane tabbedPane;
    
    public AggregatePAreaTaxonomyReportDialog(OWLPAreaTaxonomyConfiguration config) {

        OWLTaxonomy taxonomy = (OWLTaxonomy)config.getPAreaTaxonomy();
        
        importClassPanel = new AbNConceptLocationReportPanel(config, 
                new OWLClassLocationDataFactory(taxonomy.getDataManager().getOntology()), 
                new PartitionedAbNImportReportTableModel(config));

        tabbedPane = new JTabbedPane();

        tabbedPane.add("Import Class List", importClassPanel);
        
        this.add(tabbedPane);
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        this.setTitle("OWL Partial-area Taxonomy Reports");
        
        this.setSize(600, 600);
    }
    
    public void showReports(PAreaTaxonomy taxonomy) {
       
    }
}
