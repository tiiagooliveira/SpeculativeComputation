
package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

/**
 * Handles database calls for the user table.
 * @author tiago
 */
public class UserManager
{
    public static final String ALL_FIELDS = "active"
            + ",reg"
            + ",activationkey"
            + ",type"
            + ",photo"
            + ",email"
            + ",homephone"
            + ",phone"
            + ",address"
            + ",birthdate"
            + ",lastname"
            + ",name"
            + ",password"
            + ",username"
            + ",iduser";
    public static final String FIELDS = "active"
            + ",reg"
            + ",activationkey"
            + ",type"
            + ",photo"
            + ",email"
            + ",homephone"
            + ",phone"
            + ",address"
            + ",birthdate"
            + ",lastname"
            + ",name"
            + ",password"
            + ",username";
    public static final String[] ALL_FIELDS_ARRAY = { "active="
            , ",reg="
            , ",activationkey="
            , ",type="
            , ",photo="
            , ",email="
            , ",homephone="
            , ",phone="
            , ",address="
            , ",birthdate="
            , ",lastname="
            , ",name="
            , ",password="
            , ",username="
            , "iduser="
    };
    private static UserManager singleton = new UserManager();

    /**
     * Get the UtilizadorManager singleton.
     *
     * @return UtilizadorManager
     */
    public static UserManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public UserBean createUserBean()
    {
        return new UserBean();
    }

    public UserBean getUserBeanByUsername(String username){
        Connection c = null;
        StringBuilder sql = null;
        UserBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;




        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM user WHERE username=\"");
            sql.append(username);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean = UserManager.getInstance().createUserBean();
                bean.setIduser(rs.getLong("iduser"));
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setLastname(rs.getString("lastname"));
                bean.setHomephone(rs.getString("homephone"));
                bean.setType(rs.getString("type"));
                bean.setPhoto(rs.getString("photo"));
                bean.setEmail(rs.getString("email"));
                bean.setBirthdate(rs.getString("birthdate"));
                bean.setAddress(rs.getString("address"));
                bean.setActivationkey(rs.getString("activationkey"));
                bean.setReg(rs.getString("reg"));
                bean.setActive(rs.getBoolean("active"));

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
        if(bean!=null) return bean;
        return bean;
    }
    public UserBean getUserBeanByID(Long id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        UserBean bean= UserManager.getInstance().createUserBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM user WHERE iduser=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setIduser(rs.getLong("iduser"));
                bean.setUsername(rs.getString("username"));
                bean.setPassword(rs.getString("password"));
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setLastname(rs.getString("lastname"));
                bean.setHomephone(rs.getString("homephone"));
                bean.setType(rs.getString("type"));
                bean.setPhoto(rs.getString("photo"));
                bean.setEmail(rs.getString("email"));
                bean.setBirthdate(rs.getString("birthdate"));
                bean.setAddress(rs.getString("address"));
                bean.setActivationkey(rs.getString("activationkey"));
                bean.setReg(rs.getString("reg"));
                bean.setActive(rs.getBoolean("active"));

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
    public UserBean update(UserBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE user SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append(bean.getActive());
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getReg());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getActivationkey());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[12]);
            sql.append("'");
            sql.append(bean.getPassword());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[11]);
            sql.append("'");
            sql.append(bean.getName());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append("'");
            sql.append(bean.getPhone());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[10]);
            sql.append("'");
            sql.append(bean.getLastname());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getHomephone());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getType());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getPhoto());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getEmail());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append("'");
            sql.append(bean.getAddress());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[9]);
            sql.append("'");
            sql.append(bean.getBirthdate());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[13]);
            sql.append("'");
            sql.append(bean.getUsername());
            sql.append("'");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[14]);
            sql.append(bean.getIduser());
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
    public UserBean insert(UserBean bean) throws DAOException
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
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            ps = null;
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("INSERT into user(");
            sql.append(FIELDS);
            sql.append(") VALUES (");
            sql.append(bean.getActive());
            sql.append(",'");
            sql.append(bean.getReg());
            sql.append("','");
            sql.append(bean.getActivationkey());
            sql.append("','");
            sql.append(bean.getType());
            sql.append("','");
            sql.append(bean.getPhoto());
            sql.append("','");
            sql.append(bean.getEmail());
            sql.append("','");
            sql.append(bean.getHomephone());
            sql.append("','");
            sql.append(bean.getPhone());
            sql.append("','");
            sql.append(bean.getAddress());
            sql.append("','");
            sql.append(bean.getBirthdate());
            sql.append("','");
            sql.append(bean.getLastname());
            sql.append("','");
            sql.append(bean.getName());
            sql.append("','");
            sql.append(bean.getPassword());
            sql.append("','");
            sql.append(bean.getUsername());
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
    public Boolean isUser(String username) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        Boolean result=false;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        ResultSet rs = null;
        try {
            sql.append("SELECT EXISTS(SELECT 1 FROM user WHERE username='");
            sql.append(username);
            sql.append("') AS isUser");
            statement=con.prepareStatement(sql.toString());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while(rs.next()){

                if (rs.getInt("isUser")==1) return true;
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
            sql.append("SELECT EXISTS(SELECT 1 FROM user WHERE email=\"");
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
                if (rs.getInt("isUser")==1) result = true;
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



    public int deleteUserBeanByUsername(String username) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM user WHERE username = '");
            sql.append(username);
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
