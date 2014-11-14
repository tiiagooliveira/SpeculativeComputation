package cguide.execution;

import java.util.ArrayDeque;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 29-08-2013
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class TaskController {
    private ArrayDeque<String> syncTask;
    private ArrayDeque<String> pendingPlans;
    private ArrayList<String> alternativeTask;
    private ArrayList<TaskQuadruple> nextTask;




    public TaskController(){
        syncTask = new ArrayDeque<String>();
        pendingPlans = new ArrayDeque<String>();
        nextTask = new ArrayList<TaskQuadruple>();
        alternativeTask = new ArrayList<String>();
    }

    public String peekSyncTask(){
        return syncTask.peek();
    }

    public String peekPendingPlan(){
        return pendingPlans.peek();
    }

    public String popSyncTask(){
        return syncTask.pop();
    }

    public String popPendingPlans(){
        return pendingPlans.pop();
    }


    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static TaskController fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, TaskController.class);
    }

    public ArrayList<TaskQuadruple> getNextTask() {
        return nextTask;
    }

    public Boolean contains(TaskQuadruple tp){
        for(TaskQuadruple triple : nextTask){
            if (triple.equals(tp)) return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    public void setNextTask(ArrayList<TaskQuadruple> nextTask) {
        this.nextTask = nextTask;
    }

    public ArrayDeque<String> getPendingPlans() {
        return pendingPlans;
    }

    public void setPendingPlans(ArrayDeque<String> pendingPlans) {
        this.pendingPlans = pendingPlans;
    }

    public ArrayDeque<String> getSyncTask() {
        return syncTask;
    }

    public void setSyncTask(ArrayDeque<String> syncTask) {
        this.syncTask = syncTask;
    }

    public void addTask(TaskQuadruple tp){
        this.nextTask.add(tp);
    }

    public void pushPlan(String task){
        pendingPlans.push(task);
    }
    public void pushSync(String task){
        syncTask.push(task);
    }
    public void updateTask(TaskQuadruple tp,String task){
        int i=0;
        for(TaskQuadruple tpair : nextTask){
            if(tpair.equals(tp)){
                nextTask.remove(tpair);
                tpair.setTask(task);
                nextTask.add(tpair);return;
            }
            i++;
        }
        if(i == nextTask.size()-1){
            tp.setTask(task);
            nextTask.add(tp);
        }
    }

    public void updateParallelAlternate(TaskQuadruple tp,String task){


    }

    public Boolean isSynced(String id){
        for(TaskQuadruple tp : nextTask){
            if(tp.getId().equals(id)){
                if(!tp.isSync()) return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    public Boolean isSyncable(String id,String task){
        for(TaskQuadruple tq : getNextTask()){
            if(!tq.getTask().equals(task)&&tq.getId().equals(id)){
                if(!isSynced(tq.getId())){
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;

    }


    public Boolean isAlternativeTask(String task){
        return(this.alternativeTask.contains(task));
    }

    public void removeAlternativeTask(String id){

        int i = 0;
        ArrayList<TaskQuadruple> toremove= new ArrayList<TaskQuadruple>();
        for(TaskQuadruple tp:this.getNextTask()){
            if(tp.getId().equals(id)){
                toremove.add(tp);
            }
            i++;
        }
        for(TaskQuadruple tp: toremove){
            this.nextTask.remove(tp);
        }

        alternativeTask.remove(id);
    }

    public void removeParallelTask(String id,String newid){

        int i = 0;
        ArrayList<TaskQuadruple> toremove= new ArrayList<TaskQuadruple>();
        for(TaskQuadruple tp:this.getNextTask()){
            if(tp.getId().equals(id)){
                toremove.add(tp);
            }
            i++;
        }
        for(TaskQuadruple tp: toremove){
            this.nextTask.remove(tp);
        }
        if(syncTask.peek()!=null){
            TaskQuadruple temp = toremove.get(0);
            temp.setId(syncTask.peek());
            this.nextTask.add(temp);
        }else {

            TaskQuadruple temp = toremove.get(0);
            temp.setId(newid);
            this.nextTask.add(temp);
        }
        syncTask.pop();
    }

    public void addAlternativeTask(String id){
        this.alternativeTask.add(id);
    }


    public Boolean removeTask(TaskQuadruple tp){
        int i =0;
        for(TaskQuadruple TaskQuadruple : nextTask){
            if(TaskQuadruple.equals(tp)){
                nextTask.remove(i);
                return Boolean.TRUE;
            }
            i++;
        }
        return Boolean.FALSE;
    }
    public Boolean isAllowed(String id){


        if(syncTask.peek()!=null){
            return (isSynced(id)&& id.equals(this.syncTask.peek()));
        }
        else {
            return isSynced(id);
        }
    }

    public Boolean isAllowable(String id,String task){


        if(syncTask.peek()!=null){
            return (isSyncable(id,task)&& id.equals(this.syncTask.peek()));
        }
        else {
            return isSyncable(id,task);
        }
    }

    public Boolean isComplete(String id){

           for(TaskQuadruple tq : nextTask){
               if(tq.getId().equals(id)&& !isAllowed(id)){
                   return Boolean.FALSE;
               }
           }
           return Boolean.TRUE;
    }

    public void removePlan(String plan){
          ArrayList<TaskQuadruple> toremove= new ArrayList<TaskQuadruple>();
          for(TaskQuadruple tq : nextTask){
              if(tq.getPlan().equals(plan)) {
              toremove.add(tq);
              }
          }
        for(TaskQuadruple taskQuadruple : toremove){
            nextTask.remove(taskQuadruple);
            }
          pendingPlans.pop();
    }


    public void pushSyncTask(String syncTask) {
        this.syncTask.push(syncTask);
    }

    public void setAlternativeTask(ArrayList<String> alternativeTask) {
        this.alternativeTask = alternativeTask;
    }

    public Boolean isParallel(String id) {
        return(syncTask.contains(id));
    }

    public void removeNotAllowedTasks(){


        ArrayList<TaskQuadruple> toremove= new ArrayList<TaskQuadruple>();
        for(TaskQuadruple taskQuadruple : nextTask){
            if(taskQuadruple.isSync()&& this.isParallel(taskQuadruple.getId())&& !this.isAllowed(taskQuadruple.getId())){
                toremove.add(taskQuadruple);
            }
        }
        for(TaskQuadruple taskQuadruple: toremove){
            nextTask.remove(taskQuadruple);
        }

    }
}
