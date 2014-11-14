
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
 * UserBean is a mapping of user Table.
 * @author Tiago
*/
public class UserBean
    implements Serializable
{

    private Long iduser;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String birthdate;
    private String address;
    private String phone;
    private String homephone;
    private String email;
    private String photo;
    private String type;
    private String activationkey;
    private String reg;
    private Boolean active;

    /**
     * Prefered methods to create a UserBean is via the createUtilizadorBean method in UtilizadorManager or
     * via the factory class UtilizadorFactory create method
     */
    protected UserBean()
    {
    }
    //GETTERS AND SETTERS

    public Long getIduser(){
        return this.iduser;
    }
    public void setIduser(Long iduser){
        this.iduser = iduser;
    }
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomephone() {
        return homephone;
    }

    public void setHomephone(String homephone) {
        this.homephone = homephone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActivationkey() {
        return activationkey;
    }

    public void setActivationkey(String activationkey) {
        this.activationkey = activationkey;
    }


    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }



    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public UserBean createUserBean()
    {
        return new UserBean();
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(UserBean bean)
    {
        setPhone(bean.getPhone());
        setHomephone(bean.getHomephone());
        setLastname(bean.getLastname());
        setBirthdate(bean.getBirthdate());
        setUsername(bean.getUsername());
        setType(bean.getType());
        setPhoto(bean.getPhoto());
        setEmail(bean.getEmail());
        setPassword(bean.getPassword());
        setName(bean.getName());
        setIduser(bean.getIduser());
        setAddress(bean.getAddress());
        setBirthdate(bean.getBirthdate());
        setActivationkey(bean.getActivationkey());
        setActive(bean.getActive());
        setReg(bean.getReg());
    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> getDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("active", getActive()==null? "" : getActive().toString());
        dictionnary.put("activationkey", getActivationkey()==null? "": getActivationkey());
        dictionnary.put("type", getType() == null ? "" : getType());
        dictionnary.put("photo", getPhoto() == null ? "" : getPhoto());
        dictionnary.put("email", getEmail() == null ? "" : getEmail());
        dictionnary.put("homephone", getHomephone() == null ? "" : getHomephone());
        dictionnary.put("phone", getPhone() == null ? "" : getPhone());
        dictionnary.put("address", getAddress() == null ? "" : getAddress());
        dictionnary.put("birthdate", getBirthdate() == null ? "" : getBirthdate());
        dictionnary.put("lastname", getLastname() == null ? "" : getLastname());
        dictionnary.put("name", getName() == null ? "" : getName());
        dictionnary.put("password", getPassword() == null ? "" : getPassword());
        dictionnary.put("username", getUsername() == null ? "" : getUsername());
        dictionnary.put("iduser", getIduser() == null ? "" : getIduser().toString());
        dictionnary.put("reg",getIduser()==null ? "" : getReg().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> getPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("iduser", getIduser() == null ? "" : getIduser().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("active".equalsIgnoreCase(column) || "active".equalsIgnoreCase(column)) {
            return getActive() == null ? "" : getActive().toString();
        }else if ("activationkey".equalsIgnoreCase(column) || "activationkey".equalsIgnoreCase(column)) {
            return getActivationkey() == null ? "" : getActivationkey().toString();
        } else if ("type".equalsIgnoreCase(column) || "type".equalsIgnoreCase(column)) {
            return getType() == null ? "" : getType().toString();
        } else if ("photo".equalsIgnoreCase(column) || "photo".equalsIgnoreCase(column)) {
            return getPhoto() == null ? "" : getPhoto().toString();
        } else if ("email".equalsIgnoreCase(column) || "email".equalsIgnoreCase(column)) {
            return getEmail() == null ? "" : getEmail().toString();
        } else if ("homephone".equalsIgnoreCase(column) || "homephone".equalsIgnoreCase(column)) {
            return getHomephone() == null ? "" : getHomephone().toString();
        } else  if ("phone".equalsIgnoreCase(column) || "phone".equalsIgnoreCase(column)) {
            return getPhone() == null ? "" : getPhone().toString();
        } else if ("address".equalsIgnoreCase(column) || "address".equalsIgnoreCase(column)) {
            return getAddress() == null ? "" : getAddress().toString();
        } else if ("birthdate".equalsIgnoreCase(column) || "birthdate".equalsIgnoreCase(column)) {
            return getBirthdate() == null ? "" : getBirthdate().toString();
        } else if ("lastname".equalsIgnoreCase(column) || "lastname".equalsIgnoreCase(column)) {
            return getLastname() == null ? "" : getLastname().toString();
        } else if ("name".equalsIgnoreCase(column) || "name".equalsIgnoreCase(column)) {
            return getName() == null ? "" : getName().toString();
        } else if ("password".equalsIgnoreCase(column) || "password".equalsIgnoreCase(column)) {
            return getPassword() == null ? "" : getPassword().toString();
        } else if ("username".equalsIgnoreCase(column) || "username".equalsIgnoreCase(column)) {
            return getUsername() == null ? "" : getUsername().toString();
        } else if ("iduser".equalsIgnoreCase(column) || "iduser".equalsIgnoreCase(column)) {
            return getIduser() == null ? "" : getIduser().toString();
        } else if ("reg".equalsIgnoreCase(column) || "reg".equalsIgnoreCase(column)) {
            return getReg() == null ? "" : getReg().toString();
        }
        return "";
    }

    /**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof UserBean)) {
			return false;
		}

		UserBean obj = (UserBean) object;
		return new EqualsBuilder()
            .append(getActive(),obj.getActive())
            .append(getActivationkey(),obj.getActivationkey())
            .append(getType(), obj.getType())
            .append(getPhoto(), obj.getPhoto())
            .append(getEmail(), obj.getEmail())
            .append(getHomephone(), obj.getHomephone())
            .append(getPhone(), obj.getPhone())
            .append(getAddress(), obj.getAddress())
            .append(getBirthdate(), obj.getBirthdate())
            .append(getLastname(), obj.getLastname())
            .append(getName(), obj.getName())
            .append(getPassword(), obj.getPassword())
            .append(getUsername(),obj.getUsername())
            .append(getIduser(), obj.getIduser())
            .append(getReg(),obj.getReg())
            .isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(-82280557, -700257973)
            .append(getActive())
            .append(getActivationkey())
            .append(getType())
            .append(getPhoto())
            .append(getEmail())
            .append(getHomephone())
            .append(getPhone())
            .append(getAddress())
            .append(getBirthdate())
            .append(getLastname())
            .append(getName())
            .append(getPassword())
            .append(getUsername())
            .append(getIduser())
            .append(getReg())
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
            .append("active", getActive())
            .append("activationkey",getActivationkey())
            .append("type", getType())
            .append("photo", getPhoto())
            .append("email", getEmail())
            .append("homephone", getHomephone())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("birthdate", getBirthdate())
            .append("lastname", getLastname())
            .append("name", getName())
            .append("password", getPassword())
            .append("username", getUsername())
            .append("iduser", getIduser())
            .append("reg", getReg())
            .toString();
	}


    public int compareTo(Object object)
    {
        UserBean obj = (UserBean) object;
        return new CompareToBuilder()
                .append(getActive(), obj.getActive())
                .append(getActivationkey(),obj.getActivationkey())
                .append(getType(), obj.getType())
                .append(getPhoto(), obj.getPhoto())
                .append(getEmail(), obj.getEmail())
                .append(getHomephone(), obj.getHomephone())
                .append(getPhone(), obj.getPhone())
                .append(getAddress(), obj.getAddress())
                .append(getBirthdate(), obj.getBirthdate())
                .append(getLastname(), obj.getLastname())
                .append(getName(), obj.getName())
                .append(getPassword(), obj.getPassword())
                .append(getUsername(), obj.getUsername())
                .append(getIduser(), obj.getIduser())
                .append(getReg(),obj.getReg())
            .toComparison();
   }

}
