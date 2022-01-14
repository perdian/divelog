package de.perdian.divelog.web.api.places.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("'${divelog.api.places.providers.google.apiKey}' != ''")
class GooglePlacesSearchConfiguration {

    @Value("${divelog.api.places.providers.google.apiKey}")
    private String apiKey = null;

    @Bean
    GooglePlacesSearchProvider googlePlacesSearchProvider() {
        return new GooglePlacesSearchProvider(this.getApiKey());
    }

    String getApiKey() {
        return this.apiKey;
    }
    void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
