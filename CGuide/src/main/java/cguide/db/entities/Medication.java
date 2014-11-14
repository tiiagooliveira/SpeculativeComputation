package cguide.db.entities;

import cguide.db.beans.MedicationBean;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA.
 * Medication: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class Medication {
    private String idmedication;
    private String name;
    private String activeingredient;
    private String dosage;
    private String pharmaceuticalform;
    private String posology;
    private String description;
    private String time;
    private String idtask;
    private String identifier;

    public Medication() {
    }

    public static Medication fromBean(MedicationBean bean){
        Medication medication = new Medication();
        medication.idmedication =String.valueOf(bean.getIdmedication());
        medication.name = bean.getName();
        medication.activeingredient = bean.getActiveingredient();
        medication.dosage = bean.getDosage();
        medication.posology = bean.getPosology();
        medication.pharmaceuticalform=bean.getPharmaceuticalform();
        medication.description = bean.getDescription();
        medication.idtask = String.valueOf(bean.getIdtask());
        medication.time= bean.getTime();
        medication.identifier = bean.getIdentifier();
        return medication;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Medication fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Medication.class);
    }

    // getters and setters

    public void setIdmedication(String idmedication){
        this.idmedication=idmedication;
    }
    public String getIdmedication(){
        return this.idmedication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActiveingredient() {
        return activeingredient;
    }

    public void setActiveingredient(String activeingredient) {
        this.activeingredient = activeingredient;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdtask() {
        return idtask;
    }

    public void setTipo(String idtask) {
        this.idtask = idtask;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPharmaceuticalform() {
        return pharmaceuticalform;
    }

    public void setPharmaceuticalform(String pharmaceuticalform) {
        this.pharmaceuticalform = pharmaceuticalform;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
