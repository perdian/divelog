package de.perdian.divelog.web.support.authentication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.UserRepository;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DummyUserFactoryBean extends AbstractFactoryBean<User> {

    private UserRepository userRepository = null;

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    protected User createInstance() throws Exception {
        List<User> allUsers = this.getUserRepository().findAll();
        if (allUsers == null || allUsers.isEmpty()) {
            User newUser = new User();
            newUser.setName("dummy");
            this.getUserRepository().save(newUser);
            return newUser;
        } else {
            return allUsers.get(0);
        }
    }

    UserRepository getUserRepository() {
        return this.userRepository;
    }
    @Autowired
    void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
