package cguide.execution;

import java.util.ArrayList;

import cguide.execution.entities.ClinicalAction;
import cguide.execution.entities.ClinicalActionAdapter;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.ClinicalTaskAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 28-08-2013
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
public class ProcessedTask {
    private  ArrayList<ClinicalTask> tasks;
    private  TaskController controller;
    private  ProcessedDecision decision;

    public ProcessedTask(){
        tasks = new ArrayList<ClinicalTask>();
        controller = new TaskController();
        decision = new ProcessedDecision();
    }

    public static ProcessedTask fromJson(String json){

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, ProcessedTask.class);
    }

    public String toJson(){
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }


    public TaskController getController() {
        return controller;
    }

    public void setController(TaskController controller) {
        this.controller = controller;
    }

    public ArrayList<ClinicalTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ClinicalTask> tasks) {
        this.tasks = tasks;
    }


    public ProcessedDecision getDecision() {
        return decision;
    }

    public void setDecision(ProcessedDecision decision) {
        this.decision = decision;
    }
}
