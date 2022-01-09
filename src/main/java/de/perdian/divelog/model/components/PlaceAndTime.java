package de.perdian.divelog.model.components;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Embeddable;

@Embeddable
public class PlaceAndTime implements Serializable {

    static final long serialVersionUID = 1L;

    private PlaceType type = null;
    private Location location = null;
    private LocalDate date = null;
    private LocalTime time = null;

    public PlaceType getType() {
        return this.type;
    }
    public void setType(PlaceType type) {
        this.type = type;
    }

    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return this.date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return this.time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

}
