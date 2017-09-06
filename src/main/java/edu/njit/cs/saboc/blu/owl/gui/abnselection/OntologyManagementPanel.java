package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.prefs.Preferences;
import javax.swing.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Chris
 */
public class OntologyManagementPanel extends JPanel {
    
    public interface OntologyManagementListener {
        public void ontologySelected(OAFOntologyDataManager ontology);
        
        public void ontologyUnselected();
        
        public void ontologyLoading();
        
        public void ontologyLoaded(OAFOntologyDataManager ontology);
    }
    
    private final DefaultListModel<String> ontologyListModel = new DefaultListModel<>();
    private final JList ontologyList = new JList(ontologyListModel);
    
    private final JButton btnOpenOnt;
    
    private final JButton btnCloseOnt;
    
    private final BLUOntologyManager ontologyManager;
    
    private final JProgressBar loadProgressBar;
    
    private final JCheckBox chkAutoLoadOnt;
    
    private final JButton btnClearAutoLoad;
    
    private final ArrayList<OntologyManagementListener> ontologySelectionListeners = new ArrayList<>();
    
    public OntologyManagementPanel(BLUOntologyManager ontologyManager) {
        this.ontologyManager = ontologyManager;
        
        this.ontologyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
               
        btnOpenOnt = new JButton("<html><div align='center'>Open an Ontology<br>(.owl/.obo)");
        btnOpenOnt.setFont(btnOpenOnt.getFont().deriveFont(Font.BOLD, 14));
        btnOpenOnt.addActionListener((ae) -> {
            promptForAndLoadOntology();
        });

        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.add(btnOpenOnt, BorderLayout.NORTH);
        selectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Open an Ontology"));

        loadProgressBar = new JProgressBar();
        loadProgressBar.setStringPainted(true);
        loadProgressBar.setIndeterminate(true);
        loadProgressBar.setVisible(false);
        
        loadProgressBar.setFont(loadProgressBar.getFont().deriveFont(Font.BOLD, 14));
        
        selectionPanel.add(Box.createVerticalStrut(8), BorderLayout.CENTER);
        
        selectionPanel.add(loadProgressBar, BorderLayout.SOUTH);
        
        JPanel ontologyListPanel = new JPanel(new BorderLayout());

        ontologyList.addListSelectionListener((lse) -> {
            
            if (!lse.getValueIsAdjusting()) {
                if (ontologyList.getSelectedIndex() >= 0) {
                    ontologySelected(ontologyList.getSelectedIndex());
                } else {
                    ontologyUnselected();
                }
            }
        });
        
        ontologyList.setFont(ontologyList.getFont().deriveFont(Font.BOLD, 14));
        
        ontologyListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), 
                "Currently Open Ontologies"));
        
        
        JScrollPane scrollPane = new JScrollPane(ontologyList);
        
        ontologyListPanel.add(scrollPane, BorderLayout.CENTER);
        
        
        btnCloseOnt = new JButton("<html><div align='center'>Close Selected Ontology");
        btnCloseOnt.addActionListener( (ae) -> {
            if(ontologyList.getSelectedIndex() >= 0) {
                removeOntology(ontologyList.getSelectedIndex());
            }
        });
        btnCloseOnt.setFont(btnCloseOnt.getFont().deriveFont(Font.BOLD, 14));
        
        btnCloseOnt.setEnabled(false);
        
        chkAutoLoadOnt = new JCheckBox("<html>Automatically Open Selected<br>Ontology on Startup");
        chkAutoLoadOnt.setFont(chkAutoLoadOnt.getFont().deriveFont(Font.BOLD, 14));
        chkAutoLoadOnt.setSelected(false);
        chkAutoLoadOnt.setEnabled(false);
        
        chkAutoLoadOnt.addActionListener((ActionEvent ae) -> {
            
            if (ontologyList.getSelectedIndex() >= 0) {
                File ontologyFile = ontologyManager.getOpenOntologies().get(ontologyList.getSelectedIndex()).getOntologyFile();
                
                if (chkAutoLoadOnt.isSelected()) {
                    addToAutoLoad(ontologyFile);
                } else {
                    removeFromAutoLoad(ontologyFile);
                }
            }
        });
        
        btnClearAutoLoad = new JButton("Reset Auto Open Preferences");
        btnClearAutoLoad.setFont(btnClearAutoLoad.getFont().deriveFont(Font.BOLD, 14));
        btnClearAutoLoad.addActionListener((ae) -> {
            Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
            
            prefsRoot.remove("BLUOWL Autoload List");
            
            chkAutoLoadOnt.setEnabled(false);
        });

        autoLoadOntologies();
        
        JPanel closePanel = new JPanel(new BorderLayout());
        
        JPanel autoLoadPanel = new JPanel(new BorderLayout());
        
        autoLoadPanel.add(chkAutoLoadOnt, BorderLayout.NORTH);
        autoLoadPanel.add(btnClearAutoLoad, BorderLayout.SOUTH);
        
        closePanel.add(autoLoadPanel, BorderLayout.NORTH);
        closePanel.add(Box.createVerticalStrut(16), BorderLayout.CENTER);
        closePanel.add(btnCloseOnt, BorderLayout.SOUTH);
        
        JPanel contentPanel = new JPanel(new BorderLayout());

        contentPanel.add(selectionPanel, BorderLayout.NORTH);
        contentPanel.add(ontologyListPanel, BorderLayout.CENTER);
        
        contentPanel.add(closePanel, BorderLayout.SOUTH);
        
        this.setLayout(new BorderLayout());

        this.add(contentPanel, BorderLayout.CENTER);
    }
    
    private void addToAutoLoad(File ontologyFile) {
        Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
        String autoLoadListJSON = prefsRoot.get("BLUOWL Autoload List", "");

        JSONArray autoLoadList;

        JSONParser parser = new JSONParser();

        if (autoLoadListJSON.isEmpty()) {
            autoLoadList = new JSONArray();
        } else {
            try {
                autoLoadList = (JSONArray) parser.parse(autoLoadListJSON);
            } catch (ParseException pe) {
                pe.printStackTrace();
                return;
            }
        }

        String absolutePath = ontologyFile.getAbsolutePath();

        boolean found = false;

        for (Object o : autoLoadList) {
            String filePath = (String) o;

            if (filePath.equalsIgnoreCase(absolutePath)) {
                found = true;
                break;
            }
        }

        if (!found) {
            autoLoadList.add(absolutePath);
            prefsRoot.put("BLUOWL Autoload List", JSONValue.toJSONString(autoLoadList));
        }
    }
    
    private void removeFromAutoLoad(File ontologyFile) {
        Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
        String autoLoadListJSON = prefsRoot.get("BLUOWL Autoload List", "");
                
        if(autoLoadListJSON.isEmpty()) {
            return;
        }
        
        JSONParser parser = new JSONParser();
        
        try {
            JSONArray autoLoadList = (JSONArray) parser.parse(autoLoadListJSON);

            String absolutePath = ontologyFile.getAbsolutePath();
            
            autoLoadList.remove(absolutePath);

            prefsRoot.put("BLUOWL Autoload List", JSONValue.toJSONString(autoLoadList));

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }
    
    private boolean inAutoLoadList(File ontologyFile) {
        Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
        String autoLoadListJSON = prefsRoot.get("BLUOWL Autoload List", "");
                
        if(autoLoadListJSON.isEmpty()) {
            return false;
        }
        
        JSONParser parser = new JSONParser();
        
        try {
            JSONArray autoLoadList = (JSONArray) parser.parse(autoLoadListJSON);
            
            String absolutePath = ontologyFile.getAbsolutePath();
    
            for(Object o : autoLoadList) {
                String filePath = (String)o;
                
                if(filePath.equalsIgnoreCase(absolutePath)) {
                    return true;
                }
            }

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        
        return false;
    }
    
    private void autoLoadOntologies() {
        
        Thread autoLoadThread = new Thread(() -> {
            Preferences prefsRoot = Preferences.userNodeForPackage(this.getClass());
            String autoLoadListJSON = prefsRoot.get("BLUOWL Autoload List", "");

            if (autoLoadListJSON.isEmpty()) {
                return;
            }

            JSONParser parser = new JSONParser();

            try {
                JSONArray autoLoadList = (JSONArray) parser.parse(autoLoadListJSON);

                setEnabled(false);
                loadProgressBar.setString(String.format("Auto Loading Ontologies..."));
                loadProgressBar.setVisible(true);

                for (Object o : autoLoadList) {
                    String filePath = (String) o;
                    File file = new File(filePath);

                    loadProgressBar.setString(String.format("Loading %s...", file.getName()));

                    try {
                        ontologyManager.loadOntology(filePath);
                        ontologyListModel.addElement(file.getName());
                    } catch(BLUOntologyManager.FailedToOpenOntologyException e) {
                        
                        JOptionPane.showMessageDialog(
                                null, 
                                "An error occured while automatically opening " + file.getName(), 
                                "Error Automatically Opening Ontology", 
                                JOptionPane.ERROR_MESSAGE);
                        
                        e.printStackTrace();
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    setEnabled(true);
                    loadProgressBar.setVisible(false);
                });
                
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        });

        autoLoadThread.start();
    }
    
    private void ontologySelected(int selectedIndex) {
        btnCloseOnt.setEnabled(true);
        
        if(inAutoLoadList(ontologyManager.getOpenOntologies().get(selectedIndex).getOntologyFile())) {
            chkAutoLoadOnt.setSelected(true);
        } else {
            chkAutoLoadOnt.setSelected(false);
        }
        
        chkAutoLoadOnt.setEnabled(true);
 
        OAFOntologyDataManager loader = ontologyManager.getOpenOntologies().get(selectedIndex);
        
        ontologySelectionListeners.forEach((OntologyManagementListener listener) -> {
            listener.ontologySelected(loader);
        });
        
        if(!loader.allClassesHaveDefinedSuperclass()) {
            JOptionPane.showMessageDialog(this.getParent(), 
                    
                    "<html>One or more classes in the selected ontology do not have owl:subClassOf axioms.<p>"
                            + "The ontology may be unreasoned or there was an error when reasoning the ontology.<p><p>"
                            + "Stated superclasses in owl:equivalentClass axioms will be used when neccessary and available.", 
                    
                    "Ontology Not Reasoned?", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void removeOntology(int index) {
        ontologyManager.getOpenOntologies().remove(index);
        ontologyListModel.remove(index);
    }
    
    private void ontologyUnselected() {
        btnCloseOnt.setEnabled(false);
        chkAutoLoadOnt.setEnabled(false);
        chkAutoLoadOnt.setSelected(false);
        
        ontologySelectionListeners.forEach((OntologyManagementListener listener) -> {
            listener.ontologyUnselected();
        });
    }
    
    public void addOntologySelectionListener(OntologyManagementListener listener) {
        this.ontologySelectionListeners.add(listener);
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.btnOpenOnt.setEnabled(value);
        this.ontologyList.setEnabled(value);
        this.btnClearAutoLoad.setEnabled(value);
  
        if (ontologyList.getSelectedIndex() >= 0) {
            this.btnCloseOnt.setEnabled(value);
            this.chkAutoLoadOnt.setEnabled(value);
        }
    }

    private void promptForAndLoadOntology() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new OWLFileFilter());
        
        int returnVal = chooser.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            
            loadOntology(file);
        }
    }

    private void loadOntology(File file) {
        
        ontologySelectionListeners.forEach( (OntologyManagementListener listener) -> { 
           listener.ontologyLoading();
        });
        
        Thread loadThread = new Thread(() -> {
            setEnabled(false);
            loadProgressBar.setString(String.format("Loading %s...", file.getName()));
            loadProgressBar.setVisible(true);
            
            try {
                OAFOntologyDataManager result = ontologyManager.loadOntology(file.getAbsolutePath());

                SwingUtilities.invokeLater(() -> {
                    if (!ontologyListModel.contains(file.getName())) {
                        ontologyListModel.addElement(file.getName());
                    }

                    ontologyList.setSelectedIndex(ontologyListModel.indexOf(file.getName()));

                    ontologySelectionListeners.forEach((OntologyManagementListener listener) -> {
                        listener.ontologyLoaded(result);
                    });

                    setEnabled(true);
                    loadProgressBar.setVisible(false);
                });
            } catch (BLUOntologyManager.FailedToOpenOntologyException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "An error occured while opening " + file.getName(),
                        "Error Opening Ontology",
                        JOptionPane.ERROR_MESSAGE);

                e.printStackTrace();
            }
        });
        
        loadThread.start();
    }
     
    public Optional<OAFOntologyDataManager> getSelectedOntology() {
        if(ontologyList.getSelectedIndex() >= 0) {
            return Optional.of(ontologyManager.getOpenOntologies().get(ontologyList.getSelectedIndex()));
        } else {
            return Optional.empty();
        }
    }
}
