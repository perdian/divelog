package de.perdian.divelog.model.entities.components;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Embeddable
public class Environment implements Serializable {

    static final long serialVersionUID = 1L;

    private Double waterTemperature = null;
    private Double airTemperature = null;
    private WaterType waterType = null;
    private String visibility = null;
    private Boolean waves = null;
    private Boolean current = null;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public Double getWaterTemperature() {
        return this.waterTemperature;
    }
    public void setWaterTemperature(Double waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Double getAirTemperature() {
        return this.airTemperature;
    }
    public void setAirTemperature(Double airTemperature) {
        this.airTemperature = airTemperature;
    }

    @Enumerated(EnumType.STRING)
    public WaterType getWaterType() {
        return this.waterType;
    }
    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    public String getVisibility() {
        return this.visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @JsonInclude(Include.NON_DEFAULT)
    public Boolean getWaves() {
        return this.waves;
    }
    public void setWaves(Boolean waves) {
        this.waves = waves;
    }

    @JsonInclude(Include.NON_DEFAULT)
    public Boolean getCurrent() {
        return this.current;
    }
    public void setCurrent(Boolean current) {
        this.current = current;
    }

}
