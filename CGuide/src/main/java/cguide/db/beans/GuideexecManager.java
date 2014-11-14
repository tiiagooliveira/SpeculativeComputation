package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cguide.db.entities.Guideexec;
import cguide.db.entities.GuideexecList;
import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;

import com.mysql.jdbc.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Idguideline: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class GuideexecManager {
    public static final String ALL_FIELDS = "time"
            + ",start"
            + ",completed"
            + ",description"
            + ",nextTasks"
            + ",idguideline"
            + ",iduser"
            + ",idpatient"
            + ",idguideexec";

    public static final String FIELDS = "time"
            + ",start"
            + ",completed"
            + ",description"
            + ",nextTasks"
            + ",idguideline"
            + ",iduser"
            + ",idpatient";

    public static final String[] ALL_FIELDS_ARRAY = { ",time="
            , ",start="
            , ",completed="
            , ",nextTasks="
            , ",description="
            , ",idguideline="
            , ",iduser="
            , ",idpatient="
            , "idguideexec="
    };



    private static GuideexecManager singleton = new GuideexecManager();


    public static GuideexecManager getInstance()
    {
        return singleton;
    }

    public GuideexecBean createGuideexecBean()
    {
        return new GuideexecBean();
    }
    public GuideexecBean getGuideexecBeanByID(String id){
        Connection c = null;
        PreparedStatement statement = null;
        StringBuilder sql = null;
        GuideexecBean bean= GuideexecManager.getInstance().createGuideexecBean();
        ResultSet rs = null;


        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM guideexec WHERE idguideexec=");
            sql.append(id);
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean.setTime(rs.getString("time"));
                bean.setIdguideline(rs.getLong("idguideline"));
                bean.setIdguideexec(rs.getLong("idguideexec"));
                bean.setIduser(rs.getLong("iduser"));
                bean.setIdpatient(rs.getLong("idpatient"));
                bean.setCompleted(rs.getBoolean("completed"));
                bean.setNextTasks(rs.getString("nextTasks"));
                bean.setDescription(rs.getString("description"));
                bean.setStart(rs.getString("start"));
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

    public int deleteGuideexecBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM guideexec WHERE idguideexec =");
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

    public Integer insert(GuideexecBean bean) throws DAOException
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
            sql.append("INSERT into guideexec(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getTime());
            sql.append("','");
            sql.append(bean.getStart());
            sql.append("',");
            sql.append(bean.getCompleted());
            sql.append(",'");
            sql.append(bean.getDescription());
            sql.append("','");
            sql.append(bean.getNextTasks());
            sql.append("','");
            sql.append(bean.getIdguideline());
            sql.append("','");
            sql.append(bean.getIduser());
            sql.append("','");
            sql.append(bean.getIdpatient());
            sql.append("')");
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

    public GuideexecList getGuidelineListActive(String user){
        Connection c = null;
        PreparedStatement statement;
        StringBuilder sql;
        GuideexecBean bean;
        GuideexecList guidelineList=null;
        ResultSet rs = null;
        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM guideexec WHERE completed="+Boolean.FALSE+" AND iduser="+user+" ORDER BY idguideexec DESC");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            guidelineList = GuideexecList.getInstance();
            while(rs.next()){
                bean = GuideexecManager.getInstance().createGuideexecBean();
                bean.setIdguideexec(rs.getLong("idguideexec"));
                bean.setIdguideline(rs.getLong("idguideline"));
                bean.setIdpatient(rs.getLong("idpatient"));
                bean.setIduser(rs.getLong("iduser"));
                bean.setTime(rs.getString("time"));
                bean.setStart(rs.getString("start"));
                bean.setCompleted(rs.getBoolean("completed"));
                bean.setNextTasks(rs.getString("nextTasks"));
                bean.setDescription(rs.getString("description"));
                guidelineList.addGuideexec(Guideexec.fromBean(bean));
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

    public GuideexecBean update(GuideexecBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            // bean= UserManager.getInstance().getUserBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());


            sql.append("UPDATE guideexec SET ");
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append(bean.getIdguideexec());
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append(bean.getIdguideline());
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append(bean.getIduser());
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append(bean.getIdpatient());
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getTime());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getDescription());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[1]);
            sql.append("'");
            sql.append(bean.getStart());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[3]);
            sql.append("'");
            sql.append(bean.getNextTasks());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[2]);
            sql.append(bean.getCompleted());
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[8]);
            sql.append(bean.getIdguideexec());

            System.out.println(sql.toString());
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
