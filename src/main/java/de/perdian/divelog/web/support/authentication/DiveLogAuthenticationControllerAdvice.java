package de.perdian.divelog.web.support.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DiveLogAuthenticationControllerAdvice {

    private DiveLogUserHolder userHolder = null;

    @ModelAttribute("user")
    public DiveLogUser user() {
        return this.getUserHolder().getCurrentUser();
    }

    DiveLogUserHolder getUserHolder() {
        return this.userHolder;
    }
    @Autowired
    void setUserHolder(DiveLogUserHolder userHolder) {
        this.userHolder = userHolder;
    }

}
