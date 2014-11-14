package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
public class Option {
    private String id;
    private String value;
    private Boolean isNumeric;
    private ArrayList<ConditionSet> optionConditionSet;
    private String optionParameter;
    private String optionIdentifier;
    private String unit;

    public Option(){
        optionConditionSet = new ArrayList<ConditionSet>();
    }

    public static Option fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Option.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
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



    public ArrayList<ConditionSet> getOptionConditionSet() {
        return optionConditionSet;
    }

    public void setOptionConditionSet(ArrayList<ConditionSet> optionConditionSet) {
        this.optionConditionSet = optionConditionSet;
    }

    public void addOptionConditionSet(ConditionSet conditionSet) {
        this.optionConditionSet.add(conditionSet);
    }

    public String getOptionParameter() {
        return optionParameter;
    }

    public void setOptionParameter(String optionParameter) {
        this.optionParameter = optionParameter;
    }

    public String getOptionIdentifier() {
        return optionIdentifier;
    }

    public void setOptionIdentifier(String optionIdentifier) {
        this.optionIdentifier = optionIdentifier;
    }
}
