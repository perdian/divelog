package de.perdian.divelog.web.api.places.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.perdian.divelog.web.api.places.PlacesSearchProvider;
import de.perdian.divelog.web.api.places.PlacesSearchResultItem;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
@ConditionalOnExpression("'${divelog.api.places.providers.google.apiKey}' != ''")
class GooglePlacesSearchProvider implements PlacesSearchProvider {

    private static final Logger log = LoggerFactory.getLogger(GooglePlacesSearchProvider.class);

    private String apiKey = null;
    private ObjectMapper objectMapper = null;
    private OkHttpClient httpClient = null;

    @Override
    public String getName() {
        return "Google Places API";
    }

    @Override
    public List<PlacesSearchResultItem> search(String query) throws IOException {

        String requestUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
        Map<String, String> requestParameters = Map.ofEntries(
            Map.entry("inputtype", "textquery"),
            Map.entry("input", query),
            Map.entry("fields", "name,formatted_address,geometry")
        );
        JsonNode placeIdentifiersResponseNode = this.sendRequest(requestUrl, requestParameters);
        JsonNode candidatesNode = placeIdentifiersResponseNode.get("candidates");
        if (candidatesNode == null || candidatesNode.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<PlacesSearchResultItem> resultItems = new ArrayList<>(candidatesNode.size());
            for (int candidateIndex = 0; candidateIndex < candidatesNode.size(); candidateIndex++) {
                JsonNode candidateNode = candidatesNode.get(candidateIndex);
                if (candidateNode != null) {
                    PlacesSearchResultItem resultItem = new PlacesSearchResultItem();
                    JsonNode geometryNode = candidateNode.get("geometry");
                    if (geometryNode != null) {
                        resultItem.setLatitude(geometryNode.get("location").get("lat").asDouble());
                        resultItem.setLongitude(geometryNode.get("location").get("lng").asDouble());
                    }
                    resultItem.setName(candidateNode.get("name").asText());
                    resultItem.setAddress(candidateNode.get("formatted_address").asText());
                    resultItem.setDescription(candidateNode.get("formatted_address").asText());
                    resultItems.add(resultItem);
                }
            }
            log.debug("Found {} places via Google Places query '{}'", resultItems.size(), query);
            return resultItems;
        }
    }

    private JsonNode sendRequest(String url, Map<String, String> parameters) throws IOException {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.get(url).newBuilder();
        parameters.forEach((key, value) -> httpUrlBuilder.addQueryParameter(key, value));
        httpUrlBuilder.addQueryParameter("key", this.getApiKey());
        Request httpRequest = new Request.Builder().get().url(httpUrlBuilder.build()).build();
        try (Response httpResponse = this.getHttpClient().newCall(httpRequest).execute()) {
            return this.getObjectMapper().readTree(httpResponse.body().charStream());
        }
    }

    private String getApiKey() {
        return this.apiKey;
    }
    @Value("${divelog.api.places.providers.google.apiKey}")
    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    @Autowired
    void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    OkHttpClient getHttpClient() {
        return this.httpClient;
    }
    @Autowired
    void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}
