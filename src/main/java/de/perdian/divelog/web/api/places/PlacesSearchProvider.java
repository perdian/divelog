package de.perdian.divelog.web.api.places;

import java.io.IOException;
import java.util.List;

public interface PlacesSearchProvider {

    String getName();

    List<PlacesSearchResultItem> search(String query) throws IOException;

}
