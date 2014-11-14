
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
public class TaskBean
        implements Serializable
{

    private Long idtask;
    private Long idguideexec;
    private String taskType;
    private String taskFormat;
    private String taskDescription;
    private String taskIdentifier;
    private String time;
    private String taskPlan;
    private String nextTask;

    /**
     * Prefered methods to create a MedicationBean is via the createUtilizadorBean method in UtilizadorManager or
     * via the factory class UtilizadorFactory create method
     */
    protected TaskBean()
    {
    }
    //GETTERS AND SETTERS

    public Long getIdguideexec(){
        return this.idguideexec;
    }
    public void setIdguideexec(Long idguideexec){
        this.idguideexec = idguideexec;
    }


    public String getTaskFormat() {
        return taskFormat;
    }

    public void setTaskFormat(String taskFormat) {
        this.taskFormat = taskFormat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskIdentifier() {
        return taskIdentifier;
    }

    public void setTaskIdentifier(String taskIdentifier) {
        this.taskIdentifier = taskIdentifier;
    }


    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Long getIdtask() {
        return idtask;
    }

    public void setIdtask(Long idtask) {
        this.idtask = idtask;
    }


    public String getTaskPlan() {
        return taskPlan;
    }

    public void setTaskPlan(String taskPlan) {
        this.taskPlan = taskPlan;
    }

    public String getNextTask() {
        return nextTask;
    }

    public void setNextTask(String nextTask) {
        this.nextTask = nextTask;
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
    public void copy(TaskBean bean)
    {

        setTaskFormat(bean.getTaskFormat());
        setTaskDescription(bean.getTaskDescription());
        setTaskType(bean.getTaskType());
        setIdtask(bean.getIdtask());
        setTime(bean.getTime());
        setTaskIdentifier(bean.getTaskIdentifier());
        setIdguideexec(bean.getIdguideexec());
        setTaskPlan(bean.getTaskPlan());
        setNextTask(bean.getNextTask());
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof TaskBean)) {
            return false;
        }

        TaskBean obj = (TaskBean) object;
        return new EqualsBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getTaskIdentifier(), obj.getTaskIdentifier())
                .append(getTaskDescription(), obj.getTaskDescription())
                .append(getTaskFormat(), obj.getTaskFormat())
                .append(getTaskType(), obj.getTaskType())
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getTaskPlan(),obj.getTaskPlan())
                .append(getNextTask(),obj.getNextTask())
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
                .append(getTaskIdentifier())
                .append(getTaskDescription())
                .append(getTaskFormat())
                .append(getTaskType())
                .append(getIdguideexec())
                .append(getTaskPlan())
                .append(getNextTask())
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
                .append("taskIdentifier", getTaskIdentifier())
                .append("taskDescription", getTaskDescription())
                .append("taskFormat", getTaskFormat())
                .append("taskType", getTaskType())
                .append("idguideexec", getIdguideexec())
                .append("taskPlan",getTaskPlan())
                .append("nextTask",getNextTask())
                .toString();
    }


    public int compareTo(Object object)
    {
        TaskBean obj = (TaskBean) object;
        return new CompareToBuilder()
                .append(getIdtask(), obj.getIdtask())
                .append(getTime(), obj.getTime())
                .append(getTaskIdentifier(), obj.getTaskIdentifier())
                .append(getTaskDescription(), obj.getTaskDescription())
                .append(getTaskFormat(), obj.getTaskFormat())
                .append(getTaskType(), obj.getTaskType())
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getTaskPlan(),obj.getTaskPlan())
                .append(getNextTask(),obj.getNextTask())
                .toComparison();
    }

}
