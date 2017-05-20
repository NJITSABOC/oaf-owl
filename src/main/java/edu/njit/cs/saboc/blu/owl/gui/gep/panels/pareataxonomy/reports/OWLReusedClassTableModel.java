package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class OWLReusedClassTableModel extends OAFAbstractTableModel<ReusedClassEntry> {

    private final OWLPAreaTaxonomyConfiguration config;
    
    public OWLReusedClassTableModel(OWLPAreaTaxonomyConfiguration config) {
        super(new String [] {
            "Class URI",
            "Class Name",
            "Area",
            "Partial-area(s)"
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ReusedClassEntry item) {

        if (item.getPAreas().isEmpty()) {
            return new Object[] {
                item.getConcept().getIDAsString(),
                item.getConcept().getName(),
                "(none)",
                "(none)"
            };
        } else {
            
            PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
            
            Area area = taxonomy.getAreaFor(item.getPAreas().iterator().next());
            
            String areaName = area.getName("\n");
            
            ArrayList<String> sortedPAreaNames = new ArrayList<>();
            
            item.getPAreas().forEach( (parea) -> {
                sortedPAreaNames.add(String.format("%s (%d)", 
                        parea.getName(), 
                        parea.getConceptCount()));
            });
            
            Collections.sort(sortedPAreaNames);
            
            String pareaNames = String.format("%s", sortedPAreaNames.get(0));
            
            for(int c = 1; c < sortedPAreaNames.size(); c++) {
                pareaNames += String.format("%s", sortedPAreaNames.get(c));
            }

            return new Object[]{
                item.getConcept().getIDAsString(),
                item.getConcept().getName(),
                areaName,
                pareaNames
            };
        }

    }
}
