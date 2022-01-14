package de.perdian.divelog.web.api.places;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class PlacesSearchServiceImpl implements PlacesSearchService {

    private PlacesSearchProvider provider = null;
    private List<PlacesSearchResultEnhancer> enhancers = Collections.emptyList();

    @Override
    public PlacesSearchResult search(String query) throws IOException {
        PlacesSearchProvider provider = this.getProvider();
        if (provider == null) {
            return new PlacesSearchResult(false, null, Collections.emptyList());
        } else {
            List<PlacesSearchResultItem> providerItems = provider.search(query);
            PlacesSearchResult providerResult = new PlacesSearchResult(true, provider.getName(), providerItems);
            for (PlacesSearchResultEnhancer enhancer : this.getEnhancers()) {
                enhancer.enhanceResult(providerResult);
            }
            return providerResult;
        }
    }

    PlacesSearchProvider getProvider() {
        return this.provider;
    }
    @Autowired(required = false)
    void setProvider(PlacesSearchProvider provider) {
        this.provider = provider;
    }

    List<PlacesSearchResultEnhancer> getEnhancers() {
        return this.enhancers;
    }
    @Autowired(required = false)
    void setEnhancers(List<PlacesSearchResultEnhancer> enhancers) {
        this.enhancers = enhancers;
    }

}
