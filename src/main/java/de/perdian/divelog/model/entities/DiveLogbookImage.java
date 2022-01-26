package de.perdian.divelog.model.entities;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dive_logbook_images")
public class DiveLogbookImage extends AbstractIdentifiedEntity {

    static final long serialVersionUID = 1L;

    private Dive dive = null;
    private byte[] jpegBytes = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof DiveLogbookImage) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @OneToOne
    public Dive getDive() {
        return this.dive;
    }
    public void setDive(Dive dive) {
        this.dive = dive;
    }

    public byte[] getJpegBytes() {
        return this.jpegBytes;
    }
    public void setJpegBytes(byte[] jpegBytes) {
        this.jpegBytes = jpegBytes;
    }

}
