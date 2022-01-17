package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String doAddPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return this.doAdd(diveEditor);
        } else {

            Dive newEntity = new Dive();
            diveEditor.applyTo(newEntity);
            newEntity.setUser(this.getCurrentUser());

            Dive savedEntity = this.getDiveRepository().save(newEntity);
            redirectAttributes.addFlashAttribute("savedDive", savedEntity);
            return "redirect:/dives/edit/" + savedEntity.getId();

        }
    }

    @GetMapping(path = "/edit/{id}")
    public String doEdit(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/edit";
    }

    @PostMapping(path = "/edit/{id}")
    public String doEditPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult, @ModelAttribute("diveEntity") Dive diveEntity, RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            diveEditor.applyTo(diveEntity);
            this.getDiveRepository().save(diveEntity);
            redirectAttributes.addFlashAttribute("savedDive", diveEntity);
            return "redirect:/dives/edit/" + diveEntity.getId();
        } else {
            return this.doEdit(diveEditor);
        }
    }

    @ModelAttribute("dive")
    DiveEditor diveEditor(@ModelAttribute("diveEntity") Dive diveEntity) {
        DiveEditor diveEditor = new DiveEditor();
        if (diveEntity != null) {
            diveEditor.applyFrom(diveEntity);
        } else {
            diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
        }
        return diveEditor;
    }

    @ModelAttribute("diveEntity")
    public Dive diveEntity(@PathVariable(name = "id", required = false) UUID diveEntityId) {
        Specification<Dive> diveEntitySpecification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
            criteriaBuilder.equal(root.get("user"), this.getCurrentUser()),
            criteriaBuilder.equal(root.get("id"), diveEntityId)
        );
        return diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ModelAttribute("nextDiveNumber")
    Long nextDiveNumber() {
        return this.getDiveRepository().countByUser(this.getCurrentUser()) + 1;
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
