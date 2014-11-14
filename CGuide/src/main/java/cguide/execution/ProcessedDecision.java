package cguide.execution;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 15-09-2013
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
public class ProcessedDecision {
    private String optionParameter;
    private String value;
    private String parameterIdentifier;
    private String unit;
    private Boolean isNumeric;

    public ProcessedDecision(){

    }
    public static ProcessedDecision fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ProcessedDecision.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getOptionParameter() {
        return optionParameter;
    }

    public void setOptionParameter(String optionParameter) {
        this.optionParameter = optionParameter;
    }

    public String getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(String parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getNumeric() {
        return isNumeric;
    }

    public void setNumeric(Boolean numeric) {
        isNumeric = numeric;
    }
}
