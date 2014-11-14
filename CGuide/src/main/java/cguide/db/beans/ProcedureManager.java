package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class ProcedureManager {
    public static final String ALL_FIELDS = "idtask"
            + ",time"
            + ",description"
            + ",name"
            + ",identifier"
            + ",idprocedure";

    public static final String FIELDS = "idtask"
            + ",time"
            + ",description"
            + ",name"
            + ",identifier";

    public static final String[] ALL_FIELDS_ARRAY = { "idtask="
            , ",time="
            , ",description="
            , ",name="
            , ",identifier="
            , ",idprocedure="
    };



    private static ProcedureManager singleton = new ProcedureManager();


    public static ProcedureManager getInstance()
    {
        return singleton;
    }

    public ProcedureBean createProcedureBean()
    {
        return new ProcedureBean();
    }
    public ProcedureBean getProcedureBeanByID(String id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        ProcedureBean bean= ProcedureManager.getInstance().createProcedureBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM procedure WHERE idprocedure=");
            sql.append(id);
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setIdprocedure(rs.getLong("idprocedure"));
                bean.setTime(rs.getString("time"));
                bean.setIdtask(rs.getLong("idtask"));
                bean.setDescription(rs.getString("description"));
                bean.setName(rs.getString("name"));
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

    public int deleteProcedureBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM procedure WHERE idprocedure =");
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

    public ProcedureBean insert(ProcedureBean bean) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        Connection connection = null;
        try
        {
            connection = this.getConnection();
            sql = new StringBuilder();
            ps = null;
            sql.append("INSERT into procedure(");
            sql.append(FIELDS);
            sql.append(") VALUES (");
            sql.append(bean.getIdtask());
            sql.append(",'");
            sql.append(bean.getTime());
            sql.append("','");
            sql.append(bean.getDescription());
            sql.append("','");
            sql.append(bean.getName());
            sql.append("','");
            sql.append(bean.getIdentifier());
            sql.append("')");
            ps=connection.prepareStatement(sql.toString());
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

    public ProcedureBean update(ProcedureBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE procedure SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append(bean.getIdtask());
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getDescription());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getName());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getIdentifier());
            sql.append("')");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append(bean.getIdprocedure());
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
            sql.append("FROM procedure ");
            sql.append("INNER JOIN guideexec ");
            sql.append("INNER JOIN parser ");
            sql.append("INNER JOIN patient ");
            sql.append("INNER JOIN user ");
            sql.append("ON parser.idguideline=guideexec.idguideline AND procedure.idtask=guideexec.idtask ");
            sql.append("AND user.iduser = guideexec.iduser AND patient.idpatient = guideexec.idpatient ");
            sql.append("WHERE idprocedure=");
            sql.append(id);
            sql.append(" ");
            sql.append("ORDER BY procedure.idprocedure");


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
