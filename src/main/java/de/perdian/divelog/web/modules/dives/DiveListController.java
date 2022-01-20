package de.perdian.divelog.web.modules.dives;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
@RequestMapping("/dives")
public class DiveListController {

    private User currentUser = null;
    private DiveRepository diveRepository = null;
    private Integer maxDivesPerPage = 100;

    @RequestMapping("/list")
    public String doList() {
        return "/dives/list";
    }

    @ModelAttribute("dives")
    public Page<Dive> dives(@RequestParam(name = "pageIndex", required = false) Integer pageIndex, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        int cleanedPageIndex = pageIndex == null ? 0 : Math.max(0, pageIndex.intValue());
        int cleanedPageSize = pageSize == null ? this.getMaxDivesPerPage() : Math.min(this.getMaxDivesPerPage(), Math.max(1, pageSize.intValue()));
        Sort pageSort = Sort.by(Order.desc("start.date"), Order.desc("start.time"));
        PageRequest pageRequest = PageRequest.of(cleanedPageIndex, cleanedPageSize, pageSort);
        Specification<Dive> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("user"), this.getCurrentUser())
        );
        return this.getDiveRepository().findAll(specification, pageRequest);
    }

    @ModelAttribute("selectedDives")
    public List<Dive> selectedDives(@RequestParam Map<String, String> allRequestParameters) {
        Set<UUID> requestedDiveIdentifiers = allRequestParameters.keySet().stream()
            .filter(parameter -> parameter.startsWith("diveSelection_"))
            .map(parameter -> parameter.substring("diveSelection_".length()))
            .map(diveIdentifier -> UUID.fromString(diveIdentifier))
            .collect(Collectors.toSet());
        if (requestedDiveIdentifiers.isEmpty()) {
            return Collections.emptyList();
        } else {
            Specification<Dive> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("user"), this.getCurrentUser()),
                root.get("id").in(requestedDiveIdentifiers)
            );
            List<Dive> selectedDives = this.getDiveRepository().findAll(specification, Sort.by(Order.desc("start.date"), Order.desc("start.time")));
            return selectedDives;
        }
    }

    User getCurrentUser() {
        return this.currentUser;
    }
    @Autowired
    void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
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
