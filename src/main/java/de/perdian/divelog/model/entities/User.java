package de.perdian.divelog.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractIdentifiedEntity {

    static final long serialVersionUID = 1L;

    private String provider = null;
    private String providerId = null;
    private String name = null;

    @Override
    public boolean equals(Object that) {
        return (that instanceof Trip) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getProvider() {
        return this.provider;
    }
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return this.providerId;
    }
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
