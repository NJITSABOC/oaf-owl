package edu.njit.cs.saboc.blu.owl.nat;


import edu.njit.cs.saboc.blu.owl.utils.OWLUtilities;
import java.util.Optional;
import java.util.Set;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;

/**
 *
 * @author Chris O
 */
public class AxiomStringGenerator {
    
    public static String getClassExpressionStr(OWLOntology ontology, OWLClassExpression expr, boolean styled) {
        return String.format("<html>%s", AxiomStringGenerator.parseClassExpression(ontology, expr, Optional.empty(), styled));
    }
    
    public static String getStyledClassExpressionStrFiltered(OWLOntology ontology, OWLClassExpression expr, String filter) {
        return String.format("<html>%s", AxiomStringGenerator.parseClassExpression(ontology, expr, Optional.of(filter), true));
    }
    
    private static String parseClassExpression(OWLOntology ontology, OWLClassExpression expr, Optional<String> filter, boolean styled) {
        switch(expr.getClassExpressionType()) {
            case OWL_CLASS:
                return AxiomStringGenerator.parseOWLClass(ontology, (OWLClass)expr, filter);
            case OBJECT_SOME_VALUES_FROM:
                return AxiomStringGenerator.parseObjectSomeValuesOf(ontology, (OWLObjectSomeValuesFrom)expr, filter, styled);
            case OBJECT_ALL_VALUES_FROM:
                return AxiomStringGenerator.parseObjectAllValuesOf(ontology, (OWLObjectAllValuesFrom)expr, filter, styled);
            case OBJECT_MIN_CARDINALITY:  
                return AxiomStringGenerator.parseObjectMinCardinality(ontology, (OWLObjectMinCardinality)expr, filter, styled);
            case OBJECT_MAX_CARDINALITY: 
                return AxiomStringGenerator.parseObjectMaxCardinality(ontology, (OWLObjectMaxCardinality)expr, filter, styled);
            case OBJECT_EXACT_CARDINALITY: 
                return AxiomStringGenerator.parseObjectExactCardinality(ontology, (OWLObjectExactCardinality)expr, filter, styled);
            case OBJECT_HAS_VALUE:
                return AxiomStringGenerator.parseObjectHasValue(ontology, (OWLObjectHasValue)expr, filter, styled);
            case OBJECT_HAS_SELF: 
                return AxiomStringGenerator.parseObjectHasSelf(ontology, (OWLObjectHasSelf)expr, filter, styled);
            case DATA_SOME_VALUES_FROM:
                return AxiomStringGenerator.parseDataSomeValuesOf(ontology, (OWLDataSomeValuesFrom)expr, filter, styled);
            case DATA_ALL_VALUES_FROM:
                return AxiomStringGenerator.parseDataAllValuesOf(ontology, (OWLDataAllValuesFrom)expr, filter, styled);
            case DATA_MIN_CARDINALITY:
                return AxiomStringGenerator.parseDataMinCardinality(ontology, (OWLDataMinCardinality)expr, filter, styled);
            case DATA_MAX_CARDINALITY: 
                return AxiomStringGenerator.parseDataMaxCardinality(ontology, (OWLDataMaxCardinality)expr, filter, styled);
            case DATA_EXACT_CARDINALITY:
                return AxiomStringGenerator.parseDataExactCardinality(ontology, (OWLDataExactCardinality)expr, filter, styled);
            case DATA_HAS_VALUE:
                return AxiomStringGenerator.parseDataHasValue(ontology, (OWLDataHasValue)expr, filter, styled);
            case OBJECT_INTERSECTION_OF:
                return AxiomStringGenerator.parseIntersectionOf(ontology, (OWLObjectIntersectionOf)expr, filter, styled);
            case OBJECT_UNION_OF:
                return AxiomStringGenerator.parseUnionOf(ontology, (OWLObjectUnionOf)expr, filter, styled);
            case OBJECT_COMPLEMENT_OF:
                return AxiomStringGenerator.parseComplementOf(ontology, (OWLObjectComplementOf)expr, filter, styled);
            case OBJECT_ONE_OF:
                return AxiomStringGenerator.parseObjectOneOf(ontology, (OWLObjectOneOf)expr, filter, styled);
            default:
                return "(ERROR: UNKNOWN CLASS EXPRESSION TYPE)";
        }
    }
    
    public static String getAxiomTypeName(OWLClassExpression expr) {
        switch(expr.getClassExpressionType()) {
            case OWL_CLASS:
                return "OWLClass";
            case OBJECT_SOME_VALUES_FROM:
                return "ObjectSomeValuesFrom";
            case OBJECT_ALL_VALUES_FROM:
                return "ObjectAllValuesFrom";
            case OBJECT_MIN_CARDINALITY:  
                return "ObjectMinCardinality";
            case OBJECT_MAX_CARDINALITY: 
                return "ObjectMaxCardinality";
            case OBJECT_EXACT_CARDINALITY: 
                return "ObjectExactCardinality";
            case OBJECT_HAS_VALUE:
                return "ObjectHasValue";
            case OBJECT_HAS_SELF: 
                return "ObjectHasSelf";
            case DATA_SOME_VALUES_FROM:
                return "DataSomeValuesFrom";
            case DATA_ALL_VALUES_FROM:
                return "DataAllValuesFrom";
            case DATA_MIN_CARDINALITY:
                return "DataMinCardinality";
            case DATA_MAX_CARDINALITY: 
                return "DataMaxCardinality";
            case DATA_EXACT_CARDINALITY:
                return "DataExactCardinality";
            case DATA_HAS_VALUE:
                return "DataHasValue";
            case OBJECT_INTERSECTION_OF:
                return "ObjectIntersectionOf";
            case OBJECT_UNION_OF:
                return "ObjectUnionOf";
            case OBJECT_COMPLEMENT_OF:
                return "ObjectComplementOf";
            case OBJECT_ONE_OF:
                return "ObjectOneOf";
            default:
                return "UnknownType";
        }
    }
    
    private static String parseOWLClass(OWLOntology ontology, OWLClass cls, Optional<String> filter) {
        if(filter.isPresent()) {
            return String.format("%s", AxiomStringGenerator.filter(OWLUtilities.getClassLabel(ontology, cls), filter.get()));
        } else {
            return String.format("%s", OWLUtilities.getClassLabel(ontology, cls));
        }        
    }
    
    private static String parseOWLProperty(OWLOntology ontology, OWLProperty property, Optional<String> filter) {
        if (filter.isPresent()) {
            return String.format("%s", AxiomStringGenerator.filter(OWLUtilities.getPropertyLabel(ontology, property), filter.get()));
        } else {
            return String.format("%s", OWLUtilities.getPropertyLabel(ontology, property));
        }
    }
    
    private static String createPropertyConstraintStr(String propertyName, String propertyType, String constraint, String range, boolean styled) {
        
        if (styled) {
            return String.format("(<font color='red'><b>%s</b></font> <b>[%s]</b> <font color='purple'><b>%s</b></font> %s)",
                    propertyName, propertyType, constraint, range);
        } else {
            return String.format("(%s [%s] %s %s)",
                    propertyName, propertyType, constraint, range);
        }

    }
    
    private static String createOPPropertyConstraintStr(String propertyName, String constraint, String range, boolean styled) {
        return AxiomStringGenerator.createPropertyConstraintStr(propertyName, "OP", constraint, range, styled);
    }
    
    private static String createDPPropertyConstraintStr(String propertyName, String constraint, String range, boolean styled) {
        return AxiomStringGenerator.createPropertyConstraintStr(propertyName, "DP", constraint, range, styled);
    }
    
    private static String parseObjectSomeValuesOf(OWLOntology ontology, OWLObjectSomeValuesFrom someValuesFrom, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = someValuesFrom.getProperty().asOWLObjectProperty();
        
        OWLClassExpression filler = someValuesFrom.getFiller();
        
        String propertyName = parseOWLProperty(ontology, property, filter);
        String rangeStr = AxiomStringGenerator.parseClassExpression(ontology, filler, filter, styled);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, "some", rangeStr, styled);
    }
    
    private static String parseObjectAllValuesOf(OWLOntology ontology, OWLObjectAllValuesFrom allValuesFrom, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = allValuesFrom.getProperty().asOWLObjectProperty();

        OWLClassExpression filler = allValuesFrom.getFiller();
        
        String propertyName = parseOWLProperty(ontology, property, filter);
        String rangeStr = AxiomStringGenerator.parseClassExpression(ontology, filler, filter, styled);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, "all", rangeStr, styled);
    }

    private static String parseObjectMinCardinality(OWLOntology ontology, OWLObjectMinCardinality minCardinality, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = minCardinality.getProperty().asOWLObjectProperty();

        OWLClassExpression filler = minCardinality.getFiller();
        
        int cardinality = minCardinality.getCardinality();
        
        String propertyName = parseOWLProperty(ontology, property, filter);
        String rangeStr = AxiomStringGenerator.parseClassExpression(ontology, filler, filter, styled);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, String.format("min %d", cardinality), rangeStr, styled);
    }
    
    private static String parseObjectMaxCardinality(OWLOntology ontology, OWLObjectMaxCardinality maxCardinality, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = maxCardinality.getProperty().asOWLObjectProperty();

        OWLClassExpression filler = maxCardinality.getFiller();
        
        int cardinality = maxCardinality.getCardinality();

        String propertyName = parseOWLProperty(ontology, property, filter);
        String rangeStr = AxiomStringGenerator.parseClassExpression(ontology, filler, filter, styled);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, String.format("max %d", cardinality), rangeStr, styled);
    }

    private static String parseObjectExactCardinality(OWLOntology ontology, OWLObjectExactCardinality exactCardinality, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = exactCardinality.getProperty().asOWLObjectProperty();

        OWLClassExpression filler = exactCardinality.getFiller();
        
        int cardinality = exactCardinality.getCardinality();

        String propertyName = parseOWLProperty(ontology, property, filter);
        String rangeStr = AxiomStringGenerator.parseClassExpression(ontology, filler, filter, styled);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, String.format("exactly %d", cardinality), rangeStr, styled);
    }

    private static String parseObjectHasValue(OWLOntology ontology, OWLObjectHasValue hasValue, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = hasValue.getProperty().asOWLObjectProperty();
        
        Set<OWLNamedIndividual> individuals = hasValue.getIndividualsInSignature();
        
        String individualsStr;
        
        if (!individuals.isEmpty()) {
            StringBuilder individualsStrBuilder = new StringBuilder();

            individuals.forEach(((OWLNamedIndividual ni) -> {
                individualsStrBuilder.append(ni.toString());
                individualsStrBuilder.append(", ");
            }));
            
            individualsStr = individualsStrBuilder.substring(0, individualsStrBuilder.length() - 2);
        } else {
            individualsStr = "(No individuals defined)";
        }
        
        String propertyName = parseOWLProperty(ontology, property, filter);
        
        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, "has value", individualsStr, styled);
    }
    
    private static String parseObjectHasSelf(OWLOntology ontology, OWLObjectHasSelf hasSelf, Optional<String> filter, boolean styled) {
        OWLObjectProperty property = hasSelf.getProperty().asOWLObjectProperty();

        String propertyName = parseOWLProperty(ontology, property, filter);

        return AxiomStringGenerator.createOPPropertyConstraintStr(propertyName, "has self", "", styled);
    }
    
    private static String parseDataPropertyRangeValues(OWLDataRange dataRange, Optional<String> filter) {
        String dataPropertyRange;

        if (dataRange.isDatatype()) {
            OWLDatatype dataType = dataRange.asOWLDatatype();

            dataPropertyRange = dataType.toString();
        } else {
            dataPropertyRange = dataRange.toString();
        }

        if (filter.isPresent()) {
            dataPropertyRange = AxiomStringGenerator.filter(dataPropertyRange, filter.get());
        }
        
        return dataPropertyRange;
    }

    private static String parseDataSomeValuesOf(OWLOntology ontology, OWLDataSomeValuesFrom someValuesFrom, Optional<String> filter, boolean styled) {
        OWLDataProperty property = someValuesFrom.getProperty().asOWLDataProperty();

        OWLDataRange dataRange = someValuesFrom.getFiller();
        
        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        String rangeStr = AxiomStringGenerator.parseDataPropertyRangeValues(dataRange, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, "some", rangeStr, styled);
    }
    
    private static String parseDataAllValuesOf(OWLOntology ontology, OWLDataAllValuesFrom allValuesFrom, Optional<String> filter, boolean styled) {
        OWLDataProperty property = allValuesFrom.getProperty().asOWLDataProperty();

        OWLDataRange dataRange = allValuesFrom.getFiller();
        
        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        String rangeStr = AxiomStringGenerator.parseDataPropertyRangeValues(dataRange, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, "all", rangeStr, styled);
    }
    
    private static String parseDataMinCardinality(OWLOntology ontology, OWLDataMinCardinality minCardinality, Optional<String> filter, boolean styled) {
        OWLDataProperty property = minCardinality.getProperty().asOWLDataProperty();

        OWLDataRange dataRange = minCardinality.getFiller();
        
        int cardinality = minCardinality.getCardinality();
        
        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        String rangeStr = AxiomStringGenerator.parseDataPropertyRangeValues(dataRange, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, String.format("min %d", cardinality), rangeStr, styled);
    }
    
    private static String parseDataMaxCardinality(OWLOntology ontology, OWLDataMaxCardinality maxCardinality, Optional<String> filter, boolean styled) {
        OWLDataProperty property = maxCardinality.getProperty().asOWLDataProperty();

        OWLDataRange dataRange = maxCardinality.getFiller();
        
        int cardinality = maxCardinality.getCardinality();
        
        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        String rangeStr = AxiomStringGenerator.parseDataPropertyRangeValues(dataRange, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, String.format("max %d", cardinality), rangeStr, styled);
    }
    
    private static String parseDataExactCardinality(OWLOntology ontology, OWLDataExactCardinality exactCardinality, Optional<String> filter, boolean styled) {
        OWLDataProperty property = exactCardinality.getProperty().asOWLDataProperty();

        OWLDataRange dataRange = exactCardinality.getFiller();

        int cardinality = exactCardinality.getCardinality();

        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        String rangeStr = AxiomStringGenerator.parseDataPropertyRangeValues(dataRange, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, String.format("exactly %d", cardinality), rangeStr, styled);
    }
    
    private static String parseDataHasValue(OWLOntology ontology, OWLDataHasValue hasValue, Optional<String> filter, boolean styled) {
        OWLDataProperty property = hasValue.getProperty().asOWLDataProperty();

        OWLLiteral dataRange = hasValue.getValue();

        String propertyName = AxiomStringGenerator.parseOWLProperty(ontology, property, filter);

        return AxiomStringGenerator.createDPPropertyConstraintStr(propertyName, "has value", dataRange.getLiteral(), styled);
    }
    
    private static String parseIntersectionOf(OWLOntology ontology, OWLObjectIntersectionOf intersectionOf, Optional<String> filter, boolean styled) {
        
        Set<OWLClassExpression> exprs = intersectionOf.asConjunctSet();
        
        StringBuilder builder = new StringBuilder();
        
        for(OWLClassExpression expr : exprs) {
            builder.append(AxiomStringGenerator.parseClassExpression(ontology, expr, filter, styled));
            
            if(styled) {
                builder.append(" <font color='green'><i>and</i></font> ");
            } else {
                builder.append(" and ");
            }
        }
        
        if (styled) {
            String inner = builder.substring(0, builder.lastIndexOf(" <font color='green'><i>and</i></font> "));

            return String.format("<font color='green'><b><i>IntersectionOf</i></b></font>&nbsp(%s)", inner);
        } else {
            String inner = builder.substring(0, builder.lastIndexOf(" and "));

            return String.format("IntersectionOf(%s)", inner);
        }

    }
    
    private static String parseUnionOf(OWLOntology ontology, OWLObjectUnionOf unionOf, Optional<String> filter, boolean styled) {
        Set<OWLClassExpression> exprs = unionOf.asDisjunctSet();

        StringBuilder builder = new StringBuilder();

        for (OWLClassExpression expr : exprs) {
            builder.append(AxiomStringGenerator.parseClassExpression(ontology, expr, filter, styled));
            
            if(styled) {
                builder.append(" <font color='green'><i>or</i></font> ");
            } else {
                builder.append(" or ");
            }
        }
        
        if (styled) {
            String inner = builder.substring(0, builder.lastIndexOf(" <font color='green'><i>or</i></font> "));

            return String.format("<font color='green'><b><i>UnionOf</i></b></font>&nbsp(%s)", inner);
        } else {
            String inner = builder.substring(0, builder.lastIndexOf(" or "));

            return String.format("UnionOf(%s)", inner);
        }
    }
    
    private static String parseComplementOf(OWLOntology ontology, OWLObjectComplementOf complementOf, Optional<String> filter, boolean styled) {
        OWLClassExpression complement = complementOf.getOperand();

        if (styled) {
            return String.format("<font color='green'><b><i>not</i></b></font>&nbsp(%s)",
                    AxiomStringGenerator.parseClassExpression(ontology, complement, filter, styled));
        } else {
            return String.format("not(%s)",
                    AxiomStringGenerator.parseClassExpression(ontology, complement, filter, styled));
        }

    }
    
    private static String parseObjectOneOf(OWLOntology ontology, OWLObjectOneOf oneOf, Optional<String> filter, boolean styled) {
        
        Set<OWLNamedIndividual> individuals = oneOf.getIndividualsInSignature();
        
        String individualsStr;
        
        if (!individuals.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            individuals.forEach(((OWLNamedIndividual individual) -> {
                builder.append(individual.toString());
                builder.append(", ");
            }));
            
            individualsStr = builder.substring(0, builder.length() - 2);
        } else {
            individualsStr = "(No individuals defined)";
        }

        if(styled) {
            return String.format("<font color='green'><b><i>oneOf</i></b></font>&nbsp(%s)", individualsStr);
        } else{
            return String.format("oneOf(%s)", individualsStr);
        }
        
    }

    private static String filter(String text, String filter) {
        String filtered = text.replace("<", "&lt;");

        if(!filter.isEmpty()) {
            filter = filter.replace("<", "&lt;").toLowerCase();
            String tag1 = "<font style=\"BACKGROUND-COLOR: yellow\">";
            String tag2 = "</font>";
            String lower = filtered.toLowerCase();
            
            int i = lower.indexOf(filter, 0);
            
            while(i != -1) {
                int j = i + filter.length();
                
                filtered = filtered.substring(0, j) + tag2 + filtered.substring(j);
                filtered = filtered.substring(0, i) + tag1 + filtered.substring(i);
                
                i = lower.indexOf(filter, j + tag1.length() + tag2.length());
            }
        }

        return filtered;
    }
    
}