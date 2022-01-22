package de.perdian.divelog.web.modules.overview.contributors;

import java.util.List;

import org.springframework.stereotype.Component;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.web.modules.overview.Overview;
import de.perdian.divelog.web.modules.overview.OverviewContributor;
import de.perdian.divelog.web.modules.overview.OverviewOptions;

@Component
class AggregatedDurationsContributor implements OverviewContributor {

    @Override
    public void contribute(Overview overview, List<Dive> dives, OverviewOptions options) {
        overview.setAggregatedTotalTimeMinutes(
            dives.stream()
                .mapToInt(dive -> dive.getTotalTimeMinutes() == null ? 0 : dive.getTotalTimeMinutes().intValue())
                .sum()
        );
        overview.setAggregatedBottomTimeMinutes(
            dives.stream()
                .mapToInt(dive -> dive.getBottomTimeMinutes() == null ? 0 : dive.getBottomTimeMinutes().intValue())
                .sum()
        );
    }

}
