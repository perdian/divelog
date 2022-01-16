package de.perdian.divelog.web;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.perdian.divelog.support.converters.NumberToStringConverter;
import de.perdian.divelog.support.converters.StringToNumberConverter;

@Configuration
class WebConfigurationn implements WebMvcConfigurer {

    @Value("${divelog.locale}")
    private Locale locale = Locale.GERMANY;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Double.class, new StringToNumberConverter<>(Double.class, new DecimalFormat("0.######", new DecimalFormatSymbols(this.getLocale()))));
        registry.addConverter(Double.class, String.class, new NumberToStringConverter(new DecimalFormat("0.######", new DecimalFormatSymbols(this.getLocale()))));
        WebMvcConfigurer.super.addFormatters(registry);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }

    public Locale getLocale() {
        return this.locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
