package de.perdian.divelog.web.api.places;

import java.io.IOException;

public interface PlacesSearchResultEnhancer {

    void enhanceResult(PlacesSearchResult result) throws IOException;

}
