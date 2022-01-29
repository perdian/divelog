package de.perdian.divelog.web.modules.dives;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import de.perdian.divelog.model.entities.components.Air;
import de.perdian.divelog.model.entities.components.Buddy;
import de.perdian.divelog.model.entities.components.Environment;
import de.perdian.divelog.model.entities.components.Equipment;
import de.perdian.divelog.model.entities.components.Organizer;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.entities.components.Spot;
import de.perdian.divelog.web.support.types.image.Image;

public class DiveEditor {

    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Spot spot = null;
    private String comments = null;
    private Integer totalTimeMinutes = null;
    private Integer groundTimeMinutes = null;
    private Air air = null;
    private Double maxDepth = null;
    private Environment environment = null;
    private Equipment equipment = null;
    private Buddy buddy = null;
    private Organizer organizer = null;
    private Image logbookImage = null;
    private Image logbookImageUpload = null;
    private boolean logbookImageDelete = false;

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

    public Organizer getOrganizer() {
        return this.organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Image getLogbookImage() {
        return this.logbookImage;
    }
    public void setLogbookImage(Image logbookImage) {
        this.logbookImage = logbookImage;
    }

    public Image getLogbookImageUpload() {
        return this.logbookImageUpload;
    }
    public void setLogbookImageUpload(Image logbookImageUpload) {
        this.logbookImageUpload = logbookImageUpload;
    }

    public boolean isLogbookImageDelete() {
        return this.logbookImageDelete;
    }
    public void setLogbookImageDelete(boolean logbookImageDelete) {
        this.logbookImageDelete = logbookImageDelete;
    }

}
