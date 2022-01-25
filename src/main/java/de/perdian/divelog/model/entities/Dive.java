package de.perdian.divelog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.perdian.divelog.model.entities.components.Air;
import de.perdian.divelog.model.entities.components.Buddy;
import de.perdian.divelog.model.entities.components.Environment;
import de.perdian.divelog.model.entities.components.Equipment;
import de.perdian.divelog.model.entities.components.Organizer;
import de.perdian.divelog.model.entities.components.PadiStatistics;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.entities.components.Spot;;

@Entity
@Table(name = "dives")
public class Dive extends AbstractEntity implements UserContainer {

    static final long serialVersionUID = 1L;

    private User user = null;
    private Trip trip = null;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Spot spot = null;
    private Air air = null;
    private Equipment equipment = null;
    private Double maxDepth = null;
    private Integer totalTimeMinutes = null;
    private Integer bottomTimeMinutes = null;
    private Environment environment = null;
    private Buddy buddy = null;
    private PadiStatistics padiStatistics = null;
    private Organizer organizer = null;
    private String comments = null;
    private byte[] image = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Dive) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    @ManyToOne
    @JsonIgnore
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonIgnore
    public Trip getTrip() {
        return this.trip;
    }
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Valid
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

    public Integer getTotalTimeMinutes() {
        return this.totalTimeMinutes;
    }
    public void setTotalTimeMinutes(Integer totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public Integer getBottomTimeMinutes() {
        return this.bottomTimeMinutes;
    }
    public void setBottomTimeMinutes(Integer bottomTimeMinutes) {
        this.bottomTimeMinutes = bottomTimeMinutes;
    }

    public Environment getEnvironment() {
        return this.environment;
    }
    public void setEnvironment(Environment environment) {
        this.environment = environment;
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

    public Organizer getOrganizer() {
        return this.organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    @Column(length = 2000)
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getImage() {
        return this.image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

}
