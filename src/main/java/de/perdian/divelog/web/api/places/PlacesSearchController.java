package de.perdian.divelog.web.api.places;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/places")
public class PlacesSearchController {

    private PlacesSearchService searchService = null;

    @GetMapping(path = "/search")
    public PlacesSearchResult doSearch(@RequestParam("q") String query) throws IOException {
        return this.getSearchService().search(query);
    }

    PlacesSearchService getSearchService() {
        return this.searchService;
    }
    @Autowired
    void setSearchService(PlacesSearchService searchService) {
        this.searchService = searchService;
    }

}
