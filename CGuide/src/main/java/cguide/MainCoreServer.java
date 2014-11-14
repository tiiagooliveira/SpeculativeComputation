package cguide;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import cguide.parser.GuidelineHandler;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 17-07-2013
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */


public class MainCoreServer extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();
    private SessionController sessionController = new SessionController();
    private GuidelineHandler guidelineHandler = new GuidelineHandler();
    public MainCoreServer(){
             guidelineHandler.loadGuideline();
             singletons.add(sessionController);
             singletons.add(guidelineHandler);
    }
    @Override
	public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
	public Set<Object> getSingletons() {
        return singletons;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public GuidelineHandler getGuidelineHandler() {
        return this.guidelineHandler;
    }
}
