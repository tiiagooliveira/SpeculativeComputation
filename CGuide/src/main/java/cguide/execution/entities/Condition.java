package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */
public class Condition {
    private String id;
    private String value;
    private Boolean isNumeric;
    private ArrayList<TemporalRestriction> temporalRestriction;
    private String comparisonOperator;
    private ArrayList<String> parameterIdentifier;
    private String conditionParameter;
    private String unit;

    public static Condition fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Condition.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Condition(){
           temporalRestriction = new ArrayList<TemporalRestriction>();
           parameterIdentifier = new ArrayList<String>();
    }

    public void addTemporalRestriction(TemporalRestriction temporalRestriction){
        this.temporalRestriction.add(temporalRestriction);
    }

    public void addParameterIdentifier(String parameterIdentifier){
        this.parameterIdentifier.add(parameterIdentifier);
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public Boolean getIsNumeric() {
        return isNumeric;
    }

    public void setIsNumeric(Boolean numeric) {
        isNumeric = numeric;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConditionParameter() {
        return conditionParameter;
    }

    public void setConditionParameter(String conditionParameter) {
        this.conditionParameter = conditionParameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<TemporalRestriction> getTemporalRestriction() {
        return temporalRestriction;
    }

    public void setTemporalRestriction(ArrayList<TemporalRestriction> temporalRestriction) {
        this.temporalRestriction = temporalRestriction;
    }

    public ArrayList<String> getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(ArrayList<String> parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }

    public Boolean conditionCompare(Condition c){
        return(this.getComparisonOperator().equals(c.getComparisonOperator())
                &&this.getValue().equals(c.getValue())
                &&this.getConditionParameter().equals(c.getConditionParameter()));
    }
    public Boolean conditionCompareNumeric(Condition c){
        Boolean test = Boolean.FALSE;
        if(this.getComparisonOperator().equals("Different_from")){
                if(!c.getValue().equals(this.getValue())) test = Boolean.TRUE;
        }
        if(this.getComparisonOperator().equals("Equal_to")){
                if(c.getValue().equals(this.getValue())) test = Boolean.TRUE;
        }
        if(this.getComparisonOperator().equals("Greater_or_equal_than")){
                double i = Double.valueOf(c.getValue());
                double j = Double.valueOf(this.getValue());
                if(i>=j) test = Boolean.TRUE;

        }
        if(this.getComparisonOperator().equals("Greater_than")){
            double i = Double.valueOf(c.getValue());
            double j = Double.valueOf(this.getValue());
            if(i>j) test = Boolean.TRUE;
        }
        if(this.getComparisonOperator().equals("Less_or_equal_than")){
            double i = Double.valueOf(c.getValue());
            double j = Double.valueOf(this.getValue());
            if(i<=j) test = Boolean.TRUE;
        }
        if(this.getComparisonOperator().equals("Less_than")){
            double i = Double.valueOf(c.getValue());
            double j = Double.valueOf(this.getValue());
            if(i<j) test = Boolean.TRUE;
        }
        return(c.getUnit().equals(this.getUnit())&&test
                &&this.getConditionParameter().equals(c.getConditionParameter()));
    }

}
