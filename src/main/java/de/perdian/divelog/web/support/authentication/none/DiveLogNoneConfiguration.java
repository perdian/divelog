package de.perdian.divelog.web.support.authentication.none;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

@Configuration
@ConditionalOnProperty(name = "divelog.authentication.type", havingValue = "none")
class DiveLogNoneConfiguration {

    @Bean
    DiveLogUserHolder userHolder() {
        return new DiveLogNoneUserHolder();
    }

}
