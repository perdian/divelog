package de.perdian.divelog.web.support.authentication;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "divelog.authentication")
public class DiveLogAuthenticationSettings {

    private boolean required = false;
    private String oauth2Provider = "google";
    private Map<String, String> whitelistedUserIdentifiers = null;

    public boolean isRequired() {
        return this.required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getOauth2Provider() {
        return this.oauth2Provider;
    }
    public void setOauth2Provider(String oauth2Provider) {
        this.oauth2Provider = oauth2Provider;
    }

    public Map<String, String> getWhitelistedUserIdentifiers() {
        return this.whitelistedUserIdentifiers;
    }
    public void setWhitelistedUserIdentifiers(Map<String, String> whitelistedUserIdentifiers) {
        this.whitelistedUserIdentifiers = whitelistedUserIdentifiers;
    }

}
