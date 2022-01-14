package de.perdian.divelog.web.api.places.providers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.perdian.divelog.web.api.places.PlacesSearchProvider;
import de.perdian.divelog.web.api.places.PlacesSearchResultItem;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class GooglePlacesSearchProvider implements PlacesSearchProvider {

    private static final Logger log = LoggerFactory.getLogger(GooglePlacesSearchProvider.class);

    private ObjectMapper objectMapper = null;
    private String apiKey = null;

    GooglePlacesSearchProvider(String apiKey) {
        this.setApiKey(apiKey);
    }

    @Override
    public String getName() {
        return "Google Places API";
    }

    @Override
    public List<PlacesSearchResultItem> search(String query) throws IOException {
        List<String> placeIdentifiers = this.loadPlaceIdentifiers(query);
        List<PlacesSearchResultItem> placeDetails = this.loadPlaceDetails(placeIdentifiers);

        log.debug("Found {} place details for {} Google Places [Query: '{}']", placeDetails.size(), placeIdentifiers.size(), query);
        return placeDetails;
    }

    private List<PlacesSearchResultItem> loadPlaceDetails(List<String> placeIdentifiers) throws IOException {
        List<PlacesSearchResultItem> resultItems = new ArrayList<>(placeIdentifiers.size());
        String requestUrl = "https://maps.googleapis.com/maps/api/place/details/json";
        for (String placeIdentfier : placeIdentifiers) {
            JsonNode placeDetailsNode = this.sendRequest(requestUrl, Map.of("place_id", placeIdentfier));
            JsonNode resultNode = placeDetailsNode.get("result");
            if (resultNode != null) {
                PlacesSearchResultItem resultItem = new PlacesSearchResultItem();
                JsonNode geometryNode = resultNode.get("geometry");
                if (geometryNode != null) {
                    resultItem.setLatitude(geometryNode.get("location").get("lat").asDouble());
                    resultItem.setLongitude(geometryNode.get("location").get("lng").asDouble());
                }
                resultItem.setName(resultNode.get("name").asText());
                resultItems.add(resultItem);
            }
        }
        return resultItems;
    }

    private List<String> loadPlaceIdentifiers(String query) throws IOException {
        String requestUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json";
        Map<String, String> requestParameters = Map.ofEntries(Map.entry("inputtype", "textquery"), Map.entry("input", query));
        JsonNode placeIdentifiersResponseNode = this.sendRequest(requestUrl, requestParameters);
        JsonNode candidatesNode = placeIdentifiersResponseNode.get("candidates");
        if (candidatesNode == null || candidatesNode.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<String> placeIdentifiers = new ArrayList<>();
            for (int candidateIndex = 0; candidateIndex < candidatesNode.size(); candidateIndex++) {
                JsonNode candidateNode = candidatesNode.get(candidateIndex);
                String placeId = candidateNode.get("place_id").asText(null);
                if (StringUtils.isNotEmpty(placeId)) {
                    placeIdentifiers.add(placeId);
                }
            }
            return placeIdentifiers;
        }
    }

    private JsonNode sendRequest(String url, Map<String, String> parameters) throws IOException {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.get(url).newBuilder();
        parameters.forEach((key, value) -> httpUrlBuilder.addQueryParameter(key, value));
        httpUrlBuilder.addQueryParameter("key", this.getApiKey());
        Request httpRequest = new Request.Builder().get().url(httpUrlBuilder.build()).build();
        OkHttpClient httpClient = new OkHttpClient();
        try (Response httpResponse = httpClient.newCall(httpRequest).execute()) {
            return this.getObjectMapper().readTree(httpResponse.body().charStream());
        }
    }

    private String getApiKey() {
        return this.apiKey;
    }
    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    @Autowired void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
