package de.perdian.divelog.web.support.authentication.oauth2;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.UserRepository;
import de.perdian.divelog.web.support.authentication.DiveLogAuthenticationSettings;

@Service
public class DiveLogOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private OidcUserService delegate = new OidcUserService();
    private DiveLogAuthenticationSettings authenticationSettings = null;
    private UserRepository userRepository = null;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser delegateUser = this.getDelegate().loadUser(userRequest);
        String delegateId = StringUtils.defaultIfEmpty(delegateUser.getEmail(), delegateUser.getName());

        User userEntity = this.ensureUser(this.getAuthenticationSettings().getOauth2Provider(), delegateId);
        DiveLogOAuth2User user = new DiveLogOAuth2User(delegateUser.getAuthorities(), delegateUser.getIdToken(), delegateUser.getUserInfo());
        user.setUserEntity(userEntity);
        return user;

    }

    private User ensureUser(String provider, String providerId) {
        Specification<User> userSpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("provider"), provider), criteriaBuilder.equal(root.get("providerId"), providerId));
        User userEntity = this.getUserRepository().findOne(userSpecification).orElse(null);
        if (userEntity != null) {
            return userEntity;
        } else {
            String whitelistedUserIdentifiersString = this.getAuthenticationSettings().getWhitelistedUserIdentifiers() == null ? null : this.getAuthenticationSettings().getWhitelistedUserIdentifiers().get(provider);
            List<String> whitelistedUserIdentifiers = StringUtils.isEmpty(whitelistedUserIdentifiersString) ? null : Arrays.asList(StringUtils.split(whitelistedUserIdentifiersString, ",; "));
            if (whitelistedUserIdentifiers == null || whitelistedUserIdentifiers.isEmpty() || whitelistedUserIdentifiers.contains(providerId)) {
                userEntity = new User();
                userEntity.setProvider(provider);
                userEntity.setProviderId(providerId);
                return this.getUserRepository().save(userEntity);
            } else {
                throw new DisabledException("Account creation disabled");
            }
        }
    }

    OidcUserService getDelegate() {
        return this.delegate;
    }
    void setDelegate(OidcUserService delegate) {
        this.delegate = delegate;
    }

    DiveLogAuthenticationSettings getAuthenticationSettings() {
        return this.authenticationSettings;
    }
    @Autowired
    void setAuthenticationSettings(DiveLogAuthenticationSettings authenticationSettings) {
        this.authenticationSettings = authenticationSettings;
    }

    UserRepository getUserRepository() {
        return this.userRepository;
    }
    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
