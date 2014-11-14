
package cguide.db.exception;

/**
 * @author sql2java
 */
public class DataRetrievalException extends DataAccessException
{
    private static final long serialVersionUID = -1628427750056759044L;

	/**
     * contructor
     */
    public DataRetrievalException()
    {
        super();
    }

    /**
     * contructor
     */
    public DataRetrievalException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public DataRetrievalException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public DataRetrievalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
