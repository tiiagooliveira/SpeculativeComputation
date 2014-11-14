
package cguide.db.exception;

/**
 * @author sql2java
 */
public class DataAccessException extends DAOException
{
    private static final long serialVersionUID = 5584785072347143333L;

    /**
     * contructor
     */
    public DataAccessException()
    {
        super();
    }

    /**
     * contructor
     */
    public DataAccessException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public DataAccessException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public DataAccessException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
