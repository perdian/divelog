package de.perdian.divelog.web.api.locations;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LocationsCoordinates implements Serializable {

    static final long serialVersionUID = 1L;

    private Double longitude = null;
    private Double latitude = null;

    public LocationsCoordinates(Double longitude, Double latitude) {
        this.setLongitude(longitude);
        this.setLatitude(latitude);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof LocationsCoordinates thatCoordinate) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getLongitude(), thatCoordinate.getLongitude());
            equalsBuilder.append(this.getLatitude(), thatCoordinate.getLatitude());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getLongitude());
        hashCodeBuilder.append(this.getLatitude());
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public Double getLongitude() {
        return this.longitude;
    }
    private void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }
    private void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
