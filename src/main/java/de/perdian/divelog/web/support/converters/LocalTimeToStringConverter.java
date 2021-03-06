package de.perdian.divelog.web.support.converters;

import java.time.LocalTime;

import org.springframework.core.convert.converter.Converter;

public class LocalTimeToStringConverter implements Converter<LocalTime, String> {

    @Override
    public String convert(LocalTime source) {
        return source == null ? null : source.toString();
    }

}
