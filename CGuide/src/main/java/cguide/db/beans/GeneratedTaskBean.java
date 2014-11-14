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
public class GeneratedTaskBean implements Serializable {
    private String idgeneratedtask;
    private Long idguideexec;
    private String identifier;
    private String idplan;
    private String idsync;


    //UTILS
    public void copy(GeneratedTaskBean bean)
    {
        setIdplan(bean.getIdplan());
        setIdgeneratedtask(bean.getIdgeneratedtask());
        setIdsync(bean.getIdsync());
        setIdentifier(bean.getIdentifier());
    }
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof GeneratedTaskBean)) {
            return false;
        }

        GeneratedTaskBean obj = (GeneratedTaskBean) object;
        return new EqualsBuilder()
                .append(getIdsync(), obj.getIdsync())
                .append(getIdplan(),obj.getIdplan())
                .append(getIdgeneratedtask(), obj.getIdgeneratedtask())
                .append(getIdentifier(), obj.getIdentifier())
                .isEquals();
    }

    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getIdsync())
                .append(getIdgeneratedtask())
                .append(getIdplan())
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
     * <li>ToStringStyle.NO_FIELD_PARAMETERS_STYLE</li>
     * <li>ToStringStyle.SHORT_PREFIX_STYLE</li>
     * <li>ToStringStyle.SIMPLE_STYLE</li>
     */
    public String toString(ToStringStyle style) {
        return new ToStringBuilder(this, style)
                .append("idtask", getIdsync())
                .append("idgeneratedtask",getIdgeneratedtask())
                .append("idplan", getIdplan())
                .append("identifier",getIdentifier())
                .toString();
    }
    public int compareTo(Object object)
    {
        GeneratedTaskBean obj = (GeneratedTaskBean) object;
        return new CompareToBuilder()
                .append(getIdsync(), obj.getIdsync())
                .append(getIdplan(),obj.getIdplan())
                .append(getIdsync(),obj.getIdsync())
                .append(getIdentifier(), obj.getIdentifier())
                .toComparison();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public Long getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(Long idguideexec) {
        this.idguideexec = idguideexec;
    }

    public String getIdsync() {
        return idsync;
    }

    public void setIdsync(String idsync) {
        this.idsync = idsync;
    }

    public String getIdgeneratedtask() {
        return idgeneratedtask;
    }

    public void setIdgeneratedtask(String idgeneratedtask) {
        this.idgeneratedtask = idgeneratedtask;
    }

    public String getIdplan() {
        return idplan;
    }

    public void setIdplan(String idplan) {
        this.idplan = idplan;
    }
}
