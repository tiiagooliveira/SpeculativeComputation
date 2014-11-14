package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-08-2013
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalTaskList {
    private ArrayList<ClinicalTask> tasks;
    public ClinicalTaskList(){
        tasks = new ArrayList<ClinicalTask>();
    }
    public static ClinicalTaskList fromJson(String json){

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, ClinicalTaskList.class);
    }

    public String toJson(){
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }


    public ArrayList<ClinicalTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ClinicalTask> tasks) {
        this.tasks = tasks;
    }

    public int size() {
        return tasks.size();
    }
}
