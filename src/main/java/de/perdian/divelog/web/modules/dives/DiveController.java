package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import de.perdian.divelog.model.components.PlaceAndTime;
import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.User;
import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
@RequestMapping("/dives")
public class DiveController {

    private User currentUser = null;
    private DiveRepository diveRepository = null;

    @GetMapping(path = "/add")
    public String doAdd(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/add";
    }

    @PostMapping(path = "/add")
    public String doAddPost(@ModelAttribute("dive") DiveEditor diveEditor) {
        diveEditor.setDiveEntityId(UUID.randomUUID()); // TODO: REMOVE ME!
        return "/dives/edit";
    }

    @GetMapping(path = "/edit/{id}")
    public String doEdit(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/edit";
    }

    @PostMapping(path = "/edit/{id}")
    public String doEditPost(@ModelAttribute("dive") DiveEditor diveEditor) {
        throw new UnsupportedOperationException();
    }

    @ModelAttribute("dive")
    DiveEditor diveEditor(@PathVariable(name = "id", required = false) UUID diveEntityId) {

        Specification<Dive> diveEntitySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("user"), this.getCurrentUser()),
            criteriaBuilder.equal(root.get("id"), diveEntityId)
        );
        Dive diveEntity = diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        DiveEditor diveEditor = new DiveEditor(diveEntity);
        diveEditor.setNumber(this.getDiveRepository().countByUser(this.getCurrentUser()) + 1);
        diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
        return diveEditor;

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

}
