package de.perdian.divelog.web.support.spring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ModelContributorAdvice {

    private Locale locale = null;
    private Locale language = null;

    @ModelAttribute("locale")
    public String locale() {
        return this.getLocale().toString();
    }

    @ModelAttribute("language")
    public String language() {
        return this.getLanguage().toString();
    }

    Locale getLocale() {
        return this.locale;
    }
    @Value("${divelog.locale}")
    void setLocale(Locale locale) {
        this.locale = locale;
    }

    Locale getLanguage() {
        return this.language;
    }
    @Value("${divelog.language}")
    void setLanguage(Locale language) {
        this.language = language;
    }

}
