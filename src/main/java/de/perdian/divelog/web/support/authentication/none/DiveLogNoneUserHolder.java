package de.perdian.divelog.web.support.authentication.none;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.web.support.authentication.DiveLogUser;
import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

class DiveLogNoneUserHolder implements DiveLogUserHolder {

    @Override
    public DiveLogUser getCurrentUser() {
        return new DiveLogUser() {

            @Override
            public User getEntity() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

        };
    }

}
