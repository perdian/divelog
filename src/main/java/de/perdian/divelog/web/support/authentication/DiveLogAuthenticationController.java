package de.perdian.divelog.web.support.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class DiveLogAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(DiveLogAuthenticationController.class);

    private DivelogAuthenticationType authenticationType = null;

    @RequestMapping(value = "/login")
    public String doLogin(HttpServletRequest servletRequest) {

        // If we do have an error resulting from the authentication then
        // remove it from the session and make it available in the request
        HttpSession httpSession = servletRequest.getSession(false);
        Exception authenticationException = httpSession == null ? null : (Exception)httpSession.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (httpSession != null && authenticationException != null) {
            log.debug("Exception occured during login", authenticationException);
            servletRequest.setAttribute("authenticationException", authenticationException);
            httpSession.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        } else {
            return "redirect:" + this.getAuthenticationType().getLoginUrl();
        }

        return "authentication/login";

    }

    @RequestMapping(value = "/logout/complete")
    public String doLogoutComplete() {
        return "authentication/logout";
    }

    public DivelogAuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }
    @Value("${divelog.authentication.type}")
    public void setAuthenticationType(DivelogAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

}
