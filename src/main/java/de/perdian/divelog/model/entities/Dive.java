package de.perdian.divelog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.perdian.divelog.model.components.Air;
import de.perdian.divelog.model.components.Buddy;
import de.perdian.divelog.model.components.Equipment;
import de.perdian.divelog.model.components.PadiStatistics;
import de.perdian.divelog.model.components.PlaceAndTime;
import de.perdian.divelog.model.components.Spot;;

@Entity
@Table(name = "dives")
public class Dive extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private Trip trip = null;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Spot spot = null;
    private Air air = null;
    private Equipment equipment = null;
    private Double maxDepth = null;
    private Integer bottomTimeMinutes = null;
    private Buddy buddy = null;
    private PadiStatistics padiStatistics = null;
    private String comments = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Dive) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @ManyToOne
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

    public Spot getSpot() {
        return this.spot;
    }
    public void setSpot(Spot spot) {
        this.spot = spot;
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

    public Integer getBottomTimeMinutes() {
        return this.bottomTimeMinutes;
    }
    public void setBottomTimeMinutes(Integer bottomTimeMinutes) {
        this.bottomTimeMinutes = bottomTimeMinutes;
    }

    public Buddy getBuddy() {
        return this.buddy;
    }
    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public PadiStatistics getPadiStatistics() {
        return this.padiStatistics;
    }
    public void setPadiStatistics(PadiStatistics padiStatistics) {
        this.padiStatistics = padiStatistics;
    }

    @Column(length = 2000)
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
