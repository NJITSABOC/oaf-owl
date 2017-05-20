package edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.reports;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNReportPanel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.entry.FilterableStringEntry;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.FilterableList;
import edu.njit.cs.saboc.blu.core.utils.filterable.renderer.FilterableStringRenderer;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLTaxonomy;
import edu.njit.cs.saboc.blu.owl.gui.gep.panels.pareataxonomy.configuration.OWLPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class OWLPAreaTaxonomyImportsReport extends AbNReportPanel<PAreaTaxonomy> {
    
    public interface DeriveImportSubtaxonomyAction {
        public void deriveAndDisplayImportSubtaxonomy(Set<String> selectedUris);
    }

    private final FilterableList<String> uriList;
    
    private final AbstractEntityList<ReusedClassEntry> entityReuseList;
    
    private final JSplitPane splitPane;
    
    public OWLPAreaTaxonomyImportsReport(
            OWLPAreaTaxonomyConfiguration config, 
            DeriveImportSubtaxonomyAction deriveAction) {
        
        super(config);
        
        uriList = new FilterableList();
        uriList.setListCellRenderer(new FilterableStringRenderer());
        
        uriList.addListMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(uriList.getSelectedIndex() >= 0) {
                     String selectedURI = (String)uriList.getSelectedValues().iterator().next().getObject();
                     
                     entityReuseList.clearContents();
                     
                     Thread loadThread = new Thread(() -> {
                         ArrayList<ReusedClassEntry> reusedClses = getClassReuseReport(selectedURI);
                         
                         SwingUtilities.invokeLater(() -> {
                             entityReuseList.setContents(reusedClses);
                         });
                     });
                     
                     loadThread.start();
                } else {
                    entityReuseList.clearContents();
                }
            }
        });
        
        entityReuseList = new AbstractEntityList<ReusedClassEntry>(new OWLReusedClassTableModel(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<ReusedClassEntry>> entities) {
                if(entities.isPresent()) {
                    return String.format("Classes with URI (%d)", entities.get().size());
                } else {
                    return "Classes with URI";
                }
            }
        };
        
        splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.setLayout(new BorderLayout());
        
        splitPane.setTopComponent(uriList);
        
        splitPane.setDividerLocation(200);
        
        JTabbedPane importReportPane = new JTabbedPane();
        
        JPanel entitiesReusedPanel = new JPanel(new BorderLayout());

        entitiesReusedPanel.add(entityReuseList, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        final JButton deriveImportSubtaxonomyBtn = new JButton("Create Import Subtaxonomy");
        deriveImportSubtaxonomyBtn.setEnabled(false);
        
        deriveImportSubtaxonomyBtn.addActionListener( (ae) -> {
            String selectedUri = (String)uriList.getSelectedValues().iterator().next().getObject();
            
            deriveAction.deriveAndDisplayImportSubtaxonomy(Collections.singleton(selectedUri));
        });
        
        uriList.addListMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent me) {
                if (uriList.getSelectedIndex() >= 0) {
                    String selectedURI = (String) uriList.getSelectedValues().iterator().next().getObject();

                    ArrayList<ReusedClassEntry> reusedClses = getClassReuseReport(selectedURI);

                    if (reusedClses.isEmpty()) {
                        deriveImportSubtaxonomyBtn.setEnabled(false);
                    } else {
                        deriveImportSubtaxonomyBtn.setEnabled(true);
                    }
                } else {
                    deriveImportSubtaxonomyBtn.setEnabled(false);
                }
            }
        });
        
        buttonPanel.add(deriveImportSubtaxonomyBtn);
        
        entitiesReusedPanel.add(buttonPanel, BorderLayout.SOUTH);
                
        importReportPane.addTab("Classes", entitiesReusedPanel);
        
        splitPane.setBottomComponent(importReportPane);
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void displayAbNReport(PAreaTaxonomy abn) {
        
        OWLTaxonomy owlTaxonomy = (OWLTaxonomy)abn;
        
        OWLOntology ontology = owlTaxonomy.getDataManager().getSourceOntology();
        
        Set<OWLClass> clses = ontology.getClassesInSignature();
        
        HashSet<String> uniqueURIs = new HashSet<>();
        
        clses.forEach((OWLClass cls) -> {
            uniqueURIs.add(getBaseURI(cls));
        });
        
        HashSet<OWLProperty> allProperties = new HashSet<>(ontology.getObjectPropertiesInSignature());
        allProperties.addAll(ontology.getDataPropertiesInSignature());
        
        allProperties.forEach( (OWLProperty property) -> {
            uniqueURIs.add(getBaseURI(property));
        });
        
        ArrayList<String> sortedURIs = new ArrayList<>(uniqueURIs);
        
        Collections.sort(sortedURIs);
        
        ArrayList<FilterableStringEntry> uriEntries = new ArrayList<>();
        
        sortedURIs.forEach( (uri) -> {
            uriEntries.add(new FilterableStringEntry(uri));
        });
        
        uriList.setContents(uriEntries);
    }
    
    private String getBaseURI(OWLEntity entity) {
        String uri = entity.getIRI().toString().toLowerCase();

        if (uri.contains("http://purl.obolibrary.org/obo")) {
            if(uri.contains("_")){
                return uri.substring(0, uri.lastIndexOf("_"));
            } else if(uri.contains("#")) {
                return uri.substring(0, uri.lastIndexOf("#"));
            } else {
                return uri;
            }
            
            
        } else {
            if (uri.contains("#")) {
                return uri.substring(0, uri.lastIndexOf("#"));
            } else {
                return uri.substring(0, uri.lastIndexOf("/"));
            }
        }
    }

    private ArrayList<ReusedClassEntry> getClassReuseReport(String baseURI) {
        
        OWLPAreaTaxonomyConfiguration config = (OWLPAreaTaxonomyConfiguration)super.getConfiguration();
        
        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        Set<PArea> pareas = taxonomy.getPAreas();
        
        Map<OWLConcept, Set<PArea>> pareaMap = new HashMap<>();
        
        pareas.forEach( (parea) -> {
            Set<Concept> concepts = parea.getConcepts();
            
            concepts.forEach( (concept) -> {
                
                OWLConcept owlConcept = (OWLConcept)concept;
                
                String uri = concept.getIDAsString();
                
                if(uri.startsWith(baseURI)) {
                    if(!pareaMap.containsKey(owlConcept)) {
                        pareaMap.put(owlConcept, new HashSet<>());
                    }
                    
                    pareaMap.get(owlConcept).add(parea);
                }
            });
        });
        
        ArrayList<ReusedClassEntry> reusedClsEntries = new ArrayList<>();
        
        pareaMap.forEach( (concept,  conceptPAreas) -> {
            reusedClsEntries.add(new ReusedClassEntry(concept, conceptPAreas));
        });
        
        Collections.sort(reusedClsEntries, (a, b) -> a.getConcept().getName().compareTo(b.getConcept().getName()));
        
        return reusedClsEntries;
    }
}
