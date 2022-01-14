package de.perdian.divelog.web.modules.dives;

import de.perdian.divelog.model.components.PlaceAndTime;

public class DiveEditor {

    private long number = 1;
    private PlaceAndTime start = null;
    private PlaceAndTime end = null;

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

}
