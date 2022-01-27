package de.perdian.divelog.web.modules.overview;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OverviewAggregationItem implements Serializable {

    static final long serialVersionUID = 1L;

    private String titleKey = null;
    private Integer totalValue = null;
    private Double percentValue = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getTitleKey() {
        return this.titleKey;
    }
    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public Integer getTotalValue() {
        return this.totalValue;
    }
    public void setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
    }

    public Double getPercentValue() {
        return this.percentValue;
    }
    public void setPercentValue(Double percentValue) {
        this.percentValue = percentValue;
    }

}
