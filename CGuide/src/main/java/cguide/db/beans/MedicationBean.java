
package cguide.db.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * MedicationBean is a mapping of medication Table.
 * @author Tiago
 */
public class MedicationBean
        implements Serializable
{

    private Long idmedication;
    private String name;
    private String activeingredient;
    private String dosage;
    private String pharmaceuticalform;
    private String posology;
    private String description;
    private String time;
    private Long idtask;
    private String identifier;

    /**
     * Prefered methods to create a MedicationBean is via the createUtilizadorBean method in UtilizadorManager or
     * via the factory class UtilizadorFactory create method
     */
    protected MedicationBean()
    {
    }
    //GETTERS AND SETTERS

    public Long getIdmedication(){
        return this.idmedication;
    }
    public void setIdmedication(Long idmedication){
        this.idmedication = idmedication;
    }


    public String getActiveingredient() {
        return activeingredient;
    }

    public void setActiveingredient(String activeingredient) {
        this.activeingredient = activeingredient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPharmaceuticalform() {
        return pharmaceuticalform;
    }

    public void setPharmaceuticalform(String pharmaceuticalform) {
        this.pharmaceuticalform = pharmaceuticalform;
    }

    public String getPosology() {
        return posology;
    }

    public void setPosology(String posology) {
        this.posology = posology;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdtask() {
        return idtask;
    }

    public void setIdtask(Long idtask) {
        this.idtask = idtask;
    }

    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public MedicationBean createMedicationBean()
    {
        return new MedicationBean();
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(MedicationBean bean)
    {
        setPosology(bean.getPosology());
        setDescription(bean.getDescription());
        setActiveingredient(bean.getActiveingredient());
        setDosage(bean.getDosage());
        setName(bean.getName());
        setIdtask(bean.getIdtask());
        setTime(bean.getTime());
        setIdmedication(bean.getIdmedication());
        setPharmaceuticalform(bean.getPharmaceuticalform());
        setPharmaceuticalform(bean.getDosage());
        setIdentifier(bean.getIdentifier());

    }

    /**
     * @see Object#equals(Object)
     */
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof MedicationBean)) {
            return false;
        }

        MedicationBean obj = (MedicationBean) object;
        return new EqualsBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getDescription(), obj.getDescription())
                .append(getPosology(), obj.getPosology())
                .append(getPharmaceuticalform(), obj.getPharmaceuticalform())
                .append(getDosage(), obj.getDosage())
                .append(getActiveingredient(), obj.getActiveingredient())
                .append(getName(), obj.getName())
                .append(getIdmedication(), obj.getIdmedication())
                .append(getIdentifier(),obj.getIdentifier())
                .isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getIdtask())
                .append(getTime())
                .append(getDescription())
                .append(getPosology())
                .append(getPharmaceuticalform())
                .append(getDosage())
                .append(getActiveingredient())
                .append(getName())
                .append(getIdmedication())
                .append(getIdentifier())
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
                .append("idtask", getIdtask())
                .append("time", getTime())
                .append("description", getDescription())
                .append("posology", getPosology())
                .append("pharmaceuticalform", getPharmaceuticalform())
                .append("dosage", getDosage())
                .append("activeingredient", getActiveingredient())
                .append("name", getName())
                .append("idmedication", getIdmedication())
                .append("identifier", getIdentifier())
                .toString();
    }


    public int compareTo(Object object)
    {
        MedicationBean obj = (MedicationBean) object;
        return new CompareToBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getDescription(), obj.getDescription())
                .append(getPosology(), obj.getPosology())
                .append(getPharmaceuticalform(), obj.getPharmaceuticalform())
                .append(getDosage(), obj.getDosage())
                .append(getActiveingredient(), obj.getActiveingredient())
                .append(getName(), obj.getName())
                .append(getIdmedication(), obj.getIdmedication())
                .append(getIdentifier(),obj.getIdentifier())
                .toComparison();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
