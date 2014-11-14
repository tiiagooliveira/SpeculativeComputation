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
import cguide.db.beans.ObservationBean;
import cguide.db.beans.ObservationManager;
import cguide.db.entities.Observation;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/observation")
public class ObservationWebService extends AbstractWebService {

    /**
     * Method to retrieve observation's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idobservation The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Observation.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response observationDetails(@PathParam("id") String idobservation) {
        if (idobservation==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String observationJson = "";
        ObservationBean observationBean = null;
        try{
            Integer.parseInt(idobservation);
            observationBean = ObservationManager.getInstance().getObservationBeanById(idobservation);
        }catch (Exception e){
            return Response.serverError().entity("Invalid Observation Id. Please send a integer number").build();
        }
        if (observationBean != null){
            observationJson = Observation.fromBean(observationBean).toJson();
            return Response.ok(observationJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param parameter             The new parameter of the observation
     * @param unit                  The new unitof the observation
     * @param parameterIdentifier   The parameterIdentifier of the observation
     * @param variableName          The new variableName of the observation
     * @param parameterValue        The parameterValue of the observation
     * @param identifier                  The task of the observation
     * @param isnumeric             The isnumeric check
     * @param idtask           The idtask id
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
    public Response addObservation(@FormParam("parameter")                      String parameter,
                                  @FormParam("unit")                            String unit,
                                  @FormParam("isnumeric")                       String isnumeric,
                                  @FormParam("parameterIdentifier")             String parameterIdentifier,
                                  @FormParam("variableName")                    String variableName,
                                  @FormParam("parameterValue")                  String parameterValue,
                                  @FormParam("task")                            String identifier,
                                  @FormParam("idtask")                          String idtask

    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(parameter) ||
                !validParam(isnumeric) ||
                !validParam(parameterValue) ||
                !validParam(identifier) ||
                !validParam(unit) ||
                !validParam(idtask) ||
                !validParam(parameterIdentifier))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }

        ObservationBean observationBean= ObservationManager.getInstance().createObservationBean();
        try{
            observationBean.setIdtask(Long.parseLong(idtask));

        }catch (Exception e){
            return Response.serverError().entity("Invalid guideexec Id. Please send a integer number").build();
        }
        try{
            observationBean.setIsnumeric(Boolean.parseBoolean(isnumeric));

        }catch (Exception e){
            return Response.serverError().entity("Invalid isnumeric Boolean. Please send a boolean (True/False)").build();
        }
        observationBean.setIdentifier(identifier);
        observationBean.setUnit(unit);
        observationBean.setParameteridentifier(parameterIdentifier);
        if(validParam(variableName)){
            observationBean.setVariablename(variableName);
        }
        observationBean.setParametervalue(parameterValue);
        observationBean.setParameter(parameter);
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        observationBean.setTime(s.toString());
        try {
            ObservationManager.getInstance().insert(observationBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Observation successfully registered.").build();
    }

}
