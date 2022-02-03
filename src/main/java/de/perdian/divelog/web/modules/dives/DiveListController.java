package de.perdian.divelog.web.modules.dives;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUserHolder;

@Controller
@RequestMapping("/dives")
public class DiveListController {

    private DiveLogUserHolder userHolder = null;
    private DiveRepository diveRepository = null;
    private Integer maxDivesPerPage = 100;

    @RequestMapping("/list")
    public String doList() {
        return "/dives/list";
    }

    @RequestMapping("/selection")
    public String doSelection(@RequestParam("diveSelectionAction") String diveSelectionAction, @ModelAttribute("selectedDives") List<Dive> selectedDives, Model model) {
        if ("print".equalsIgnoreCase(diveSelectionAction)) {
            model.addAttribute("allDives", this.getDiveRepository().findAll(this.getUserHolder().getCurrentUser().specification(Dive.class), Dive.sortWithOldestFirst()));
            return "/dives/selection/print";
        } else {
            throw new UnsupportedOperationException("Unsupported diveSelectionAction: " + diveSelectionAction);
        }
    }

    @ModelAttribute(name = "dives", binding = false)
    public Page<Dive> dives(@RequestParam(name = "pageIndex", required = false) Integer pageIndex, @RequestParam(name = "pageSize", required = false) Integer pageSize) {

        int cleanedPageIndex = pageIndex == null ? 0 : Math.max(0, pageIndex.intValue());
        int cleanedPageSize = pageSize == null ? this.getMaxDivesPerPage() : Math.min(this.getMaxDivesPerPage(), Math.max(1, pageSize.intValue()));
        PageRequest pageRequest = PageRequest.of(cleanedPageIndex, cleanedPageSize, Dive.sortWithNewestFirst());
        Specification<Dive> specification = this.getUserHolder().getCurrentUser().specification(Dive.class);

        return this.getDiveRepository().findAll(specification, pageRequest);

    }

    @ModelAttribute(name = "selectedDives", binding = false)
    public List<Dive> selectedDives(@ModelAttribute("dives") Page<Dive> allDives, @RequestParam(name = "diveSelection", required = false) List<UUID> diveIdentifiers) {
        return diveIdentifiers == null ? Collections.emptyList() : allDives.getContent().stream()
            .filter(dive -> diveIdentifiers.contains(dive.getId()))
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

    Integer getMaxDivesPerPage() {
        return this.maxDivesPerPage;
    }
    @Value("${divelog.lists.maxDivesPerPage:100}")
    void setMaxDivesPerPage(Integer maxDivesPerPage) {
        this.maxDivesPerPage = maxDivesPerPage;
    }

}
