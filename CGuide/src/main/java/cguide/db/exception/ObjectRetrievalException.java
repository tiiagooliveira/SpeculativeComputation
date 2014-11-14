
package cguide.db.exception;

/**
 * @author sql2java
 */
public class ObjectRetrievalException extends DataRetrievalException
{
    private static final long serialVersionUID = -3197505872331833160L;

	/**
     * contructor
     */
    public ObjectRetrievalException()
    {
        super();
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public ObjectRetrievalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
