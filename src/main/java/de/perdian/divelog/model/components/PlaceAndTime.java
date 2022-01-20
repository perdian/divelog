package de.perdian.divelog.model.components;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class PlaceAndTime implements Serializable {

    static final long serialVersionUID = 1L;

    private PlaceType type = null;
    private Location location = null;
    private LocalDate date = null;
    private LocalTime time = null;

    public PlaceAndTime() {
    }

    public PlaceAndTime(LocalDate date, LocalTime time) {
        this.setDate(date);
        this.setTime(time);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @Enumerated(EnumType.STRING)
    public PlaceType getType() {
        return this.type;
    }
    public void setType(PlaceType type) {
        this.type = type;
    }

    @Valid
    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @NotEmpty
    @Transient
    public String getDateIso() {
        return this.getDate() == null ? null : this.getDate().toString();
    }
    public void setDateIso(String dateIso) {
        this.setDate(StringUtils.isEmpty(dateIso) ? null : LocalDate.parse(dateIso));
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

    @Transient
    public Instant getUtcTimestamp() {
        if (this.getDate() == null || this.getTime() == null) {
            return null;
        } else if (this.getLocation() == null || StringUtils.isEmpty(this.getLocation().getTimezoneId())) {
            return null;
        } else {
            LocalDateTime localDateTime = this.getTime().atDate(this.getDate());
            return localDateTime.atZone(ZoneId.of(this.getLocation().getTimezoneId())).toInstant();
        }
    }

}
