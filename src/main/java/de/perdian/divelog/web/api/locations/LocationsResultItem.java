package de.perdian.divelog.web.api.locations;

import java.io.Serializable;

public class LocationsResultItem implements Serializable {

    static final long serialVersionUID = 1L;

    private LocationsCoordinates coordinates = null;
    private Integer count = null;

    LocationsResultItem(LocationsCoordinates coordinates, Integer count) {
        this.setCoordinates(coordinates);
        this.setCount(count);
    }

    public LocationsCoordinates getCoordinates() {
        return this.coordinates;
    }
    void setCoordinates(LocationsCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getCount() {
        return this.count;
    }
    void setCount(Integer count) {
        this.count = count;
    }

}
