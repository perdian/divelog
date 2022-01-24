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
public class DiveLogUserFactoryBean extends AbstractFactoryBean<DiveLogUser> {

    private UserRepository userRepository = null;

    @Override
    public Class<?> getObjectType() {
        return DiveLogUser.class;
    }

    @Override
    protected DiveLogUser createInstance() throws Exception {
        List<User> allUsers = this.getUserRepository().findAll();
        if (allUsers == null || allUsers.isEmpty()) {
            User newUser = new User();
            newUser.setName("dummy");
            this.getUserRepository().save(newUser);
            return new DiveLogUserImpl(newUser);
        } else {
            return new DiveLogUserImpl(allUsers.get(0));
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
