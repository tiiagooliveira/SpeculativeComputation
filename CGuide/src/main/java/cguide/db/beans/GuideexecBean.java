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
public class GuideexecBean implements Serializable {
    private Long iduser;
    private Long idguideline;
    private Long idpatient;
    private Long idguideexec;
    private String time;
    private String start;
    private String nextTasks;
    private String description;
    private Boolean completed;


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public Long getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(Long idguideexec) {
        this.idguideexec = idguideexec;
    }

    //UTILS
    public void copy(GuideexecBean bean)
    {
        setTime(bean.getTime());
        setIdpatient(bean.getIdpatient());
        setIduser(bean.getIduser());
        setIdguideline(bean.getIdguideline());
        setIdguideexec(bean.getIdguideexec());
        setCompleted(bean.getCompleted());
        setNextTasks(bean.getNextTasks());
        setDescription(bean.getDescription());
        setStart(bean.getStart());
    }
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof GuideexecBean)) {
            return false;
        }

        GuideexecBean obj = (GuideexecBean) object;
        return new EqualsBuilder()
                .append(getTime(), obj.getTime())
                .append(getIdpatient(), obj.getIdpatient())
                .append(getIduser(),obj.getIduser())
                .append(getIdguideline(),obj.getIdguideline())
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getCompleted(),obj.getCompleted())
                .append(getNextTasks(),obj.getNextTasks())
                .append(getDescription(),obj.getDescription())
                .append(getStart(),obj.getStart())
                .isEquals();
    }

    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getTime())
                .append(getIdpatient())
                .append(getIduser())
                .append(getIdguideline())
                .append(getIdguideline())
                .append(getIdguideexec())
                .append(getNextTasks())
                .append(getCompleted())
                .append(getDescription())
                .append(getStart())
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

                .append("time",getTime())
                .append("idpatient", getIdpatient())
                .append("iduser", getIduser())
                .append("idguideline", getIdguideline())
                .append("idguideexec", getIdguideexec())
                .append("nextTasks", getNextTasks())
                .append("completed", getCompleted())
                .append("description", getDescription())
                .append("start",getStart())
                .toString();
    }
    public int compareTo(Object object)
    {
        GuideexecBean obj = (GuideexecBean) object;
        return new CompareToBuilder()
                .append(getTime(),obj.getTime())
                .append(getIdpatient(), obj.getIdpatient())
                .append(getIduser(), obj.getIduser())
                .append(getIdguideline(), obj.getIdguideline())
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getNextTasks(),obj.getNextTasks())
                .append(getCompleted(),obj.getCompleted())
                .append(getDescription(),obj.getDescription())
                .toComparison();
    }

    public Long getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(Long idpatient) {
        this.idpatient = idpatient;
    }

    public Long getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(Long idguideline) {
        this.idguideline = idguideline;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(String nextTasks) {
        this.nextTasks = nextTasks;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
