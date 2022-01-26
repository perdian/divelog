package de.perdian.divelog.model.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class AbstractEntity implements Serializable {

    static final long serialVersionUID = 1L;

    private Instant createdAt = null;
    private Instant updatedAt = null;

    @PrePersist
    void onInsert() {
        this.setCreatedAt(Instant.now());
        this.setUpdatedAt(Instant.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setUpdatedAt(Instant.now());
    }

    @JsonIgnore
    public Instant getCreatedAt() {
        return this.createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @JsonIgnore
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

}
