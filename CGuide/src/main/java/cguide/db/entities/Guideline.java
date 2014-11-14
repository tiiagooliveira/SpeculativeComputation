package cguide.db.entities;

import cguide.db.beans.GuidelineBean;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA.
 * Guideline: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class Guideline {
    private String idguideline;
    private String dateofcreation;
    private String dateofupdate;
    private String versionnumber;
    private String guidelinename;
    private String authorship;
    private String identifier;
    private String description;

    public Guideline() {
    }

    public static Guideline fromBean(GuidelineBean bean){
        Guideline guideline = new Guideline();
        guideline.idguideline =String.valueOf(bean.getIdguideline());
        guideline.dateofcreation = bean.getDateofcreation();
        guideline.dateofupdate = bean.getDateofupdate();
        guideline.versionnumber = bean.getVersionnumber();
        guideline.guidelinename=bean.getGuidelinename();
        guideline.authorship = bean.getAuthorship();
        guideline.identifier = bean.getIdentifier();
        guideline.description = bean.getDescription();
        return guideline;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Guideline fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Guideline.class);
    }



    // getters and setters

    public String getDateofcreation() {
        return dateofcreation;
    }

    public void setDateofcreation(String dateofcreation) {
        this.dateofcreation = dateofcreation;
    }

    public void setIdguideline(String idguideline){
        this.idguideline=idguideline;
    }
    public String getIdguideline(){
        return this.idguideline;
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

    public void setGuidelinename(String guidelinename) {
        this.guidelinename = guidelinename;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDateofupdate() {
        return dateofupdate;
    }

    public void setDateofupdate(String dateofupdate) {
        this.dateofupdate = dateofupdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
