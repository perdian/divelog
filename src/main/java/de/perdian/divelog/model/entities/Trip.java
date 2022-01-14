package de.perdian.divelog.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.perdian.divelog.model.components.PlaceAndTime;

@Entity
@Table(name = "trips")
public class Trip extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private User user = null;
    private String title = null;
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

    @ManyToOne
    public User getUser() {
        return this.user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
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

    @Column(length = 2000)
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

}
