
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
 * ObservationBean is a mapping of observation Table.
 * @author Tiago
 */
public class ObservationBean
        implements Serializable
{

    private Long idobservation;
    private String parameter;
    private String parameteridentifier;
    private String variablename;
    private String parametervalue;
    private String identifier;
    private String unit;
    private String time;
    private Long idtask;
    private Boolean isnumeric;

    /**
     * Prefered methods to create a ObservationBean is via the createUtilizadorBean method in UtilizadorManager or
     * via the factory class UtilizadorFactory create method
     */
    protected ObservationBean()
    {
    }
    //GETTERS AND SETTERS

    public Long getIdobservation(){
        return this.idobservation;
    }
    public void setIdobservation(Long idobservation){
        this.idobservation = idobservation;
    }


    public String getParameteridentifier() {
        return parameteridentifier;
    }

    public void setParameteridentifier(String parameteridentifier) {
        this.parameteridentifier = parameteridentifier;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getParametervalue() {
        return parametervalue;
    }

    public void setParametervalue(String parametervalue) {
        this.parametervalue = parametervalue;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getVariablename() {
        return variablename;
    }

    public void setVariablename(String variablename) {
        this.variablename = variablename;
    }


    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Long getIdtask() {
        return idtask;
    }

    public void setIdtask(Long idtask) {
        this.idtask = idtask;
    }

    public Boolean getIsnumeric() {
        return isnumeric;
    }

    public void setIsnumeric(Boolean isnumeric) {
        this.isnumeric = isnumeric;
    }

    /**
     * Creates a new UtilizadorBean instance.
     *
     * @return the new UtilizadorBean
     */
    public ObservationBean createObservationBean()
    {
        return new ObservationBean();
    }

    /**
     * Copies the passed bean into the current bean.
     *
     * @param bean the bean to copy into the current bean
     */
    public void copy(ObservationBean bean)
    {
        setIdentifier(bean.getIdentifier());
        setUnit(bean.getUnit());
        setParameteridentifier(bean.getParameteridentifier());
        setVariablename(bean.getVariablename());
        setParameter(bean.getParameter());
        setIdtask(bean.getIdtask());
        setTime(bean.getTime());
        setIdobservation(bean.getIdobservation());
        setParametervalue(bean.getParametervalue());
        setParametervalue(bean.getVariablename());
        setIsnumeric(bean.getIsnumeric());

    }

    /**
     * return a dictionnary of the object
     */
    public Map<String,String> getDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("idtask", getIdtask() == null ? "" : getIdtask().toString());
        dictionnary.put("time", getTime() == null ? "" : getTime());
        dictionnary.put("unit", getUnit() == null ? "" : getUnit());
        dictionnary.put("identifier", getIdentifier() == null ? "" : getIdentifier());
        dictionnary.put("parametervalue", getParametervalue() == null ? "" : getParametervalue());
        dictionnary.put("variablename", getVariablename() == null ? "" : getVariablename());
        dictionnary.put("parameteridentifier", getParameteridentifier() == null ? "" : getParameteridentifier());
        dictionnary.put("parameter", getParameter() == null ? "" : getParameter());
        dictionnary.put("idobservation", getIdobservation() == null ? "" : getIdobservation().toString());
        dictionnary.put("isnumeric", getIsnumeric() == null ? "" : getIsnumeric().toString());
        return dictionnary;
    }

    /**
     * return a dictionnary of the pk columns
     */
    public Map<String,String> getPkDictionnary()
    {
        Map<String,String> dictionnary = new HashMap<String,String>();
        dictionnary.put("idobservation", getIdobservation() == null ? "" : getIdobservation().toString());
        return dictionnary;
    }

    /**
     * return a the value string representation of the given field
     */
    public String getValue(String column)
    {
        if (null == column || "".equals(column)) {
            return "";
        } else if ("idtask".equalsIgnoreCase(column) || "idtask".equalsIgnoreCase(column)) {
            return getIdtask() == null ? "" : getIdtask().toString();
        } else if ("time".equalsIgnoreCase(column) || "time".equalsIgnoreCase(column)) {
            return getTime() == null ? "" : getTime();
        } else if ("unit".equalsIgnoreCase(column) || "unit".equalsIgnoreCase(column)) {
            return getUnit() == null ? "" : getUnit();
        } else  if ("identifier".equalsIgnoreCase(column) || "identifier".equalsIgnoreCase(column)) {
            return getIdentifier() == null ? "" : getIdentifier();
        } else if ("variablename".equalsIgnoreCase(column) || "variablename".equalsIgnoreCase(column)) {
            return getVariablename() == null ? "" : getVariablename();
        } else if ("parameteridentifier".equalsIgnoreCase(column) || "parameteridentifier".equalsIgnoreCase(column)) {
            return getParameteridentifier() == null ? "" : getParameteridentifier();
        } else if ("parameter".equalsIgnoreCase(column) || "parameter".equalsIgnoreCase(column)) {
            return getParameter() == null ? "" : getParameter();
        } else if ("idobservation".equalsIgnoreCase(column) || "idobservation".equalsIgnoreCase(column)) {
            return getIdobservation() == null ? "" : getIdobservation().toString();
        } else if ("isnumeric".equalsIgnoreCase(column) || "isnumeric".equalsIgnoreCase(column)) {
            return getIdobservation() == null ? "" : getIdobservation().toString();
        }
        return "";
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof ObservationBean)) {
            return false;
        }

        ObservationBean obj = (ObservationBean) object;
        return new EqualsBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getUnit(), obj.getUnit())
                .append(getIdentifier(), obj.getIdentifier())
                .append(getParametervalue(), obj.getParametervalue())
                .append(getVariablename(), obj.getVariablename())
                .append(getParameteridentifier(), obj.getParameteridentifier())
                .append(getParameter(), obj.getParameter())
                .append(getIdobservation(), obj.getIdobservation())
                .append(getIsnumeric(), obj.getIsnumeric())
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
                .append(getUnit())
                .append(getIdentifier())
                .append(getParametervalue())
                .append(getVariablename())
                .append(getParameteridentifier())
                .append(getParameter())
                .append(getIdobservation())
                .append(getIsnumeric())
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
     * <li>ToStringStyle.NO_FIELD_PARAMETERS_STYLE</li>
     * <li>ToStringStyle.SHORT_PREFIX_STYLE</li>
     * <li>ToStringStyle.SIMPLE_STYLE</li>
     */
    public String toString(ToStringStyle style) {
        return new ToStringBuilder(this, style)
                .append("idtask", getIdtask())
                .append("time", getTime())
                .append("unit", getUnit())
                .append("identifier", getIdentifier())
                .append("parametervalue", getParametervalue())
                .append("variablename", getVariablename())
                .append("parameteridentifier", getParameteridentifier())
                .append("parameter", getParameter())
                .append("idobservation", getIdobservation())
                .append("isnumeric", getIsnumeric())
                .toString();
    }


    public int compareTo(Object object)
    {
        ObservationBean obj = (ObservationBean) object;
        return new CompareToBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getUnit(), obj.getUnit())
                .append(getIdentifier(), obj.getIdentifier())
                .append(getParametervalue(), obj.getParametervalue())
                .append(getVariablename(), obj.getVariablename())
                .append(getParameteridentifier(), obj.getParameteridentifier())
                .append(getParameter(), obj.getParameter())
                .append(getIdobservation(), obj.getIdobservation())
                .append(getIsnumeric(),obj.getIsnumeric())
                .toComparison();
    }
}
