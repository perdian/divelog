package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Location implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private Double latitude = null;
    private Double longitude = null;
    private String timezoneId = null;
    private String countryCode = null;

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

    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
