package de.perdian.divelog.web.api.locations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.components.Location;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

@RestController
@RequestMapping("/api/locations")
public class LocationsController {

    private DiveLogUserHolder userHolder = null;
    private DiveRepository diveRepository = null;

    @GetMapping("/overview")
    public LocationsResult doOverview() {

        Specification<Dive> specification = this.getUserHolder().getCurrentUser().specification(Dive.class);
        List<Dive> allDives = this.getDiveRepository().findAll(specification);

        LocationsResult result = new LocationsResult();
        result.setLocations(this.filterLocations(allDives, dive -> dive.getStart() == null ? null : dive.getStart().getLocation()));
        result.setSpots(this.filterLocations(allDives, dive -> dive.getSpot() == null ? null : dive.getSpot().getLocation()));
        return result;

    }

    private List<LocationsResultItem> filterLocations(List<Dive> allDives, Function<Dive, Location> locationFunction) {

        Map<LocationsCoordinates, AtomicInteger> coordinatesCounter = new HashMap<>();
        for (Dive dive : allDives) {
            Location location = locationFunction.apply(dive);
            Double longitude = location == null ? null : location.getLongitude();
            Double latitude = location == null ? null : location.getLatitude();
            if (longitude != null && latitude != null) {
                coordinatesCounter.compute(new LocationsCoordinates(longitude, latitude), (k, v) -> v == null ? new AtomicInteger() : v).incrementAndGet();
            }
        }

        return coordinatesCounter.entrySet().stream()
            .map(entry -> new LocationsResultItem(entry.getKey(), entry.getValue().intValue()))
            .toList();

    }

    DiveLogUserHolder getUserHolder() {
        return this.userHolder;
    }
    @Autowired
    void setUserHolder(DiveLogUserHolder userHolder) {
        this.userHolder = userHolder;
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

}
