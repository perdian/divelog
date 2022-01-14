package de.perdian.divelog.model.components;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Embeddable
public class Buddy implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private BuddyType type = BuddyType.BUDDY;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    public BuddyType getType() {
        return this.type;
    }
    public void setType(BuddyType type) {
        this.type = type;
    }

}
