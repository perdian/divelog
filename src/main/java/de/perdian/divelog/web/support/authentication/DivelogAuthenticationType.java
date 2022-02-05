package de.perdian.divelog.web.support.authentication;

public enum DivelogAuthenticationType {

    NONE(false, null),
    GOOGLE(true, "/oauth2/authorization/google");

    private boolean loginRequired = false;
    private String loginUrl = null;

    private DivelogAuthenticationType(boolean loginRequired, String loginUrl) {
        this.setLoginRequired(loginRequired);
        this.setLoginUrl(loginUrl);
    }

    public boolean isLoginRequired() {
        return this.loginRequired;
    }
    private void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public String getLoginUrl() {
        return this.loginUrl;
    }
    private void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

}
