package cguide.execution.entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public class Exam extends ClinicalAction {
    public static Exam fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Exam.class);
    }

    @Override
	public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
