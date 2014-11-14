package cguide.execution.entities;

import com.google.gson.Gson;

/**
 * Created by IntelliJ IDEA.
 * Entities.Guideline: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class Guideline {
    private String id;
    private String dateofcreation;
    private String dateofupdate;
    private String versionnumber;
    private String guidelinename;
    private String authorship;
    private String guidelinedescription;
    private Scope  scope;
    private Plan   plan;

    public Guideline() {
    }
    // getters and setters
    public static Guideline fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Guideline.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getDateofcreation() {
        return dateofcreation;
    }

    public void setDateofcreation(String dateofcreation) {
        this.dateofcreation = dateofcreation;
    }


    public String getVersionnumber() {
        return versionnumber;
    }

    public void setVersionnumber(String versionnumber) {
        this.versionnumber = versionnumber;
    }


    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
    }


    public String getGuidelinename() {
        return guidelinename;
    }

    public void setGuidelinename(String guideline) {
        this.guidelinename = guideline;
    }

    public String getDateofupdate() {
        return dateofupdate;
    }

    public void setDateofupdate(String dateofupdate) {
        this.dateofupdate = dateofupdate;
    }

    public String getGuidelinedescription() {
        return guidelinedescription;
    }

    public void setGuidelinedescription(String guidelinedescription) {
        this.guidelinedescription = guidelinedescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
