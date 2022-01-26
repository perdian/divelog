package de.perdian.divelog.model.entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public class AbstractIdentifiedEntity extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private UUID id = null;

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        toStringBuilder.append("id", this.getId());
        return toStringBuilder.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof AbstractIdentifiedEntity thatEntity) {
            return this.getId() != null && this.getId().equals(thatEntity.getId());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getId());
    }

    @Id
    @GeneratedValue(generator = "uuid2")
    @JsonIgnore
    public UUID getId() {
        return this.id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

}
