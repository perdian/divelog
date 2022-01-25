package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.perdian.divelog.model.entities.Dive;
import de.perdian.divelog.model.entities.components.PlaceAndTime;
import de.perdian.divelog.model.repositories.DiveRepository;
import de.perdian.divelog.web.support.authentication.DiveLogUser;

@Controller
@RequestMapping("/dives")
public class DiveEditorController {

    private DiveLogUser currentUser = null;
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
            newEntity.setUser(this.getCurrentUser().getUserEntity());

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

    @GetMapping(path = "/image/{id}")
    public ResponseEntity<?> doImage(@ModelAttribute("dive") DiveEditor diveEditor) {
        if (diveEditor.getImage() == null || !diveEditor.getImage().isAvailable()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity
                .ok()
                .contentLength(diveEditor.getImage().getJpegBytes().length)
                .contentType(MediaType.IMAGE_JPEG)
                .eTag(DigestUtils.md5DigestAsHex(diveEditor.getImage().getJpegBytes()))
                .body(diveEditor.getImage().getJpegBytes());
        }
    }

    @ModelAttribute(name = "dive")
    DiveEditor diveEditor(@ModelAttribute("diveEntity") Dive diveEntity) {
        DiveEditor diveEditor = new DiveEditor();
        if (diveEntity != null) {
            diveEditor.applyFrom(diveEntity);
        } else {
            diveEditor.setStart(new PlaceAndTime(LocalDate.now(), null));
        }
        return diveEditor;
    }

    @ModelAttribute(name = "diveEntity", binding = false)
    public Dive diveEntity(@PathVariable(name = "id", required = false) UUID diveEntityId) {
        Specification<Dive> diveEntitySpecification = this.getCurrentUser().specification(Dive.class).and(
            (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), diveEntityId)
        );
        return diveEntityId == null ? null : this.getDiveRepository().findOne(diveEntitySpecification).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @ModelAttribute(name = "nextDiveNumber", binding = false)
    Long nextDiveNumber() {
        return this.getDiveRepository().count(this.getCurrentUser().specification(Dive.class)) + 1;
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

}
