package cguide.execution;

import cguide.execution.entities.Condition;
import cguide.execution.entities.ConditionSet;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 28-08-2013
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public class TaskRequest {
    private String guideexec;
    private ConditionSet conditionSet;
    private TaskQuadruple taskQuadruple;

    public TaskRequest(){
        conditionSet = new ConditionSet();
    }

    public String getGuideexec() {
        return guideexec;
    }

    public static TaskRequest fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, TaskRequest.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public void addCondition(Condition c){
        this.conditionSet.addCondition(c);
    }

    public void setGuideexec(String guideexec) {
        this.guideexec = guideexec;
    }

    public ConditionSet getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(ConditionSet conditionSet) {
        this.conditionSet = conditionSet;
    }

    public TaskQuadruple getTaskQuadruple() {
        return taskQuadruple;
    }

    public void setTaskQuadruple(TaskQuadruple taskQuadruple) {
        this.taskQuadruple = taskQuadruple;
    }
}
