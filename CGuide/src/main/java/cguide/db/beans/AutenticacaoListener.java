
package cguide.db.beans;


import cguide.db.exception.DAOException;

/**
 * Listener that is notified of autenticacao table changes.
 * @author sql2java
 */
public interface AutenticacaoListener
{
    /**
     * Invoked just before inserting a AutenticacaoBean record into the database.
     *
     * @param bean the AutenticacaoBean that is about to be inserted
     */
    public void beforeInsert(AutenticacaoBean bean) throws DAOException;


    /**
     * Invoked just after a AutenticacaoBean record is inserted in the database.
     *
     * @param bean the AutenticacaoBean that was just inserted
     */
    public void afterInsert(AutenticacaoBean bean) throws DAOException;


    /**
     * Invoked just before updating a AutenticacaoBean record in the database.
     *
     * @param bean the AutenticacaoBean that is about to be updated
     */
    public void beforeUpdate(AutenticacaoBean bean) throws DAOException;


    /**
     * Invoked just after updating a AutenticacaoBean record in the database.
     *
     * @param bean the AutenticacaoBean that was just updated
     */
    public void afterUpdate(AutenticacaoBean bean) throws DAOException;


    /**
     * Invoked just before deleting a AutenticacaoBean record in the database.
     *
     * @param bean the AutenticacaoBean that is about to be deleted
     */
    public void beforeDelete(AutenticacaoBean bean) throws DAOException;


    /**
     * Invoked just after deleting a AutenticacaoBean record in the database.
     *
     * @param bean the AutenticacaoBean that was just deleted
     */
    public void afterDelete(AutenticacaoBean bean) throws DAOException;

}
