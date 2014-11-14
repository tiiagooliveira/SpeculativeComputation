package cguide.db.webservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;

import cguide.AbstractWebService;
import cguide.db.beans.GuidelineManager;
import cguide.db.entities.GuidelineList;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/guidelines")
public class GuidelinesWebService extends AbstractWebService {


    /**
     * Method to retrieve parser's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @return The information about the user in a json object
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(GuidelineList.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response guidelineDetails() {
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String guidelineJson = "";
        GuidelineList guidelineList;
        try{
            guidelineList = GuidelineManager.getInstance().getGuidelineList();
        }catch (Exception e){
            return Response.serverError().entity("Invalid Guideline Id. Please send a integer number").build();
        }
        if (guidelineList != null){
            guidelineJson = guidelineList.toJson();
            return Response.ok(guidelineJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }



}
