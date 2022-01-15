package de.perdian.divelog.web.modules.dives;

import java.util.UUID;

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
    private Integer maxDepth = null;
    private Environment environment = null;
    private PadiStatistics padiStatistics = null;
    private Equipment equipment = null;
    private Buddy buddy = null;

    public DiveEditor() {
        this(null);
    }

    public DiveEditor(Dive diveEntity) {
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

    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getTotalTimeMinutes() {
        return this.totalTimeMinutes;
    }
    public void setTotalTimeMinutes(Integer totalTimeMinutes) {
        this.totalTimeMinutes = totalTimeMinutes;
    }

    public Integer getGroundTimeMinutes() {
        return this.groundTimeMinutes;
    }
    public void setGroundTimeMinutes(Integer groundTimeMinutes) {
        this.groundTimeMinutes = groundTimeMinutes;
    }

    public Air getAir() {
        return this.air;
    }
    public void setAir(Air air) {
        this.air = air;
    }

    public Integer getMaxDepth() {
        return this.maxDepth;
    }
    public void setMaxDepth(Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

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
