package cguide.parser.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class Parameter {
    private String id;
    private ArrayList<String> possibleValue;
    private ArrayList<String> variableName;
    private Boolean numeric;
    private String parameterIdentifier;
    private String questionParameter;
    private String unit;
    private String parameterDescription;

    public Parameter(){
        possibleValue = new ArrayList<String>();
        variableName = new ArrayList<String>();
    }


    public void initParameter(){
        possibleValue = new ArrayList<String>();
        variableName = new ArrayList<String>();
        numeric = null;
        parameterIdentifier=null;
        questionParameter = null;
        unit = null;
        parameterDescription = null;
        id = null;
    }





    public Parameter getInstance(){
        Parameter p = new Parameter();
        p.setNumeric(this.getNumeric());
        p.setVariableName(new ArrayList<String>(this.getVariableName()));
        p.setPossibleValue(new ArrayList<String>(this.getPossibleValue()));
        p.setQuestionParameter(this.getQuestionParameter());
        p.setUnit(this.getUnit());
        p.setId(this.getId());
        p.setParameterIdentifier(this.getParameterIdentifier());
        return p;
    }

    public static Parameter fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Parameter.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addPossibleValue(String possibleValue){
        this.possibleValue.add(possibleValue);
    }

    public void addVariableName(String variableName){
        this.variableName.add(variableName);
    }

    public String getParameterDescription() {
        return parameterDescription;
    }

    public void setParameterDescription(String parameterDescription) {
        this.parameterDescription = parameterDescription;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuestionParameter() {
        return questionParameter;
    }

    public void setQuestionParameter(String questionParameter) {
        this.questionParameter = questionParameter;
    }

    public String getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(String parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }

    public Boolean getNumeric() {
        return numeric;
    }

    public void setNumeric(Boolean numeric) {
        this.numeric = numeric;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getPossibleValue() {
        return possibleValue;
    }

    public void setPossibleValue(ArrayList<String> possibleValue) {
        this.possibleValue = possibleValue;
    }

    public ArrayList<String> getVariableName() {
        return variableName;
    }

    public void setVariableName(ArrayList<String> variableName) {
        this.variableName = variableName;
    }
}
