package cguide.db.webservices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cguide.db.beans.GuidelineBean;
import cguide.db.beans.GuidelineManager;
import cguide.db.entities.Guideline;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/guideline")
public class GuidelineWebService extends AbstractWebService {


    /**
     * Method to return the profile of the logged in user
     * @return A json object with userdetails
     */

    @GET
    @Path("/update")
    @StatusCodes({@ResponseCode( code = 403, condition = "If the user is not logged in!"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs!"),
            @ResponseCode( code = 200, condition = "If the response could be calculated!")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGuideline() {
        if (!requestOwner.getType().equals("admin")){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        ArrayList<String> guidelines= guidelineHandler.getGuidelines();
        for (String identifier : guidelines){
            try {
                if(GuidelineManager.getInstance().isGuideline(identifier)){
                    GuidelineBean guidelineBean = new GuidelineBean();
                    cguide.execution.entities.Guideline guideline = guidelineHandler.getClinicalPracticeGuideline(identifier);
                    guidelineBean.setIdentifier(guideline.getId());
                    guidelineBean.setAuthorship(guideline.getAuthorship());
                    guidelineBean.setDateofcreation(guideline.getDateofcreation());
                    guidelineBean.setDateofupdate(guideline.getDateofupdate());
                    guidelineBean.setGuidelinename(guideline.getGuidelinename());
                    guidelineBean.setVersionnumber(guideline.getVersionnumber());
                    guidelineBean.setDescription(guideline.getGuidelinedescription());
                    //GuidelineManager.getInstance().update(guidelineBean);
                } else {
                    GuidelineBean guidelineBean = new GuidelineBean();
                    cguide.execution.entities.Guideline guideline = guidelineHandler.getClinicalPracticeGuideline(identifier);
                    guidelineBean.setIdentifier(guideline.getId());
                    guidelineBean.setAuthorship(guideline.getAuthorship());
                    guidelineBean.setDateofcreation(guideline.getDateofcreation());
                    guidelineBean.setDateofupdate(guideline.getDateofupdate());
                    guidelineBean.setGuidelinename(guideline.getGuidelinename());
                    guidelineBean.setVersionnumber(guideline.getVersionnumber());
                    guidelineBean.setDescription(guideline.getGuidelinedescription());
                    GuidelineManager.getInstance().insert(guidelineBean);
                }

            } catch (DAOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return Response.ok("Guidelines"+guidelines.toString()+" successfully updated/added.").build();
    }


    /**
     * Method to retrieve parser's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idguideline The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Guideline.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response guidelineDetails(@PathParam("id") String idguideline) {
        if (idguideline==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String guidelineJson = "";
        GuidelineBean guidelineBean = null;
        try{
            Integer.parseInt(idguideline);
            guidelineBean = GuidelineManager.getInstance().getGuidelineBeanByID(idguideline);
        }catch (Exception e){
            return Response.serverError().entity("Invalid Guideline Id. Please send a integer number").build();
        }
        if (guidelineBean != null){
            guidelineJson = Guideline.fromBean(guidelineBean).toJson();
            return Response.ok(guidelineJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }


    /**
     * This method receives information about a given user to insert in the system
     *
     * @param dateOfCreation           The new dateOfCreation of the parser
     * @param dateOfUpdate    The new versionNumber of the parser
     * @param versionNumber      The Guideline Exec ID of the parser
     * @param guidelineName   The new versionNumber of the parser
     * @param authorship      The Guideline Exec ID of the parser
     * @param identifier      Guideline id
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
    public Response addGuideline(@FormParam("dateOfCreation")  String dateOfCreation,
                            @FormParam("dateOfUpdate")         String dateOfUpdate,
                            @FormParam("versionNumber")  String versionNumber,
                            @FormParam("guidelineName")  String guidelineName,
                            @FormParam("identifier")  String identifier,
                            @FormParam("authorship")  String authorship
    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(dateOfCreation) ||
                !validParam(dateOfCreation) ||
                !validParam(guidelineName) ||
                !validParam(identifier) ||
                !validParam(authorship) ||
                !validParam(versionNumber))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        GuidelineBean guidelineBean=GuidelineManager.getInstance().createGuidelineBean();
        try{
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            cal.setTime(dateFormat.parse(dateOfCreation));
            guidelineBean.setDateofcreation(dateOfCreation);
            cal.setTime(dateFormat.parse(dateOfUpdate));
            guidelineBean.setDateofupdate(dateOfUpdate);
        }catch (Exception e){
            return Response.serverError().entity("Invalid date. Please send a date in the format yyyy-mm-dd hh:mm:ss").build();
        }
        guidelineBean.setVersionnumber(versionNumber);
        guidelineBean.setGuidelinename(guidelineName);
        guidelineBean.setAuthorship(authorship);
        guidelineBean.setIdentifier(identifier);
        try {
            GuidelineManager.getInstance().insert(guidelineBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Guideline successfully added.").build();
    }

}
