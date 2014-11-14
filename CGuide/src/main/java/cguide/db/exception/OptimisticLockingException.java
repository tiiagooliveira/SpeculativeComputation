
package cguide.db.exception;

/**
 * @author sql2java
 */
public class OptimisticLockingException extends DataAccessException
{
    private static final long serialVersionUID = -1348627690415864134L;

	/**
     * contructor
     */
    public OptimisticLockingException()
    {
        super();
    }

    /**
     * contructor
     */
    public OptimisticLockingException(String message)
    {
        super(message);
    }

    /**
     * contructor
     */
    public OptimisticLockingException(Throwable cause)
    {
        super(cause);
    }

    /**
     * contructor
     */
    public OptimisticLockingException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
