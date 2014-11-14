package cguide.parser.entities;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 22:37
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalTask {
    private String id;
    private ArrayList<String> clinicalTasks;
    private String taskType;
    private String taskFormat;
    private String generalDescription;
    private String syncTask;
    private ArrayList<TriggerCondition> triggerCondition;
    private ArrayList<PreCondition> preCondition;

    public ClinicalTask(){
        this.clinicalTasks = new ArrayList<String>();
        this.triggerCondition = new ArrayList<TriggerCondition>();
        this.preCondition = new ArrayList<PreCondition>();
        this.generalDescription = null;
        this.taskFormat = null;
        this.taskType =null;
        this.id = null;
        this.syncTask = null;
    }

    public static ClinicalTask fromJson(String json){

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, ClinicalTask.class);
    }

    public String toJson(){
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }


    public void addClinicalTask(String clinicalTask){
        this.clinicalTasks.add(clinicalTask);
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public ArrayList<String> getClinicalTasks() {
        return clinicalTasks;
    }

    public void setClinicalTasks(ArrayList<String> clinicalTasks) {
        this.clinicalTasks = clinicalTasks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addTriggerCondition(TriggerCondition triggerCondition){
        this.triggerCondition.add(triggerCondition);

    }

    public void addPreCondition(PreCondition preCondition){
        this.preCondition.add(preCondition);

    }

    public ArrayList<PreCondition> getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(ArrayList<PreCondition> preCondition) {
        this.preCondition = preCondition;
    }

    public ArrayList<TriggerCondition> getTriggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(ArrayList<TriggerCondition> triggerCondition) {
        this.triggerCondition = triggerCondition;
    }

    public String getSyncTask() {
        return syncTask;
    }

    public void setSyncTask(String syncTask) {
        this.syncTask = syncTask;
    }

    public String getTaskFormat() {
        return taskFormat;
    }

    public void setTaskFormat(String taskFormat) {
        this.taskFormat = taskFormat;
    }
}
