package cguide.db.entities;

import cguide.db.beans.TaskBean;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 25-08-2013
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class Task {
    private String idtask;
    private String idguideexec;
    private String time;
    private String taskType;
    private String taskFormat;
    private String taskDescription;
    private String taskIdentifier;

    public Task(){
        
    }

    public static Task fromBean(TaskBean bean){
        Task task = new Task();
        task.idtask =String.valueOf(bean.getIdtask());
        task.idguideexec = String.valueOf(bean.getIdguideexec());
        task.time = bean.getTime();
        task.taskType = bean.getTaskType();
        task.taskFormat = bean.getTaskFormat();
        task.taskDescription=bean.getTaskDescription();
        task.taskIdentifier = bean.getTaskIdentifier();
        return task;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Task fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Task.class);
    }


    public String getTaskIdentifier() {
        return taskIdentifier;
    }

    public void setTaskIdentifier(String taskIdentifier) {
        this.taskIdentifier = taskIdentifier;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskFormat() {
        return taskFormat;
    }

    public void setTaskFormat(String taskFormat) {
        this.taskFormat = taskFormat;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(String idguideexec) {
        this.idguideexec = idguideexec;
    }

    public String getIdtask() {
        return idtask;
    }

    public void setIdtask(String idtask) {
        this.idtask = idtask;
    }
}
