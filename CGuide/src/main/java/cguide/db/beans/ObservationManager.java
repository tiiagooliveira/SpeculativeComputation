
package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;
import cguide.execution.entities.Condition;

/**
 * Handles database calls for the observation table.
 * @author tiago
 */
public class ObservationManager
{
    public static final String ALL_FIELDS = "idtask"
            + ",time"
            + ",identifier"
            + ",parametervalue"
            + ",variablename"
            + ",parameteridentifier"
            + ",isnumeric"
            + ",unit"
            + ",parameter"
            + ",idobservation";
    public static final String FIELDS = "idtask"
            + ",time"
            + ",identifier"
            + ",parametervalue"
            + ",variablename"
            + ",parameteridentifier"
            + ",isnumeric"
            + ",unit"
            + ",parameter";
    public static final String[] ALL_FIELDS_ARRAY = { "idtask="
            , ",time="
            , ",identifier="
            , ",parametervalue="
            , ",variablename="
            , ",parameteridentifier="
            , ",isnumeric="
            , ",unit="
            , ",parameter="
            , "idobservation="
    };
    private static ObservationManager singleton = new ObservationManager();

    /**
     * Get the UtilizadorManager singleton.
     *
     * @return UtilizadorManager
     */
    public static ObservationManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public ObservationBean createObservationBean()
    {
        return new ObservationBean();
    }

    public ObservationBean getObservationBeanById(String id){
        Connection c = null;
        StringBuilder sql = null;
        ObservationBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;




        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM observation WHERE unit=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean = ObservationManager.getInstance().createObservationBean();
                bean.setIdobservation(rs.getLong("idobservation"));
                bean.setUnit(rs.getString("unit"));
                bean.setVariablename(rs.getString("variableName"));
                bean.setParameter(rs.getString("parameter"));
                bean.setParametervalue(rs.getString("parameterValue"));
                bean.setIdentifier(rs.getString("identifier"));
                bean.setTime(rs.getString("time"));
                bean.setIsnumeric(rs.getBoolean("isnumeric"));
                bean.setParameteridentifier(rs.getString("parameterIdentifier"));
                bean.setIdtask(rs.getLong("idtask"));
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
    public ObservationBean getObservationBeanByID(Long id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        ObservationBean bean= ObservationManager.getInstance().createObservationBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM observation WHERE idobservation=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setIdobservation(rs.getLong("idobservation"));
                bean.setUnit(rs.getString("unit"));
                bean.setVariablename(rs.getString("variablename"));
                bean.setParameter(rs.getString("parameter"));
                bean.setParametervalue(rs.getString("parametervalue"));
                bean.setIdentifier(rs.getString("identifier"));
                bean.setTime(rs.getString("time"));
                bean.setIsnumeric(rs.getBoolean("isnumeric"));
                bean.setParameteridentifier(rs.getString("parameteridentifier"));
                bean.setTime(rs.getString("time"));
                bean.setIdtask(rs.getLong("idtask"));

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

    /**
     * Insert the UtilizadorBean bean into the database.
     *
     * @param bean the UtilizadorBean bean to be saved
     * @return the inserted bean
     * @throws DAOException
     */
    //13
    public ObservationBean update(ObservationBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            // bean= ObservationManager.getInstance().getObservationBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE observation SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append(bean.getIdtask());
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getIdentifier());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getParametervalue());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getVariablename());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getParameteridentifier());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append(bean.getIsnumeric());
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append("'");
            sql.append(bean.getUnit());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[9]);
            sql.append("'");
            sql.append(bean.getParameter());
            sql.append("'");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[10]);
            sql.append(bean.getIdobservation());
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

    /**
     * Update the UtilizadorBean bean record in the database according to the changes.
     *
     * @param bean the UtilizadorBean bean to be updated
     * @return the updated bean
     * @throws DAOException
     */
    //14
    public ObservationBean insert(ObservationBean bean) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        Statement statement = null;
        Connection connection = null;
        try
        {
            connection = this.getConnection();
            sql = new StringBuilder();
            // bean= ObservationManager.getInstance().getObservationBeanByID(4L);
            ps = null;
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("INSERT into observation(");
            sql.append(FIELDS);
            sql.append(") VALUES (");
            sql.append(bean.getIdtask());
            sql.append(",'");
            sql.append(bean.getTime());
            sql.append("','");
            sql.append(bean.getIdentifier());
            sql.append("','");
            sql.append(bean.getParametervalue());
            sql.append("','");
            sql.append(bean.getVariablename());
            sql.append("','");
            sql.append(bean.getParameteridentifier());
            sql.append("',");
            sql.append(bean.getIsnumeric());
            sql.append(",'");
            sql.append(bean.getUnit());
            sql.append("','");
            sql.append(bean.getParameter());
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



    public int deleteObservationBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM observation WHERE idobservation = '");
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
            sql.append("FROM observation ");
            sql.append("INNER JOIN guideexec ");
            sql.append("INNER JOIN parser ");
            sql.append("INNER JOIN patient ");
            sql.append("INNER JOIN user ");
            sql.append("ON parser.idguideline=guideexec.idguideline AND observation.idtask=guideexec.idtask ");
            sql.append("AND user.iduser = guideexec.iduser AND patient.idpatient = guideexec.idpatient ");
            sql.append("WHERE idnonmedication=");
            sql.append(id);
            sql.append(" ");
            sql.append("ORDER BY observation.idobservation");


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
    public ArrayList<Condition> getParameterByIdguideexec(Long id) throws DAOException {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();
        String result=null;
        ArrayList<Condition> str = new ArrayList<Condition>();
        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("SELECT *");
            sql.append("FROM observation INNER JOIN task INNER JOIN guideexec ");
            sql.append("ON observation.idtask = task.idtask AND task.idguideexec=guideexec.idguideexec ");
            sql.append("WHERE guideexec.idguideexec=");
            sql.append(id);
            sql.append(" Order BY guideexec.idguideexec");

            ps= c.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Condition condition = new Condition();
                condition.setComparisonOperator("Equal_to");
                condition.setConditionParameter(rs.getString("parameter"));
                condition.setValue(rs.getString("parameterValue"));
                condition.setId(rs.getString("identifier"));
                condition.setIsNumeric(rs.getBoolean("isnumeric"));
                condition.setUnit(rs.getString("unit"));
                condition.addParameterIdentifier(rs.getString("parameterIdentifier"));
                str.add(condition);

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
