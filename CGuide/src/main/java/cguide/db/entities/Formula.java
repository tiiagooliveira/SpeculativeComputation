package cguide.db.entities;

import cguide.db.beans.FormulaBean;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Formula {
    private String idformula;
    private String idtask;
    private String name;
    private String description;
    private String time;
    private String identifier;

    public void Formula(){

    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Formula fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Formula.class);
    }
    public static Formula fromBean(FormulaBean bean){
        Formula formula = new Formula();
        formula.idtask =String.valueOf(bean.getIdtask());
        formula.idformula=String.valueOf(bean.getIdformula());
        formula.description=bean.getDescription();
        formula.time = bean.getTime();
        formula.identifier = bean.getIdentifier();
        return formula;
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

    public String getIdformula() {
        return idformula;
    }

    public void setIdformula(String idformula) {
        this.idformula = idformula;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
