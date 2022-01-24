package de.perdian.divelog.web.support.authentication;

import org.springframework.data.jpa.domain.Specification;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.entities.UserContainer;

public interface DiveLogUser {

    User getUserEntity();

    <T extends UserContainer> Specification<T> specification(Class<T> entityClass);

}
