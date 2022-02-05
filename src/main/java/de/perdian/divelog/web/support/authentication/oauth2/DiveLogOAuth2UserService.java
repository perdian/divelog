package de.perdian.divelog.web.support.authentication.oauth2;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.transaction.annotation.Transactional;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.UserRepository;
import de.perdian.divelog.web.support.authentication.DivelogAuthenticationType;

class DiveLogOAuth2UserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private DivelogAuthenticationType authenticationType = null;
    private String whitelistedUserIdentifiers = null;
    private UserRepository userRepository = null;
    private OidcUserService delegate = new OidcUserService();

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser delegateUser = this.getDelegate().loadUser(userRequest);
        String delegateId = StringUtils.defaultIfEmpty(delegateUser.getEmail(), delegateUser.getName());

        User userEntity = this.ensureUser(this.getAuthenticationType().name(), delegateId);
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
            List<String> whitelistedUserIdentifiers = StringUtils.isEmpty(this.getWhitelistedUserIdentifiers()) ? null : Arrays.asList(StringUtils.split(this.getWhitelistedUserIdentifiers(), ",; "));
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

    DivelogAuthenticationType getAuthenticationType() {
        return this.authenticationType;
    }
    @Value("${divelog.authentication.type}")
    void setAuthenticationType(DivelogAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    String getWhitelistedUserIdentifiers() {
        return this.whitelistedUserIdentifiers;
    }
    @Value("${divelog.authentication.whitelistedUserIdentifiers}")
    void setWhitelistedUserIdentifiers(String whitelistedUserIdentifiers) {
        this.whitelistedUserIdentifiers = whitelistedUserIdentifiers;
    }

    UserRepository getUserRepository() {
        return this.userRepository;
    }
    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    OidcUserService getDelegate() {
        return this.delegate;
    }
    void setDelegate(OidcUserService delegate) {
        this.delegate = delegate;
    }

}
