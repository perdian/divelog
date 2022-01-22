package de.perdian.divelog.web.modules.overview;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Overview implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer totalDives = null;
    private Integer aggregatedTotalTimeMinutes = null;
    private Integer aggregatedBottomTimeMinutes = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public Integer getTotalDives() {
        return this.totalDives;
    }
    public void setTotalDives(Integer totalDives) {
        this.totalDives = totalDives;
    }

    public Integer getAggregatedTotalTimeMinutes() {
        return this.aggregatedTotalTimeMinutes;
    }
    public void setAggregatedTotalTimeMinutes(Integer aggregatedTotalTimeMinutes) {
        this.aggregatedTotalTimeMinutes = aggregatedTotalTimeMinutes;
    }

    public Integer getAggregatedBottomTimeMinutes() {
        return this.aggregatedBottomTimeMinutes;
    }
    public void setAggregatedBottomTimeMinutes(Integer aggregatedBottomTimeMinutes) {
        this.aggregatedBottomTimeMinutes = aggregatedBottomTimeMinutes;
    }

}
