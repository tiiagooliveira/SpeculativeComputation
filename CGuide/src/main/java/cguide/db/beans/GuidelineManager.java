package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cguide.db.entities.Guideline;
import cguide.db.entities.GuidelineList;
import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Guidelinename: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class GuidelineManager {
    public static final String ALL_FIELDS = "authorship"
            + ",guidelinename"
            + ",versionnumber"
            + ",dateofupdate"
            + ",dateofcreation"
            + ",identifier"
            + ",description"
            + ",idguideline";

    public static final String FIELDS = "authorship"
            + ",guidelinename"
            + ",versionnumber"
            + ",dateofupdate"
            + ",dateofcreation"
            + ",identifier"
            + ",description";

    public static final String[] ALL_FIELDS_ARRAY = { "authorship="
            , ",guidelinename="
            , ",versionnumber="
            , ",dateofupdate="
            , ",dateofcreation="
            , ",description="
            , ",identifier="
            , ",idguideline="
    };



    private static GuidelineManager singleton = new GuidelineManager();


    public static GuidelineManager getInstance()
    {
        return singleton;
    }

    public GuidelineBean createGuidelineBean()
    {
        return new GuidelineBean();
    }
    public GuidelineBean getGuidelineBeanByID(String id){
        Connection c = null;
        PreparedStatement statement;
        StringBuilder sql;
        GuidelineBean bean;
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM guideline WHERE idguideline=");
            sql.append(id);
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            bean = GuidelineManager.getInstance().createGuidelineBean();
            while(rs.next()){
                bean.setDateofupdate(rs.getString("dateOfUpdate"));
                bean.setGuidelinename(rs.getString("guidelineName"));
                bean.setAuthorship(rs.getString("authorship"));
                bean.setVersionnumber(rs.getString("versionNumber"));
                bean.setDateofcreation(rs.getString("dateOfCreation"));
                bean.setIdguideline(rs.getLong("idguideline"));
                bean.setIdentifier(rs.getString("identifier"));
                bean.setDescription(rs.getString("description"));
                return bean;
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

    public GuidelineList getGuidelineList(){
        Connection c = null;
        PreparedStatement statement;
        StringBuilder sql;
        GuidelineBean bean;
        GuidelineList guidelineList=null;
        ResultSet rs = null;
        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM guideline");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            guidelineList = GuidelineList.getInstance();
            while(rs.next()){
                bean = GuidelineManager.getInstance().createGuidelineBean();
                bean.setDateofupdate(rs.getString("dateOfUpdate"));
                bean.setGuidelinename(rs.getString("guidelineName"));
                bean.setAuthorship(rs.getString("authorship"));
                bean.setVersionnumber(rs.getString("versionNumber"));
                bean.setDateofcreation(rs.getString("dateOfCreation"));
                bean.setIdguideline(rs.getLong("idguideline"));
                bean.setIdentifier(rs.getString("identifier"));
                bean.setDescription(rs.getString("description"));

                guidelineList.addGuideline(Guideline.fromBean(bean));
            }
            return guidelineList;
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

    public Boolean isGuideline(String identifier) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        Boolean result=false;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        ResultSet rs = null;
        try {
            sql.append("SELECT EXISTS(SELECT 1 FROM guideline WHERE identifier='");
            sql.append(identifier);
            sql.append("') AS isGuideline");
            statement=con.prepareStatement(sql.toString());
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            while(rs.next()){

                if (rs.getInt("isGuideline")==1) return true;
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

    public int deleteGuidelineBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM guideline WHERE idguideline =");
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

    public GuidelineBean insert(GuidelineBean bean) throws DAOException
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
            sql.append("INSERT into guideline(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getAuthorship());
            sql.append("','");
            sql.append(bean.getGuidelinename());
            sql.append("','");
            sql.append(bean.getVersionnumber());
            sql.append("','");
            sql.append(bean.getDateofupdate());
            sql.append("','");
            sql.append(bean.getDateofcreation());
            sql.append("','");
            sql.append(bean.getIdentifier());
            sql.append("','");
            sql.append(bean.getDescription());
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

    public GuidelineBean update(GuidelineBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE guideline SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getAuthorship());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getGuidelinename());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append("'");
            sql.append(bean.getVersionnumber());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getDateofupdate());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getDateofcreation());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getIdentifier());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getDescription());
            sql.append("')");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append(bean.getIdguideline());
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
