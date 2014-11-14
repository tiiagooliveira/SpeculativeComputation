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
 * Dateofcreation: 13:19
 * To change this template use File | Settings | File Templates.
 */
public class GuidelineBean implements Serializable {
    private Long idguideline;
    private String versionnumber;
    private String guidelinename;
    private String idguideexec;
    private String dateofcreation;
    private String dateofupdate;
    private String authorship;
    private String identifier;
    private String description;

    public String getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(String idguideexec) {
        this.idguideexec = idguideexec;
    }

    public String getGuidelinename() {
        return guidelinename;
    }

    public void setGuidelinename(String guidelinename) {
        this.guidelinename = guidelinename;
    }

    public String getDateofupdate() {
        return dateofupdate;
    }

    public void setDateofupdate(String dateofupdate) {
        this.dateofupdate = dateofupdate;
    }

    public String getAuthorship() {
        return authorship;
    }

    public void setAuthorship(String authorship) {
        this.authorship = authorship;
    }

    public String getVersionnumber() {
        return versionnumber;
    }

    public void setVersionnumber(String versionnumber) {
        this.versionnumber = versionnumber;
    }

    public String getDateofcreation() {
        return dateofcreation;
    }

    public void setDateofcreation(String dateofcreation) {
        this.dateofcreation = dateofcreation;
    }


    public Long getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(Long idguideline) {
        this.idguideline = idguideline;
    }

    //UTILS
    public void copy(GuidelineBean bean)
    {
        setDateofcreation(bean.getDateofcreation());
        setIdguideline(bean.getIdguideline());
        setIdguideexec(bean.getIdguideexec());
        setGuidelinename(bean.getGuidelinename());
        setVersionnumber(bean.getVersionnumber());
        setDateofupdate(bean.getDateofupdate());
        setAuthorship(bean.getAuthorship());
        setIdentifier(bean.getIdentifier());
    }
    @Override
	public boolean equals(Object object)
    {
        if (!(object instanceof GuidelineBean)) {
            return false;
        }

        GuidelineBean obj = (GuidelineBean) object;
        return new EqualsBuilder()
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getDateofcreation(), obj.getDateofcreation())
                .append(getGuidelinename(),obj.getGuidelinename())
                .append(getVersionnumber(),obj.getVersionnumber())
                .append(getIdguideline(), obj.getIdguideline())
                .append(getDateofupdate(),obj.getDateofupdate())
                .append(getAuthorship(),obj.getAuthorship())
                .append(getIdentifier(),obj.getIdentifier())
                .isEquals();
    }

    @Override
	public int hashCode()
    {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(getIdguideexec())
                .append(getDateofcreation())
                .append(getGuidelinename())
                .append(getVersionnumber())
                .append(getIdguideline())
                .append(getDateofupdate())
                .append(getAuthorship())
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
     * <li>ToStringStyle.NO_FIELD_VERSIONNUMBERS_STYLE</li>
     * <li>ToStringStyle.SHORT_PREFIX_STYLE</li>
     * <li>ToStringStyle.SIMPLE_STYLE</li>
     */
    public String toString(ToStringStyle style) {
        return new ToStringBuilder(this, style)
                .append("idguideexec", getIdguideexec())
                .append("dateofcreation",getDateofcreation())
                .append("guidelinename", getGuidelinename())
                .append("versionnumber", getVersionnumber())
                .append("idguideline", getIdguideline())
                .append("dateofupdate",getDateofupdate())
                .append("authorship",getAuthorship())
                .append("identifier",getIdentifier())
                .toString();
    }
    public int compareTo(Object object)
    {
        GuidelineBean obj = (GuidelineBean) object;
        return new CompareToBuilder()
                .append(getIdguideexec(), obj.getIdguideexec())
                .append(getDateofcreation(),obj.getDateofcreation())
                .append(getGuidelinename(), obj.getGuidelinename())
                .append(getVersionnumber(), obj.getVersionnumber())
                .append(getIdguideline(), obj.getIdguideline())
                .append(getDateofupdate(),obj.getDateofupdate())
                .append(getAuthorship(),obj.getAuthorship())
                .append(getIdentifier(),obj.getIdentifier())
                .toComparison();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
