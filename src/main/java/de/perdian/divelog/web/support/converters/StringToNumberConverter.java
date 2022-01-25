package de.perdian.divelog.web.support.converters;

import java.text.DecimalFormat;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

public class StringToNumberConverter<T extends Number> implements Converter<String, T> {

    private Class<T> numberClass = null;
    private DecimalFormat decimalFormat = null;

    public StringToNumberConverter(Class<T> numberClass, DecimalFormat decimalFormat) {
        this.setNumberClass(numberClass);
        this.setDecimalFormat(decimalFormat);
    }

    @Override
    public T convert(String source) {
        String trimmedSource = source == null ? null : source.trim();
        if (StringUtils.hasText(trimmedSource)) {
            return NumberUtils.parseNumber(trimmedSource, this.getNumberClass(), this.getDecimalFormat());
        } else {
            return null;
        }
    }

    private Class<T> getNumberClass() {
        return this.numberClass;
    }
    private void setNumberClass(Class<T> numberClass) {
        this.numberClass = numberClass;
    }

    private DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }
    private void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

}
