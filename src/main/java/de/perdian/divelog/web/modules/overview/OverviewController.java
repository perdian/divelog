package de.perdian.divelog.web.modules.overview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
public class OverviewController {

    private DiveRepository diveRepository = null;

    @GetMapping(path = { "/", "/overview" })
    public String doOverview() {
        return "/overview";
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

}
