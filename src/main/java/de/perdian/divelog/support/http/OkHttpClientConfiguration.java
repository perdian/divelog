package de.perdian.divelog.support.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
class OkHttpClientConfiguration {

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

}
