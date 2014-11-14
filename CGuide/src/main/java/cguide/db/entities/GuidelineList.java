package cguide.db.entities;

import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 26-08-2013
 * Time: 19:56
 * To change this template use File | Settings | File Templates.
 */
public class GuidelineList {
    private ArrayList<Guideline> guidelines;

    GuidelineList(){
           guidelines = new ArrayList<Guideline>();
    }

    public static GuidelineList fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, GuidelineList.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static GuidelineList getInstance(){
        GuidelineList list = new GuidelineList();
        list.setGuidelines(new ArrayList<Guideline>());
        return list;
    }

    public void addGuideline(Guideline guideline){
         guidelines.add(guideline);
    }


    public ArrayList<Guideline> Guidelines() {
        return guidelines;
    }

    public void setGuidelines(ArrayList<Guideline> guidelines) {
        this.guidelines = guidelines;
    }
}
