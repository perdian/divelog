package de.perdian.divelog.web.modules.dives;

import de.perdian.divelog.model.components.PlaceAndTime;
import de.perdian.divelog.model.components.Spot;

public class DiveEditor {

    private long number = 1;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private Spot spot = null;
    private String comments = null;

    public long getNumber() {
        return this.number;
    }
    public void setNumber(long number) {
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

}
