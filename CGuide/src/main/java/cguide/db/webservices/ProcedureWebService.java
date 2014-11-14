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
import cguide.db.beans.ProcedureBean;
import cguide.db.beans.ProcedureManager;
import cguide.db.entities.Procedure;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/procedure")
public class ProcedureWebService extends AbstractWebService {

    /**
     * Method to retrieve procedure's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idprocedure The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Procedure.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response procedureDetails(@PathParam("id") String idprocedure) {
        if (idprocedure==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String procedureJson = "";
        ProcedureBean procedureBean = null;
        try{
            Integer.parseInt(idprocedure);
            procedureBean = ProcedureManager.getInstance().getProcedureBeanByID(idprocedure);
        }catch (Exception e){
            return Response.serverError().entity("Invalid GuideExec Id. Please send a integer number").build();
        }
        if (procedureBean != null){
            procedureJson = Procedure.fromBean(procedureBean).toJson();
            return Response.ok(procedureJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param name           The new name of the procedure
     * @param description    The new description of the procedure
     * @param idtask      The Guideline Exec ID of the procedure
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
    public Response addProcedure(@FormParam("idtask")  String idtask,
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
        ProcedureBean procedureBean=ProcedureManager.getInstance().createProcedureBean();
        try{

            procedureBean.setIdtask(Long.parseLong(idtask));
        }catch (Exception e){
            return Response.serverError().entity("Invalid Guideexec Id. Please send a integer number").build();
        }
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        procedureBean.setTime(s.toString());
        procedureBean.setName(name);
        procedureBean.setDescription(description);
        procedureBean.setIdentifier(identifier);
        try {
            ProcedureManager.getInstance().insert(procedureBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Procedure successfully registered.").build();
    }





}
