package cguide.db.entities;

import cguide.db.beans.NonmedicationBean;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Nonmedication {
    private String idnonmedication;
    private String idtask;
    private String description;
    private String time;
    private String identifier;


    public void Nonmedication(){

    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Nonmedication fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Nonmedication.class);
    }
    public static Nonmedication fromBean(NonmedicationBean bean){
        Nonmedication nonmedication = new Nonmedication();
        nonmedication.idtask =String.valueOf(bean.getIdtask());
        nonmedication.description=bean.getDescription();
        nonmedication.idnonmedication=String.valueOf(bean.getIdnonmedication());
        nonmedication.time = bean.getTime();
        nonmedication.identifier = bean.getIdentifier();
        return nonmedication;
    }


    //GETTERS & SETTERS
    public String getIdtask() {
        return idtask;
    }

    public void setIdtask(String idtask) {
        this.idtask = idtask;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdnonmedication() {
        return idnonmedication;
    }

    public void setIdnonmedication(String idnonmedication) {
        this.idnonmedication = idnonmedication;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
