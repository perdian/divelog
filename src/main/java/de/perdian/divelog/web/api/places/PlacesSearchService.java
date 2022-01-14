package de.perdian.divelog.web.api.places;

import java.io.IOException;

public interface PlacesSearchService {

    PlacesSearchResult search(String query) throws IOException;

}
