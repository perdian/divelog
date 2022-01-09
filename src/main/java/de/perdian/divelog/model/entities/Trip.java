package de.perdian.divelog.model.entities;

import javax.persistence.Entity;

import de.perdian.divelog.model.components.PlaceAndTime;

@Entity
public class Trip extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private PlaceAndTime start = null;
    private PlaceAndTime end = null;
    private String comments = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Trip) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
