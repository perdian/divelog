package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class PadiStatistics implements Serializable {

    static final long serialVersionUID = 1L;

    private String pressureGroupBefore = null;
    private String pressureGroupAfter = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getPressureGroupBefore() {
        return this.pressureGroupBefore;
    }
    public void setPressureGroupBefore(String pressureGroupBefore) {
        this.pressureGroupBefore = pressureGroupBefore;
    }

    public String getPressureGroupAfter() {
        return this.pressureGroupAfter;
    }
    public void setPressureGroupAfter(String pressureGroupAfter) {
        this.pressureGroupAfter = pressureGroupAfter;
    }

}
