package edu.njit.cs.saboc.blu.owl.abnhistory;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser.AbNParseException;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;
import edu.njit.cs.saboc.blu.owl.abn.tan.OWLTANFactory;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOntologyDataManager;

/**
 *
 * @author Hao Liu
 */
public class OWLAbNDerivationFactory extends AbNDerivationFactory{

    private final OAFOntologyDataManager manager;    

    public OWLAbNDerivationFactory(OAFOntologyDataManager manager) {
        this.manager = manager;
    }

    @Override
    public PAreaTaxonomyFactory getPAreaTaxonomyFactory() throws AbNParseException {
        throw new AbNParseException("OWL PAreaTaxonomy Factory not injectable.");
    }

    @Override
    public TANFactory getTANFactory() {
        return new OWLTANFactory(manager);
    }

    @Override
    public TargetAbstractionNetworkFactory getTargetAbNFactory() throws AbNParseException {
        throw new AbNParseException("OWL Target AbN Factory not injectable.");
    }
}
