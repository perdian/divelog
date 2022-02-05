package de.perdian.divelog.web.support.authentication.oauth2;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.web.support.authentication.DiveLogUser;

class DiveLogOAuth2User extends DefaultOidcUser implements DiveLogUser {

    static final long serialVersionUID = 1L;

    private User userEntity = null;

    DiveLogOAuth2User(Collection<? extends GrantedAuthority> mappedAuthorities, OidcIdToken idToken, OidcUserInfo userInfo) {
        super(new HashSet<>(mappedAuthorities), idToken, userInfo);
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public User getEntity() {
        return this.userEntity;
    }
    void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

}
