
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
 * PatientBean is a mapping of patient Table.
 * @author Tiago
 */
public class PatientBean
        implements Serializable
{

    private Long idpatient;
    private String nutente;
    private String name;
    private String lastname;
    private String birthdate;
    private String address;
    private String phone;
    private String homephone;
    private String email;
    private String type;
    private String time;

    /**
     * Prefered methods to create a PatientBean is via the createUtilizadorBean method in UtilizadorManager or
     * via the factory class UtilizadorFactory create method
     */
    protected PatientBean()
    {
    }
    //GETTERS AND SETTERS

    public Long getIdpatient(){
        return this.idpatient;
    }
    public void setIdpatient(Long idpatient){
        this.idpatient = idpatient;
    }
    public String getNutente(){
        return this.nutente;
    }
    public void setNutente(String nutente){
        this.nutente = nutente;
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


    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public PatientBean createPatientBean()
    {
        return new PatientBean();
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(PatientBean bean)
    {
        setPhone(bean.getPhone());
        setHomephone(bean.getHomephone());
        setLastname(bean.getLastname());
        setBirthdate(bean.getBirthdate());
        setNutente(bean.getNutente());
        setType(bean.getType());
        setEmail(bean.getEmail());
        setName(bean.getName());
        setIdpatient(bean.getIdpatient());
        setAddress(bean.getAddress());
        setBirthdate(bean.getBirthdate());
        setTime(bean.getTime());
    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> getDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("time", getTime()==null? "" : getTime().toString());
        dictionnary.put("type", getType() == null ? "" : getType());
        dictionnary.put("email", getEmail() == null ? "" : getEmail());
        dictionnary.put("homephone", getHomephone() == null ? "" : getHomephone());
        dictionnary.put("phone", getPhone() == null ? "" : getPhone());
        dictionnary.put("address", getAddress() == null ? "" : getAddress());
        dictionnary.put("birthdate", getBirthdate() == null ? "" : getBirthdate());
        dictionnary.put("lastname", getLastname() == null ? "" : getLastname());
        dictionnary.put("name", getName() == null ? "" : getName());
        dictionnary.put("nutente", getNutente() == null ? "" : getNutente());
        dictionnary.put("idpatient", getIdpatient() == null ? "" : getIdpatient().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> getPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("idpatient", getIdpatient() == null ? "" : getIdpatient().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("type".equalsIgnoreCase(column) || "type".equalsIgnoreCase(column)) {
            return getType() == null ? "" : getType().toString();
        } else if ("email".equalsIgnoreCase(column) || "email".equalsIgnoreCase(column)) {
            return getEmail() == null ? "" : getEmail().toString();
        } else if ("homephone".equalsIgnoreCase(column) || "homephone".equalsIgnoreCase(column)) {
            return getHomephone() == null ? "" : getHomephone().toString();
        } else  if ("phone".equalsIgnoreCase(column) || "phone".equalsIgnoreCase(column)) {
            return getPhone() == null ? "" : getPhone().toString();
        } else if ("birthdate".equalsIgnoreCase(column) || "birthdate".equalsIgnoreCase(column)) {
            return getBirthdate() == null ? "" : getBirthdate().toString();
        } else if ("lastname".equalsIgnoreCase(column) || "lastname".equalsIgnoreCase(column)) {
            return getLastname() == null ? "" : getLastname().toString();
        }  else if ("nutente".equalsIgnoreCase(column) || "nutente".equalsIgnoreCase(column)) {
            return getName() == null ? "" : getName().toString();
        } else if ("idpatient".equalsIgnoreCase(column) || "idpatient".equalsIgnoreCase(column)) {
            return getIdpatient() == null ? "" : getIdpatient().toString();
        }
        return "";
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof PatientBean)) {
            return false;
        }

        PatientBean obj = (PatientBean) object;
        return new EqualsBuilder()
                .append(getType(), obj.getType())
                .append(getEmail(), obj.getEmail())
                .append(getHomephone(), obj.getHomephone())
                .append(getPhone(), obj.getPhone())
                .append(getAddress(), obj.getAddress())
                .append(getBirthdate(), obj.getBirthdate())
                .append(getLastname(), obj.getLastname())
                .append(getName(), obj.getName())
                .append(getNutente(),obj.getNutente())
                .append(getIdpatient(), obj.getIdpatient())
                .isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getType())
                .append(getEmail())
                .append(getHomephone())
                .append(getPhone())
                .append(getAddress())
                .append(getBirthdate())
                .append(getLastname())
                .append(getName())
                .append(getNutente())
                .append(getIdpatient())
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
                .append("type", getType())
                .append("email", getEmail())
                .append("homephone", getHomephone())
                .append("phone", getPhone())
                .append("address", getAddress())
                .append("birthdate", getBirthdate())
                .append("lastname", getLastname())
                .append("name", getName())
                .append("nutente", getNutente())
                .append("idpatient", getIdpatient())
                .toString();
    }


    public int compareTo(Object object)
    {
        PatientBean obj = (PatientBean) object;
        return new CompareToBuilder()
                .append(getType(), obj.getType())
                .append(getEmail(), obj.getEmail())
                .append(getHomephone(), obj.getHomephone())
                .append(getPhone(), obj.getPhone())
                .append(getAddress(), obj.getAddress())
                .append(getBirthdate(), obj.getBirthdate())
                .append(getLastname(), obj.getLastname())
                .append(getName(), obj.getName())
                .append(getNutente(), obj.getNutente())
                .append(getIdpatient(), obj.getIdpatient())
                .toComparison();
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
