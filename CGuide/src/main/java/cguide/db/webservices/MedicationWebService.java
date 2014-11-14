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
import cguide.db.beans.MedicationBean;
import cguide.db.beans.MedicationManager;
import cguide.db.entities.Medication;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/medication")
public class MedicationWebService extends AbstractWebService {

    /**
     * Method to retrieve medication's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idmedication The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Medication.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response medicationDetails(@PathParam("id") String idmedication) {
        if (idmedication==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validUser = true;
        String medicationJson = "";
        MedicationBean medicationBean = null;
        try{
            Integer.parseInt(idmedication);
            medicationBean = MedicationManager.getInstance().getMedicationBeanById(idmedication);
        }catch (Exception e){
            return Response.serverError().entity("Invalid Medication Id. Please send a integer number").build();
        }
        if (medicationBean != null){
            medicationJson = Medication.fromBean(medicationBean).toJson();
            return Response.ok(medicationJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param name           The new name of the medication
     * @param activeIngredient    The new active ingredient of the medication
     * @param dosage      The dosage of the medication
     * @param pharmaceuticalForm   The new dosage of the medication
     * @param posology      The posology of the medication
     * @param description      The description of the medication
     * @param idtask            The task of the medication
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
    public Response addMedication(@FormParam("name")                    String name,
                                 @FormParam("activeIngredient")         String activeIngredient,
                                 @FormParam("dosage")                   String dosage,
                                 @FormParam("pharmaceuticalForm")       String pharmaceuticalForm,
                                 @FormParam("posology")                 String posology,
                                 @FormParam("idtask")                     String idtask,
                                 @FormParam("identifier")                     String identifier,
                                 @FormParam("description")              String description
    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(name) ||
                !validParam(pharmaceuticalForm) ||
                !validParam(posology) ||
                !validParam(identifier) ||
                !validParam(description) ||
                !validParam(activeIngredient) ||
                !validParam(dosage))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        MedicationBean medicationBean=MedicationManager.getInstance().createMedicationBean();
        try{

            medicationBean.setIdtask(Long.parseLong(idtask));
        }catch (Exception e){
            return Response.serverError().entity("Invalid idtask Id. Please send a integer number").build();
        }
        if(validParam(description)){
        medicationBean.setDescription(description);
        }
        medicationBean.setActiveingredient(activeIngredient);

        if(validParam(dosage)){
             medicationBean.setDosage(dosage);
        }
        if(validParam(pharmaceuticalForm)){
             medicationBean.setPharmaceuticalform(pharmaceuticalForm);
        }
        if(validParam(posology)){
             medicationBean.setPosology(posology);
            }
        if(validParam(identifier)){
             medicationBean.setIdentifier(identifier);
        }
             medicationBean.setName(name);
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        medicationBean.setTime(s.toString());
        try {
            MedicationManager.getInstance().insert(medicationBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Medication successfully registered.").build();
    }
}
