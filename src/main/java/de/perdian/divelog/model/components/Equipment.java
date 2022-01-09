package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Equipment implements Serializable {

    static final long serialVersionUID = 1L;

    private Double weight = null;
    private SuitType suitType = null;
    private Double suitThickness = null;
    private Boolean cap = Boolean.FALSE;
    private Boolean gloves = Boolean.FALSE;
    private String computer = null;

    public Double getWeight() {
        return this.weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public SuitType getSuitType() {
        return this.suitType;
    }
    public void setSuitType(SuitType suitType) {
        this.suitType = suitType;
    }

    public Double getSuitThickness() {
        return this.suitThickness;
    }
    public void setSuitThickness(Double suitThickness) {
        this.suitThickness = suitThickness;
    }

    public Boolean getCap() {
        return this.cap;
    }
    public void setCap(Boolean cap) {
        this.cap = cap;
    }

    public Boolean getGloves() {
        return this.gloves;
    }
    public void setGloves(Boolean gloves) {
        this.gloves = gloves;
    }

    public String getComputer() {
        return this.computer;
    }
    public void setComputer(String computer) {
        this.computer = computer;
    }

}
