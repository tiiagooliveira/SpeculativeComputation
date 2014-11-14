import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import cguide.db.beans.GeneratedTaskManager;
import cguide.db.beans.UserBean;
import cguide.execution.SyncController;
import cguide.execution.TaskQuadruple;
import cguide.execution.TaskTriple;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 23-07-2013
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
public class SQLTest {
    private static final String JDBC_DRIVER ="com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cguide";
    private static final String JDBC_USER = "cguide";
    private static final String JDBC_PASSWORD = "password_!\"#";
    private static UserBean bean=null;
    private static PreparedStatement statement = null;
    private static Connection connection = null;
    public static final String FIELDS = "active"
            + ",activationkey"
            + ",type"
            + ",photo"
            + ",email"
            + ",homephone"
            + ",phone"
            + ",adress"
            + ",birthdate"
            + ",lastname"
            + ",name"
            + ",password"
            + ",username";
    public static final String[] ALL_FIELDS_ARRAY = { "active="
            , ",type="
            , ",photo="
            , ",email="
            , ",homephone="
            , ",phone="
            , ",adress="
            , ",birthdate="
            , ",lastname="
            , ",name="
            , ",password="
            , ",username="
            , "iduser="
    };
  public static void main(String[] argv) throws SQLException {
      String username="admin";
      SyncController controller = new SyncController();
      TaskTriple tp = new TaskTriple();
      /*controller.addTask(new TaskTriple("a","a","c"));
      controller.addTask(new TaskTriple("as","a","c"));
      controller.addTask(new TaskTriple("a","a","c"));
      controller.addTask(new TaskTriple("a","a","d"));
      controller.addTask(new TaskTriple("b","b","e"));
      controller.addTask(new TaskTriple("b", "b", "e"));
      controller.addTask(new TaskTriple("b", "b", "e"));
      controller.pushSync("c");
      controller.pushSync("d");
      controller.pushSync("e");
      System.out.println("PEEK=" + controller.getSyncTask().peek());
      System.out.println(controller.getNextTask().toString());
      System.out.println(controller.getSyncTask().toString());

      System.out.println("c is synced ="+controller.isSynced("c"));
      System.out.println("d is synced ="+controller.isSynced("d"));
      System.out.println("e is synced ="+controller.isSynced("e"));
      System.out.println("c is allowed ="+controller.isAllowed("c"));
      System.out.println("d is allowed ="+controller.isAllowed("d"));
      System.out.println("e is allowed ="+controller.isAllowed("e"));*/

      /*GeneratedTaskBean bean = GeneratedTaskManager.getInstance().createGeneratedTaskBean();
      bean.setIdentifier("jose");
      bean.setIdplan(123123123L);
      bean.setIdsync(123123123L);
      bean.setIdguideexec(219L);
      System.out.println(GeneratedTaskManager.getInstance().insert(bean));*/
      ArrayList<TaskQuadruple> generatedTaskBeans;
      ArrayList<String> ids = new ArrayList<String>();
      ids.add("1");
      ids.add("2");
      ids.add("3");
      generatedTaskBeans = GeneratedTaskManager.getInstance().getGeneratedTaskBeanListBeanByID(ids);
      Gson gson = new Gson();
      System.out.println(gson.toJson(generatedTaskBeans));
  }

}