package de.perdian.divelog.web.modules.overview;

import java.util.List;

import de.perdian.divelog.model.entities.Dive;

@FunctionalInterface
public interface OverviewContributor {

    void contribute(Overview overview, List<Dive> dives, OverviewOptions options);

}
