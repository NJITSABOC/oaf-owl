
package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.AbNReportsBtn;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ExportAbNButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.HighlightOverlappingButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.PopoutAbNDetailsButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.SavePNGButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.ShowSourceHierarchyButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons.PAreaTaxonomyHelpButton;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.AggregatePAreaTaxonomyReportDialog;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports.OWLPAreaTaxonomyReportDialog;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyOptionsPanel extends AbNOptionsPanel<PAreaTaxonomy> {
    
    public OWLPAreaTaxonomyOptionsPanel(OWLPAreaTaxonomyConfiguration config) {
        
        PAreaTaxonomyHelpButton pareaHelpBtn = new PAreaTaxonomyHelpButton(config);
        ExportAbNButton exportBtn = new ExportAbNButton("Export Partial-area Taxonomy", config);
        SavePNGButton pngBtn = new SavePNGButton(config);
        PopoutAbNDetailsButton popoutBtn = new PopoutAbNDetailsButton(config);

        AbNReportsBtn<PAreaTaxonomy> reportsBtn = new AbNReportsBtn<PAreaTaxonomy>() {

            @Override
            public void displayReportsAndMetrics() {
                if (config.getPAreaTaxonomy().isAggregated()) {
                    AggregatePAreaTaxonomyReportDialog reportDialog = new AggregatePAreaTaxonomyReportDialog(config);
                    reportDialog.showReports(config.getPAreaTaxonomy());
                    reportDialog.setModal(true);

                    reportDialog.setVisible(true);

                } else {
                    OWLPAreaTaxonomyReportDialog reportDialog = new OWLPAreaTaxonomyReportDialog(config);
                    reportDialog.showReports(config.getPAreaTaxonomy());
                    reportDialog.setModal(true);

                    reportDialog.setVisible(true);
                }
            }

            @Override
            public void setEnabledFor(PAreaTaxonomy entity) {
                this.setEnabled(true);
            }
        };
        
        ShowSourceHierarchyButton showHierarchyBtn = new ShowSourceHierarchyButton();
        HighlightOverlappingButton showOverlappingBtn = new HighlightOverlappingButton(config);

        showHierarchyBtn.setCurrentAbN(config.getPAreaTaxonomy());
        showOverlappingBtn.setCurrentAbN(config.getPAreaTaxonomy());
        reportsBtn.setCurrentAbN(config.getPAreaTaxonomy());
        pareaHelpBtn.setCurrentAbN(config.getPAreaTaxonomy());
        exportBtn.setCurrentAbN(config.getPAreaTaxonomy());
        pngBtn.setCurrentAbN(config.getPAreaTaxonomy());
        popoutBtn.setCurrentEntity(config.getPAreaTaxonomy());
        
        super.addOptionButton(showHierarchyBtn);
        super.addOptionButton(showOverlappingBtn);
        super.addOptionButton(reportsBtn);
        super.addOptionButton(pareaHelpBtn);
        super.addOptionButton(exportBtn);
        super.addOptionButton(pngBtn);
        super.addOptionButton(popoutBtn);
    }
}