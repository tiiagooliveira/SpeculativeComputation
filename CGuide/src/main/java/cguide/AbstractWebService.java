package cguide;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import cguide.db.beans.UserBean;
import cguide.parser.GuidelineHandler;

/*
 * Created by IntelliJ IDEA.
 * User: tiago
 * Date: 18/07/13
 * Time: 11:53 AM
 */

public abstract class AbstractWebService {

    protected SessionController sessionController;

    protected UserBean requestOwner;

    protected GuidelineHandler guidelineHandler;

    @Context
    public void setApplication(Application application){
        if (application instanceof MainCoreServer){
            sessionController = ((MainCoreServer) application).getSessionController();
            guidelineHandler = ((MainCoreServer) application).getGuidelineHandler();
        }
    }


    @Context
    public void setHttpServletRequest(HttpServletRequest httpServletRequest){
        if (httpServletRequest.getAttribute("requestOwner") != null){
            requestOwner = (UserBean)httpServletRequest.getAttribute("requestOwner");
        }
    }

    protected Boolean validParam(String value){
        return value != null && value.trim().length() > 0;
    }

    protected Boolean isAdmin(){
        return requestOwner != null && requestOwner.getType().equals("admin");
    }
    protected Boolean isUser(){
        return requestOwner != null && (requestOwner.getType().equals("user") || requestOwner.getType().equals("admin"));
    }
}
