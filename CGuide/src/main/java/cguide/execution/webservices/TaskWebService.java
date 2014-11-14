package cguide.execution.webservices;

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
import cguide.db.beans.TaskBean;
import cguide.db.beans.TaskManager;
import cguide.db.exception.DAOException;
import cguide.execution.entities.Action;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.Decision;
import cguide.execution.entities.End;
import cguide.execution.entities.Plan;
import cguide.execution.entities.Question;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/execution/task")
public class TaskWebService extends AbstractWebService {

    /**
     * Method to retrieve task's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idtask The taskidentifier of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(ClinicalTask.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response taskDetails(@PathParam("id") String idtask) {
        if (idtask==null)  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        boolean validUser = true;
        String taskJson;
        ClinicalTask task;
        task = guidelineHandler.getClinicalTask(idtask);
        if (task != null){
            if(task.getTaskType().equals("Question")){
                Question question = (Question) task;
            taskJson = question.toJson();
                return Response.ok(taskJson, MediaType.APPLICATION_JSON_TYPE).build();
            }
            if(task.getTaskType().equals("Decision")){
                Decision decision = (Decision) task;
                taskJson = decision.toJson();
                return Response.ok(taskJson, MediaType.APPLICATION_JSON_TYPE).build();
            }
            if(task.getTaskType().equals("End")){
                End end = (End) task;
                taskJson = end.toJson();
                return Response.ok(taskJson, MediaType.APPLICATION_JSON_TYPE).build();
            }
            if(task.getTaskType().equals("Plan")){
                Plan plan = (Plan) task;
                taskJson = plan.toJson();
                return Response.ok(taskJson, MediaType.APPLICATION_JSON_TYPE).build();
            }
            if(task.getTaskType().equals("Action")){
                Action action = (Action) task;
                taskJson = action.toJson();
                return Response.ok(taskJson, MediaType.APPLICATION_JSON_TYPE).build();
            }
            else{
                return Response.status(Response.Status.BAD_REQUEST).entity("Not a valid task.").build();
            }


        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Task not found.").build();
        }
    }
    /**
     * This method receives information about a given user to insert in the system
     *
     * @param taskidentifier           The new taskidentifier of the task
     * @param taskDescription    The new active ingredient of the task
     * @param taskFormat      The taskformat of the task
     * @param taskType   The new taskformat of the task
     * @param idguideexec            The task of the task
     * @param taskPlan   The new taskformat of the task
     * @param nextTask           The task of the task
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
    public Response addTask(@FormParam("taskIdentifier")                     String taskidentifier,
                                  @FormParam("taskDescription")              String taskDescription,
                                  @FormParam("taskFormat")                   String taskFormat,
                                  @FormParam("taskType")                     String taskType,
                                  @FormParam("idguideexec")                  String idguideexec,
                                  @FormParam("taskPlan")                     String taskPlan,
                                  @FormParam("nextTask")                     String nextTask
    )  {

        if (requestOwner == null){
            return Response.status(Response.Status.FORBIDDEN).entity("User not logged in").build();
        }
        if (!validParam(taskidentifier) ||
                !validParam(taskType) ||
                !validParam(taskDescription) ||
                !validParam(taskPlan) ||
                !validParam(nextTask) ||
                !validParam(taskFormat))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        TaskBean taskBean=TaskManager.getInstance().createTaskBean();
        try{

            taskBean.setIdguideexec(Long.parseLong(idguideexec));
        }catch (Exception e){
            return Response.serverError().entity("Invalid Guideexec Id. Please send a integer number").build();
        }
        taskBean.setTaskDescription(taskDescription);
        taskBean.setTaskFormat(taskFormat);
        taskBean.setTaskType(taskType);
        taskBean.setNextTask(nextTask);
        taskBean.setTaskPlan(taskPlan);
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        taskBean.setTime(s.toString());
        try {
            TaskManager.getInstance().insert(taskBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return Response.ok("Task successfully registered.").build();
    }
}
