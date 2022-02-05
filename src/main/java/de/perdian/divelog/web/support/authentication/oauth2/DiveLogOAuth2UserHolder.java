package de.perdian.divelog.web.support.authentication.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import de.perdian.divelog.web.support.authentication.DiveLogUser;
import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

@Component
class DiveLogOAuth2UserHolder implements DiveLogUserHolder {

    @Override
    public DiveLogUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication == null ? null : authentication.getPrincipal();
        if (principal instanceof DiveLogUser) {
            return DiveLogUser.class.cast(principal);
        } else {
            return null;
        }
    }

}
