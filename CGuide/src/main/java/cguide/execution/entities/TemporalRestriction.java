package cguide.execution.entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
public class TemporalRestriction {
    private String id;
    private Double minRestrictionValue;
    private Double maxRestrictionValue;
    private String temporalUnit;
    private String temporalOperator;

    public TemporalRestriction(){

    }
    public static TemporalRestriction fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, TemporalRestriction.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public String getTemporalOperator() {
        return temporalOperator;
    }

    public void setTemporalOperator(String temporalOperator) {
        this.temporalOperator = temporalOperator;
    }

    public String getTemporalUnit() {
        return temporalUnit;
    }

    public void setTemporalUnit(String temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public Double getMinRestrictionValue() {
        return minRestrictionValue;
    }

    public void setMinRestrictionValue(double minRestrictionValue) {
        this.minRestrictionValue = minRestrictionValue;
    }

    public Double getMaxRestrictionValue() {
        return maxRestrictionValue;
    }

    public void setMaxRestrictionValue(double maxRestrictionValue) {
        this.maxRestrictionValue = maxRestrictionValue;
    }
    public Boolean isSingleRestrictionValue(){
        return maxRestrictionValue.equals(minRestrictionValue);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
