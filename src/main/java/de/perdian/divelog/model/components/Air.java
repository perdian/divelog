package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Air implements Serializable {

    static final long serialVersionUID = 1L;

    private AirType type = AirType.REGULAR;
    private Integer pressureStart = null;
    private Integer pressureEnd = null;

    public AirType getType() {
        return this.type;
    }
    public void setType(AirType type) {
        this.type = type;
    }

    public Integer getPressureStart() {
        return this.pressureStart;
    }
    public void setPressureStart(Integer pressureStart) {
        this.pressureStart = pressureStart;
    }

    public Integer getPressureEnd() {
        return this.pressureEnd;
    }
    public void setPressureEnd(Integer pressureEnd) {
        this.pressureEnd = pressureEnd;
    }

}
