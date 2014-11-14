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
public class PreCondition {
    private  ArrayList<ConditionSet> preConditionSet;

    public PreCondition(){
        preConditionSet = new ArrayList<ConditionSet>();

    }

    public static PreCondition fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, PreCondition.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addPrecondition(ConditionSet conditionSet){
        preConditionSet.add(conditionSet);

    }

    public ArrayList<ConditionSet> getPrecondition() {
        return preConditionSet;
    }

    public void setPrecondition(ArrayList<ConditionSet> precondition) {
        this.preConditionSet = precondition;
    }
}
