package de.perdian.divelog.web.modules.dives;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.perdian.divelog.model.components.PlaceAndTime;
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
        return "/dives/edit";
    }

    @ModelAttribute("dive")
    DiveEditor diveEditor() {
        DiveEditor diveEditor = new DiveEditor();
        diveEditor.setNumber(this.getDiveRepository().countDivesByUser(this.getCurrentUser()) + 1);
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
