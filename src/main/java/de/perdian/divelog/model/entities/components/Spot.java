package de.perdian.divelog.model.entities.components;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.Valid;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Spot implements Serializable {

    static final long serialVersionUID = 1L;

    private Location location = null;
    private String website = null;
    private String comments = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Valid
    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getWebsite() {
        return this.website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    @Column(length = 2000)
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
