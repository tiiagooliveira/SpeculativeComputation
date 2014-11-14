package cguide.db.entities;

import cguide.db.beans.ObservationBean;

import com.google.gson.Gson;


/**
 * Created by IntelliJ IDEA.
 * Observation: tiago
 * Date: 18/07/12
 * Time: 10:25 PM
 */
public class Observation {
    private String idobservation;
    private String parameter;
    private String unit;
    private String parameteridentifier;
    private String identifier;
    private String variablename;
    private String parametervalue;
    private String time;
    private String idtask;
    private Boolean isnumeric;

    public Observation() {
    }

    public static Observation fromBean(ObservationBean bean){
        Observation observation = new Observation();
        observation.idobservation =String.valueOf(bean.getIdobservation());
        observation.parameter = bean.getParameter();
        observation.unit = bean.getUnit();
        observation.parameteridentifier = bean.getParameteridentifier();
        observation.variablename = bean.getVariablename();
        observation.identifier =bean.getIdentifier();
        observation.parametervalue = bean.getParametervalue();
        observation.idtask = String.valueOf(bean.getIdtask());
        observation.time= bean.getTime();
        observation.isnumeric=bean.getIsnumeric();
        return observation;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Observation fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Observation.class);
    }


    public String toJsonListElement(){
        return "{\"idobservation\":\"" + idobservation + "\",\"parameter\":\"" + parameter + "\",\"parametervalue\":\"" + parametervalue + "\",\"idtask\":\"" + idtask + "\"}";
    }

    // getters and setters

    public void setIdobservation(String idobservation){
        this.idobservation=idobservation;
    }
    public String getIdobservation(){
        return this.idobservation;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getParameteridentifier() {
        return parameteridentifier;
    }

    public void setParameteridentifier(String parameteridentifier) {
        this.parameteridentifier = parameteridentifier;
    }

    public String getVariablename() {
        return variablename;
    }

    public void setVariablename(String variablename) {
        this.variablename = variablename;
    }

    public String getParametervalue() {
        return parametervalue;
    }

    public void setParametervalue(String parametervalue) {
        this.parametervalue = parametervalue;
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


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean getIsnumeric() {
        return isnumeric;
    }

    public void setIsnumeric(Boolean isnumeric) {
        this.isnumeric = isnumeric;
    }

}
