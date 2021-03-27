package de.perdian.divelog.model.entities;

import java.time.Duration;

import javax.persistence.Entity;

import de.perdian.divelog.model.components.Air;
import de.perdian.divelog.model.components.Equipment;
import de.perdian.divelog.model.components.PlaceAndTime;;

@Entity
public class Dive extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private Trip trip = null;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Air air = null;
    private Equipment equipment = null;
    private Double maxDepth = null;
    private Duration bottomTime = null;
    private String comments = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Dive) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Trip getTrip() {
        return this.trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public PlaceAndTime getStart() {
        return this.start;
    }
    public void setStart(PlaceAndTime start) {
        this.start = start;
    }

    public PlaceAndTime getEnd() {
        return this.end;
    }
    public void setEnd(PlaceAndTime end) {
        this.end = end;
    }

    public Air getAir() {
        return this.air;
    }
    public void setAir(Air air) {
        this.air = air;
    }

    public Equipment getEquipment() {
        return this.equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Double getMaxDepth() {
        return this.maxDepth;
    }
    public void setMaxDepth(Double maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Duration getBottomTime() {
        return this.bottomTime;
    }
    public void setBottomTime(Duration bottomTime) {
        this.bottomTime = bottomTime;
    }

    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
