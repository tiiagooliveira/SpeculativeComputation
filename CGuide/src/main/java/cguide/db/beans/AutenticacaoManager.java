
package cguide.db.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import cguide.db.exception.DAOException;
import cguide.db.exception.DataAccessException;
import cguide.db.exception.ObjectRetrievalException;

/**
 * Handles database calls (save, load, count, etc...) for the autenticacao table.
 * @author sql2java
 */
public class AutenticacaoManager 
{

    /* set =QUERY for loadUsingTemplate */
    public static final int SEARCH_EXACT = 0;
    /* set %QUERY% for loadLikeTemplate */
    public static final int SEARCH_LIKE = 1;
    /* set %QUERY for loadLikeTemplate */
    public static final int SEARCH_STARTING_LIKE = 2;
    /* set QUERY% for loadLikeTemplate */
    public static final int SEARCH_ENDING_LIKE = 3;

    /**
     * Identify the duracao field.
     */
    public static final int ID_DURACAO = 0;

    /**
     * Identify the auth field.
     */
    public static final int ID_AUTH = 1;

    /**
     * Identify the utilizador_id field.
     */
    public static final int ID_UTILIZADOR_ID = 2;

    /**
     * Identify the id field.
     */
    public static final int ID_ID = 3;

    /**
     * Contains all the full fields of the autenticacao table.
     */
    private static final String[] FULL_FIELD_NAMES =
    {
        "autenticacao.duracao"
        ,"autenticacao.auth"
        ,"autenticacao.utilizador_id"
        ,"autenticacao.id"
    };

    /**
     * Contains all the fields of the autenticacao table.
     */
    public static final String[] FIELD_NAMES =
    {
        "duracao"
        ,"auth"
        ,"utilizador_id"
        ,"id"
    };

    /**
     * Field that contains the comma separated fields of the autenticacao table.
     */
    public static final String ALL_FULL_FIELDS = "autenticacao.duracao"
                            + ",autenticacao.auth"
                            + ",autenticacao.utilizador_id"
                            + ",autenticacao.id";

    /**
     * Field that contains the comma separated fields of the autenticacao table.
     */
    public static final String ALL_FIELDS = "duracao"
                            + ",auth"
                            + ",utilizador_id"
                            + ",id";

    private static AutenticacaoManager singleton = new AutenticacaoManager();

    /**
     * Get the AutenticacaoManager singleton.
     *
     * @return AutenticacaoManager
     */
    public static AutenticacaoManager getInstance()
    {
        return singleton;
    }


    /**
     * Creates a new AutenticacaoBean instance.
     *
     * @return the new AutenticacaoBean
     */
    public AutenticacaoBean createAutenticacaoBean()
    {
        return new AutenticacaoBean();
    }

    //////////////////////////////////////
    // PRIMARY KEY METHODS
    //////////////////////////////////////

    /**
     * Loads a AutenticacaoBean from the autenticacao using its key fields.
     *
     * @param id Integer - PK# 1
     * @return a unique AutenticacaoBean
     * @throws DAOException
     */
    //1
    public AutenticacaoBean loadByPrimaryKey(Integer id) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        try
        {
            c = this.getConnection();
            StringBuilder sql = new StringBuilder("SELECT " + ALL_FIELDS + " FROM autenticacao WHERE id=?");
            ps = c.prepareStatement(sql.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            if (id == null) { ps.setNull(1, Types.INTEGER); } else { Manager.setInteger(ps, 1, id); }
            List<AutenticacaoBean> pReturn = this.loadByPreparedStatementAsList(ps);
            if (pReturn.size() == 0) {
                throw new ObjectRetrievalException();
            } else {
                return pReturn.get(0);
            }
        }
        catch(SQLException e)
        {
            throw new ObjectRetrievalException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
        }
    }

    /**
     * Deletes rows according to its keys.
     *
     * @param id Integer - PK# 1
     * @return the number of deleted rows
     * @throws DAOException
     */
    //2
    public int deleteByPrimaryKey(Integer id) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        try
        {
            c = this.getConnection();
            StringBuilder sql = new StringBuilder("DELETE FROM autenticacao WHERE id=?");
            ps = c.prepareStatement(sql.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            if (id == null) { ps.setNull(1, Types.INTEGER); } else { Manager.setInteger(ps, 1, id); }
            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
        }
    }


    //////////////////////////////////////
    // GET/SET FOREIGN KEY BEAN METHOD
    //////////////////////////////////////
    /**
     * Retrieves the UtilizadorBean object from the autenticacao.utilizador_id field.
     *
     * @param bean the AutenticacaoBean
     * @return the associated UtilizadorBean bean
     * @throws DAOException
     */
//    //3.2 GET IMPORTED VALUES
//    public UserBean getUtilizadorBean(AutenticacaoBean bean) throws DAOException
//    {
//        UserBean other = UserManager.getInstance().createUtilizadorBean();
//        other.setIdtask(bean.getUtilizadorId());
//        bean.setUtilizadorBean(UserManager.getInstance().loadUniqueUsingTemplate(other));
//        return bean.getUtilizadorBean();
//    }

    /**
     * Associates the AutenticacaoBean object to the UtilizadorBean object.
     *
     * @param bean the AutenticacaoBean object to use
     * @param beanToSet the UtilizadorBean object to associate to the AutenticacaoBean
     * @return the associated UtilizadorBean bean
     * @throws Exception
     */
    //4.2 ADD IMPORTED VALUE
    public UserBean addUserBean(UserBean beanToSet, AutenticacaoBean bean) throws Exception
    {
        beanToSet.setIduser(bean.getUtilizadorId());
        return UserManager.getInstance().insert(beanToSet);
    }

    /**
     * Associates the AutenticacaoBean object to the UtilizadorBean object.
     *
     * @param bean the AutenticacaoBean object to use
     * @param beanToSet the UtilizadorBean object to associate to the AutenticacaoBean
     * @return the associated UtilizadorBean bean
     * @throws Exception
     */
    //5.2 SET IMPORTED
//    public UserBean setUtilizadorBean(AutenticacaoBean bean, UserBean beanToSet) throws Exception
//    {
//        bean.setUtilizadorId(beanToSet.getIdtask());
//        return UserManager.getInstance().save(beanToSet);
//    }



    //////////////////////////////////////
    // LOAD ALL
    //////////////////////////////////////

    /**
     * Loads all the rows from autenticacao.
     *
     * @return an array of AutenticacaoManager bean
     * @throws DAOException
     */
    //5
    public AutenticacaoBean[] loadAll() throws DAOException
    {
        return this.loadUsingTemplate(null);
    }

    /**
     * Loads all the rows from autenticacao.
     *
     * @return a list of AutenticacaoManager bean
     * @throws DAOException
     */
    //5
    public List<AutenticacaoBean> loadAllAsList() throws DAOException
    {
        return this.loadUsingTemplateAsList(null);
    }


    /**
     * Loads the given number of rows from autenticacao, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return an array of AutenticacaoManager bean
     * @throws DAOException
     */
    //6
    public AutenticacaoBean[] loadAll(int startRow, int numRows) throws DAOException
    {
        return this.loadUsingTemplate(null, startRow, numRows);
    }

    /**
     * Loads the given number of rows from autenticacao, given the start row.
     *
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return a list of AutenticacaoManager bean
     * @throws DAOException
     */
    //6
    public List<AutenticacaoBean> loadAllAsList(int startRow, int numRows) throws DAOException
    {
        return this.loadUsingTemplateAsList(null, startRow, numRows);
    }

    //////////////////////////////////////
    // SQL 'WHERE' METHOD
    //////////////////////////////////////
    /**
     * Retrieves an array of AutenticacaoBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //7
    public AutenticacaoBean[] loadByWhere(String where) throws DAOException
    {
        return this.loadByWhere(where, null);
    }
    /**
     * Retrieves a list of AutenticacaoBean given a sql 'where' clause.
     *
     * @param where the sql 'where' clause
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //7
    public List<AutenticacaoBean> loadByWhereAsList(String where) throws DAOException
    {
        return this.loadByWhereAsList(where, null);
    }

    /**
     * Retrieves an array of AutenticacaoBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //8
    public AutenticacaoBean[] loadByWhere(String where, int[] fieldList) throws DAOException
    {
        return this.loadByWhere(where, fieldList, 1, -1);
    }


    /**
     * Retrieves a list of AutenticacaoBean given a sql where clause, and a list of fields.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'WHERE' clause
     * @param fieldList array of field's ID
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //8
    public List<AutenticacaoBean> loadByWhereAsList(String where, int[] fieldList) throws DAOException
    {
        return this.loadByWhereAsList(where, fieldList, 1, -1);
    }

    /**
     * Retrieves an array of AutenticacaoBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param fieldList table of the field's associated constants
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //9
    public AutenticacaoBean[] loadByWhere(String where, int[] fieldList, int startRow, int numRows) throws DAOException
    {
        return this.loadByWhereAsList(where, fieldList, startRow, numRows).toArray(new AutenticacaoBean[0]);
    }

    /**
     * Retrieves a list of AutenticacaoBean given a sql where clause and a list of fields, and startRow and numRows.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the sql 'where' clause
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param fieldList table of the field's associated constants
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //9
    public List<AutenticacaoBean> loadByWhereAsList(String where, int[] fieldList, int startRow, int numRows) throws DAOException
    {
        StringBuffer sql = new StringBuffer(128);
        if(fieldList == null) {
            sql.append("SELECT ").append(ALL_FIELDS).append(" FROM autenticacao ").append(where);
        } else
        {
            sql.append("SELECT ");
            for(int i = 0; i < fieldList.length; i++)
            {
                if(i != 0) {
                    sql.append(",");
                }
                sql.append(FULL_FIELD_NAMES[fieldList[i]]);
            }
            sql.append(" FROM autenticacao ");
            sql.append(where);
        }
        Connection c = null;
        Statement st = null;
        ResultSet rs =  null;
        // 
        try
        {
            c = this.getConnection();
            st = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql.toString());
            return this.decodeResultSetAsList(rs, fieldList, startRow, numRows);
        }
        catch(SQLException e)
        {
            throw new ObjectRetrievalException(e);
        }
        finally
        {
            sql = null;
            this.getManager().close(st, rs);
            this.freeConnection(c);
        }
    }


    /**
     * Deletes all rows from autenticacao table.
     * @return the number of deleted rows.
     * @throws DAOException
     */
    //10
    public int deleteAll() throws DAOException
    {
        return this.deleteByWhere("");
    }


    /**
     * Deletes rows from the autenticacao table using a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     * <br>Attention, if 'WHERE' is omitted it will delete all records.
     *
     * @param where the sql 'where' clause
     * @return the number of deleted rows
     * @throws DAOException
     */
    //11
    public int deleteByWhere(String where) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;

        try
        {
            c = this.getConnection();
            StringBuilder sql = new StringBuilder("DELETE FROM autenticacao " + where);
            // 
            ps = c.prepareStatement(sql.toString());
            return ps.executeUpdate();
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
        }
    }

    //_____________________________________________________________________
    //
    // SAVE
    //_____________________________________________________________________
    /**
     * Saves the AutenticacaoBean bean into the database.
     *
     * @param bean the AutenticacaoBean bean to be saved
     * @return the inserted or updated bean
     * @throws DAOException
     */
    //12
    public AutenticacaoBean save(AutenticacaoBean bean) throws DAOException
    {
        if (bean.isNew()) {
            return this.insert(bean);
        } else {
            return this.update(bean);
        }
    }

    /**
     * Insert the AutenticacaoBean bean into the database.
     *
     * @param bean the AutenticacaoBean bean to be saved
     * @return the inserted bean
     * @throws DAOException
     */
    //13
    public AutenticacaoBean insert(AutenticacaoBean bean) throws DAOException
    {
        // mini checks
        if (!bean.isModified()) {
            return bean; // should not we log something ?
        }
        if (!bean.isNew()){
            return this.update(bean);
        }

        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = null;
        StringBuilder values = null;

        try
        {
            c = this.getConnection();
            if (!bean.isIdModified())
            {
//                StringBuilder hint = new StringBuilder("SELECT MAX(id) AS MAX+1 FROM autenticacao");
//                // 
//
//                ps = c.prepareStatement(hint.toString());
//                ResultSet rs = null;
//                try
//                {
//                    rs = ps.executeQuery();
//                    if(rs.next()) {
//                        bean.setIdtask(Manager.getInteger(rs.get, 1));
//                    } else {
//                        this.getManager().log("ATTENTION: Could not retrieve generated key!");
//                    }
//                }
//                finally
//                {
//                    this.getManager().close(ps, rs);
//                    ps=null;
//                }
            }
            this.beforeInsert(bean); // listener callback
            int _dirtyCount = 0;
            sql = new StringBuilder("INSERT into autenticacao (");
            values = new StringBuilder(") values ( ");

            if (bean.isDuracaoModified()) {
                if (_dirtyCount>0) {
                    sql.append(",");
                    values.append(",");
                }
                sql.append("duracao");

                values.append("?");
                _dirtyCount++;
            }

            if (bean.isAuthModified()) {
                if (_dirtyCount>0) {
                    sql.append(",");
                    values.append(",");
                }
                sql.append("auth");

                values.append("?");
                _dirtyCount++;
            }

            if (bean.isUtilizadorIdModified()) {
                if (_dirtyCount>0) {
                    sql.append(",");
                    values.append(",");
                }
                sql.append("utilizador_id");

                values.append("?");
                _dirtyCount++;
            }

            if (bean.isIdModified()) {
                if (_dirtyCount>0) {
                    sql.append(",");
                    values.append(",");
                }
                sql.append("id");

                values.append("?");
                _dirtyCount++;
            }

            sql.append(values.toString());
            sql.append(")");


            // 

            ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            this.fillPreparedStatement(ps, bean, SEARCH_EXACT);

            ps.executeUpdate();

            bean.isNew(false);
            bean.resetIsModified();
            this.afterInsert(bean); // listener callback
            return bean;
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            sql = null;
            this.getManager().close(ps);
            this.freeConnection(c);
        }
    }

    /**
     * Update the AutenticacaoBean bean record in the database according to the changes.
     *
     * @param bean the AutenticacaoBean bean to be updated
     * @return the updated bean
     * @throws DAOException
     */
    //14
    public AutenticacaoBean update(AutenticacaoBean bean) throws DAOException
    {
        // mini checks
        if (!bean.isModified()) {
            return bean; // should not we log something ?
        }
        if (bean.isNew()){
            return this.insert(bean);
        }

        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = null;

        try
        {
            c = this.getConnection();

            this.beforeUpdate(bean); // listener callback
            sql = new StringBuilder("UPDATE autenticacao SET ");
            boolean useComma=false;

            if (bean.isDuracaoModified()) {
                if (useComma) {
                    sql.append(", ");
                } else {
                    useComma=true;
                }
                sql.append("duracao=?");
            }

            if (bean.isAuthModified()) {
                if (useComma) {
                    sql.append(", ");
                } else {
                    useComma=true;
                }
                sql.append("auth=?");
            }

            if (bean.isUtilizadorIdModified()) {
                if (useComma) {
                    sql.append(", ");
                } else {
                    useComma=true;
                }
                sql.append("utilizador_id=?");
            }

            if (bean.isIdModified()) {
                if (useComma) {
                    sql.append(", ");
                } else {
                    useComma=true;
                }
                sql.append("id=?");
            }
            sql.append(" WHERE ");
            sql.append("id=?");
            // 
            ps = c.prepareStatement(sql.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);

            int _dirtyCount = this.fillPreparedStatement(ps, bean, SEARCH_EXACT);

            if (_dirtyCount == 0) {
                // 
                return bean;
            }

            if (bean.getId() == null) { ps.setNull(++_dirtyCount, Types.INTEGER); } else { Manager.setInteger(ps, ++_dirtyCount, bean.getId()); }
            ps.executeUpdate();
            bean.resetIsModified();
            this.afterUpdate(bean); // listener callback

            return bean;
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            sql = null;
            this.getManager().close(ps);
            this.freeConnection(c);
        }
    }

    /**
     * Saves an array of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be saved
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //15
    public AutenticacaoBean[] save(AutenticacaoBean[] beans) throws DAOException
    {
        for (AutenticacaoBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }

    /**
     * Saves a list of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be saved
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //15
    public List<AutenticacaoBean> save(List<AutenticacaoBean> beans) throws DAOException
    {
        for (AutenticacaoBean bean : beans) 
        {
            this.save(bean);
        }
        return beans;
    }

    /**
     * Insert an array of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be inserted
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //16
    public AutenticacaoBean[] insert(AutenticacaoBean[] beans) throws DAOException
    {
        return this.save(beans);
    }

    /**
     * Insert a list of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be inserted
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //16
    public List<AutenticacaoBean> insert(List<AutenticacaoBean> beans) throws DAOException
    {
        return this.save(beans);
    }

    /**
     * Updates an array of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be inserted
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //17
    public AutenticacaoBean[] update(AutenticacaoBean[] beans) throws DAOException
    {
        return this.save(beans);
    }

    /**
     * Updates a list of AutenticacaoBean beans into the database.
     *
     * @param beans the AutenticacaoBean bean table to be inserted
     * @return the saved AutenticacaoBean array.
     * @throws DAOException
     */
    //17
    public List<AutenticacaoBean> update(List<AutenticacaoBean> beans) throws DAOException
    {
        return this.save(beans);
    }
    

    //_____________________________________________________________________
    //
    // USING TEMPLATE
    //_____________________________________________________________________
    /**
     * Loads a unique AutenticacaoBean bean from a template one giving a c
     *
     * @param bean the AutenticacaoBean bean to look for
     * @return the bean matching the template
     * @throws DAOException
     */
    //18
    public AutenticacaoBean loadUniqueUsingTemplate(AutenticacaoBean bean) throws DAOException
    {
         AutenticacaoBean[] beans = this.loadUsingTemplate(bean);
         if (beans.length == 0) {
             return null;
         }
         if (beans.length > 1) {
             throw new ObjectRetrievalException("More than one element !!");
         }
         return beans[0];
     }

    /**
     * Loads an array of AutenticacaoBean from a template one.
     *
     * @param bean the AutenticacaoBean template to look for
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //19
    public AutenticacaoBean[] loadUsingTemplate(AutenticacaoBean bean) throws DAOException
    {
        return this.loadUsingTemplate(bean, 1, -1);
    }

    /**
     * Loads a list of AutenticacaoBean from a template one.
     *
     * @param bean the AutenticacaoBean template to look for
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //19
    public List<AutenticacaoBean> loadUsingTemplateAsList(AutenticacaoBean bean) throws DAOException
    {
        return this.loadUsingTemplateAsList(bean, 1, -1);
    }

    /**
     * Loads an array of AutenticacaoBean from a template one, given the start row and number of rows.
     *
     * @param bean the AutenticacaoBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //20
    public AutenticacaoBean[] loadUsingTemplate(AutenticacaoBean bean, int startRow, int numRows) throws DAOException
    {
        return this.loadUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * Loads a list of AutenticacaoBean from a template one, given the start row and number of rows.
     *
     * @param bean the AutenticacaoBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //20
    public List<AutenticacaoBean> loadUsingTemplateAsList(AutenticacaoBean bean, int startRow, int numRows) throws DAOException
    {
        return this.loadUsingTemplateAsList(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * Loads an array of AutenticacaoBean from a template one, given the start row and number of rows.
     *
     * @param bean the AutenticacaoBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //20
    public AutenticacaoBean[] loadUsingTemplate(AutenticacaoBean bean, int startRow, int numRows, int searchType) throws DAOException
    {
    	return this.loadUsingTemplateAsList(bean, startRow, numRows, searchType).toArray(new AutenticacaoBean[0]);
    }

    /**
     * Loads a list of AutenticacaoBean from a template one, given the start row and number of rows.
     *
     * @param bean the AutenticacaoBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return all the AutenticacaoBean matching the template
     * @throws DAOException
     */
    //20
    public List<AutenticacaoBean> loadUsingTemplateAsList(AutenticacaoBean bean, int startRow, int numRows, int searchType) throws DAOException
    {
        // 
        Connection c = null;
        PreparedStatement ps = null;
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT ").append(ALL_FIELDS).append(" FROM autenticacao ");
        StringBuilder sqlWhere = new StringBuilder("");

        try
        {
            if (this.fillWhere(sqlWhere, bean, searchType) > 0)
            {
                sql.append(" WHERE ").append(sqlWhere);
            }
            else
            {
                // 
            }
            // 

            c = this.getConnection();
            int scrollType = ResultSet.TYPE_SCROLL_INSENSITIVE;
            if (startRow != 1) {
                scrollType = ResultSet.TYPE_SCROLL_SENSITIVE;
            }
            ps = c.prepareStatement(sql.toString(),
                                    scrollType,
                                    ResultSet.CONCUR_READ_ONLY);
            this.fillPreparedStatement(ps, bean, searchType);

            ps.executeQuery();
            return this.loadByPreparedStatementAsList(ps, null, startRow, numRows);
        }
        catch(SQLException e)
        {
            throw new ObjectRetrievalException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
            sql = null;
            sqlWhere = null;
        }
    }

    /**
     * Deletes rows using a AutenticacaoBean template.
     *
     * @param bean the AutenticacaoBean object(s) to be deleted
     * @return the number of deleted objects
     * @throws DAOException
     */
    //21
    public int deleteUsingTemplate(AutenticacaoBean bean) throws DAOException
    {
        if (bean.isIdInitialized()) {
            return this.deleteByPrimaryKey(bean.getId());
        }
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder("DELETE FROM autenticacao ");
        StringBuilder sqlWhere = new StringBuilder("");

        try
        {
            this.beforeDelete(bean); // listener callback
            if (this.fillWhere(sqlWhere, bean, SEARCH_EXACT) > 0)
            {
                sql.append(" WHERE ").append(sqlWhere);
            }
            else
            {
                // 
            }
            // 

            c = this.getConnection();
            ps = c.prepareStatement(sql.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            this.fillPreparedStatement(ps, bean, SEARCH_EXACT);

            int _rows = ps.executeUpdate();
            this.afterDelete(bean); // listener callback
            return _rows;
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
            sql = null;
            sqlWhere = null;
        }
    }


    //_____________________________________________________________________
    //
    // USING INDICES
    //_____________________________________________________________________

    /**
     * Retrieves an array of AutenticacaoBean using the idx_f2fff4c65f015bce index.
     *
     * @param utilizadorId the utilizador_id column's value filter.
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    public AutenticacaoBean[] loadByidx_f2fff4c65f015bce(Long utilizadorId) throws DAOException
    {
    	return this.loadByidx_f2fff4c65f015bceAsList(utilizadorId).toArray(new AutenticacaoBean[0]);
    }
    
    /**
     * Retrieves a list of AutenticacaoBean using the idx_f2fff4c65f015bce index.
     *
     * @param utilizadorId the utilizador_id column's value filter.
     * @return a list of AutenticacaoBean
     * @throws DAOException
     */
    public List<AutenticacaoBean> loadByidx_f2fff4c65f015bceAsList(Long utilizadorId) throws DAOException
    {
        AutenticacaoBean bean = this.createAutenticacaoBean();
        bean.setUtilizadorId(utilizadorId);
        return loadUsingTemplateAsList(bean);
    }
    
    /**
     * Deletes rows using the idx_f2fff4c65f015bce index.
     *
     * @param utilizadorId the utilizador_id column's value filter.
     * @return the number of deleted objects
     * @throws DAOException
     */
    public int deleteByidx_f2fff4c65f015bce(Long utilizadorId) throws DAOException
    {
        AutenticacaoBean bean = this.createAutenticacaoBean();
        bean.setUtilizadorId(utilizadorId);
        return deleteUsingTemplate(bean);
    }
    


    //_____________________________________________________________________
    //
    // COUNT
    //_____________________________________________________________________

    /**
     * Retrieves the number of rows of the table autenticacao.
     *
     * @return the number of rows returned
     * @throws DAOException
     */
    //24
    public int countAll() throws DAOException
    {
        return this.countWhere("");
    }

    /**
     * Retrieves the number of rows of the table autenticacao with a 'where' clause.
     * It is up to you to pass the 'WHERE' in your where clausis.
     *
     * @param where the restriction clause
     * @return the number of rows returned
     * @throws DAOException
     */
    //25
    public int countWhere(String where) throws DAOException
    {
        String sql = "SELECT COUNT(*) AS MCOUNT FROM autenticacao " + where;
        // 
        Connection c = null;
        Statement st = null;
        ResultSet rs =  null;
        try
        {
            int iReturn = -1;
            c = this.getConnection();
            st = c.createStatement();
            rs =  st.executeQuery(sql);
            if (rs.next())
            {
                iReturn = rs.getInt("MCOUNT");
            }
            if (iReturn != -1) {
                return iReturn;
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(st, rs);
            this.freeConnection(c);
            sql = null;
        }
        throw new DataAccessException("Error in countWhere where=[" + where + "]");
    }

    /**
     * Retrieves the number of rows of the table autenticacao with a prepared statement.
     *
     * @param ps the PreparedStatement to be used
     * @return the number of rows returned
     * @throws DAOException
     */
    //26
    private int countByPreparedStatement(PreparedStatement ps) throws DAOException
    {
        ResultSet rs =  null;
        try
        {
            int iReturn = -1;
            rs = ps.executeQuery();
            if (rs.next()) {
                iReturn = rs.getInt("MCOUNT");
            }
            if (iReturn != -1) {
                return iReturn;
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(rs);
        }
       throw new DataAccessException("Error in countByPreparedStatement");
    }

    /**
     * count the number of elements of a specific AutenticacaoBean bean
     *
     * @param bean the AutenticacaoBean bean to look for ant count
     * @return the number of rows returned
     * @throws DAOException
     */
    //27
    public int countUsingTemplate(AutenticacaoBean bean) throws DAOException
    {
        return this.countUsingTemplate(bean, -1, -1);
    }

    /**
     * count the number of elements of a specific AutenticacaoBean bean , given the start row and number of rows.
     *
     * @param bean the AutenticacaoBean template to look for and count
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the number of rows returned
     * @throws DAOException
     */
    //20
    public int countUsingTemplate(AutenticacaoBean bean, int startRow, int numRows) throws DAOException
    {
        return this.countUsingTemplate(bean, startRow, numRows, SEARCH_EXACT);
    }

    /**
     * count the number of elements of a specific AutenticacaoBean bean given the start row and number of rows and the search type
     *
     * @param bean the AutenticacaoBean template to look for
     * @param startRow the start row to be used (first row = 1, last row=-1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param searchType exact ?  like ? starting like ?
     * @return the number of rows returned
     * @throws DAOException
     */
    //20
    public int countUsingTemplate(AutenticacaoBean bean, int startRow, int numRows, int searchType) throws DAOException
    {
        Connection c = null;
        PreparedStatement ps = null;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS MCOUNT FROM autenticacao");
        StringBuilder sqlWhere = new StringBuilder("");

        try
        {
            if (this.fillWhere(sqlWhere, bean, SEARCH_EXACT) > 0)
            {
                sql.append(" WHERE ").append(sqlWhere);
            }
            else
            {
                // 
            }
            // 

            c = this.getConnection();
            ps = c.prepareStatement(sql.toString(),
                                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
            this.fillPreparedStatement(ps, bean, searchType);

            return this.countByPreparedStatement(ps);
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(ps);
            this.freeConnection(c);
            sql = null;
            sqlWhere = null;
        }
    }

    //


    /**
     * fills the given StringBuilder with the sql where clausis constructed using the bean and the search type
     * @param sqlWhere the StringBuilder that will be filled
     * @param bean the bean to use for creating the where clausis
     * @param searchType exact ?  like ? starting like ?
     * @return the number of clausis returned
     */
    protected int fillWhere(StringBuilder sqlWhere, AutenticacaoBean bean, int searchType)
    {
        if (bean == null) {
            return 0;
        }
        int _dirtyCount = 0;
        String sqlEqualsOperation = "=";
        if (searchType != SEARCH_EXACT) {
            sqlEqualsOperation = " like ";
        }
        try
        {
            if (bean.isDuracaoModified()) {
                _dirtyCount ++;
                if (bean.getDuracao() == null) {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("duracao IS NULL");
                } else {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("duracao = ?");
                }
            }
            if (bean.isAuthModified()) {
                _dirtyCount ++;
                if (bean.getAuth() == null) {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("auth IS NULL");
                } else {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("auth ").append(sqlEqualsOperation).append("?");
                }
            }
            if (bean.isUtilizadorIdModified()) {
                _dirtyCount ++;
                if (bean.getUtilizadorId() == null) {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("utilizador_id IS NULL");
                } else {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("utilizador_id = ?");
                }
            }
            if (bean.isIdModified()) {
                _dirtyCount ++;
                if (bean.getId() == null) {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("id IS NULL");
                } else {
                    sqlWhere.append((sqlWhere.length() == 0) ? " " : " AND ").append("id = ?");
                }
            }
        }
        finally
        {
            sqlEqualsOperation = null;
        }
        return _dirtyCount;
    }

    /**
     * fill the given prepared statement with the bean values and a search type
     * @param ps the PreparedStatement that will be filled
     * @param bean the bean to use for creating the where clausis
     * @param searchType exact ?  like ? starting like ?
     * @return the number of clausis returned
     * @throws DAOException
     */
    protected int fillPreparedStatement(PreparedStatement ps, AutenticacaoBean bean, int searchType) throws DAOException
    {
        if (bean == null) {
            return 0;
        }
        int _dirtyCount = 0;
        try
        {
            if (bean.isDuracaoModified()) {
                // 
                if (bean.getDuracao() == null) { ps.setNull(++_dirtyCount, Types.TIMESTAMP); } else { ps.setTimestamp(++_dirtyCount, new Timestamp(bean.getDuracao().getTime())); }
            }
            if (bean.isAuthModified()) {
                switch (searchType) {
                    case SEARCH_EXACT:
                        // 
                        if (bean.getAuth() == null) { ps.setNull(++_dirtyCount, Types.VARCHAR); } else { ps.setString(++_dirtyCount, bean.getAuth()); }
                        break;
                    case SEARCH_LIKE:
                        // 
                        ps.setString(++_dirtyCount, "%" + bean.getAuth() + "%");
                        break;
                    case SEARCH_STARTING_LIKE:
                        // 
                        ps.setString(++_dirtyCount, "%" + bean.getAuth());
                        break;
                    case SEARCH_ENDING_LIKE:
                        // 
                        if (bean.getAuth() + "%" == null) { ps.setNull(++_dirtyCount, Types.VARCHAR); } else { ps.setString(++_dirtyCount, bean.getAuth() + "%"); }
                        break;
                    default:
                        throw new DAOException("Unknown search type " + searchType);
                }
            }
            if (bean.isUtilizadorIdModified()) {
                // 
                if (bean.getUtilizadorId() == null) { ps.setNull(++_dirtyCount, Types.BIGINT); } else { Manager.setLong(ps, ++_dirtyCount, bean.getUtilizadorId()); }
            }
            if (bean.isIdModified()) {
                // 
                if (bean.getId() == null) { ps.setNull(++_dirtyCount, Types.INTEGER); } else { Manager.setInteger(ps, ++_dirtyCount, bean.getId()); }
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        return _dirtyCount;
    }


    //_____________________________________________________________________
    //
    // DECODE RESULT SET
    //_____________________________________________________________________

    /**
     * decode a resultset in an array of AutenticacaoBean objects
     *
     * @param rs the resultset to decode
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //28
    public AutenticacaoBean[] decodeResultSet(ResultSet rs, int[] fieldList, int startRow, int numRows) throws DAOException
    {
    	return this.decodeResultSetAsList(rs, fieldList, startRow, numRows).toArray(new AutenticacaoBean[0]);
    }

    /**
     * decode a resultset in a list of AutenticacaoBean objects
     *
     * @param rs the resultset to decode
     * @param fieldList table of the field's associated constants
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @return the resulting AutenticacaoBean table
     * @throws DAOException
     */
    //28
    public List<AutenticacaoBean> decodeResultSetAsList(ResultSet rs, int[] fieldList, int startRow, int numRows) throws DAOException
    {
        List<AutenticacaoBean> v = new ArrayList<AutenticacaoBean>();
        try
        {
            if (rs.absolute(startRow) && numRows!=0)
            {
                int count = 0;
                if(fieldList == null) {
                    do
                    {
                        v.add(decodeRow(rs));
                        count++;
                    } while ( (count<numRows||numRows<0) && rs.next() );
                }
                else {
                    do
                    {
                        v.add(decodeRow(rs, fieldList));
                        count++;
                    } while ( (count<numRows||numRows<0) && rs.next() );
                }
            }
            return v;
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
    }

    /**
     * Transforms a ResultSet iterating on the autenticacao on a AutenticacaoBean bean.
     *
     * @param rs the ResultSet to be transformed
     * @return bean resulting AutenticacaoBean bean
     * @throws DAOException
     */
    //29
    public AutenticacaoBean decodeRow(ResultSet rs) throws DAOException
    {
        AutenticacaoBean bean = this.createAutenticacaoBean();
        try
        {
            bean.setDuracao(rs.getTimestamp(1));
            bean.setAuth(rs.getString(2));
            bean.setUtilizadorId(Manager.getLong(rs, 3));
            bean.setId(Manager.getInteger(rs, 4));
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        bean.isNew(false);
        bean.resetIsModified();

        return bean;
    }

    /**
     * Transforms a ResultSet iterating on the autenticacao table on a AutenticacaoBean bean according to a list of fields.
     *
     * @param rs the ResultSet to be transformed
     * @param fieldList table of the field's associated constants
     * @return bean resulting AutenticacaoBean bean
     * @throws DAOException
     */
    //30
    public AutenticacaoBean decodeRow(ResultSet rs, int[] fieldList) throws DAOException
    {
        AutenticacaoBean bean = this.createAutenticacaoBean();
        int pos = 0;
        try
        {
            for(int i = 0; i < fieldList.length; i++)
            {
                switch(fieldList[i])
                {
                    case ID_DURACAO:
                        ++pos;
                        bean.setDuracao(rs.getTimestamp(pos));
                        break;
                    case ID_AUTH:
                        ++pos;
                        bean.setAuth(rs.getString(pos));
                        break;
                    case ID_UTILIZADOR_ID:
                        ++pos;
                        bean.setUtilizadorId(Manager.getLong(rs, pos));
                        break;
                    case ID_ID:
                        ++pos;
                        bean.setId(Manager.getInteger(rs, pos));
                        break;
                    default:
                        throw new DAOException("Unknown field id " + fieldList[i]);
                }
            }
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        bean.isNew(false);
        bean.resetIsModified();

        return bean;
    }

    /**
     * Transforms a ResultSet iterating on the autenticacao on a AutenticacaoBean bean using the names of the columns
     *
     * @param rs the ResultSet to be transformed
     * @return bean resulting AutenticacaoBean bean
     * @throws DAOException
     */
    //31
    public AutenticacaoBean metaDataDecodeRow(ResultSet rs) throws DAOException
    {
        AutenticacaoBean bean = this.createAutenticacaoBean();
        try
        {
            bean.setDuracao(rs.getTimestamp("duracao"));
            bean.setAuth(rs.getString("auth"));
            bean.setUtilizadorId(Manager.getLong(rs, "utilizador_id"));
            bean.setId(Manager.getInteger(rs, "id"));
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }

        bean.isNew(false);
        bean.resetIsModified();

        return bean;
    }

    //////////////////////////////////////
    // PREPARED STATEMENT LOADER
    //////////////////////////////////////

    /**
     * Loads all the elements using a prepared statement.
     *
     * @param ps the PreparedStatement to be used
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //32
    public AutenticacaoBean[] loadByPreparedStatement(PreparedStatement ps) throws DAOException
    {
        return this.loadByPreparedStatement(ps, null);
    }

    /**
     * Loads all the elements using a prepared statement.
     *
     * @param ps the PreparedStatement to be used
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //32
    public List<AutenticacaoBean> loadByPreparedStatementAsList(PreparedStatement ps) throws DAOException
    {
        return this.loadByPreparedStatementAsList(ps, null);
    }

    /**
     * Loads all the elements using a prepared statement specifying a list of fields to be retrieved.
     *
     * @param ps the PreparedStatement to be used
     * @param fieldList table of the field's associated constants
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //33
    public AutenticacaoBean[] loadByPreparedStatement(PreparedStatement ps, int[] fieldList) throws DAOException
    {
        return this.loadByPreparedStatementAsList(ps, fieldList).toArray(new AutenticacaoBean[0]);
    }

    /**
     * Loads all the elements using a prepared statement specifying a list of fields to be retrieved.
     *
     * @param ps the PreparedStatement to be used
     * @param fieldList table of the field's associated constants
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //33
    public List<AutenticacaoBean> loadByPreparedStatementAsList(PreparedStatement ps, int[] fieldList) throws DAOException
    {		        
        ResultSet rs =  null;
		List<AutenticacaoBean> v =  null;
        try
        {
            rs =  ps.executeQuery();
            v = new ArrayList<AutenticacaoBean>();
			if(fieldList == null) {
				while(rs.next()) {
					v.add(decodeRow(rs));
				}
			}
			else {
				while(rs.next()) {
					v.add(decodeRow(rs, fieldList));
				}
			}

			return v;
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            getManager().close(rs);
        }
    }

    /**
     * Loads all the elements using a prepared statement specifying a list of fields to be retrieved,
     * and specifying the start row and the number of rows.
     *
     * @param ps the PreparedStatement to be used
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param fieldList table of the field's associated constants
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //34
    public AutenticacaoBean[] loadByPreparedStatement(PreparedStatement ps, int[] fieldList, int startRow, int numRows) throws DAOException
    {
        ResultSet rs =  null;
        try
        {
            rs = ps.executeQuery();
            return this.decodeResultSet(rs, fieldList, startRow, numRows);
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(rs);
        }
    }

    /**
     * Loads all the elements using a prepared statement specifying a list of fields to be retrieved,
     * and specifying the start row and the number of rows.
     *
     * @param ps the PreparedStatement to be used
     * @param startRow the start row to be used (first row = 1, last row = -1)
     * @param numRows the number of rows to be retrieved (all rows = a negative number)
     * @param fieldList table of the field's associated constants
     * @return an array of AutenticacaoBean
     * @throws DAOException
     */
    //34
    public List<AutenticacaoBean> loadByPreparedStatementAsList(PreparedStatement ps, int[] fieldList, int startRow, int numRows) throws DAOException
    {
        ResultSet rs =  null;
        try
        {
            rs = ps.executeQuery();
            return this.decodeResultSetAsList(rs, fieldList, startRow, numRows);
        }
        catch(SQLException e)
        {
            throw new DataAccessException(e);
        }
        finally
        {
            this.getManager().close(rs);
        }
    }

    //_____________________________________________________________________
    //
    // LISTENER
    //_____________________________________________________________________
    private AutenticacaoListener listener = null;

    /**
     * Registers a unique AutenticacaoListener listener.
     */
    //35
    public void registerListener(AutenticacaoListener listener)
    {
        this.listener = listener;
    }

    /**
     * Before the save of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be saved
     */
    //36
    private void beforeInsert(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.beforeInsert(bean);
        }
    }

    /**
     * After the save of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be saved
     */
    //37
    private void afterInsert(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.afterInsert(bean);
        }
    }

    /**
     * Before the update of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be updated
     */
    //38
    private void beforeUpdate(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.beforeUpdate(bean);
        }
    }

    /**
     * After the update of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be updated
     */
    //39
    private void afterUpdate(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.afterUpdate(bean);
        }
    }

    /**
     * Before the delete of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be deleted
     */
    private void beforeDelete(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.beforeDelete(bean);
        }
    }

    /**
     * After the delete of the AutenticacaoBean bean.
     *
     * @param bean the AutenticacaoBean bean to be deleted
     */
    private void afterDelete(AutenticacaoBean bean) throws DAOException
    {
        if (listener != null) {
            listener.afterDelete(bean);
        }
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
