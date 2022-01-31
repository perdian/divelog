package de.perdian.divelog.model.entities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.perdian.divelog.model.entities.components.Air;
import de.perdian.divelog.model.entities.components.Buddy;
import de.perdian.divelog.model.entities.components.Environment;
import de.perdian.divelog.model.entities.components.Equipment;
import de.perdian.divelog.model.entities.components.Organizer;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.entities.components.Spot;;

@Entity
@Table(name = "dives")
public class Dive extends AbstractIdentifiedEntity implements UserContainer {

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
    private Organizer organizer = null;
    private String comments = null;
    private DiveLogbookImage logbookImage = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Dive) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("id", this.getId());
        toStringBuilder.append("start", this.getStart());
        toStringBuilder.append("end", this.getEnd());
        return toStringBuilder.toString();
    }

    public static Sort sortWithNewestFirst() {
        return Sort.by(Order.desc("start.date"), Order.desc("start.time"), Order.desc("createdAt"));
    }

    public static Comparator<Dive> comparatorWithOldestFirst() {
        return (d1, d2) -> {

            ZoneId zoneId = d1.getStart() == null ? null : d1.getStart().getLocation() == null || StringUtils.isEmpty(d1.getStart().getLocation().getTimezoneId()) ? ZoneId.of("UTC") : ZoneId.of(d1.getStart().getLocation().getTimezoneId());
            LocalDate localDate1 = d1.getStart() == null ? null : d1.getStart().getDate();
            LocalTime localTime1 = d1.getStart() == null || d1.getStart().getTime() == null ? LocalTime.of(12, 0) : d1.getStart().getTime();
            Instant instant1 = localDate1 == null ? null : localDate1.atTime(localTime1).atZone(zoneId).toInstant();

            LocalDate localDate2 = d2.getEnd() == null || d2.getEnd().getDate() == null ? localDate1 : d2.getEnd().getDate();
            LocalTime localTime2 = d2.getEnd() == null || d2.getEnd().getTime() == null ? LocalTime.of(12, 0) : d2.getEnd().getTime();
            Instant instant2 = localDate2 == null ? null : localDate2.atTime(localTime2).atZone(zoneId).toInstant();

            int resultInstant = instant1 == null || instant2 == null ? 0 : instant1.compareTo(instant2);
            return resultInstant != 0 ? resultInstant : d1.getCreatedAt().compareTo(d2.getCreatedAt());

        };
    }

    public static Sort sortWithOldestFirst() {
        return Sort.by(Order.asc("start.date"), Order.asc("start.time"), Order.asc("createdAt"));
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true, mappedBy = "dive")
    public DiveLogbookImage getLogbookImage() {
        return this.logbookImage;
    }
    public void setLogbookImage(DiveLogbookImage logbookImage) {
        this.logbookImage = logbookImage;
    }
    public DiveLogbookImage ensureLogbookImage() {
        if (this.getLogbookImage() == null) {
            DiveLogbookImage newLogbookImage = new DiveLogbookImage();
            newLogbookImage.setDive(this);
            this.setLogbookImage(newLogbookImage);
        }
        return this.getLogbookImage();
    }

}
