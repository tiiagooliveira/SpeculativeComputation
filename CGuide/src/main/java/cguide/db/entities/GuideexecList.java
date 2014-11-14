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
public class GuideexecList {
    private ArrayList<Guideexec> guideexecs;

    GuideexecList(){
           guideexecs = new ArrayList<Guideexec>();
    }

    public static GuideexecList fromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, GuideexecList.class);
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    public static GuideexecList getInstance(){
        GuideexecList list = new GuideexecList();
        list.setGuideexecs(new ArrayList<Guideexec>());
        return list;
    }

    public void addGuideexec(Guideexec guideexec){
            guideexecs.add(guideexec);
    }
    public void setGuideexecs(ArrayList<Guideexec> guideexecs){
        this.guideexecs = guideexecs;

    }

    public ArrayList<Guideexec> getGuideexecs(){
        return new ArrayList<Guideexec>(this.guideexecs);
    }
}
