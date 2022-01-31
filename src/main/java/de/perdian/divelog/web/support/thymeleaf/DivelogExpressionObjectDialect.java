package de.perdian.divelog.web.support.thymeleaf;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import de.perdian.divelog.model.entities.Dive;

@Component
public class DivelogExpressionObjectDialect implements IExpressionObjectDialect {

    private Locale locale = null;

    @Override
    public String getName() {
        return "divelog";
    }

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return false;
            }

            @Override
            public Set<String> getAllExpressionObjectNames() {
                return Set.of("divelog");
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                return DivelogExpressionObjectDialect.this;
            }

        };
    }

    public String formatNumber(Number number, String pattern) {
        if (number == null) {
            return null;
        } else {
            return new DecimalFormat(pattern, new DecimalFormatSymbols(this.getLocale())).format(number);
        }
    }

    public String formatTemporal(TemporalAccessor value, String pattern) {
        if (value == null) {
            return null;
        } else {
            return DateTimeFormatter.ofPattern(pattern, this.getLocale()).format(value);
        }
    }

    public String formatMinutesAsDuration(Integer value) {
        return this.formatDuration(value == null ? null : Duration.ofMinutes(value));
    }

    public String formatDuration(Duration value) {
        if (value == null) {
            return null;
        } else {
            DecimalFormat format = new DecimalFormat("00");
            StringBuilder result = new StringBuilder();
            result.append(format.format(value.toHours())).append(":").append(format.format(value.toMinutesPart()));
            return result.toString();
        }
    }

    public double sumTotalHours(List<Dive> allDives, Dive untilDive) {
        int untilDiveIndex = allDives.indexOf(untilDive);
        List<Dive> countDives = untilDiveIndex < 0 ? Collections.emptyList() : allDives.subList(untilDiveIndex, allDives.size());

        return countDives.stream()
            .mapToInt(dive -> dive.getTotalTimeMinutes() == null ? 0 : dive.getTotalTimeMinutes().intValue())
            .sum() / 60d;
    }

    Locale getLocale() {
        return this.locale;
    }
    @Value("${divelog.locale}")
    void setLocale(Locale locale) {
        this.locale = locale;
    }

}
