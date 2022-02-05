package de.perdian.divelog.web.support.authentication.oauth2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

@Configuration
@ConditionalOnProperty(name = "divelog.authentication.type", havingValue = "google")
class DiveLogOAuth2Configuration {

    @Bean
    DiveLogUserHolder userHolder() {
        return new DiveLogOAuth2UserHolder();
    }

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> userService() {
        return new DiveLogOAuth2UserService();
    }

}
