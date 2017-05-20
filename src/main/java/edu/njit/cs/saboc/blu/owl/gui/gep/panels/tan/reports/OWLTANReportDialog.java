
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.reports;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNConceptLocationReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.OverlappingConceptReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.PartitionedAbNImportReportTableModel;
import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.OWLClassLocationDataFactory;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.tan.configuration.OWLTANConfiguration;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 */
public class OWLTANReportDialog extends JDialog {
    
    private final OverlappingConceptReportPanel overlappingConceptPanel;
    
    private final AbNConceptLocationReportPanel importConceptsPanel;
    
    private final JTabbedPane tabbedPane;
    
    public OWLTANReportDialog(OWLTANConfiguration config) {

        overlappingConceptPanel = new OverlappingConceptReportPanel(config);
        
        OWLAbstractionNetwork abn = (OWLAbstractionNetwork)config.getTribalAbstractionNetwork();
        
        importConceptsPanel = new AbNConceptLocationReportPanel(config, 
                new OWLClassLocationDataFactory(abn.getDataManager().getOntology()), 
                new PartitionedAbNImportReportTableModel(config));

        
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Overlapping Classes", overlappingConceptPanel);
        tabbedPane.add("Import Class List", importConceptsPanel);
        
        this.add(tabbedPane);
        
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        this.setTitle("OWL Tribal Abstraction Network Reports");
        
        this.setSize(600, 600);
    }
    
    public void showReports(ClusterTribalAbstractionNetwork tan) {
        
//        Set<Band> bands = tan.getBands();
//        
//        boolean hasOverlapping = false;
//        
//        for(Band band : bands) {
//            if(band.hasOverlappingConcepts()) {
//                hasOverlapping = true;
//                break;
//            }
//        }
//        
//        if(hasOverlapping) {
//            overlappingConceptPanel.displayAbNReport(tan);
//        } else {
//            tabbedPane.setEnabledAt(0, false);
//        }
    }
}
