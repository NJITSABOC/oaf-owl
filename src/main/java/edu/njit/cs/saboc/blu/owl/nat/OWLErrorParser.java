package edu.njit.cs.saboc.blu.owl.nat;

import edu.njit.cs.saboc.blu.owl.abn.pareataxonomy.OWLInheritableProperty;
import edu.njit.cs.saboc.blu.owl.nat.error.OtherErrorWithOtherRestrictionType;
import edu.njit.cs.saboc.blu.owl.nat.error.RemoveOtherRestrictionTypeError;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedPropertyRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.CombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.nat.restrictionresult.OtherCombinedRestrictionResult;
import edu.njit.cs.saboc.blu.owl.ontology.OAFOWLOntology;
import edu.njit.cs.saboc.blu.owl.ontology.OWLConcept;
import edu.njit.cs.saboc.nat.generic.errorreport.error.ErrorParser;
import edu.njit.cs.saboc.nat.generic.errorreport.error.OntologyError;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.semanticweb.owlapi.model.OWLClassExpression;

/**
 *
 * @author Chris O
 */
public class OWLErrorParser extends ErrorParser<OWLConcept, OWLInheritableProperty> {
    
    protected class BaseOtherRestrictionTypeResult extends BaseParseResult {
        
        private final OWLClassExpression expr;
        
        public BaseOtherRestrictionTypeResult(BaseParseResult baseResult, OWLClassExpression expr) {
            super(baseResult.getComment(), baseResult.getSeverity());
            
            this.expr = expr;
        }
        
        public OWLClassExpression getRestrictionExpression() {
            return expr;
        }
    }
    
    private final OWLBrowserDataSource dataSource;
    
    public OWLErrorParser(OWLBrowserDataSource dataSource) {
        super(dataSource);
        
        this.dataSource = dataSource;
    }

    @Override
    public OntologyError<OWLConcept> parseError(OWLConcept concept, JSONObject object) throws ErrorParseException {
        
        if (!object.containsKey("type")) {
            throw new ErrorParseException("Type not defined for error object.");
        }

        String type = object.get("type").toString();
        
        if(type.equals("RemoveOtherRestrictionTypeError")) {
            return parseRemoveOtherRestrictionTypeError(concept, object);
        } else if(type.equals("OtherErrorWithOtherRestrictionType")) {
            return parseOtherErrorWithOtherRestrictionType(concept, object);
        } else {
            return super.parseError(concept, object);
        }
    }
    
    private RemoveOtherRestrictionTypeError parseRemoveOtherRestrictionTypeError(OWLConcept concept, JSONObject object) throws ErrorParseException {
        BaseOtherRestrictionTypeResult baseResult = parseBaseOtherRestrictionTypeError(concept, object);
        
        return new RemoveOtherRestrictionTypeError(
                (OAFOWLOntology)dataSource.getOntology(), 
                baseResult.getComment(), 
                baseResult.getSeverity(), 
                baseResult.getRestrictionExpression());
    }
    
    private OtherErrorWithOtherRestrictionType parseOtherErrorWithOtherRestrictionType(OWLConcept concept, JSONObject object) throws ErrorParseException {
        BaseOtherRestrictionTypeResult baseResult = parseBaseOtherRestrictionTypeError(concept, object);
        
        return new OtherErrorWithOtherRestrictionType(
                (OAFOWLOntology)dataSource.getOntology(), 
                baseResult.getComment(), 
                baseResult.getSeverity(), 
                baseResult.getRestrictionExpression());
    }
    
    private BaseOtherRestrictionTypeResult parseBaseOtherRestrictionTypeError(OWLConcept concept, JSONObject object) throws ErrorParseException {
        BaseParseResult baseResult = super.getBaseParseResult(object);
        
        if(!object.containsKey("restriction")) {
            throw new ErrorParseException("Restriction value not set.");
        }
        
        OWLClassExpression restrictionExpr = getRestrictionExpression(concept, object.get("restriction").toString());
        
        return new BaseOtherRestrictionTypeResult(baseResult, restrictionExpr);
    }
    
    private OWLClassExpression getRestrictionExpression(OWLConcept concept, String restrictionExprName) throws ErrorParseException {
        ArrayList<CombinedRestrictionResult> results = dataSource.getAllRestrictions(concept);
        
        for(CombinedRestrictionResult result : results) {
            
            OWLClassExpression restrictionExpr;
            
            if(result instanceof CombinedPropertyRestrictionResult) {
                CombinedPropertyRestrictionResult<?, ?, ?> propertyResult = (CombinedPropertyRestrictionResult<?, ?, ?>)result;
                
                restrictionExpr = propertyResult.getAllResults().get(0).getRestriction();
            } else {
                OtherCombinedRestrictionResult otherResult = (OtherCombinedRestrictionResult)result;

                restrictionExpr = otherResult.getRestriction();
            }
            
            if(restrictionExpr.toString().equalsIgnoreCase(restrictionExprName)) {
                return restrictionExpr;
            }
        }
        
        throw new ErrorParseException("Restriction class expression not found in ontology.");
    }
    
}
