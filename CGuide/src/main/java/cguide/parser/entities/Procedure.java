package cguide.parser.entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:30
 * To change this template use File | Settings | File Templates.
 */
public class Procedure extends ClinicalAction {
    public static Procedure fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Procedure.class);
    }

    @Override
	public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
