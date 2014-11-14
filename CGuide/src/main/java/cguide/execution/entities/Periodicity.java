package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class Periodicity  {
    private String id;
    private Double minPeriodicityValue;
    private Double maxPeriodicityValue;
    private ArrayList<ConditionSet> stopConditionSet;
    private Integer repetitionValue;
    private String temporalUnit;

    public Periodicity(){
           stopConditionSet = new ArrayList<ConditionSet>();
    }

    public static Periodicity fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Periodicity.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public void  addStopConditionSet(ConditionSet conditionSet){
        this.stopConditionSet.add(conditionSet);
    }
    public Integer getRepetitionValue() {
        return repetitionValue;
    }

    public void setRepetitionValue(Integer repetitionValue) {
        this.repetitionValue = repetitionValue;
    }

    public Double getMinPeriodicityValue() {
        return minPeriodicityValue;
    }

    public void setMinPeriodicityValue(Double minPeriodicityValue) {
        this.minPeriodicityValue = minPeriodicityValue;
    }


    public String getTemporalUnit() {
        return temporalUnit;
    }

    public void setTemporalUnit(String temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMaxPeriodicityValue() {
        return maxPeriodicityValue;
    }

    public void setMaxPeriodicityValue(Double maxPeriodicityValue) {
        this.maxPeriodicityValue = maxPeriodicityValue;
    }

    public ArrayList<ConditionSet> getStopConditionSet() {
        return stopConditionSet;
    }

    public void setStopConditionSet(ArrayList<ConditionSet> stopConditionSet) {
        this.stopConditionSet = stopConditionSet;
    }
}
