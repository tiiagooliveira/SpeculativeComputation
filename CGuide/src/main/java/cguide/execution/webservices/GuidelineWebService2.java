package cguide.execution.webservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.enunciate.jaxrs.ResponseCode;
import org.codehaus.enunciate.jaxrs.StatusCodes;
import org.codehaus.enunciate.jaxrs.TypeHint;

import cguide.AbstractWebService;
import cguide.db.beans.GeneratedTaskBean;
import cguide.db.beans.GeneratedTaskManager;
import cguide.db.beans.GuideexecBean;
import cguide.db.beans.GuideexecManager;
import cguide.db.beans.ObservationBean;
import cguide.db.beans.ObservationManager;
import cguide.db.beans.TaskBean;
import cguide.db.beans.TaskManager;
import cguide.db.exception.DAOException;
import cguide.execution.ProcessedDecision;
import cguide.execution.ProcessedTask;
import cguide.execution.TaskController;
import cguide.execution.TaskQuadruple;
import cguide.execution.TaskRequest;
import cguide.execution.entities.Action;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.ClinicalTaskList;
import cguide.execution.entities.Condition;
import cguide.execution.entities.Decision;
import cguide.execution.entities.End;
import cguide.execution.entities.Option;
import cguide.execution.entities.Plan;
import cguide.execution.entities.Question;
import cguide.utils.Utils;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */
@Path("/execution/guideline2")
public class GuidelineWebService2 extends AbstractWebService {


    @POST
    @Path("/tasks")
    @TypeHint(ProcessedTask.class)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response getTasks(@FormParam("request") String request) {

        if (!validParam(request))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }
        TaskController controller;
        try {

            controller = TaskController.fromJson(request);

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request format").build();
        }
        ClinicalTaskList clinicalTaskList = new ClinicalTaskList();

        clinicalTaskList.setTasks(getClinicalTasks(controller.getNextTask()));

        if(clinicalTaskList.size()>0) {return Response.ok(clinicalTaskList.toJson(), MediaType.APPLICATION_JSON_TYPE).build();}
        else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }


    /**
     * Method to retrieve task's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param request The usertaskidentifier of the user
     * @return The information about the user in a json object
     */
    @POST
    @Path("/next")
    @TypeHint(ProcessedTask.class)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response nextTask(@FormParam("request") String request) {
        if (!validParam(request))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        Gson gson = new Gson();
        TaskRequest taskRequest = gson.fromJson(request,TaskRequest.class);
        ClinicalTask task = guidelineHandler.getClinicalTask(taskRequest.getTaskQuadruple().getTask());
        String format= task.getTaskFormat();
        ProcessedTask processedTask = new ProcessedTask();
        ProcessedDecision processedDecision = null;
        String idtask = null;


        //LOAD SYNCTASK
        GuideexecBean guideexecBean = GuideexecManager.getInstance().getGuideexecBeanByID(taskRequest.getGuideexec());
        TaskController taskController= gson.fromJson(guideexecBean.getNextTasks(), TaskController.class);
        if(guideexecBean==null){

            return Response.status(Response.Status.NOT_FOUND).entity("Failed to retrieve Guideline execution from db.").build();

        }
        if(taskController==null){
            return Response.status(Response.Status.NOT_FOUND).entity("Failed to task Controller from db.").build();

        }

        String idtaskExecuted=null;
        //creating task to insert into the database
        TaskBean taskBean = TaskManager.getInstance().createTaskBean();
        taskBean.setIdguideexec(guideexecBean.getIdguideexec());
        taskBean.setTaskDescription(task.getGeneralDescription());
        taskBean.setTaskFormat(task.getTaskFormat());
        taskBean.setTaskIdentifier(task.getId());
        taskBean.setTaskType(task.getTaskType());
        Timestamp s = new Timestamp(Calendar.getInstance().getTimeInMillis());
        taskBean.setTime(s.toString());
        taskBean.setNextTask(taskController.toJson());
        try {
            idtaskExecuted = TaskManager.getInstance().insert(taskBean);
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        /*if(taskController.peekSyncTask()!=null&&!taskController.isSyncable(taskRequest.getTaskQuadruple().getId(),taskRequest.getTaskQuadruple().getTask())){

        return Response.status(Response.Status.BAD_REQUEST).entity("Current task requires other parallel tasks to be completed first.").build();
        }*/

        // if task to be executed is a alternative task
        // remove the other tasks, and remove it from the alternative task list
        if(!taskController.contains(taskRequest.getTaskQuadruple())){

            return Response.status(Response.Status.NOT_FOUND).entity("Task not found in the current guideline execution.").build();

        }
        //Remove alternative tasks

        if (taskController.isAlternativeTask(taskRequest.getTaskQuadruple().getId())){
             taskController.removeAlternativeTask(taskRequest.getTaskQuadruple().getId());
            //taskController.addTask(taskRequest.getTaskQuadruple());
        }


        TaskQuadruple currentTask = taskRequest.getTaskQuadruple();
        GeneratedTaskBean generatedTaskBean = GeneratedTaskManager.getInstance().createGeneratedTaskBean();
        generatedTaskBean.setIdguideexec(guideexecBean.getIdguideexec());
        generatedTaskBean.setIdsync(currentTask.getSync());
        generatedTaskBean.setIdplan(currentTask.getPlan());
        generatedTaskBean.setIdentifier(currentTask.getTask());
        try {
           idtask = String.valueOf(GeneratedTaskManager.getInstance().insert(generatedTaskBean));
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



        
        if(task.getTaskType().equals("Plan")){
            Plan plan = (Plan) task;

            String id = idtask;
            taskController.pushPlan(idtask);
            if(taskController.getSyncTask().peek()!=null){
                id = taskRequest.getTaskQuadruple().getId();
            }
            if(plan.getFirstTaskFormat().equals("hasFirstTask")){
                if(taskController.getSyncTask().peek()!=null){
                    // if it's a parallel task
                    
                    taskController.updateTask(taskRequest.getTaskQuadruple(),plan.getFirstClinicalTask().get(0));

                } else {
                    if(plan.getFirstClinicalTask().size()>1){
                        //Spawn parallel tasks
                        //get Sync Task
                        for(String ct :plan.getFirstClinicalTask()){
                            taskController.addTask(new TaskQuadruple(ct,plan.getSyncTask(),id,idtask));
                            taskController.pushSync(id);
                        }

                    }else{
                        taskController.addTask(new TaskQuadruple(plan.getFirstClinicalTask().get(0),plan.getFirstClinicalTask().get(0),id,idtask));
                       }
                    taskController.removeTask(taskRequest.getTaskQuadruple());
                }
            }
            if(plan.getFirstTaskFormat().equals("stopConditionTask")){
            }
        }
        if(task.getTaskType().equals("Decision")){
            //handle decision
            Decision decision = (Decision) task;
            String id = idtask;
            System.out.println("DECISION");
            processedDecision = new ProcessedDecision();
            //calculate decision
            ArrayList<Condition> previousConditions = new ArrayList<Condition>();
            try {
                previousConditions = ObservationManager.getInstance().getParameterByIdguideexec(guideexecBean.getIdguideexec());
            } catch (DAOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            for(Option option : decision.getOptions()){
                 Boolean test = Utils.processDecision(previousConditions,option.getOptionConditionSet());

                 if(test){
                     processedDecision.setUnit(option.getUnit());
                     processedDecision.setOptionParameter(option.getOptionParameter());
                     processedDecision.setParameterIdentifier(option.getOptionIdentifier());
                     processedDecision.setValue(option.getValue());
                     processedDecision.setNumeric(option.getNumeric());
                }
            }



            if(taskController.getSyncTask().peek()!=null){
                id = taskRequest.getTaskQuadruple().getId();
            }

            if(decision.getTaskFormat().equals("parallelTask")){
                for(String taskname: decision.getClinicalTasks()){
                    taskController.addTask(new TaskQuadruple(taskname,decision.getSyncTask(),id,taskRequest.getTaskQuadruple().getPlan()));

                }
                taskController.pushSync(id);

            }
            if(decision.getTaskFormat().equals("alternativeTask")){
                 int counter=0;

                Gson gson1 = new Gson();
                for(String taskname: decision.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    if(Utils.alternateTaskTest(previousConditions, ct.getTriggerCondition())){
                        if(taskController.getSyncTask().peek()!=null){

                            //update request task
                            //in case it was updated already it adds a new task to the next tasks
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                         }
                        else{

                            //adds a new task
                            //request task is to be removed after
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));
                 
                        }

                        counter++;
                    }
                }
                //insert conditions from the request
                //to do, update if a value changes


                //if there's more then one selectable task, set it to alternative
                if(counter>1){
                    taskController.addAlternativeTask(id);
                 }

                if(counter==0){
                    // if no task is selectable add all as alternative tasks
                    for(String taskname: decision.getClinicalTasks()){
                        ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                        if(taskController.getSyncTask().peek()!=null){
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                         }
                        else{
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));

                         }
                    }
                    //set as alternative task
                    taskController.addAlternativeTask(id);
                }


            }
            if(decision.getTaskFormat().equals("preferenceAlternativeTask")){
                //add all the tasks and set it to alternative task
                  for(String taskname: decision.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    if(taskController.getSyncTask().peek()!=null){
                        taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                    }
                    else{
                        taskController.addTask(new TaskQuadruple(taskname,id,id,taskRequest.getTaskQuadruple().getPlan()));
                        }
                }
                //set as alternative task
                taskController.addAlternativeTask(id);
                //remove request task

            }
            if(decision.getTaskFormat().equals("nextTask")){
                taskController.addTask(new TaskQuadruple(decision.getClinicalTasks().get(0),decision.getClinicalTasks().get(0),id,taskRequest.getTaskQuadruple().getPlan()));



            }
            if(decision.getTaskFormat().equals("stopConditionTask")){

            }
        }
        if(task.getTaskType().equals("Question")){
            //handle Question
            Question question = (Question) task;


            String id = idtask;
            if(taskController.getSyncTask().peek()!=null){
                
                id = taskRequest.getTaskQuadruple().getId();
            }
            if(taskRequest.getConditionSet()==null){

                return Response.status(Response.Status.BAD_REQUEST).entity("Missing parameters for Question task.").build();


            }

            if(format.equals("parallelTask")){
                
                for(String taskname: question.getClinicalTasks()){
                    taskController.addTask(new TaskQuadruple(taskname,question.getSyncTask(),id,taskRequest.getTaskQuadruple().getPlan()));

                }
                taskController.pushSync(id);


            }

            if(format.equals("alternativeTask")){
                

                int counter=0;
                ArrayList<Condition> previousConditions = new ArrayList<Condition>();
                try {
                    
                    previousConditions = ObservationManager.getInstance().getParameterByIdguideexec(guideexecBean.getIdguideexec());
                } catch (DAOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                // add all selectable tasks
                previousConditions.addAll(taskRequest.getConditionSet().getCondition());
                Gson gson1 = new Gson();
                System.out.println("question"+question.toJson());
                for(String taskname: question.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    System.out.println("taskname"+taskname);
                    System.out.println("ct"+ct.toJson());
                    if(Utils.alternateTaskTest(previousConditions, ct.getTriggerCondition())){
                        if(taskController.getSyncTask().peek()!=null){

                            //update request task
                            //in case it was updated already it adds a new task to the next tasks
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                            
                        }
                        else{

                            //adds a new task
                            //request task is to be removed after
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));
                            

                        }

                        counter++;
                    }
                }
                //insert conditions from the request
                //to do, update if a value changes


                //if there's more then one selectable task, set it to alternative
                if(counter>1){
                    taskController.addAlternativeTask(id);
                    
                }

                if(counter==0){
                    // if no task is selectable add all as alternative tasks
                    for(String taskname: question.getClinicalTasks()){
                        ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                        if(taskController.getSyncTask().peek()!=null){
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                            
                        }
                        else{
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));

                            
                        }
                    }
                    //set as alternative task
                    taskController.addAlternativeTask(id);
                }

            }
            if(format.equals("preferenceAlternativeTask")){
                //add all the tasks and set it to alternative task
                
                for(String taskname: question.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    if(taskController.getSyncTask().peek()!=null){
                        taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                        
                    }
                    else{
                        taskController.addTask(new TaskQuadruple(taskname,id,id,taskRequest.getTaskQuadruple().getPlan()));

                        
                    }
                }
                //set as alternative task
                taskController.addAlternativeTask(id);
                //remove request task
            }
            if(format.equals("nextTask")){
                // not going to happen but we will see
                taskController.addTask(new TaskQuadruple(question.getClinicalTasks().get(0),question.getClinicalTasks().get(0),id,taskRequest.getTaskQuadruple().getPlan()));
            }
            if(format.equals("stopConditionTask")){
                // probably not going to happen either

            }

            for(Condition c : taskRequest.getConditionSet().getCondition()){
                ObservationBean observationBean = ObservationManager.getInstance().createObservationBean();
                observationBean.setIdentifier(c.getId());
                observationBean.setIdtask(Long.valueOf(idtaskExecuted));
                observationBean.setParameter(c.getConditionParameter());
                if(c.getParameterIdentifier().size()>0){
                observationBean.setParameteridentifier(c.getParameterIdentifier().get(0));
                }
                observationBean.setIsnumeric(c.getIsNumeric());
                observationBean.setParametervalue(c.getValue());
                observationBean.setIdentifier(c.getId());
                observationBean.setUnit(c.getUnit());
                s = new Timestamp(Calendar.getInstance().getTimeInMillis());
                observationBean.setTime(s.toString());


                try {
                    
                    ObservationManager.getInstance().insert(observationBean);
                } catch (DAOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        if(task.getTaskType().equals("End")){
            //handle End
            End end = (End) task;

            //remove all tasks from the plan
            if(taskController.peekPendingPlan()!=null){
                Plan plan = (Plan) guidelineHandler.getClinicalTask(GeneratedTaskManager.getInstance().getTaskIdentifierById(taskRequest.getTaskQuadruple().getPlan()));

                taskController.removePlan(taskRequest.getTaskQuadruple().getPlan());


                //add a next task to the old plan with ending plan next task
            for(String plantask : plan.getClinicalTasks() ){
                taskController.addTask(new TaskQuadruple(plantask,plantask,idtask,taskController.peekPendingPlan()));
                }
                        //implement for diferent type of tasks, though this should work fine, unless it's alternative which i doubt it can happen
            }
            //if no plans on the stack yay we are done
            // still figuring it out
            else{
                // set guideline has completed
                guideexecBean.setCompleted(true);
                try {
                    guideexecBean.setNextTasks("Guideline Execution Completed");
                    GuideexecManager.getInstance().update(guideexecBean);
                    taskController.setNextTask(null);
                    return Response.ok(processedTask.toJson(), MediaType.APPLICATION_JSON_TYPE).build();
                } catch (DAOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }

        }

        if(task.getTaskType().equals("Action")){
            //handle Action
            Action action = (Action) task;
            //handle plan
            
            String id = idtask;
            if(taskController.getSyncTask().peek()!=null){
                id = taskRequest.getTaskQuadruple().getId();

                
            }
            if(format.equals("parallelTask")){
                for(String taskname: action.getClinicalTasks()){
                    taskController.addTask(new TaskQuadruple(taskname,action.getSyncTask(),id,taskRequest.getTaskQuadruple().getPlan()));
                }
                taskController.pushSync(id);
            }
            if(format.equals("alternativeTask")){
                int counter=0;

                // add all selectable tasks
                for(String taskname: action.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    if(Utils.alternateTaskTest(taskRequest.getConditionSet().getCondition(), ct.getTriggerCondition())){
                        if(taskController.getSyncTask().peek()!=null){
                             
                            //update request task
                            //in case it was updated allready it adds a new task to the next taks
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                          }
                        else{
                            //adds a new task
                            //request task is to be removed after
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));
                        }

                        counter++;
                    }
                }
                //if there's more then one selectable task, set it to alternative
                if(counter>1){
                    taskController.addAlternativeTask(id);
                }

                if(counter==0){
                    // if no task is selectable add all has alternative tasks
                    for(String taskname: action.getClinicalTasks()){
                        ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                        
                        if(taskController.getSyncTask().peek()!=null){
                            taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                            }
                        else{
                            taskController.addTask(new TaskQuadruple(ct.getId(),ct.getId(),id,taskRequest.getTaskQuadruple().getPlan()));
                            }
                    }
                    //set as alternative task
                    taskController.addAlternativeTask(id);
                }
            }
            if(format.equals("preferenceAlternativeTask")){

                //add all the tasks and set it to alternative task

                for(String taskname: action.getClinicalTasks()){
                    ClinicalTask ct = guidelineHandler.getClinicalTask(taskname);
                    
                    if(taskController.getSyncTask().peek()!=null){
                        taskController.updateTask(taskRequest.getTaskQuadruple(),ct.getId());
                     }
                    else{
                        taskController.addTask(new TaskQuadruple(taskname,id,id,taskRequest.getTaskQuadruple().getPlan()));

                    }
                }
                //set as alternative task
                taskController.addAlternativeTask(id);
            }
            if(format.equals("nextTask")){

                taskController.addTask(new TaskQuadruple(action.getClinicalTasks().get(0),action.getClinicalTasks().get(0),id,taskRequest.getTaskQuadruple().getPlan()));

            }
            if(format.equals("stopConditionTask")){

            }
        }
        taskController.removeTask(taskRequest.getTaskQuadruple());

        //test if the execution of this task triggered the completion of a parallel task
        if(taskController.getSyncTask().peek()!=null){
            //if is parallel
            if(taskController.getSyncTask().peek().equals(taskRequest.getTaskQuadruple().getId())){
                //if the id of the task is the same in the sync queue

                if(taskController.isAllowed(taskRequest.getTaskQuadruple().getId())){

                // if all tasks are Sync'ed remove the tasks and pop the id fro
                taskController.removeParallelTask(taskRequest.getTaskQuadruple().getId(),idtask);
                }else{


                    // Hide tasks who are sync'd and are parallel


                }
            }
            //remove the tasks that are sync'ed but require other tasks to be sync'ed


        }
        guideexecBean.setNextTasks(taskController.toJson());
        try {
            GuideexecManager.getInstance().update(guideexecBean);
        } catch (DAOException e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Failed to update execution").build();

        }

       if(taskController.peekSyncTask()!=null){
        taskController.removeNotAllowedTasks();
        }

        processedTask.setController(taskController);
        processedTask.setTasks(getClinicalTasks(taskController.getNextTask()));
        if(processedDecision!=null){processedTask.setDecision(processedDecision);}
        return Response.ok(processedTask.toJson(), MediaType.APPLICATION_JSON_TYPE).build();

    }

    /**
     * Method to retrieve observation's information.
     * The request must contain a Cookie Header with a valid auth-token and the information
     * will only be delivered if the user has permissions to access it
     * @param idguideexec The username of the user
     * @return The information about the user in a json object
     */
    @GET
    @Path("/last/{idguideexec}")
    @Produces(MediaType.APPLICATION_JSON)
    @TypeHint(ProcessedTask.class)
    @StatusCodes({@ResponseCode( code = 406, condition = "If mandatory parameters are missing"),
            @ResponseCode( code = 500, condition = "If a problem with the database occurs"),
            @ResponseCode( code = 403, condition = "If the auth-token is missing or has no privileges to access the information"),
            @ResponseCode( code = 404, condition = "If the resource was not found"),
            @ResponseCode( code = 200, condition = "If the resource is found")
    })
    public Response lastTask(@PathParam("idguideexec") String idguideexec) {
        if (!validParam(idguideexec))  {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Mandatory params are missing").build();
        }
        if (!isUser()){
            return Response.status(Response.Status.FORBIDDEN).entity("Private information").build();
        }

        GuideexecBean guideexecBean;
        ProcessedTask processedTask = new ProcessedTask();
        try{
            Integer.parseInt(idguideexec);
            guideexecBean = GuideexecManager.getInstance().getGuideexecBeanByID(idguideexec);
        }catch (Exception e){
            return Response.serverError().entity("Invalid idguideexec Id. Please send a integer number").build();
        }
        if (guideexecBean!= null){
            TaskController taskController = TaskController.fromJson(guideexecBean.getNextTasks());


            processedTask.setTasks(getClinicalTasks(taskController.getNextTask()));
            processedTask.setController(taskController);
            return Response.ok(processedTask.toJson(), MediaType.APPLICATION_JSON_TYPE).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).entity("Resource not found.").build();
        }
    }

    private ArrayList<ClinicalTask> getClinicalTasks(ArrayList<TaskQuadruple> TaskQuadruples){
        ArrayList<ClinicalTask> tasks = new ArrayList<ClinicalTask>();
        for(TaskQuadruple task : TaskQuadruples){
            tasks.add(guidelineHandler.getClinicalTask(task.getTask()));
        }
        return tasks;
    }

}
