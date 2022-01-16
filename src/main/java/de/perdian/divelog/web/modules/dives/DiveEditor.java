package de.perdian.divelog.web.modules.dives;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import de.perdian.divelog.model.components.Air;
import de.perdian.divelog.model.components.Buddy;
import de.perdian.divelog.model.components.Environment;
import de.perdian.divelog.model.components.Equipment;
import de.perdian.divelog.model.components.PadiStatistics;
import de.perdian.divelog.model.components.PlaceAndTime;
import de.perdian.divelog.model.components.Spot;
import de.perdian.divelog.model.entities.Dive;

public class DiveEditor {

    private UUID diveEntityId = null;
    private Long number = null;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Spot spot = null;
    private String comments = null;
    private Integer totalTimeMinutes = null;
    private Integer groundTimeMinutes = null;
    private Air air = null;
    private Double maxDepth = null;
    private Environment environment = null;
    private PadiStatistics padiStatistics = null;
    private Equipment equipment = null;
    private Buddy buddy = null;

    public DiveEditor() {
        this(null);
    }

    public DiveEditor(Dive diveEntity) {
        this.setDiveEntityId(diveEntity == null ? null : diveEntity.getId());
        // TODO: Extract information from entity
    }

    public UUID getDiveEntityId() {
        return this.diveEntityId;
    }
    public void setDiveEntityId(UUID diveEntityId) {
        this.diveEntityId = diveEntityId;
    }

    public Long getNumber() {
        return this.number;
    }
    public void setNumber(Long number) {
        this.number = number;
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

    @Valid
    public Spot getSpot() {
        return this.spot;
    }
    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @NotNull @Positive
    public Integer getTotalTimeMinutes() {
        return this.totalTimeMinutes;
    }
    public void setTotalTimeMinutes(Integer totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    @Positive
    public Integer getGroundTimeMinutes() {
        return this.groundTimeMinutes;
    }
    public void setGroundTimeMinutes(Integer groundTimeMinutes) {
        this.groundTimeMinutes = groundTimeMinutes;
    }

    @Valid
    public Air getAir() {
        return this.air;
    }
    public void setAir(Air air) {
        this.air = air;
    }

    @Positive
    public Double getMaxDepth() {
        return this.maxDepth;
    }
    public void setMaxDepth(Double maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Valid
    public Environment getEnvironment() {
        return this.environment;
    }
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public PadiStatistics getPadiStatistics() {
        return this.padiStatistics;
    }
    public void setPadiStatistics(PadiStatistics padiStatistics) {
        this.padiStatistics = padiStatistics;
    }

    @Valid
    public Equipment getEquipment() {
        return this.equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Buddy getBuddy() {
        return this.buddy;
    }
    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

}
