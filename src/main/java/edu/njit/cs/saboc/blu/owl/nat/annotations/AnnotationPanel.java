package edu.njit.cs.saboc.blu.owl.nat.annotations;

import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.FilterableStringRenderer;
import edu.njit.cs.saboc.blu.owl.nat.OWLBrowserDataSource;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.NATBrowserPanel;
import edu.njit.cs.saboc.nat.generic.gui.layout.BaseNATAdjustableLayout;
import edu.njit.cs.saboc.nat.generic.gui.panels.BaseNATPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class AnnotationPanel extends BaseNATPanel<OWLConcept> {
    
    private final FilterableList<String> typeList;
    
    private final FilterableList<String> annotationsList;
    
    public AnnotationPanel(
            NATBrowserPanel<OWLConcept> mainPanel, 
            OWLConcept concept) {
        
        super(mainPanel);
        
        OWLBrowserDataSource dataSource = (OWLBrowserDataSource)mainPanel.getDataSource().get();
        
        Map<String, Set<String>> annotations = dataSource.getAnnotations(concept);
        
        this.typeList = new FilterableList<>();
        this.typeList.setPreferredSize(new Dimension(150, -1));
        this.typeList.setListCellRenderer(new FilterableStringRenderer());
        
        this.annotationsList = new FilterableList<>();
        this.annotationsList.setListCellRenderer(new FilterableStringRenderer());
        
        this.typeList.addListMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    if(typeList.getSelectedIndex() >= 0) {
                        String selectedElement = typeList.getSelectedValues().get(0).getObject();
                        
                        initializeAnnotationList(annotations.get(selectedElement));
                    } else {
                        clearAnnotationList();
                    }
                }
            }
        });
        
        this.setLayout(new BorderLayout());
        
        JSplitPane splitPane = BaseNATAdjustableLayout.createStyledSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        
        splitPane.setLeftComponent(typeList);
        splitPane.setRightComponent(annotationsList);
        
        this.add(splitPane);
        
        initializeTypeList(annotations.keySet());
    }
    
    private void initializeTypeList(Set<String> types) {
        ArrayList<String> sortedTypes = new ArrayList<>(types);
        Collections.sort(sortedTypes);
        
        ArrayList<Filterable<String>> contents = new ArrayList<>();
        
        sortedTypes.forEach( (type) -> {
            contents.add(new FilterableStringEntry(type));
        });
        
        typeList.setContents(contents);
    }
    
    private void initializeAnnotationList(Set<String> annotations) {
        ArrayList<String> sortedAnnotations = new ArrayList<>(annotations);
        Collections.sort(sortedAnnotations);
        
        ArrayList<Filterable<String>> contents = new ArrayList<>();
        
        sortedAnnotations.forEach( (type) -> {
            contents.add(new FilterableStringEntry(type));
        });
        
        annotationsList.setContents(contents);
    }
    
    private void clearAnnotationList() {
        this.annotationsList.setContents(new ArrayList<>());
    }
    
    
    
}
