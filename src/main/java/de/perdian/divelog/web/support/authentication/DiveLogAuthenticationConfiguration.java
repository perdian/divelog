package de.perdian.divelog.web.support.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class DiveLogAuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    private DivelogAuthenticationType authenticationType = null;
    private OAuth2UserService<OidcUserRequest, OidcUser> oauth2UserService = null;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (this.getAuthenticationType().isLoginRequired()) {
            http.oauth2Login().loginPage("/authentication/login").userInfoEndpoint().oidcUserService(this.getOauth2UserService());
            http.authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/authentication/logout/complete").permitAll()
                .antMatchers("/authentication/login").permitAll()
                .anyRequest().authenticated();
            http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/authentication/logout"))
                .logoutSuccessUrl("/authentication/logout/complete");
            http.rememberMe().disable();
        } else {
            http.authorizeRequests().anyRequest().permitAll();
        }
        http.csrf().disable();
    }

    DivelogAuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }
    @Value("${divelog.authentication.type}")
    void setAuthenticationType(DivelogAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    OAuth2UserService<OidcUserRequest, OidcUser> getOauth2UserService() {
        return this.oauth2UserService;
    }
    @Autowired(required = false)
    void setOauth2UserService(OAuth2UserService<OidcUserRequest, OidcUser> oauth2UserService) {
        this.oauth2UserService = oauth2UserService;
    }

}
