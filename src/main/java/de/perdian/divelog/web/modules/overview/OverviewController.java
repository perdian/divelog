package de.perdian.divelog.web.modules.overview;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUser;

@Controller
public class OverviewController {

    private static final Logger log = LoggerFactory.getLogger(OverviewController.class);

    private DiveRepository diveRepository = null;
    private DiveLogUser currentUser = null;
    private List<OverviewContributor> overviewContributors = null;

    @GetMapping(path = { "/", "/overview" })
    public String doOverview() {
        return "/overview/overview";
    }

    @ModelAttribute(name = "overview", binding = false)
    public Overview overview(@ModelAttribute("dives") List<Dive> dives, @ModelAttribute("overviewOptions") OverviewOptions overviewOptions) {
        Overview overview = new Overview();
        for (OverviewContributor contributor : this.getOverviewContributors()) {
            log.trace("Invoking OverviewContributor '{}' for {} dives using options: {}", contributor, dives.size(), overviewOptions);
            contributor.contribute(overview, dives, overviewOptions);
        }
        return overview;
    }

    @ModelAttribute(name = "dives", binding = false)
    public List<Dive> dives() {
        Specification<Dive> specification = this.getCurrentUser().specification(Dive.class);
        return this.getDiveRepository().findAll(specification, Dive.sortWithNewestFirst());
    }

    @ModelAttribute(name = "overviewQuery")
    public OverviewQuery overviewQuery() {
        return new OverviewQuery();
    }

    @ModelAttribute(name = "overviewOptions")
    public OverviewOptions overviewOptions() {
        return new OverviewOptions();
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

    DiveLogUser getCurrentUser() {
        return this.currentUser;
    }
    @Autowired
    void setCurrentUser(DiveLogUser currentUser) {
        this.currentUser = currentUser;
    }

    List<OverviewContributor> getOverviewContributors() {
        return this.overviewContributors;
    }
    @Autowired
    void setOverviewContributors(List<OverviewContributor> overviewContributors) {
        this.overviewContributors = overviewContributors;
    }

}
