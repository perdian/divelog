package de.perdian.divelog.model.entities.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Organizer implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private Location location = null;
    private String website = null;
    private OrganizerType type = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

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

    public OrganizerType getType() {
        return this.type;
    }
    public void setType(OrganizerType type) {
        this.type = type;
    }

}
