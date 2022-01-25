package de.perdian.divelog.web.support.converters;

import java.text.DecimalFormat;

import org.springframework.core.convert.converter.Converter;

public class NumberToStringConverter implements Converter<Number, String> {

    private DecimalFormat decimalFormat = null;

    public NumberToStringConverter(DecimalFormat decimalFormat) {
        this.setDecimalFormat(decimalFormat);
    }

    @Override
    public String convert(Number number) {
        if (number != null) {
            return this.getDecimalFormat().format(number);
        } else {
            return null;
        }
    }

    private DecimalFormat getDecimalFormat() {
        return this.decimalFormat;
    }
    private void setDecimalFormat(DecimalFormat decimalFormat) {
        this.decimalFormat = decimalFormat;
    }

}
