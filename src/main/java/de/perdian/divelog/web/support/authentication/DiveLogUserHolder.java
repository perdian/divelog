package de.perdian.divelog.web.support.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DiveLogUserHolder {

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
