package de.perdian.divelog.web.api.places.enhancers;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import de.perdian.divelog.web.api.places.PlacesSearchResult;
import de.perdian.divelog.web.api.places.PlacesSearchResultEnhancer;
import de.perdian.divelog.web.api.places.PlacesSearchResultItem;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
@ConditionalOnExpression("'${divelog.api.places.enhancers.geonames.username}' != ''")
class GeonamesSearchResultEnhancer implements PlacesSearchResultEnhancer {

    private static final Logger log = LoggerFactory.getLogger(GeonamesSearchResultEnhancer.class);

    private String username = null;
    private OkHttpClient httpClient = null;
    private Map<String, GeonamesCacheEntry> cache = new HashMap<>();

    @Override
    public void enhanceResult(PlacesSearchResult result) {
        for (PlacesSearchResultItem resultItem : result.getItems()) {
            if (resultItem.getTimezoneId() == null && resultItem.getLongitude() != null && resultItem.getLatitude() != null) {
                try {
                    resultItem.setTimezoneId(this.computeTimezoneId(resultItem.getLongitude(), resultItem.getLatitude()));
                } catch (Exception e) {
                    log.warn("Cannot load timezone information from Geonames for result item: '{}'", resultItem);
                }
            }
        }
    }

    private ZoneId computeTimezoneId(Double longitude, Double latitude) throws Exception {
        String cacheKey = longitude + "@" + latitude;
        GeonamesCacheEntry cacheEntry = this.getCache().get(cacheKey);
        if (cacheEntry == null || cacheEntry.isExpired()) {
            ZoneId timezoneId = this.computeTimezoneIdFromGeonames(longitude, latitude);
            cacheEntry = new GeonamesCacheEntry(timezoneId, Instant.now().plus(1, ChronoUnit.HOURS));
            this.getCache().put(cacheKey, cacheEntry);
        }
        return cacheEntry.getTimezoneId();
    }

    private ZoneId computeTimezoneIdFromGeonames(Double longitude, Double latitude) throws Exception {
        StringBuilder geonamesRequestUrl = new StringBuilder();
        geonamesRequestUrl.append("http://api.geonames.org/timezone");
        geonamesRequestUrl.append("?lng=").append(longitude);
        geonamesRequestUrl.append("&lat=").append(latitude);
        geonamesRequestUrl.append("&username=").append(this.getUsername());
        Request geonamesRequest = new Request.Builder().get().url(geonamesRequestUrl.toString()).build();
        try (Response geonamesResponse = this.getHttpClient().newCall(geonamesRequest).execute()) {
            SAXReader saxReader = new SAXReader();
            Document geonamesDocument = saxReader.read(geonamesResponse.body().charStream());
            Element timezoneElement = geonamesDocument.getRootElement().element("timezone");
            Element timezoneIdElement = timezoneElement == null ? null : timezoneElement.element("timezoneId");
            String timezoneIdValue = timezoneIdElement == null ? null : timezoneIdElement.getTextTrim();
            return timezoneIdValue == null ? null : ZoneId.of(timezoneIdValue);
        }
    }

    private static class GeonamesCacheEntry implements Serializable {

        static final long serialVersionUID = 1L;

        private ZoneId timezoneId = null;
        private Instant expiration = null;

        private GeonamesCacheEntry(ZoneId timezoneId, Instant expiration) {
            this.setTimezoneId(timezoneId);
            this.setExpiration(expiration);
        }

        private boolean isExpired() {
            return this.getExpiration().isAfter(Instant.now());
        }

        private ZoneId getTimezoneId() {
            return this.timezoneId;
        }
        private void setTimezoneId(ZoneId timezoneId) {
            this.timezoneId = timezoneId;
        }

        private Instant getExpiration() {
            return this.expiration;
        }
        private void setExpiration(Instant expiration) {
            this.expiration = expiration;
        }

    }

    String getUsername() {
        return this.username;
    }
    @Value("${divelog.api.places.enhancers.geonames.username}")
    void setUsername(String username) {
        this.username = username;
    }

    OkHttpClient getHttpClient() {
        return this.httpClient;
    }
    @Autowired
    void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    Map<String, GeonamesCacheEntry> getCache() {
        return this.cache;
    }
    void setCache(Map<String, GeonamesCacheEntry> cache) {
        this.cache = cache;
    }

}
