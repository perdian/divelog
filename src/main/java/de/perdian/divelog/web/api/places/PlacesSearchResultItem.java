package de.perdian.divelog.web.api.places;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PlacesSearchResultItem implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private Double latitude = null;
    private Double longitude = null;

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


}
