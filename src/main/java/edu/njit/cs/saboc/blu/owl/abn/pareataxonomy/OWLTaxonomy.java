
package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.owl.abn.OWLAbstractionNetwork;
import edu.njit.cs.saboc.blu.owl.utils.owlproperties.PropertyTypeAndUsage;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public interface OWLTaxonomy extends OWLAbstractionNetwork {
    public Set<PropertyTypeAndUsage> getPropertyTypesAndUsages();
    
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> selectedURIs);
}
