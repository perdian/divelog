package de.perdian.divelog.web.modules.overview;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
public class OverviewController {

    private static final Logger log = LoggerFactory.getLogger(OverviewController.class);

    private DiveRepository diveRepository = null;
    private User currentUser = null;
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
        Sort sort = Sort.by(Order.desc("start.date"), Order.desc("start.time"));
        Specification<Dive> specification = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), this.getCurrentUser());
        return this.getDiveRepository().findAll(specification, sort);
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

    User getCurrentUser() {
        return this.currentUser;
    }
    @Autowired
    void setCurrentUser(User currentUser) {
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
