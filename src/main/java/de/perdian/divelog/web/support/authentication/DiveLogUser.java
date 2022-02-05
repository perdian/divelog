package de.perdian.divelog.web.support.authentication;

import org.springframework.data.jpa.domain.Specification;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.entities.UserContainer;

public interface DiveLogUser {

    User getEntity();
    String getUsername();

    default <T extends UserContainer> Specification<T> specification(Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> {
            if (this.getEntity() == null) {
                return criteriaBuilder.isNull(root.get("user"));
            } else {
                return criteriaBuilder.equal(root.get("user"), this.getEntity());
            }
        };
    }

}
