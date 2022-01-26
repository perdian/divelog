package de.perdian.divelog.web.modules.dives;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.perdian.divelog.model.entities.Dive;

@Controller
@RequestMapping("/dives")
public class DiveEditorController {

    private DiveEditorService diveEditorService = null;

    @GetMapping(path = "/add")
    public String doAdd(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/add";
    }

    @PostMapping(path = "/add")
    public String doAddPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return this.doAdd(diveEditor);
        } else {
            Dive createdDiveEntity = this.getDiveEditorService().createDiveFromEditor(diveEditor);
            redirectAttributes.addFlashAttribute("updatedDive", createdDiveEntity);
            return "redirect:/dives/edit/" + createdDiveEntity.getId();
        }
    }

    @GetMapping(path = "/edit/{id}")
    public String doEdit(@ModelAttribute("dive") DiveEditor diveEditor) {
        return "/dives/edit";
    }

    @PostMapping(path = "/edit/{id}")
    public String doEditPost(@Valid @ModelAttribute("dive") DiveEditor diveEditor, BindingResult bindingResult, @ModelAttribute("diveEntity") Dive diveEntity, RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            Dive updatedDiveEntity = this.getDiveEditorService().updateDiveFromEditor(diveEntity, diveEditor);
            redirectAttributes.addFlashAttribute("updatedDive", updatedDiveEntity);
            return "redirect:/dives/edit/" + updatedDiveEntity.getId();
        } else {
            return this.doEdit(diveEditor);
        }
    }

    @GetMapping(path = "/logbookImage/{id}")
    public ResponseEntity<?> doImage(@ModelAttribute("dive") DiveEditor diveEditor) {
        if (diveEditor.getLogbookImage() == null || !diveEditor.getLogbookImage().isAvailable()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity
                .ok()
                .contentLength(diveEditor.getLogbookImage().getJpegBytes().length)
                .contentType(MediaType.IMAGE_JPEG)
                .eTag(DigestUtils.md5DigestAsHex(diveEditor.getLogbookImage().getJpegBytes()))
                .body(diveEditor.getLogbookImage().getJpegBytes());
        }
    }

    @ModelAttribute(name = "dive")
    DiveEditor diveEditor(@ModelAttribute("diveEntity") Dive diveEntity) {
        return this.getDiveEditorService().createDiveEditor(diveEntity);
    }

    @ModelAttribute(name = "diveEntity", binding = false)
    public Dive diveEntity(@PathVariable(name = "id", required = false) UUID diveEntityId) {
        return this.getDiveEditorService().createDiveEntity(diveEntityId);
    }

    DiveEditorService getDiveEditorService() {
        return this.diveEditorService;
    }
    @Autowired
    void setDiveEditorService(DiveEditorService diveEditorService) {
        this.diveEditorService = diveEditorService;
    }

}
