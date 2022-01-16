package de.perdian.divelog.web.modules.dives;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String doAddPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult) {
        diveEditor.setDiveEntityId(UUID.randomUUID()); // TODO: REMOVE ME!
        return "/dives/edit";
    }

    @GetMapping(path = "/edit/{id}")
    public String doEdit(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/edit";
    }

    @PostMapping(path = "/edit/{id}")
    public String doEditPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult) {
        return "/dives/edit";
    }

    @ModelAttribute("dive")
    DiveEditor diveEditor(@PathVariable(name = "id", required = false) UUID diveEntityId) {

//        Specification<Dive> diveEntitySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
//            criteriaBuilder.equal(root.get("user"), this.getCurrentUser()),
//            criteriaBuilder.equal(root.get("id"), diveEntityId)
//        );
//        Dive diveEntity = diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Dive diveEntity = new Dive();
        diveEntity.setId(UUID.randomUUID());

        DiveEditor diveEditor = new DiveEditor(diveEntity);
        diveEditor.setNumber(this.getDiveRepository().countByUser(this.getCurrentUser()) + 1);
//        diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
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
