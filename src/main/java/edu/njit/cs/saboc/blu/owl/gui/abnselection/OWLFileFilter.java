/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.njit.cs.saboc.blu.owl.gui.abnselection;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Chris
 */
public class OWLFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return f.getName().endsWith(".owl") || f.getName().endsWith(".obo");
    }

    public String getDescription() {
        return "Ontology Files (.owl/.obo)";
    }
}
