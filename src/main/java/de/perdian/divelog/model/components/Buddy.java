package de.perdian.divelog.model.components;

import java.io.Serializable;

public class Buddy implements Serializable {

    static final long serialVersionUID = 1L;

    private String name = null;
    private BuddyType type = BuddyType.BUDDY;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BuddyType getType() {
        return this.type;
    }
    public void setType(BuddyType type) {
        this.type = type;
    }

}
