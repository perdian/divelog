package de.perdian.divelog.web.api.locations;

import java.io.Serializable;
import java.util.List;

public class LocationsResult implements Serializable {

    static final long serialVersionUID = 1L;

    private List<LocationsResultItem> locations = null;
    private List<LocationsResultItem> spots = null;

    public List<LocationsResultItem> getLocations() {
        return this.locations;
    }
    void setLocations(List<LocationsResultItem> locations) {
        this.locations = locations;
    }

    public List<LocationsResultItem> getSpots() {
        return this.spots;
    }
    void setSpots(List<LocationsResultItem> spots) {
        this.spots = spots;
    }

}
