
package cguide.db.exception;

import java.sql.SQLException;

/**
 * @author sql2java
 */
public class DAOException extends SQLException
{
    private static final long serialVersionUID = 5165438223391151142L;

    /**
     * contructor
     */
    public DAOException()
    {
        super();
    }

    /**
     * contructor
     */
    public DAOException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public DAOException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public DAOException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
