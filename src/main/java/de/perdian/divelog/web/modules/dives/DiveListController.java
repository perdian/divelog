package de.perdian.divelog.web.modules.dives;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
@RequestMapping("/dives")
public class DiveListController {

    private User currentUser = null;
    private DiveRepository diveRepository = null;

    @RequestMapping("/list")
    public String doList() {
        return "/dives/list";
    }

    @ModelAttribute("dives")
    public List<Dive> dives() {
        Specification<Dive> diveEntitySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("user"), this.getCurrentUser())
        );
        Sort diveEntitySort = Sort.by(
            Order.asc("start.date"), Order.asc("start.time")
        );
        return this.getDiveRepository().findAll(diveEntitySpecification, diveEntitySort);
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

}
