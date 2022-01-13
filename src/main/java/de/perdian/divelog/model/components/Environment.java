package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Environment implements Serializable {

    static final long serialVersionUID = 1L;

    private Double temperatureAir = null;
    private Double temperatureSurface = null;
    private Double temperatureGround = null;
    private WaterType waterType = null;
    private String visibility = null;
    private Boolean waves = null;
    private Boolean current = null;

    public Double getTemperatureAir() {
        return this.temperatureAir;
    }
    public void setTemperatureAir(Double temperatureAir) {
        this.temperatureAir = temperatureAir;
    }

    public Double getTemperatureSurface() {
        return this.temperatureSurface;
    }
    public void setTemperatureSurface(Double temperatureSurface) {
        this.temperatureSurface = temperatureSurface;
    }

    public Double getTemperatureGround() {
        return this.temperatureGround;
    }
    public void setTemperatureGround(Double temperatureGround) {
        this.temperatureGround = temperatureGround;
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

    public Boolean getWaves() {
        return this.waves;
    }
    public void setWaves(Boolean waves) {
        this.waves = waves;
    }

    public Boolean getCurrent() {
        return this.current;
    }
    public void setCurrent(Boolean current) {
        this.current = current;
    }

}
