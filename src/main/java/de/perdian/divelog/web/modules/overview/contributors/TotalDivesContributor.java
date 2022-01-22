package de.perdian.divelog.web.modules.overview.contributors;

import java.util.List;

import org.springframework.stereotype.Component;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.web.modules.overview.Overview;
import de.perdian.divelog.web.modules.overview.OverviewContributor;
import de.perdian.divelog.web.modules.overview.OverviewOptions;

@Component
class TotalDivesContributor implements OverviewContributor {

    @Override
    public void contribute(Overview overview, List<Dive> dives, OverviewOptions options) {
        overview.setTotalDives(dives.size());
    }

}
