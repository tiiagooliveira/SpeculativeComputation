package cguide.db.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 31-07-2013
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class ExamBean implements Serializable {
    private Long idexam;
    private String name;
    private String description;
    private Long idtask;
    private String time;
    private String identifier;

    public Long getIdtask() {
        return idtask;
    }

    public void setIdtask(Long idtask) {
        this.idtask = idtask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Long getIdexam() {
        return idexam;
    }

    public void setIdexam(Long idexam) {
        this.idexam = idexam;
    }

    //UTILS
    public void copy(ExamBean bean)
    {
        setTime(bean.getTime());
        setIdexam(bean.getIdexam());
        setIdtask(bean.getIdtask());
        setDescription(bean.getDescription());
        setName(bean.getName());
        setIdentifier(bean.getIdentifier());
    }
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof ExamBean)) {
            return false;
        }

        ExamBean obj = (ExamBean) object;
        return new EqualsBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getDescription(),obj.getDescription())
                .append(getName(),obj.getName())
                .append(getIdexam(), obj.getIdexam())
                .append(getIdentifier(), obj.getIdentifier())
                .isEquals();
    }

    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getIdtask())
                .append(getTime())
                .append(getDescription())
                .append(getName())
                .append(getIdexam())
                .append(getIdentifier())
                .toHashCode();
    }

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
                .append("time",getTime())
                .append("description", getDescription())
                .append("name", getName())
                .append("idexam", getIdexam())
                .append("identifier",getIdentifier())
                .toString();
    }
    public int compareTo(Object object)
    {
        ExamBean obj = (ExamBean) object;
        return new CompareToBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(),obj.getTime())
                .append(getDescription(), obj.getDescription())
                .append(getName(), obj.getName())
                .append(getIdexam(), obj.getIdexam())
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
