package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class Plan extends ClinicalTask {
    private ArrayList<Outcome> outcome;
    private ArrayList<String> firstClinicalTask;
    private String firstTaskFormat;
    private Duration duration;
    private Periodicity periodicity;

    public Plan(){

        super();
        firstClinicalTask = new ArrayList<String>();
        outcome = new ArrayList<Outcome>();

    }

    public static Plan fromJson(String json){

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, Plan.class);
    }

    @Override
	public String toJson(){
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }


    public void addOutcome(Outcome outcome){
        this.outcome.add(outcome);
    }

    public void addFirstTask(String clinicalTask){
        this.firstClinicalTask.add(clinicalTask);
    }

    public ArrayList<String> getFirstClinicalTask() {
        return firstClinicalTask;
    }

    public void setFirstClinicalTask(ArrayList<String> firstClinicalTask) {
        this.firstClinicalTask = firstClinicalTask;
    }


    public ArrayList<Outcome> getOutcome() {
        return outcome;
    }

    public void setOutcome(ArrayList<Outcome> outcome) {
        this.outcome = outcome;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public String getFirstTaskFormat() {
        return firstTaskFormat;
    }

    public void setFirstTaskFormat(String firstTaskFormat) {
        this.firstTaskFormat = firstTaskFormat;
    }
}
