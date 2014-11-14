package cguide.db.webservices;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
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
import cguide.db.beans.PatientBean;
import cguide.db.beans.PatientManager;
import cguide.db.entities.Patient;
import cguide.db.exception.DAOException;

/**
 * Created with IntelliJ IDEA.
 * Patient: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/patient")
public class PatientWebService extends AbstractWebService {

    /**
     * Method to retrieve action's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the patient has permissions to access it
     * @param nutente The patientname of the patient
     * @return The information about the patient in a json object
     */
    @GET
    @Path("/{nutente}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(Patient.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response patientDetails(@PathParam("nutente") String nutente) {
        if (nutente==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        boolean validPatient = true;
        String patientJson = "";
        PatientBean patientBean = null;
        try{
            patientBean = PatientManager.getInstance().getPatientBeanByNutente(nutente);
        }catch (Exception e){
            return Response.serverError().entity("Patient not found").build();
        }
        if (patientBean != null){
            patientJson = Patient.fromBean(patientBean).toJson();
            return Response.ok(patientJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }

    /**
     * This method receives information about a given patient to insert in the system
     *
     * @param nutente        The new nutente of the patient
     * @param name           The patientname of the patient
     * @param lastname       The new familyName of the patient
     * @param birthdate      The new birth date of the patient
     * @param address        The Address of the patient
     * @param phone          The new mobile phone of the patient
     * @param homephone      The new phone of the patient
     * @param email          The new email of the patient
     */
    @PUT
    @Path("/add/")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the rating was successfully inserted"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has" +
                    "no privileges to access the information")
    })
    public Response addPatient(@FormParam("nutente") String nutente,
                              @FormParam("name") String name,
                              @FormParam("lastname") String lastname,
                              @FormParam("birthdate") String birthdate,
                              @FormParam("address") String address,
                              @FormParam("phone") String phone,
                              @FormParam("homephone") String homephone,
                              @FormParam("type") @DefaultValue("user") String type,
                              @FormParam("email") String email)  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(nutente) ||
                !validParam(name) ||
                !validParam(lastname) ||
                !validParam(birthdate) ||
                !validParam(address) ||
                !validParam(phone)) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }

        try {
            if(PatientManager.getInstance().isPatient(nutente)) return Response.ok("Nutente allready exists.").build();

        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        PatientBean patientBean = PatientManager.getInstance().createPatientBean();
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        patientBean.setTime(s.toString());

        try{
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            cal.setTime(dateFormat.parse(birthdate));
            patientBean.setBirthdate(birthdate);
        }catch (Exception e){
            return Response.serverError().entity("Invalid date. Please send a date in the format yyyy-mm-dd hh:mm:ss").build();
        }
        patientBean.setName(name);
        patientBean.setLastname(lastname);
        patientBean.setPhone(phone);
        patientBean.setAddress(address);
        patientBean.setNutente(nutente);
        if (validParam(type)) {
            patientBean.setType(type);
        }
        if (validParam(email)) {
            patientBean.setEmail(email);
        }
        if (validParam(homephone)) {
            patientBean.setEmail(homephone);
        }
        try{
            PatientManager.getInstance().insert(patientBean);
        }catch (DAOException e){
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok("Patient successfully registered.").build();
    }
    //-------------------------------
    // DELETE Patient
    //-------------------------------

    /**
     * This method deletes a given patient from the system
     *
     * @param nutente The nutente of the patient to remove
     */
    @DELETE
    @Path("/patient/{username}")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the user was successfully deleted"),
            @ResponseCode( code = 404, condition = "If the user was not found"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has" +
                    "no privileges to access the information")
    })
    public Response deleteUser(@PathParam("nutente") String nutente) {
        if (!validParam(nutente))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        int deleted;
        try{
            deleted = PatientManager.getInstance().deletePatientBeanByNutente(nutente);
        }catch (DAOException e){
            return Response.serverError().build();
        }
        if (deleted > 0){
            return Response.ok("Patient was successfully deleted").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }




}
