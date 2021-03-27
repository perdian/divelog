package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Location implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private Double latitude = null;
    private Double longitude = null;
    private String timezoneId = null;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimezoneId() {
        return this.timezoneId;
    }
    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

}
