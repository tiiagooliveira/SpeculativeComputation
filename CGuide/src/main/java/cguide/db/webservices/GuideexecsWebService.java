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
import cguide.db.beans.GuideexecBean;
import cguide.db.beans.GuideexecManager;
import cguide.db.entities.Guideexec;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/guideexec")
public class GuideexecsWebService extends AbstractWebService {

    /**
     * Method to retrieve guideexec's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idguideexec The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Guideexec.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response guideexecDetails(@PathParam("id") String idguideexec) {
        if (idguideexec==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String guideexecJson = "";
        GuideexecBean guideexecBean = null;
        try{
            Integer.parseInt(idguideexec);
            guideexecBean = GuideexecManager.getInstance().getGuideexecBeanByID(idguideexec);
        }catch (Exception e){
            return Response.serverError().entity("Invalid  Id. Please send a integer number").build();
        }
        if (guideexecBean != null){
            guideexecJson = Guideexec.fromBean(guideexecBean).toJson();
            return Response.ok(guideexecJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param idguideline       The id of the parser
     * @param iduser            The nid of the user
     * @param idpatient         The id of the patient
     * @param description       the description
     * @param completed
     * @param nextTasks
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
    public Response addGuideexec(@FormParam("idguideline")  String idguideline,
                            @FormParam("iduser")         String iduser,
                            @FormParam("description")         String description,
                            @FormParam("completed")         String completed,
                            @FormParam("nextTasks")         String nextTasks,
                            @FormParam("idpatient")  String idpatient
    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(idguideline) ||
                !validParam(iduser) ||
                !validParam(idpatient))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        GuideexecBean guideexecBean=GuideexecManager.getInstance().createGuideexecBean();
        int key = 0;
        try{

            guideexecBean.setIdguideline(Long.parseLong(idguideline));
        }catch (Exception e){
            return Response.serverError().entity("Invalid parser Id. Please send a integer number").build();
        }
        try{

            guideexecBean.setIduser(Long.parseLong(iduser));
        }catch (Exception e){
            return Response.serverError().entity("Invalid user Id. Please send a integer number").build();
        }
        try{

        guideexecBean.setIdpatient(Long.parseLong(idpatient));
        }catch (Exception e){
            return Response.serverError().entity("Invalid patient Id. Please send a integer number").build();
        }
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        guideexecBean.setTime(s.toString());
        try {
            key = GuideexecManager.getInstance().insert(guideexecBean);

        } catch (DAOException e) {

        }
        if(key >0){


            return Response.ok("Guideexec #"+key+"# successfully registered").build();

        }else{
            return Response.serverError().entity("Invalid Patient Number").build();
        }
    }
}
