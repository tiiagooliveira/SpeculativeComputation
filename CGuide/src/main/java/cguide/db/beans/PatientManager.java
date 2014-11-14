
package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

/**
 * Handles database calls for the patient table.
 * @author tiago
 */
public class PatientManager
{
    public static final String ALL_FIELDS = "time"
            + ",email"
            + ",type"
            + ",homephone"
            + ",phone"
            + ",address"
            + ",birthdate"
            + ",nutente"
            + ",lastname"
            + ",name"
            + ",idpatient";
    public static final String FIELDS ="time"
            + ",email"
            + ",type"
            + ",homephone"
            + ",phone"
            + ",address"
            + ",birthdate"
            + ",nutente"
            + ",lastname"
            + ",name";
    public static final String[] ALL_FIELDS_ARRAY = { ",time="
            , ",email="
            , ",type="
            , ",homephone="
            , ",phone="
            , ",address="
            , ",birthdate="
            , ",nutente="
            , ",lastname="
            , ",name="
            , "idpatient="
    };
    private static PatientManager singleton = new PatientManager();

    /**
     * Get the UtilizadorManager singleton.
     *
     * @return UtilizadorManager
     */
    public static PatientManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public PatientBean createPatientBean()
    {
        return new PatientBean();
    }



    public PatientBean getPatientBeanByNutente(String nutente){
        Connection c = null;
        StringBuilder sql = null;
        PatientBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;




        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM patient WHERE nutente=\"");
            sql.append(nutente);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean = PatientManager.getInstance().createPatientBean();
                bean.setIdpatient(rs.getLong("idpatient"));
                bean.setNutente(rs.getString("nutente"));
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setLastname(rs.getString("lastname"));
                bean.setHomephone(rs.getString("homephone"));
                bean.setType(rs.getString("type"));
                bean.setEmail(rs.getString("email"));
                bean.setBirthdate(rs.getString("birthdate"));
                bean.setAddress(rs.getString("address"));
                bean.setTime(rs.getString("time"));
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
    public PatientBean getPatientBeanByID(Long id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        PatientBean bean= PatientManager.getInstance().createPatientBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM patient WHERE idpatient=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setIdpatient(rs.getLong("idpatient"));
                bean.setNutente(rs.getString("nutente"));
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setLastname(rs.getString("lastname"));
                bean.setHomephone(rs.getString("homephone"));
                bean.setType(rs.getString("type"));
                bean.setEmail(rs.getString("email"));
                bean.setBirthdate(rs.getString("birthdate"));
                bean.setAddress(rs.getString("address"));
                bean.setTime(rs.getString("time"));

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
    public PatientBean update(PatientBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            // bean= PatientManager.getInstance().getPatientBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE patient SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getEmail());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getType());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getHomephone());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getPhone());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getAddress());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getBirthdate());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append("'");
            sql.append(bean.getNutente());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append("'");
            sql.append(bean.getLastname());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[9]);
            sql.append("'");
            sql.append(bean.getName());
            sql.append("'");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[10]);
            sql.append(bean.getIdpatient());
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
    public PatientBean insert(PatientBean bean) throws DAOException
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
            // bean= PatientManager.getInstance().getPatientBeanByID(4L);
            ps = null;
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("INSERT into patient(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getTime());
            sql.append("','");
            sql.append(bean.getEmail());
            sql.append("','");
            sql.append(bean.getType());
            sql.append("','");
            sql.append(bean.getHomephone());
            sql.append("','");
            sql.append(bean.getPhone());
            sql.append("','");
            sql.append(bean.getAddress());
            sql.append("','");
            sql.append(bean.getBirthdate());
            sql.append("','");
            sql.append(bean.getNutente());
            sql.append("','");
            sql.append(bean.getLastname());
            sql.append("','");
            sql.append(bean.getName());
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
    public Boolean isPatient(String nutente) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        Boolean result=false;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        ResultSet rs = null;
        try {
            sql.append("SELECT EXISTS(SELECT 1 FROM patient WHERE nutente='");
            sql.append(nutente);
            sql.append("') AS isPatient");
            statement=con.prepareStatement(sql.toString());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while(rs.next()){

                if (rs.getInt("isPatient")==1) return true;
            }
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
    public Boolean isEmail(String email) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        Boolean result=false;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        ResultSet rs = null;
        try {
            sql.append("SELECT EXISTS(SELECT 1 FROM patient WHERE email=\"");
            sql.append(email);
            sql.append("\") AS isEmail");
            statement=con.prepareStatement(sql.toString());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int i=0;
        try {
            if(rs!=null){
                while(rs.next()){
                    if (rs.getInt("isPatient")==1) result = true;
                }
            }
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



    public int deletePatientBeanByNutente(String nutente) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM patient WHERE nutente = '");
            sql.append(nutente);
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
