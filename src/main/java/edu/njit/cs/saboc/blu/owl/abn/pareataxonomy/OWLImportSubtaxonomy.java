package edu.njit.cs.saboc.blu.owl.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.provenance.OWLImportSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class OWLImportSubtaxonomy<T extends PArea> extends OWLPAreaTaxonomy<PArea> 
    implements SubAbstractionNetwork<PAreaTaxonomy> {
    
    private final PAreaTaxonomy sourceTaxonomy;
    private final Set<String> uris;
    
    public OWLImportSubtaxonomy(
            PAreaTaxonomy sourceTaxonomy, 
            OAFOntologyDataManager dataManager,
            PAreaTaxonomy importSubtaxonomy, 
            Set<String> uris) {
        
        super(
                dataManager, 
                importSubtaxonomy,
                new OWLImportSubtaxonomyDerivation(
                        sourceTaxonomy.getDerivation(), 
                        uris));
        
        this.sourceTaxonomy = sourceTaxonomy;
        this.uris = uris;
    }
    
    public Set<String> getImportURIs() {
        return uris;
    }

    @Override
    public PAreaTaxonomy getSuperAbN() {
        return sourceTaxonomy;
    }

    @Override
    public OWLImportSubtaxonomy createImportSubtaxonomy(Set<String> uris) {
        OWLImportSubtaxonomyGenerator generator = new OWLImportSubtaxonomyGenerator();

        return generator.createImportSubtaxonomy(sourceTaxonomy, this.getPAreaTaxonomyFactory(), uris);
    }
}
