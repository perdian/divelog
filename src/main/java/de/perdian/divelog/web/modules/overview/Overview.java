package de.perdian.divelog.web.modules.overview;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Overview implements Serializable {

    static final long serialVersionUID = 1L;

    private Integer totalDives = null;
    private Integer aggregatedTotalTimeMinutes = null;
    private Integer aggregatedBottomTimeMinutes = null;
    private List<OverviewAggregationItem> aggregatedLocationTypes = null;
    private List<OverviewAggregationItem> aggregatedAirTypes = null;
    private List<OverviewAggregationItem> aggregatedWaterTypes = null;
    private List<OverviewAggregationItem> aggregatedSuitTypes = null;

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

    public List<OverviewAggregationItem> getAggregatedLocationTypes() {
        return this.aggregatedLocationTypes;
    }
    public void setAggregatedLocationTypes(List<OverviewAggregationItem> aggregatedLocationTypes) {
        this.aggregatedLocationTypes = aggregatedLocationTypes;
    }

    public List<OverviewAggregationItem> getAggregatedAirTypes() {
        return this.aggregatedAirTypes;
    }
    public void setAggregatedAirTypes(List<OverviewAggregationItem> aggregatedAirTypes) {
        this.aggregatedAirTypes = aggregatedAirTypes;
    }

    public List<OverviewAggregationItem> getAggregatedWaterTypes() {
        return this.aggregatedWaterTypes;
    }
    public void setAggregatedWaterTypes(List<OverviewAggregationItem> aggregatedWaterTypes) {
        this.aggregatedWaterTypes = aggregatedWaterTypes;
    }

    public List<OverviewAggregationItem> getAggregatedSuitTypes() {
        return this.aggregatedSuitTypes;
    }
    public void setAggregatedSuitTypes(List<OverviewAggregationItem> aggregatedSuitTypes) {
        this.aggregatedSuitTypes = aggregatedSuitTypes;
    }

}
