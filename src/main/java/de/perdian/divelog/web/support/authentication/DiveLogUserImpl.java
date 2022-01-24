package de.perdian.divelog.web.support.authentication;

import org.springframework.data.jpa.domain.Specification;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.entities.UserContainer;

class DiveLogUserImpl implements DiveLogUser {

    private User userEntity = null;

    DiveLogUserImpl(User userEntity) {
        this.setUserEntity(userEntity);
    }

    @Override
    public <T extends UserContainer> Specification<T> specification(Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), this.getUserEntity());
    }

    @Override
    public User getUserEntity() {
        return this.userEntity;
    }
    private void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

}
