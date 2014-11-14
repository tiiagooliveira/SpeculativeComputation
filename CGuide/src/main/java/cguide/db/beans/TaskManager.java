
package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

/**
 * Handles database calls for the task table.
 * @author tiago
 */
public class TaskManager
{
    public static final String ALL_FIELDS = "time"
            + ",nextTask"
            + ",taskPlan"
            + ",taskIdentifier"
            + ",taskDescription"
            + ",taskFormat"
            + ",taskType"
            + ",idguideexec"
            + ",idtask";
    public static final String FIELDS ="time"
            + ",nextTask"
            + ",taskPlan"
            + ",taskIdentifier"
            + ",taskDescription"
            + ",taskFormat"
            + ",taskType"
            + ",idguideexec";
    public static final String[] ALL_FIELDS_ARRAY = { "time="
            , ",nextTask="
            , ",taskPlan="
            , ",taskIdentifier="
            , ",taskDescription="
            , ",taskFormat="
            , ",taskType="
            , ",idguideexec="
            , ",idtask="
    };
    private static TaskManager singleton = new TaskManager();

    /**
     * Get the UtilizadorManager singleton.
     *
     * @return UtilizadorManager
     */
    public static TaskManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public TaskBean createTaskBean()
    {
        return new TaskBean();
    }

    public TaskBean getTaskBeanById(String id){
        Connection c = null;
        StringBuilder sql = null;
        TaskBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;

        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM task WHERE idtask=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean = TaskManager.getInstance().createTaskBean();
                bean.setIdguideexec(rs.getLong("idguideexec"));
                bean.setTaskFormat(rs.getString("taskFormat"));
                bean.setTaskDescription(rs.getString("taskDescription"));
                bean.setTaskIdentifier(rs.getString("taskIdentifier"));
                bean.setTime(rs.getString("time"));
                bean.setTaskType(rs.getString("taskType"));
                bean.setIdtask(rs.getLong("idtask"));
                bean.setTaskPlan(rs.getString("taskPlan"));
                bean.setNextTask(rs.getString("nextTask"));
            }
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().close(rs);
            this.getManager().releaseConnection(c);
            sql=null;
        }

        return bean;
    }
    public String getTaskIdentifierById(String id){
        Connection c = null;
        StringBuilder sql = null;
        TaskBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;

        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM task WHERE idtask=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                return (rs.getString("taskIdentifier"));
            }
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().close(rs);
            this.getManager().releaseConnection(c);
            sql=null;
        }
        return null;
    }

    /**
     * Insert the UtilizadorBean bean into the database.
     *
     * @param bean the UtilizadorBean bean to be saved
     * @return the inserted bean
     * @throws cguide.db.exception.DAOException
     */
    //13
    public TaskBean update(TaskBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            // bean= TaskManager.getInstance().getTaskBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE task SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getNextTask());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getTaskPlan());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getTaskIdentifier());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getTaskDescription());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getTaskFormat());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getTaskType());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append(bean.getIdguideexec());
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append(bean.getIdtask());
            ps= c.prepareStatement(sql.toString());
            ps.executeUpdate();
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().releaseConnection(c);
            sql=null;
        }

        return bean;
    }
    public ArrayList<String> getGuidelineData(Long id) throws DAOException {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        String result=null;
        ArrayList<String> str = new ArrayList<String>();
        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("SELECT user.name,patient.name,parser.guidelineName ");
            sql.append("FROM task ");
            sql.append("INNER JOIN guideexec ");
            sql.append("INNER JOIN parser ");
            sql.append("INNER JOIN patient ");
            sql.append("INNER JOIN user ");
            sql.append("ON parser.idguideline=guideexec.idguideline AND task.idguideexec=guideexec.idguideexec ");
            sql.append("AND user.iduser = guideexec.iduser AND patient.idpatient = guideexec.idpatient ");
            sql.append("WHERE idtask=");
            sql.append(id);
            sql.append(" ");
            sql.append("ORDER BY task.idtask");

            ps= c.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                str.add(rs.getString(1));
                str.add(rs.getString(2));
                str.add(rs.getString(3));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().releaseConnection(c);
            sql=null;
        }

        return str;
    }

    /**
     * Update the UtilizadorBean bean record in the database according to the changes.
     *
     * @param bean the UtilizadorBean bean to be updated
     * @return the updated bean
     * @throws cguide.db.exception.DAOException
     */
    //14
    public String insert(TaskBean bean) throws DAOException
    {
        Connection c;
        PreparedStatement ps;
        StringBuilder sql;
        String key = null;
        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("INSERT into task(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getTime());
            sql.append("','");
            sql.append(bean.getNextTask());
            sql.append("','");
            sql.append(bean.getTaskPlan());
            sql.append("','");
            sql.append(bean.getTaskIdentifier());
            sql.append("','");
            sql.append(bean.getTaskDescription());
            sql.append("','");
            sql.append(bean.getTaskFormat());
            sql.append("','");
            sql.append(bean.getTaskType());
            sql.append("',");
            sql.append(bean.getIdguideexec());
            sql.append(")");
            ps=c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                key = rs.getString(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return key;
    }



    public int deleteTaskBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM task WHERE idtask = '");
            sql.append(id);
            sql.append("'");
            statement=con.prepareStatement(sql.toString());
            result =statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        }

        if (statement !=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        return result;
    }



    //_____________________________________________________________________
    //
    // UTILS
    //_____________________________________________________________________

    /**
     * Retrieves the manager object used to get connections.
     *
     * @return the manager used
     */
    //40
    private Manager getManager()
    {
        return Manager.getInstance();
    }

    /**
     * Frees the connection.
     *
     * @param c the connection to release
     */
    //41
    private void freeConnection(Connection c)
    {
        this.getManager().releaseConnection(c); // back to pool
    }

    /**
     * Gets the connection.
     */
    //42
    private Connection getConnection() throws DAOException
    {
        try
        {
            return this.getManager().getConnection();
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
    }

}
