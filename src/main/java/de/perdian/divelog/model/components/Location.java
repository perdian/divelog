package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

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

    @DecimalMin("-90")
    @DecimalMax("90")
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @DecimalMin("-180")
    @DecimalMax("180")
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

    @Length(max = 2)
    public String getCountryCode() {
        return this.countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
