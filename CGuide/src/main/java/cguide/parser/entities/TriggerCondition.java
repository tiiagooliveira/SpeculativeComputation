package cguide.parser.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 23-08-2013
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
public class TriggerCondition {
    private  ArrayList<ConditionSet> triggerConditionSet;

    public TriggerCondition(){
        triggerConditionSet = new ArrayList<ConditionSet>();

    }

    public static TriggerCondition fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, TriggerCondition.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addTriggerCondition(ConditionSet conditionSet){
        triggerConditionSet.add(conditionSet);

    }

    public ArrayList<ConditionSet> getTriggerConditionSet() {
        return triggerConditionSet;
    }


    public void setTriggerConditionSet(ArrayList<ConditionSet> triggerConditionSet) {
        this.triggerConditionSet = triggerConditionSet;
    }
}
