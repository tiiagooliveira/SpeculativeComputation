package cguide.db.entities;

import cguide.db.beans.ProcedureBean;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Procedure {
    private String idprocedure;
    private String idtask;
    private String description;
    private String time;
    private String identifier;


    public void Procedure(){

    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Procedure fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Procedure.class);
    }
    public static Procedure fromBean(ProcedureBean bean){
        Procedure procedure = new Procedure();
        procedure.idtask =String.valueOf(bean.getIdtask());
        procedure.description=bean.getDescription();
        procedure.idprocedure=String.valueOf(bean.getIdprocedure());
        procedure.time = bean.getTime();
        procedure.identifier = bean.getIdentifier();
        return procedure;
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

    public String getIdprocedure() {
        return idprocedure;
    }

    public void setIdprocedure(String idprocedure) {
        this.idprocedure = idprocedure;
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
