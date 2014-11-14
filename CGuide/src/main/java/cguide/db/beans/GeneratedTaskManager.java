package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;
import cguide.execution.TaskQuadruple;

import com.mysql.jdbc.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Idguideline: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class GeneratedTaskManager {
    public static final String ALL_FIELDS = "identifier"
            + ",idsync"
            + ",idplan"
            + ",idguideexec"
            + ",idgeneratedTask";

    public static final String FIELDS = "identifier"
            + ",idsync"
            + ",idplan"
            + ",idguideexec";

    public static final String[] ALL_FIELDS_ARRAY = { "identifier="
            , ",idsync="
            , ",idplan="
            , ",idguideexec="
            , "idgeneratedTask="
    };



    private static GeneratedTaskManager singleton = new GeneratedTaskManager();


    public static GeneratedTaskManager getInstance()
    {
        return singleton;
    }

    public GeneratedTaskBean createGeneratedTaskBean()
    {
        return new GeneratedTaskBean();
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
            sql.append("SELECT * FROM generatedtask WHERE idgeneratedTask=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                return (rs.getString("identifier"));
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
    public GeneratedTaskBean getGeneratedTaskBeanBeanByID(String id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        GeneratedTaskBean bean= GeneratedTaskManager.getInstance().createGeneratedTaskBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM generatedTask WHERE idgeneratedTask=");
            sql.append(id);
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setIdgeneratedtask(rs.getString("idgeneratedTask"));
                bean.setIdguideexec(rs.getLong("idguideexec"));
                bean.setIdplan(rs.getString("idplan"));
                bean.setIdsync(rs.getString("idsync"));
                bean.setIdentifier(rs.getString("identifier"));
            }
            return bean;
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().close(rs);
            this.getManager().releaseConnection(c);
        }

        return null;
    }

    public ArrayList<TaskQuadruple> getGeneratedTaskBeanListBeanByID(ArrayList<String> ids){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        ArrayList<TaskQuadruple> generatedTaskQuadruple = new ArrayList<TaskQuadruple>();
         ResultSet rs = null;
        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM generatedTask WHERE ");
            int i =1;
            if(ids.size()>0){
            for(String id : ids){
               sql.append("idgeneratedTask=").append(id);
               if(ids.size()>i){
               sql.append(" OR ");
               }
                   i++;
            }
            }
            else {
                return null;
            }

            System.out.println(sql.toString());
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                TaskQuadruple taskQuadruple = new TaskQuadruple();
                taskQuadruple.setId(rs.getString("idgeneratedTask"));
                taskQuadruple.setPlan(rs.getString("idplan"));
                taskQuadruple.setSync(rs.getString("idsync"));
                taskQuadruple.setTask(rs.getString("identifier"));
                generatedTaskQuadruple.add(taskQuadruple );
            }
            if(generatedTaskQuadruple.size()==0){
                return null;
            }
            else{

                return generatedTaskQuadruple;
            }
        } catch (DAOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().close(rs);
            this.getManager().releaseConnection(c);
        }

        return null;
    }

    public int deleteGeneratedTaskBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM generatedTask WHERE idgeneratedTask=");
            sql.append(id);
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

    public Integer insert(GeneratedTaskBean bean) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        Connection connection = null;
        Integer key = 0;
        try
        {
            connection = this.getConnection();
            sql = new StringBuilder();
            ps = null;
            sql.append("INSERT into generatedTask(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getIdentifier());
            sql.append("','");
            sql.append(bean.getIdsync());
            sql.append("',");
            sql.append(bean.getIdplan());
            sql.append(",");
            sql.append(bean.getIdguideexec());
            sql.append(")");
            ps=connection.prepareStatement(sql.toString(), java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                key = rs.getInt(1);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(c!=null) {
            this.getManager().releaseConnection(c);
            sql=null;
        }

        return key;
    }



    public GeneratedTaskBean update(GeneratedTaskBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());


            sql.append("UPDATE generatedTask SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getIdentifier());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getIdsync());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getIdplan());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append(bean.getIdguideexec());
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append(bean.getIdgeneratedtask());


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
