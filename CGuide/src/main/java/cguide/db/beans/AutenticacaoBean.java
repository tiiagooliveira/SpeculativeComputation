
package cguide.db.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * AutenticacaoBean is a mapping of autenticacao Table.
 * @author sql2java
*/
public class AutenticacaoBean
    implements Serializable, GeneratedBean
{

	
    private java.util.Date duracao;
    private boolean duracaoIsModified = false;
    private boolean duracaoIsInitialized = false;

    private String auth;
    private boolean authIsModified = false;
    private boolean authIsInitialized = false;

    private Long utilizadorId;
    private boolean utilizadorIdIsModified = false;
    private boolean utilizadorIdIsInitialized = false;

    private Integer id;
    private boolean idIsModified = false;
    private boolean idIsInitialized = false;

    private boolean _isNew = true;

    /**
     * Prefered methods to create a AutenticacaoBean is via the createAutenticacaoBean method in AutenticacaoManager or
     * via the factory class AutenticacaoFactory create method
     */
    protected AutenticacaoBean()
    {
    }

    /**
     * Getter method for duracao.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: autenticacao.duracao</li>
     * <li>column size: 22</li>
     * <li>jdbc type returned by the driver: Types.TIMESTAMP</li>
     * </ul>
     *
     * @return the value of duracao
     */
    public java.util.Date getDuracao()
    {
        return duracao;
    }

    /**
     * Setter method for duracao.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to duracao
     */
    public void setDuracao(java.util.Date newVal)
    {
        if ((newVal != null && duracao != null && (newVal.compareTo(duracao) == 0)) ||
            (newVal == null && duracao == null && duracaoIsInitialized)) {
            return;
        }
        duracao = newVal;
        duracaoIsModified = true;
        duracaoIsInitialized = true;
    }

    /**
     * Setter method for duracao.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to duracao
     */
    public void setDuracao(long newVal)
    {
        setDuracao(new java.util.Date(newVal));
    }

    /**
     * Determines if the duracao has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isDuracaoModified()
    {
        return duracaoIsModified;
    }

    /**
     * Determines if the duracao has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isDuracaoInitialized()
    {
        return duracaoIsInitialized;
    }

    /**
     * Getter method for auth.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: autenticacao.auth</li>
     * <li>column size: 36</li>
     * <li>jdbc type returned by the driver: Types.VARCHAR</li>
     * </ul>
     *
     * @return the value of auth
     */
    public String getAuth()
    {
        return auth;
    }

    /**
     * Setter method for auth.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to auth
     */
    public void setAuth(String newVal)
    {
        if ((newVal != null && auth != null && (newVal.compareTo(auth) == 0)) ||
            (newVal == null && auth == null && authIsInitialized)) {
            return;
        }
        auth = newVal;
        authIsModified = true;
        authIsInitialized = true;
    }

    /**
     * Determines if the auth has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isAuthModified()
    {
        return authIsModified;
    }

    /**
     * Determines if the auth has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isAuthInitialized()
    {
        return authIsInitialized;
    }

    /**
     * Getter method for utilizadorId.
     * <br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: autenticacao.utilizador_id</li>
     * <li> foreign key: utilizador.id</li>
     * <li>column size: 19</li>
     * <li>jdbc type returned by the driver: Types.BIGINT</li>
     * </ul>
     *
     * @return the value of utilizadorId
     */
    public Long getUtilizadorId()
    {
        return utilizadorId;
    }

    /**
     * Setter method for utilizadorId.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to utilizadorId
     */
    public void setUtilizadorId(Long newVal)
    {
        if ((newVal != null && utilizadorId != null && (newVal.compareTo(utilizadorId) == 0)) ||
            (newVal == null && utilizadorId == null && utilizadorIdIsInitialized)) {
            return;
        }
        utilizadorId = newVal;
        utilizadorIdIsModified = true;
        utilizadorIdIsInitialized = true;
    }

    /**
     * Setter method for utilizadorId.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to utilizadorId
     */
    public void setUtilizadorId(long newVal)
    {
        setUtilizadorId(new Long(newVal));
    }

    /**
     * Determines if the utilizadorId has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isUtilizadorIdModified()
    {
        return utilizadorIdIsModified;
    }

    /**
     * Determines if the utilizadorId has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isUtilizadorIdInitialized()
    {
        return utilizadorIdIsInitialized;
    }

    /**
     * Getter method for id.
     * <br>
     * PRIMARY KEY.<br>
     * Meta Data Information (in progress):
     * <ul>
     * <li>full name: autenticacao.id</li>
     * <li>column size: 10</li>
     * <li>jdbc type returned by the driver: Types.INTEGER</li>
     * </ul>
     *
     * @return the value of id
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Setter method for id.
     * <br>
     * The new value is set only if compareTo() says it is different,
     * or if one of either the new value or the current value is null.
     * In case the new value is different, it is set and the field is marked as 'modified'.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(Integer newVal)
    {
        if ((newVal != null && id != null && (newVal.compareTo(id) == 0)) ||
            (newVal == null && id == null && idIsInitialized)) {
            return;
        }
        id = newVal;
        idIsModified = true;
        idIsInitialized = true;
    }

    /**
     * Setter method for id.
     * <br>
     * Convenient for those who do not want to deal with Objects for primary types.
     *
     * @param newVal the new value to be assigned to id
     */
    public void setId(int newVal)
    {
        setId(new Integer(newVal));
    }

    /**
     * Determines if the id has been modified.
     *
     * @return true if the field has been modified, false if the field has not been modified
     */
    public boolean isIdModified()
    {
        return idIsModified;
    }

    /**
     * Determines if the id has been initialized.
     * <br>
     * It is useful to determine if a field is null on purpose or just because it has not been initialized.
     *
     * @return true if the field has been initialized, false otherwise
     */
    public boolean isIdInitialized()
    {
        return idIsInitialized;
    }

    /** The Utilizador referenced by this bean. */
    private UserBean referencedUtilizador;
    /** Getter method for UtilizadorBean. */
    public UserBean getUtilizadorBean() {
        return this.referencedUtilizador;
    }
    /** Setter method for UtilizadorBean. */
    public void setUtilizadorBean(UserBean reference) {
        this.referencedUtilizador = reference;
    }
    
    /**
     * Determines if the current object is new.
     *
     * @return true if the current object is new, false if the object is not new
     */
    @Override
	public boolean isNew()
    {
        return _isNew;
    }

    /**
     * Specifies to the object if it has been set as new.
     *
     * @param isNew the boolean value to be assigned to the isNew field
     */
    public void isNew(boolean isNew)
    {
        this._isNew = isNew;
    }

    /**
     * Determines if the object has been modified since the last time this method was called.
     * <br>
     * We can also determine if this object has ever been modified since its creation.
     *
     * @return true if the object has been modified, false if the object has not been modified
     */
    @Override
	public boolean isModified()
    {
        return duracaoIsModified 		|| authIsModified  		|| utilizadorIdIsModified  		|| idIsModified  ;
    }

    /**
     * Resets the object modification status to 'not modified'.
     */
    @Override
	public void resetIsModified()
    {
        duracaoIsModified = false;
        authIsModified = false;
        utilizadorIdIsModified = false;
        idIsModified = false;
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(AutenticacaoBean bean)
    {
        setDuracao(bean.getDuracao());
        setAuth(bean.getAuth());
        setUtilizadorId(bean.getUtilizadorId());
        setId(bean.getId());
    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> getDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("duracao", getDuracao() == null ? "" : getDuracao().toString());
        dictionnary.put("auth", getAuth() == null ? "" : getAuth().toString());
        dictionnary.put("utilizador_id", getUtilizadorId() == null ? "" : getUtilizadorId().toString());
        dictionnary.put("id", getId() == null ? "" : getId().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> getPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("id", getId() == null ? "" : getId().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    @Override
	public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("duracao".equalsIgnoreCase(column) || "duracao".equalsIgnoreCase(column)) {
            return getDuracao() == null ? "" : getDuracao().toString();
        } else if ("auth".equalsIgnoreCase(column) || "auth".equalsIgnoreCase(column)) {
            return getAuth() == null ? "" : getAuth().toString();
        } else if ("utilizador_id".equalsIgnoreCase(column) || "utilizadorId".equalsIgnoreCase(column)) {
            return getUtilizadorId() == null ? "" : getUtilizadorId().toString();
        } else if ("id".equalsIgnoreCase(column) || "id".equalsIgnoreCase(column)) {
            return getId() == null ? "" : getId().toString();
        }
        return "";
    }

    /**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof AutenticacaoBean)) {
			return false;
		}

		AutenticacaoBean obj = (AutenticacaoBean) object;
		return new EqualsBuilder()
            .append(getDuracao(), obj.getDuracao())
            .append(getAuth(), obj.getAuth())
            .append(getUtilizadorId(), obj.getUtilizadorId())
            .append(getId(), obj.getId())
            .isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getDuracao())
            .append(getAuth())
            .append(getUtilizadorId())
            .append(getId())
            .toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString()
	{
	    return toString(ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * you can use the following styles:
	 * <li>ToStringStyle.DEFAULT_STYLE</li>
	 * <li>ToStringStyle.MULTI_LINE_STYLE</li>
     * <li>ToStringStyle.NO_FIELD_NAMES_STYLE</li>
     * <li>ToStringStyle.SHORT_PREFIX_STYLE</li>
     * <li>ToStringStyle.SIMPLE_STYLE</li>
	 */
	public String toString(ToStringStyle style) {
		return new ToStringBuilder(this, style)
            .append("duracao", getDuracao())
            .append("auth", getAuth())
            .append("utilizador_id", getUtilizadorId())
            .append("id", getId())
            .toString();
	}


    public int compareTo(Object object)
    {
        AutenticacaoBean obj = (AutenticacaoBean) object;
        return new CompareToBuilder()
            .append(getDuracao(), obj.getDuracao())
            .append(getAuth(), obj.getAuth())
            .append(getUtilizadorId(), obj.getUtilizadorId())
            .append(getId(), obj.getId())
            .toComparison();
   }
}
