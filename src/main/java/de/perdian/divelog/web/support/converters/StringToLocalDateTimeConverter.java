package de.perdian.divelog.web.support.converters;

import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return StringUtils.isEmpty(source) ? null : LocalTime.parse(source);
    }

}
