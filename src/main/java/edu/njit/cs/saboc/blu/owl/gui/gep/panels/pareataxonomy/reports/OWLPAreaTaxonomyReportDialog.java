package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNConceptLocationReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.OverlappingConceptReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.PartitionedAbNImportReportTableModel;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay.CreateAndDisplayImportSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyReportDialog extends JDialog {
    
    private final OverlappingConceptReportPanel overlappingClassPanel;
    
    private final AbNConceptLocationReportPanel importClassPanel;
    
    private final OWLPAreaTaxonomyImportsReport importReportPanel;
    
    private final JTabbedPane tabbedPane;
    
    public OWLPAreaTaxonomyReportDialog(OWLPAreaTaxonomyConfiguration config) {

        overlappingClassPanel = new OverlappingConceptReportPanel(config);
        
        OWLTaxonomy taxonomy = (OWLTaxonomy)config.getPAreaTaxonomy();
        
        importClassPanel = new AbNConceptLocationReportPanel(config, 
                new OWLClassLocationDataFactory(taxonomy.getDataManager().getOntology()), 
                new PartitionedAbNImportReportTableModel(config));
        
        importReportPanel = new OWLPAreaTaxonomyImportsReport(config, (selectedURIs) -> {
            
            CreateAndDisplayImportSubtaxonomy subtaxonomyCreator = new CreateAndDisplayImportSubtaxonomy(
                    "Import Subtaxonomy", 
                    selectedURIs, 
                    config.getPAreaTaxonomy(), 
                    config.getUIConfiguration().getAbNDisplayManager());
            
            subtaxonomyCreator.run();
        });
        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Overlapping Classes", overlappingClassPanel);
        tabbedPane.add("Import Class List", importClassPanel);
        tabbedPane.add("Imported Content Report", importReportPanel);
        
        this.add(tabbedPane);
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        this.setTitle("OWL Partial-area Taxonomy Reports");
        
        this.setSize(600, 600);
    }
    
    public void showReports(PAreaTaxonomy taxonomy) {
        Set<Area> areas = taxonomy.getAreaTaxonomy().getAreas();
        
        boolean hasOverlapping = false;
        
        for(Area area : areas) {
            if(area.hasOverlappingConcepts()) {
                hasOverlapping = true;
                break;
            }
        }
        
        if(hasOverlapping) {
            overlappingClassPanel.displayAbNReport(taxonomy);
        } else {
            tabbedPane.setEnabledAt(0, false);
        }
        
        importReportPanel.displayAbNReport(taxonomy);
    }
}