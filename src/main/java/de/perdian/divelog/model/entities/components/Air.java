package de.perdian.divelog.model.entities.components;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Air implements Serializable {

    static final long serialVersionUID = 1L;

    private AirType type = AirType.REGULAR;
    private Integer pressureStart = null;
    private Integer pressureEnd = null;

    public Air() {
    }

    public Air(AirType type) {
        this.setType(type);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @NotNull
    @Enumerated(EnumType.STRING)
    public AirType getType() {
        return this.type;
    }
    public void setType(AirType type) {
        this.type = type;
    }

    @Positive
    public Integer getPressureStart() {
        return this.pressureStart;
    }
    public void setPressureStart(Integer pressureStart) {
        this.pressureStart = pressureStart;
    }

    @Positive
    public Integer getPressureEnd() {
        return this.pressureEnd;
    }
    public void setPressureEnd(Integer pressureEnd) {
        this.pressureEnd = pressureEnd;
    }

}
