package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class ConditionSet {
    private String id;
    private ArrayList<Condition> condition;
    private ArrayList<String> comparisonOperator;
    private ArrayList<Integer> conditionSetCounter;

    public ConditionSet(){
        condition= new ArrayList<Condition>();
        comparisonOperator = new ArrayList<String>();
        conditionSetCounter = new ArrayList<Integer>();
    }

    public void addCondition(Condition condition){
            this.condition.add(condition);
    }
    public static ConditionSet fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ConditionSet.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addComparisonOperator(String comparisonOperator){
        this.comparisonOperator.add(comparisonOperator);
    }

    public void addConditionSetCounter(Integer setCounter){
        this.conditionSetCounter.add(setCounter);
    }

    public ArrayList<Condition> getCondition() {
        return condition;
    }

    public void setCondition(ArrayList<Condition> condition) {
        this.condition = condition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(ArrayList<String> comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public ArrayList<Integer> getConditionSetCounter() {
        return conditionSetCounter;
    }

    public void setConditionSetCounter(ArrayList<Integer> conditionSetCounter) {
        this.conditionSetCounter = conditionSetCounter;
    }
}
