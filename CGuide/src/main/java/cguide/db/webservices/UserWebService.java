package cguide.db.webservices;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.ResponseHeader;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;

import cguide.AbstractWebService;
import cguide.db.beans.AutenticacaoBean;
import cguide.db.beans.AutenticacaoManager;
import cguide.db.beans.UserBean;
import cguide.db.beans.UserManager;
import cguide.db.entities.User;
import cguide.db.entities.UserType;
import cguide.db.exception.DAOException;
import cguide.utils.Mailer;
import cguide.utils.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 18-07-2013
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/")
public class UserWebService extends AbstractWebService {


    /**
     * Method to return the profile of the logged in user
     * @return A json object with userdetails
     */

    @GET
    @Path("/whoami")
    @StatusCodes({@ResponseCode( code = 403, condition = "If the user is not logged in!"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs!"),
            @ResponseCode( code = 200, condition = "If the response could be calculated!")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response whoAmI() {
        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        return Response.ok(User.fromBean(requestOwner).toJson(), MediaType.APPLICATION_JSON_TYPE).build();
    }



    /**
     * Method to be called to check if a username exists
     * @param username Username to check
     * @return A json object like "{exists:true}"
     */

    @POST
    @Path("/exists")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the response could be calculated")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response userExists(@QueryParam("username") String username) throws SQLException {
        if (!validParam(username))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
            Boolean userExists = false;
            userExists = UserManager.getInstance().isUser(username);


        return Response.ok("{exists:" + userExists.toString() + "}", MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Method to be called to check if a username exists (repeated method, but the username is received in path)
     * @param username Username to check
     * @return A json object like "{exists:true}"
     */

    @GET
    @Path("/exists/{username}")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the response could be calculated")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response userExistsPath(@PathParam("username") String username) {
        if (!validParam(username))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        Boolean userExists;
        try{
            userExists = UserManager.getInstance().isUser(username);
        }catch (SQLException e){
            return Response.serverError().build();
        }
        return Response.ok("{exists:" + userExists.toString() + "}", MediaType.APPLICATION_JSON_TYPE).build();
    }

    /**
     * Method used for the applications to obtain a token to perform requests on behalf of the user
     *
     * @param username    Username of the user to authenticate
     * @param password    Password of the user to authenticate
     * @return An auth-token for the application to authenticate the next requests
     */

    @POST
    @Path("/auth")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the credentials are wrong"),
            @ResponseCode( code = 200, condition = "If the response is positive")
    })
    @ResponseHeader(name = "Set-Cookie", description = "The auth-token to identify the user")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response userAuthentication(@FormParam("username") String username,
                                       @FormParam("password") String password) {
        if (!validParam(username) ||
                !validParam(password))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        boolean validUser;
        new AutenticacaoManager();
		AutenticacaoBean autenticacaoBean = AutenticacaoManager.getInstance().createAutenticacaoBean();
        UserBean user = UserManager.getInstance().getUserBeanByUsername(username);
        validUser = !(user == null);
        if (validUser){
            validUser = user.getUsername().equals(username) && user.getPassword().equals(password);
            if (validUser){
                if ((user.getActive() != null) && (!user.getActive())){
                    return Response.status(Response.Status.FORBIDDEN).entity("User must confirm the email account before login").build();
                }
            }
            // prepare auth token
            autenticacaoBean = AutenticacaoManager.getInstance().createAutenticacaoBean();
            autenticacaoBean.setUtilizadorId(user.getIduser());
            autenticacaoBean.setAuth(UUID.randomUUID().toString());
            java.util.Date validity = new java.util.Date();
            // 5 days validity
            validity.setTime((validity.getTime() + (5*24*60*60*1000)));
            autenticacaoBean.setDuracao(validity);
            try {
                AutenticacaoManager.getInstance().save(autenticacaoBean);
            } catch (DAOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (!validUser){
            return Response.status(Response.Status.FORBIDDEN).entity("User credentials are invalid").build();
        }else{
            NewCookie authCookie = new NewCookie("auth-token", autenticacaoBean.getAuth(),"/",null,null, 5*24*60*60,false);
//            NewCookie validityCookie = new NewCookie("token-validity", autenticacaoBean.getDuracao().toString());
            return Response.ok().
                    cookie(authCookie).
                    entity("User credentials are valid and a new cookie was returned").build();
        }
    }

    //----------------------------------------------------------------
    //
    // General operations over user cguide.db.entities
    //
    //----------------------------------------------------------------

    //-------------------------------
    // GET USER
    //-------------------------------

    /*@GET
    @Path("/user/details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userDetailsOld(@QueryParam("username") String username) {
        return userDetails(username);
    }*/

    /**
     * Method to retrieve user's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param username The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/user/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(User.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response userDetails(@PathParam("username") String username) {
        if (!validParam(username))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isAdmin() && !isUser(username)){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        Boolean validUser=true;
        String userJson = "";
        UserBean utilizadorBean = null;
        if (requestOwner.getUsername().equals("username")){
            utilizadorBean = requestOwner;
            userJson = User.fromBean(utilizadorBean).toJson();
            return Response.ok(userJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else{
            try {
                if (isAdmin()&&UserManager.getInstance().isUser(username)){

                    UserBean user = UserManager.getInstance().getUserBeanByUsername(username);
                    if(user!=null){
                    utilizadorBean = user;
                    userJson = User.fromBean(utilizadorBean).toJson();
                    return Response.ok(userJson, MediaType.APPLICATION_JSON_TYPE).build();
                    }
                }
            } catch (DAOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        if (validUser){
            return Response.ok(userJson, MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }


/**
     * Method to be called when a user receives an email with the confirmation url
     *
     * Note: This should not be implemented by applications
     *
     * @param username  Username to activate
     * @param key       Key to prove that the email was received
     */

    @GET
    @Path("/activation")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the account was activated"),
            @ResponseCode( code = 404, condition = "If the tuple is incorrect")
    })
    public Response userActivation(@QueryParam("username") String username, @QueryParam("key") String key) {
        if (!validParam(username) ||
                !validParam(key))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }

        Boolean validUser = false;
        try{
            UserBean user= UserManager.getInstance().getUserBeanByUsername(username);
            if (user != null){
                if (user.getActivationkey().equals(key)){
                    validUser = true;
                    user.setActive(Boolean.TRUE);
                    UserManager.getInstance().update(user);
                }
            }
        }catch (DAOException e){
            return Response.serverError().entity("Error while processing activation request").build();
        }
        if (validUser){
            return Response.ok("Email was validated. Login is now allowed").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found. Check your link or register again").build();
        }
    }

    /**
     * Method to be called when a  user forgets the password.
     * This will create a new password and sent it to the email.
     *
     * @param username The username of the user
     */
    @GET
    @Path("/forgot")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the email with the new password was sent"),
            @ResponseCode( code = 404, condition = "If the user was not found")
    })
    public Response userForgot(@QueryParam("username") String username) {
        if (!validParam(username))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        Boolean validUser = false;
        try{
            UserBean user= UserManager.getInstance().getUserBeanByUsername(username);
            if (user != null){
                validUser = true;
                user.setPassword(Utils.generateRandomKey(6));
                UserManager.getInstance().update(user);
                Mailer.sendPasswordRecoveryEmail(user.getEmail(), user.getPassword(), user.getUsername());
            }
        }catch (Exception e){
            return Response.serverError().entity("Error while processing forgot request").build();
        }
        if (validUser){
            return Response.ok("An email has been sent with a password!").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found. Check your link or register again").build();
        }
    }
    //-------------------------------
    // ADD USER
    //-------------------------------

    /**
     * This method receives information about a given user to insert in the system
     *
     * @param username       The username of the user
     * @param password       The new password of the user
     * @param name           The new name of the user
     * @param email          The new email of the user
     * @param lastname       The new familyName of the user
     * @param photo          The new url to the photo of the user
     * @param phone          The new mobile phone of the user
     * @param type           The type of the user (This can only be setted by an administrator)
     * @param homephone      The new phone of the user
     * @param address         the Address of the user
     * @param birthdate      The new birth date of the user
     */
    @PUT
    @Path("/user/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the user was successfully inserted")
    })
    public Response addNewUser(@PathParam("username") String username,
                               @FormParam("password") String password,
                               @FormParam("name") String name,
                               @FormParam("email") String email,
                               @FormParam("lastname") String lastname,
                               @FormParam("photo") String photo,
                               @FormParam("phone") @DefaultValue("photo") String phone,
                               @FormParam("type") @DefaultValue("user") String type,
                               @FormParam("homephone") String homephone,
                               @FormParam("address") String address,
                               @FormParam("birthdate") String birthdate) {


        if (!validParam(username) ||
                !validParam(password) ||
                !validParam(name) ||
                !validParam(email))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isAdmin()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        Boolean userExists = null;
        Boolean mailExists = null;
        try {
            userExists = UserManager.getInstance().isUser(username);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (userExists){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Username already exists").build();
        }
        try{
            mailExists = UserManager.getInstance().isEmail(email);
        }catch (DAOException e){
            return Response.serverError().build();
        }
        if (mailExists){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Email already registered").build();
        }

        UserBean userBean = UserManager.getInstance().createUserBean();
        if (validParam(birthdate)){
            userBean.setBirthdate(birthdate);
        }
        if (validParam(phone)){
            userBean.setPhone(phone);
        }
        if (validParam(homephone)){
            userBean.setHomephone(homephone);
        }
        if (validParam(lastname)){
            userBean.setLastname(lastname);
        }
        if (validParam(photo)){
            userBean.setPhoto(photo);
        }
        if (validParam(address)){
            userBean.setAddress(address);
        }
        userBean.setEmail(email);
        userBean.setName(name);
        userBean.setPassword(password);
        userBean.setUsername(username);
        userBean.setActive(false);

        userBean.setActivationkey(UUID.randomUUID().toString());
        Calendar c=Calendar.getInstance();
        Timestamp ts = new Timestamp(c.getTimeInMillis());
        userBean.setReg(ts.toString());

        if (isAdmin() && validParam(type)){
            UserType userType;
            try{
                userType = UserType.valueOf(type);
                type = userType.name();
            }catch (Exception e){return Response.status(Response.Status.BAD_REQUEST).entity("User type is wrong: " + type).build();
            }
            userBean.setType(type);
        }else{
            userBean.setType(UserType.user.name());
        }

        try{
            if (!userExists) UserManager.getInstance().insert(userBean);
        }catch (DAOException e){
            e.printStackTrace();
            return Response.serverError().build();
        }

        try{
            Mailer.sendActivationMail(email, userBean.getActivationkey().toString(), userBean.getUsername(),userBean.getPassword());
        }catch (Exception e){
            // TODO remove user?
            return Response.serverError().entity("Unable to send confirmation mail").build();
        }
        return Response.ok("User successfully registered. A confirmation email was sent..").build();
    }

    @POST
    @Path("/user/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the user was successfully updated"),
            @ResponseCode( code = 404, condition = "If the user was not found"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has" +
                    "no privileges to access the information")
    })
    public Response updateUser(@PathParam("username") String username,
                               @FormParam("password") String password,
                               @FormParam("name") String name,
                               @FormParam("email") String email,
                               @FormParam("lastname") String lastname,
                               @FormParam("photo") String photo,
                               @FormParam("phone") String phone,
                               @FormParam("type") @DefaultValue("user") String type,
                               @FormParam("homephone") String homephone,
                               @FormParam("address") String address,
                               @FormParam("birthdate") String birthdate) {
        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("Must be logged in").build();
        }

        UserBean userToEdit;
        if (requestOwner.getUsername().equals(username)){
            userToEdit = requestOwner;
        }else if (isAdmin()){
            try{
                UserBean user = UserManager.getInstance().getUserBeanByUsername(username);
                if (user != null){
                    userToEdit = user;
                }else{
                    return Response.status(Response.Status.NOT_FOUND).entity("User was not found").build();
                }
            }catch (Exception e){
                return Response.serverError().entity("Error while getting user from database").build();
            }
        }else{
            return Response.status(Response.Status.FORBIDDEN).entity("User don't have permission to edit other users").build();
        }

        if (!validParam(username) ||
                !validParam(email))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (validParam(birthdate)){
            try{
                userToEdit.setBirthdate(birthdate);
            }catch (Exception e){
                return Response.serverError().entity("Invalid birthdate date. Please send a timestamp in miliseconds").build();
            }
        }
        if (validParam(address)){

                userToEdit.setAddress(address);
        }
        if (validParam(name)){
            userToEdit.setName(name);
        }
        if(validParam(lastname)){
        userToEdit.setLastname(lastname);
         }
        if(validParam(homephone)){
            userToEdit.setHomephone(homephone);
        }
        if(validParam(phone)){
            userToEdit.setPhone(phone);
        }
        if (validParam(password)){
            userToEdit.setPassword(password);
        }
        if(validParam(photo)){
            userToEdit.setPhoto(photo);
        }
        String oldEmail = userToEdit.getEmail();
        if (!userToEdit.getEmail().equals(email)){
            userToEdit.setEmail(email);
            userToEdit.setActive(false);
            userToEdit.setActivationkey(UUID.randomUUID().toString());
            try{
                Mailer.sendEmailChangedMail(email, userToEdit.getActivationkey(), userToEdit.getUsername());
            }catch (Exception e){
                return Response.serverError().build();
            }
        }
        if (isAdmin() && validParam(type)){
            UserType userType;
            UserType currentUserType;
            try{
                userType = UserType.valueOf(type);
                type = userType.name();
                currentUserType = UserType.valueOf(userToEdit.getType());
            }catch (Exception e){
                return Response.status(Response.Status.BAD_REQUEST).entity("User type is wrong: " + type).build();
            }
            userToEdit.setType(type);
        }

        try{
            UserManager.getInstance().update(userToEdit);
        }catch (Exception e){
            return Response.serverError().build();
        }
        return Response.ok("User was successfully updated").build();
    }

    //-------------------------------
    // DELETE USER
    //-------------------------------

    /**
     * This method deletes a given user from the system
     *
     * @param username  The username of the user to remove
     */
    @DELETE
    @Path("/user/{username}")
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 200, condition = "If the user was successfully deleted"),
            @ResponseCode( code = 404, condition = "If the user was not found"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has" +
                    "no privileges to access the information")
    })
    public Response deleteUser(@PathParam("username") String username) {
        if (!validParam(username))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isAdmin() && !isUser(username)){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        int deleted;
        try{
            deleted = UserManager.getInstance().deleteUserBeanByUsername(username);
        }catch (DAOException e){
            return Response.serverError().build();
        }
        if (deleted > 0){
            return Response.ok("User was successfully deleted").build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }

    @Override
	protected Boolean validParam(String value){
        return value != null && value.trim().length() > 0;
    }

    protected Boolean isUser(String username){
        return requestOwner != null && requestOwner.getUsername().equals(username);
    }
}
