package cguide.execution.entities;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:23
 * To change this template use File | Settings | File Templates.
 */
public class Action extends ClinicalTask {

    private ArrayList<ClinicalAction> clinicalActions;
    private Outcome outcome;
    private Duration duration;
    private Periodicity periodicity;

    public Action(){
        clinicalActions = new ArrayList<ClinicalAction>();
        outcome = new Outcome();
    }

    public static Action fromJson(String json){

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, Action.class);
    }

    @Override
	public String toJson(){
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }


    public void addClinicalAction(ClinicalAction clinicalAction){
        this.clinicalActions.add(clinicalAction);

    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public ArrayList<ClinicalAction> getClinicalActions() {
        return clinicalActions;
    }

    public void setClinicalActions(ArrayList<ClinicalAction> clinicalActions) {
        this.clinicalActions = clinicalActions;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }
}
