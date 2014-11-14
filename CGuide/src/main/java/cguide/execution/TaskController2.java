package cguide.execution;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 28-08-2013
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
public class TaskController2 {
    private ArrayDeque<String> pendingPlans;
    private ArrayDeque<String> pendingTasks;
    private TreeMap<String,ArrayList<String>> tasks;

    public TaskController2(){
        pendingTasks = new ArrayDeque<String>();
        pendingPlans = new ArrayDeque<String>();
        tasks = new TreeMap<String, ArrayList<String>>();

    }

    public void pushPendingTask(String s,ArrayList<String> tasks){
         pendingTasks.push(s);
         this.tasks.put(s,tasks);
    }
    public void pushPendingPlan(String s, ArrayList<String> tasks){
         pendingPlans.push(s);
         this.tasks.put(s,tasks);

    }
    public String popPendingTasks(){
           return pendingTasks.pop();
    }
    public void putTasks(String syncTask, ArrayList<String> tasks){
        pendingTasks.push(syncTask);
        this.tasks.put(syncTask,tasks);
    }

    public ArrayDeque<String> getPendingTasks() {
        return pendingTasks;
    }

    public TreeMap<String, ArrayList<String>> getTasks() {
        return tasks;
    }

    public void setTasks(TreeMap<String, ArrayList<String>> tasks) {
        this.tasks = tasks;
    }

    public Boolean isSynced(String syncTask){
        ArrayList<String> task = tasks.get(syncTask);
        for (String s : task){
            if(!s.equals(syncTask)){
                return Boolean.FALSE;
            }
        }
        if(task.size()>0){return Boolean.TRUE;}
        else {
            return Boolean.FALSE;
        }
    }
    public ArrayList<String> headTask(){
        return tasks.get(pendingTasks.peek());
    }

    public ArrayDeque<String> getPendingPlans() {
        return pendingPlans;
    }

    public void setPendingPlans(ArrayDeque<String> pendingPlans) {
        this.pendingPlans = pendingPlans;
    }
}
