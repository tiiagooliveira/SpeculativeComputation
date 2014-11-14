package cguide.parser.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class Scope {
    private String id;
    private ArrayList<String> diseaseOrCondition;
    private ArrayList<String> intendedUser;
    private ArrayList<String> targetPopulation;
    private ArrayList<String> hasClinicalSpeciality;
    private ArrayList<String> hasGuidelineCategory;
    private ArrayList<ConditionSet> conditionSet;

    public Scope(){
        this.diseaseOrCondition = new ArrayList<String>();
        this.intendedUser = new ArrayList<String>();
        this.targetPopulation = new ArrayList<String>();
        this.hasClinicalSpeciality = new ArrayList<String>();
        this.hasGuidelineCategory = new ArrayList<String>();
        this.conditionSet = new ArrayList<ConditionSet>();
    }
    public static Scope fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Scope.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addDiseageOrCondition(String str){
       this.diseaseOrCondition.add(str);
    }
    public void addIntendedUser(String str){
        this.intendedUser.add(str);
    }

    public void addTargetPopulation(String str){
        this.targetPopulation.add(str);
    }
    public void addClinicalSpecialty(String str){
        this.hasClinicalSpeciality.add(str);
    }
    public void addGuidelineCategory(String str){
        this.hasGuidelineCategory.add(str);
    }
    public void addConditionSet(ConditionSet conditionset){
        this.conditionSet.add(conditionset);
    }

    public ArrayList<ConditionSet> getConditionSet() {
        return conditionSet;
    }

    public void setConditionSet(ArrayList<ConditionSet> conditionSet) {
        this.conditionSet = conditionSet;
    }

    public void setGuidelineCategory(ArrayList<String> guidelineCategory) {
        this.hasGuidelineCategory = guidelineCategory;
    }

    public ArrayList<String> getHasClinicalSpeciality() {
        return hasClinicalSpeciality;
    }

    public void setHasClinicalSpeciality(ArrayList<String> hasClinicalSpeciality) {
        this.hasClinicalSpeciality = hasClinicalSpeciality;
    }

    public ArrayList<String> getTargetPopulation() {
        return targetPopulation;
    }

    public void setTargetPopulation(ArrayList<String> targetPopulation) {
        this.targetPopulation = targetPopulation;
    }

    public ArrayList<String> getIntendedUser() {
        return intendedUser;
    }

    public void setIntendedUser(ArrayList<String> intendedUser) {
        this.intendedUser = intendedUser;
    }

    public ArrayList<String> getDiseaseOrCondition() {
        return diseaseOrCondition;
    }

    public void setDiseaseOrCondition(ArrayList<String> diseaseOrCondition) {
        this.diseaseOrCondition = diseaseOrCondition;
    }

    public ArrayList<String> getHasGuidelineCategory() {
        return hasGuidelineCategory;
    }

    public void setHasGuidelineCategory(ArrayList<String> hasGuidelineCategory) {
        this.hasGuidelineCategory = hasGuidelineCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
