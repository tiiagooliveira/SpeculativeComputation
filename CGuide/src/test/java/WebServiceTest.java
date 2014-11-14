import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.http.Header;
import org.apache.http.message.BasicNameValuePair;

import cguide.execution.ProcessedTask;
import cguide.execution.TaskQuadruple;
import cguide.execution.TaskRequest;
import cguide.execution.TaskTriple;
import cguide.execution.entities.ClinicalAction;
import cguide.execution.entities.ClinicalActionAdapter;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.ClinicalTaskAdapter;
import cguide.execution.entities.Condition;
import cguide.execution.entities.ConditionSet;
import cguide.execution.entities.Formula;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 23-07-2013
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class WebServiceTest {
  public static void main(String[] argv) {

      Header cookieFromLogin=null;
      Calendar c = new GregorianCalendar(1987,06,01);
      c.setTimeZone(TimeZone.getTimeZone("Europe/Lisbon"));
      System.out.println(c.getTimeZone());
      System.out.println(c.getTimeInMillis());
      ConditionSet cs = new ConditionSet();





      ArrayList<BasicNameValuePair> valuePair=new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuePair2=new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairExam=new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairGuideline=new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairObservation=new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairTask = new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairFormula = new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuepairMedication = new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuePairGuideexec = new ArrayList<BasicNameValuePair>();
      ArrayList<BasicNameValuePair> valuePairRequest = new ArrayList<BasicNameValuePair>();
      TaskRequest request = new TaskRequest();
      request.setGuideexec("129");
      request.setTaskQuadruple(new TaskQuadruple("Q01", "Q01", "373","373"));
      //request.setTaskQuadruple(new TaskQuadruple("Workup1", "Workup1", "172","171"));
      //request.setTaskTriple(new TaskTriple("Workup2", "Workup2", "123"));
      //request.setTaskQuadruple(new TaskQuadruple("GlobalPlanColonCancer", "GlobalPlanColonCancer", "-1", "-1"));
      //request.setTaskQuadruple(new TaskQuadruple("Q02", "Q02", "161","159"));
      TaskTriple tp;
      TaskQuadruple qp;
      Gson gson = new Gson();
      System.out.println(gson.toJson(request));
      System.out.println(valuePairRequest);
      Condition condition = new Condition();
      condition.setValue("pedunculated");
      condition.setConditionParameter("shape of polyps");
      condition.setComparisonOperator("Equal_to");
      cs.addCondition(condition);
      condition = new Condition();
      condition.setValue("yes");
      condition.setConditionParameter("colon cancer appropriate for resection");
      condition.setComparisonOperator("Equal_to");
      cs.addCondition(condition);

      condition = new Condition();
      condition.setValue("no");
      condition.setConditionParameter("suspected or proven metastatic synchronous adenocarcinoma");
      condition.setComparisonOperator("Equal_to");
      cs.addCondition(condition);


      condition = new Condition();
      condition.setValue("yes");
      condition.setConditionParameter("invasive cancer");
      condition.setComparisonOperator("Equal_to");
      cs.addCondition(condition);
      request.setConditionSet(cs);
      System.out.println(cs.getCondition());


      valuePairRequest.add(new BasicNameValuePair("request", gson.toJson(request)));

      valuePairGuideexec.add(new BasicNameValuePair("idguideline","1"));
      valuePairGuideexec.add(new BasicNameValuePair("idguideline","1"));
      valuePairGuideexec.add(new BasicNameValuePair("idguideline","1"));
      valuePairGuideexec.add(new BasicNameValuePair("idguideline","1"));

      System.out.println(valuePairGuideexec);

      valuepairTask.add(new BasicNameValuePair("idguideexec","1"));
      valuepairTask.add(new BasicNameValuePair("taskType","tipo da taska"));
      valuepairTask.add(new BasicNameValuePair("taskFormat","tipo do formato"));
      valuepairTask.add(new BasicNameValuePair("taskDescription","descrição da taska"));
      valuepairTask.add(new BasicNameValuePair("taskIdentifier","identificador da taska"));
      valuepairTask.add(new BasicNameValuePair("taskPlan","Plano da taska"));
      valuepairTask.add(new BasicNameValuePair("nextTask","proxima taska"));




      ArrayList<BasicNameValuePair> valuepairPatient=new ArrayList<BasicNameValuePair>();
      valuePair.add(new BasicNameValuePair("username","admin"));
      valuePair.add(new BasicNameValuePair("password", "admin"));
      
      valuepairExam.add(new BasicNameValuePair("idtask", "1"));
      valuepairExam.add(new BasicNameValuePair("name", "exam2"));
      valuepairExam.add(new BasicNameValuePair("description", "description2"));
      valuepairExam.add(new BasicNameValuePair("identifier", "id"));

      valuepairFormula.add(new BasicNameValuePair("idtask", "1"));
      valuepairFormula.add(new BasicNameValuePair("parameter", "parameter"));
      valuepairFormula.add(new BasicNameValuePair("description", "description"));
      valuepairFormula.add(new BasicNameValuePair("identifier", "id"));


      valuepairGuideline.add(new BasicNameValuePair("dateOfCreation", "2013-08-02 15:05:16"));
      valuepairGuideline.add(new BasicNameValuePair("dateOfUpdate", "2013-08-02 15:05:16"));
      valuepairGuideline.add(new BasicNameValuePair("versionNumber", "version number"));
      valuepairGuideline.add(new BasicNameValuePair("guidelineName", "parser name"));
      valuepairGuideline.add(new BasicNameValuePair("authorship", "tiago Vilas Boas"));
      valuepairGuideline.add(new BasicNameValuePair("identifier", "identifier"));

      valuepairMedication.add(new BasicNameValuePair("name", "name"));
      valuepairMedication.add(new BasicNameValuePair("idtask", "1"));
      valuepairMedication.add(new BasicNameValuePair("activeIngredient", "active ingredient"));
      valuepairMedication.add(new BasicNameValuePair("dosage", "dosage"));
      valuepairMedication.add(new BasicNameValuePair("pharmaceuticalForm", "pharmaceutical form"));
      valuepairMedication.add(new BasicNameValuePair("posology", "posology"));
      valuepairMedication.add(new BasicNameValuePair("description", "description"));
      valuepairMedication.add(new BasicNameValuePair("identifier","identifier"));


      valuepairObservation.add(new BasicNameValuePair("parameter", "parameter 1"));
      valuepairObservation.add(new BasicNameValuePair("unit", "unit 1"));
      valuepairObservation.add(new BasicNameValuePair("isnumeric","true"));
      valuepairObservation.add(new BasicNameValuePair("parameterIdentifier", "parameterId 1"));
      valuepairObservation.add(new BasicNameValuePair("variableName", "variablename 1"));
      valuepairObservation.add(new BasicNameValuePair("parameterValue", "parameterValue 1"));
      valuepairObservation.add(new BasicNameValuePair("task", "task 1"));
      valuepairObservation.add(new BasicNameValuePair("idguideexec", "1"));

      valuepairPatient.add(new BasicNameValuePair("name", "jose"));
      valuepairPatient.add(new BasicNameValuePair("lastname", "joseph"));
      valuepairPatient.add(new BasicNameValuePair("nutente", "123123123"));
      valuepairPatient.add(new BasicNameValuePair("birthdate", "1958-05-01"));
      valuepairPatient.add(new BasicNameValuePair("address", "adress"));
      valuepairPatient.add(new BasicNameValuePair("phone", "9123123123"));
      valuepairPatient.add(new BasicNameValuePair("homephone", "2521123123"));
      valuepairPatient.add(new BasicNameValuePair("email", "email"));



      SendHttpMethod sp = new SendHttpMethod("http://localhost:8080/CGuide_war_exploded/auth",valuePair);
      String result = null;
      String action = null;

      try {
          result = sp.sendLoginPost();
          cookieFromLogin=sp.getCookieLogin();
          System.out.println(cookieFromLogin);

          if(sp.getHttpCode()==200) {

              System.out.println(result);
          }
          else if(sp.getHttpCode()==500) {
              System.out.println("SOMETHING WENT BOOM or just something wrong with the database");
              System.out.println(result);
          }
          else if(sp.getHttpCode()==404) {
              System.out.println("Resource not found");
              System.out.println(result);
          }
          else if(sp.getHttpCode()==-1){
              System.out.println("Connection Down");
              System.out.println(result);
          }
          else{
              System.out.println(sp.getHttpCode());
              System.out.println(result);
          }


      } catch (Exception e) {
          e.printStackTrace();
      }
      String username="jose";
      int id = 1;
      //GuidelineList result2 = guidelineList.fromBean(UserManager.getInstance().createUserBean());

      Formula formula = new Formula();
      long i=0;
      long j = 0;
      while(j<1000000000){
          while(i<2000000000){
              i++;
          }
          j++;
      }

      SendHttpMethod sp2 = new SendHttpMethod("http://localhost:8080/CGuide_war_exploded/execution/guideline2/next/",valuePairRequest);
      SendHttpMethod update = new SendHttpMethod("http://localhost:8080/CGuide_war_exploded/guideline/update",valuePairRequest);



      try {
         String s = "jsoe";
          ClinicalTask task = new ClinicalTask();
         ProcessedTask processedTask = new ProcessedTask();
         System.out.println("start");
         System.out.println(valuePairRequest);
         s= (String) update.sendUpdateGuidelines(cookieFromLogin);
         System.out.println("stop");

         Gson gson2 = new GsonBuilder()
                 .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                 .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                 .setPrettyPrinting()
                 .create();
         System.out.println(s);

//
//
//       System.out.println(processedTask.getController().getNextTask().toString());


         if(sp2.getHttpCode()==200) {
              System.out.println(s);
          }
          else if(sp2.getHttpCode()==500) {
              System.out.println("SOMETHING WENT BOOM or just something wrong with the database");
              System.out.println(s);
          }
          else if(sp2.getHttpCode()==404) {
              System.out.println("task not found");
              System.out.println(s);
          }
          else if(sp2.getHttpCode()==-1){
              System.out.println("Connection Down");
              System.out.println(s);
          }
          else{
              System.out.println(sp2.getHttpCode());
              System.out.println(s);
          }

      } catch (Exception e) {
          e.printStackTrace();
      }

  }
}