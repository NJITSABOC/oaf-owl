package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyTextConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.OWLEntityNameConfiguration;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OWLDiffPAreaTaxonomyTextConfiguration extends DiffPAreaTaxonomyTextConfiguration {

    public OWLDiffPAreaTaxonomyTextConfiguration(DiffPAreaTaxonomy taxonomy) {
        super(new OWLEntityNameConfiguration(), taxonomy);
    }

    @Override
    public String getNodeHelpDescription(PArea parea) {
        DiffPArea diffPArea = (DiffPArea)parea;
        
        String rootName = parea.getName();
        
        String base = String.format("<b>%s</b> is a diff partial-area that identifies the state of a "
                + "subhierarchy of %d classes that are in the domain of the same set of properties.", 
                rootName, diffPArea.getConceptCount());
        
        String typeDetails = getNodeTypeDescription(diffPArea);
        
        return String.format("%s<p>%s", base, typeDetails);
    }
    
    private String getNodeTypeDescription(DiffPArea parea) {
        ChangeState nodeState = parea.getDiffNode().getChangeDetails().getNodeState();
        
        String result = "[UNKNOWN DIFF PAREA STATE]";
        
        String name = parea.getName();
        
        if(nodeState == ChangeState.Introduced) {
            result = String.format("<b>%s</b> is an <b>introduced partial-area</b>. "
                    + "This represents a new introduction point for a subhierarchy of "
                    + "classes that are in the domains of the same types of properties.", name);
            
        } else if(nodeState == ChangeState.Removed) {
            result = String.format("<b>%s</b> is a <b>removed partial-area</b>. "
                    + "This represents a the removal of an introduction point for a subhierarchy of "
                    + "classes that are in the domains of the same types of properties.", name);
            
        } else if(nodeState == ChangeState.Modified) {
            result = String.format("<b>%s</b> is a <b>modified partial-area</b>. "
                    + "This represents a subhierarchy of  "
                    + "classes that are in the domains of the same types of properties "
                    + "changing. The subhierarchy may have changed due to classes being added or "
                    + "removed from the subhierarchy or the subclass relations from the "
                    + "root of the subhierarchy changing.", name);
            
        } else if(nodeState == ChangeState.Unmodified) {
            result = String.format("<b>%s</b> is an <b>unmodified partial-area</b>. "
                    + "This represents a subhierarchy of  "
                    + "classes that are in the domains of the same types of properties "
                    + "undergoing no changes.", name);
        } else {
            
        }
        
        return result;
    }
    
    public String getPropertyUsageStr(Set<PropertyTypeAndUsage> propertyTypes) {
        
        if(propertyTypes.isEmpty()) {
            return "No Property Uses Selected";
        }
        
        ArrayList<String> propertyTypeStrs = new ArrayList<>();

        for (PropertyTypeAndUsage propertyType : propertyTypes) {
           propertyTypeStrs.add(propertyType.toString());
        }
        
        Collections.sort(propertyTypeStrs);
        
        String result = propertyTypeStrs.get(0);
        
        for(int c = 1; c < propertyTypeStrs.size(); c++) {
            result += propertyTypeStrs.get(c);
        }
        
        return result;        
    }
    
    private String getDiffPAreaTaxonomySummary() {
        return "[OWL DIFF PAREA TAXONOMY SUMMARY]";
    }
    
    @Override
    public String getAbNSummary() {
        
        PAreaTaxonomy taxonomy = super.getPAreaTaxonomy();
        
        String summary = "";
        

        summary += "<p><b>Help / Description:</b><br>";
        summary += getAbNHelpDescription();
        
        return summary;
    }
    
    @Override
    public String getAbNHelpDescription() {
        
        String desc = " A <b>diff partial-area taxonomy</b> summarizes changes\n" +
                "to sets of structurally and semantically similar classes between two ontology releases. "
                + "A diff partial-area taxonomy consists of two elements: <b>diff areas</b> and <b>diff partial-areas</b>. "
                + "<p>"
                + "A <b> diff area</b> captures changes to sets of classes that explicitly, or through inheritance, "
                + "are in the domains same types of properties. "
                + "<p>"
                + "A <b>diff partial-area</b> summarize changes to the subhierarchies of classes in each diff area.";
        
        return desc;
    }
}
