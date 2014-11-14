package cguide.db.webservices;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;

import cguide.AbstractWebService;
import cguide.db.beans.NonmedicationBean;
import cguide.db.beans.NonmedicationManager;
import cguide.db.entities.Nonmedication;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/nonmedication")
public class NonmedicationWebService extends AbstractWebService {

    /**
     * Method to retrieve nonmedication's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idnonmedication The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Nonmedication.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response nonmedicationDetails(@PathParam("id") String idnonmedication) {
        if (idnonmedication==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String nonmedicationJson = "";
        NonmedicationBean nonmedicationBean = null;
        try{
            Integer.parseInt(idnonmedication);
            nonmedicationBean = NonmedicationManager.getInstance().getNonmedicationBeanByID(idnonmedication);
        }catch (Exception e){
            return Response.serverError().entity("Invalid GuideExec Id. Please send a integer number").build();
        }
        if (nonmedicationBean != null){
            nonmedicationJson = Nonmedication.fromBean(nonmedicationBean).toJson();
            return Response.ok(nonmedicationJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param name           The new name of the nonmedication
     * @param description    The new description of the nonmedication
     * @param idtask      The Guideline Exec ID of the nonmedication
     * @param identifier
     */
    @PUT
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the rating was successfully inserted"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has" +
                    "no privileges to access the information")
    })
    public Response addNonmedication(@FormParam("idtask")  String idtask,
                            @FormParam("name")         String name,
                            @FormParam("identifier")  String identifier,
                            @FormParam("description")  String description
    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(idtask) ||
                !validParam(name) ||
                !validParam(identifier) ||
                !validParam(description))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        NonmedicationBean nonmedicationBean=NonmedicationManager.getInstance().createNonmedicationBean();
        try{

            nonmedicationBean.setIdtask(Long.parseLong(idtask));
        }catch (Exception e){
            return Response.serverError().entity("Invalid Guideexec Id. Please send a integer number").build();
        }
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        nonmedicationBean.setTime(s.toString());
        nonmedicationBean.setName(name);
        nonmedicationBean.setDescription(description);
        nonmedicationBean.setIdentifier(identifier);
        try {
            NonmedicationManager.getInstance().insert(nonmedicationBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Nonmedication successfully registered.").build();
    }





}
