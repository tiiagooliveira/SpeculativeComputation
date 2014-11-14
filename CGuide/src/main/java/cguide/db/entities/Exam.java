package cguide.db.entities;

import cguide.db.beans.ExamBean;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class Exam {
    private String idexam;
    private String idtask;
    private String name;
    private String description;
    private String time;
    private String identifier;

    public void Exam(){

    }
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static Exam fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Exam.class);
    }
    public static Exam fromBean(ExamBean bean){
        Exam exam = new Exam();
        exam.idtask =String.valueOf(bean.getIdtask());
        exam.time = bean.getTime();
        exam.idexam = String.valueOf(bean.getIdexam());
        exam.description = bean.getDescription();
        exam.name = bean.getName();
        exam.identifier = bean.getIdentifier();
        return exam;
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

    public String getIdexam() {
        return idexam;
    }

    public void setIdexam(String idexam) {
        this.idexam = idexam;
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
