package cguide.execution.entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 20-08-2013
 * Time: 23:26
 * To change this template use File | Settings | File Templates.
 */
public class ClinicalAction {
    private String actionType;
    private String id;
    private String name;
    private String description;

    public static ClinicalAction fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ClinicalAction.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
