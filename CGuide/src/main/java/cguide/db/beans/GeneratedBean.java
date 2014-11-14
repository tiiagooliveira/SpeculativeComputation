
package cguide.db.beans;

/**
 * @author sql2java
 * @version $Revision: 1.3 $
 */
public interface GeneratedBean {

    public boolean isNew();
    public boolean isModified();
    public void resetIsModified();
    public String getValue(String column);
}
