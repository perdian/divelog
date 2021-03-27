package de.perdian.divelog.model.components;

import java.io.Serializable;
import java.time.Duration;

public class PadiStatistics implements Serializable {

    static final long serialVersionUID = 1L;

    private Duration surfaceIntervalBefore = null;
    private String pressureGroupBefore = null;
    private String pressureGroupAfter = null;

    public Duration getSurfaceIntervalBefore() {
        return this.surfaceIntervalBefore;
    }
    public void setSurfaceIntervalBefore(Duration surfaceIntervalBefore) {
        this.surfaceIntervalBefore = surfaceIntervalBefore;
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
