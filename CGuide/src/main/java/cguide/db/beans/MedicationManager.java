
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
 * Handles database calls for the medication table.
 * @author tiago
 */
public class MedicationManager
{
    public static final String ALL_FIELDS = "time"
            + ",idtask"
            + ",description"
            + ",activeingredient"
            + ",dosage"
            + ",pharmaceuticalform"
            + ",posology"
            + ",unit"
            + ",idmedication";
    public static final String FIELDS ="time"
            + ",idtask"
            + ",description"
            + ",activeingredient"
            + ",dosage"
            + ",pharmaceuticalform"
            + ",posology"
            + ",identifier";
    public static final String[] ALL_FIELDS_ARRAY = { "time="
            , ",idtask="
            , ",description="
            , ",activeingredient="
            , ",dosage="
            , ",pharmaceuticalform="
            , ",posology="
            , ",unit="
            , "idmedication="
    };
    private static MedicationManager singleton = new MedicationManager();

    /**
     * Get the UtilizadorManager singleton.
     *
     * @return UtilizadorManager
     */
    public static MedicationManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public MedicationBean createMedicationBean()
    {
        return new MedicationBean();
    }

    public MedicationBean getMedicationBeanById(String id){
        Connection c = null;
        StringBuilder sql = null;
        MedicationBean bean= null;
        ResultSet rs=null;
        PreparedStatement statement = null;




        try {
            c = this.getConnection();
            sql = new StringBuilder();
            sql.append("SELECT * FROM medication WHERE idmedication=\"");
            sql.append(id);
            sql.append("\"");
            statement=c.prepareStatement(sql.toString());
            rs = statement.executeQuery();
            while(rs.next()){
                bean = MedicationManager.getInstance().createMedicationBean();
                bean.setIdmedication(rs.getLong("idmedication"));
                bean.setDosage(rs.getString("dosage"));
                bean.setActiveingredient(rs.getString("activeingredient"));
                bean.setDescription(rs.getString("description"));
                bean.setTime(rs.getString("time"));
                bean.setPharmaceuticalform(rs.getString("pharmaceuticalform"));
                bean.setPosology(rs.getString("posology"));
                bean.setIdtask(rs.getLong("idtask"));
                bean.setName(rs.getString("name"));
                bean.setIdentifier(rs.getString("identifier"));
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
    public MedicationBean update(MedicationBean bean) throws DAOException {



        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder();

        try
        {
            c = this.getConnection();
            sql = new StringBuilder();
            // bean= MedicationManager.getInstance().getMedicationBeanByID(4L);
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("UPDATE medication SET ");
            sql.append(ALL_FIELDS_ARRAY[0]);
            sql.append("'");
            sql.append(bean.getTime());
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
            sql.append(bean.getActiveingredient());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[4]);
            sql.append("'");
            sql.append(bean.getDosage());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[5]);
            sql.append("'");
            sql.append(bean.getPharmaceuticalform());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[6]);
            sql.append("'");
            sql.append(bean.getPosology());
            sql.append("'");
            sql.append(ALL_FIELDS_ARRAY[7]);
            sql.append("'");
            sql.append(bean.getPosology());
            sql.append("'");
            sql.append(" WHERE ");
            sql.append(ALL_FIELDS_ARRAY[9]);
            sql.append(bean.getIdmedication());
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
            sql.append("FROM medication ");
            sql.append("INNER JOIN guideexec ");
            sql.append("INNER JOIN parser ");
            sql.append("INNER JOIN patient ");
            sql.append("INNER JOIN user ");
            sql.append("ON parser.idguideline=guideexec.idguideline AND medication.idtask=guideexec.idtask ");
            sql.append("AND user.iduser = guideexec.iduser AND patient.idpatient = guideexec.idpatient ");
            sql.append("WHERE idmedication=");
            sql.append(id);
            sql.append(" ");
            sql.append("ORDER BY medication.idmedication");


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
     * @throws DAOException
     */
    //14
    public MedicationBean insert(MedicationBean bean) throws DAOException
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
            // bean= MedicationManager.getInstance().getMedicationBeanByID(4L);
            ps = null;
            //     ResultSet rs = statement.executeQuery(sql.toString());
            sql.append("INSERT into medication(");
            sql.append(FIELDS);
            sql.append(") VALUES ('");
            sql.append(bean.getTime());
            sql.append("',");
            sql.append(bean.getIdtask());
            sql.append(",'");
            sql.append(bean.getDescription());
            sql.append("','");
            sql.append(bean.getActiveingredient());
            sql.append("','");
            sql.append(bean.getDosage());
            sql.append("','");
            sql.append(bean.getPharmaceuticalform());
            sql.append("','");
            sql.append(bean.getPosology());
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



    public int deleteMedicationBeanById(Long id) throws DAOException {
        PreparedStatement statement = null;
        StringBuilder sql = new StringBuilder();
        Connection con = null;
        int result=0;

        //registering the jdbc driver here, your string to use
        //here depends on what driver you are using.
        con = this.getConnection();
        try {
            sql.append("DELETE FROM medication WHERE idmedication = '");
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
