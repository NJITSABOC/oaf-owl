package edu.njit.cs.saboc.blu.owl.gui.abnselection.createanddisplay;

import edu.njit.cs.saboc.blu.core.gui.dialogs.AbNCreateAndDisplayDialog;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLImportSubtaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class CreateAndDisplayImportSubtaxonomy extends AbNCreateAndDisplayDialog<OWLImportSubtaxonomy> {

    private final Set<String> selectedURIs;
    
    private final PAreaTaxonomy sourceTaxonomy;

    public CreateAndDisplayImportSubtaxonomy(
            String text, 
            Set<String> selectedURIs, 
            PAreaTaxonomy sourceTaxonomy,
            AbNDisplayManager displayManager) {
        
        super(text, displayManager);
        
        this.selectedURIs = selectedURIs;
        this.sourceTaxonomy = sourceTaxonomy;
    }

    @Override
    protected void display(OWLImportSubtaxonomy taxonomy) {
        super.getDisplayFrameListener().displayPAreaTaxonomy(taxonomy);
    }

    @Override
    protected OWLImportSubtaxonomy create() {
        OWLTaxonomy taxonomy = (OWLTaxonomy)sourceTaxonomy;
        
        return taxonomy.createImportSubtaxonomy(selectedURIs);
    }
}