package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFRecentlyOpenedFileManager.RecentlyOpenedFileException;
import edu.njit.cs.saboc.blu.core.utils.toolstate.OAFStateFileManager;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;

/**
 *
 * @author Chris
 */
public class BLUOntologyManager {
    
    private final ArrayList<OAFOntologyDataManager> openOntologies = new ArrayList<>();
    
    private OAFStateFileManager stateFileManager;

    public BLUOntologyManager() {
        
        try {
            this.stateFileManager = new OAFStateFileManager("BLUOWL");
        } catch(RecentlyOpenedFileException rofe) {
            this.stateFileManager = null;
        }
    }
    
    public class FailedToOpenOntologyException extends Exception {
        public FailedToOpenOntologyException(String reason) {
            super(reason);
        }
    }

    public OAFOntologyDataManager loadOntology(String file) throws FailedToOpenOntologyException {
        try {
            OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager(); 

            String fileName = file.substring(file.lastIndexOf("\\") + 1);
            
            for(OAFOntologyDataManager loader : openOntologies) {
                if(loader.getOntologyName().equals(fileName)) {
                    return loader;
                }
            }
            
            File ontologyFile = new File(file);

            OWLOntology ontology = owlManager.loadOntologyFromOntologyDocument(ontologyFile);

            OAFOntologyDataManager loader = new OAFOntologyDataManager(
                    stateFileManager, 
                    owlManager, 
                    ontologyFile, 
                    fileName, 
                    ontology);
            
            openOntologies.add(loader);
            
            return loader;
            
        } catch (OWLOntologyCreationIOException e) {
            Throwable ioException = e.getCause();
            
            if (ioException instanceof FileNotFoundException) {
                System.out.println("Could not load ontology. File not found: " + ioException.getMessage());
            } else if (ioException instanceof UnknownHostException) {
                System.out.println("Could not load ontology. Unknown host: " + ioException.getMessage());
            } else {
                System.out.println("Could not load ontology: " + ioException.getClass().getSimpleName() + " " + ioException.getMessage());
            }
            
            throw new FailedToOpenOntologyException("Ontology file not found");
            
        } catch (UnparsableOntologyException e) {
            System.out.println("Could not parse the ontology: " + e.getMessage());

            Map<OWLParser, OWLParserException> exceptions = e.getExceptions();

            for (OWLParser parser : exceptions.keySet()) {
                System.out.println("Tried to parse the ontology with the " + parser.getClass().getSimpleName() + " parser");
                System.out.println("Failed because: " + exceptions.get(parser).getMessage());
            }
            
            throw new FailedToOpenOntologyException("Unparseable ontology");
            
        } catch (UnloadableImportException e) {
            System.out.println("Could not load import: " + e.getImportsDeclaration());

            OWLOntologyCreationException cause = e.getOntologyCreationException();
            System.out.println("Reason: " + cause.getMessage());
            
            throw new FailedToOpenOntologyException("Unloadable import");
            
        } catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
            
             throw new FailedToOpenOntologyException("Could not load ontology");
        }
    }
    
    public void closeOntology(OAFOntologyDataManager loader) {
        openOntologies.remove(loader);
    }
    
    public ArrayList<OAFOntologyDataManager> getOpenOntologies() {
        return openOntologies;
    }
}
