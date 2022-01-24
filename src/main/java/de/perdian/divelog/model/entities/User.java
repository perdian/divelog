package de.perdian.divelog.model.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import de.perdian.divelog.model.entities.components.UserProvider;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    static final long serialVersionUID = 1L;

    private UserProvider provider = null;
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

    @Enumerated(EnumType.STRING)
    public UserProvider getProvider() {
        return this.provider;
    }
    public void setProvider(UserProvider provider) {
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
