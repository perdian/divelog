package de.perdian.divelog.web.modules.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import de.perdian.divelog.model.repositories.DiveRepository;

@Controller
public class DashboardController {

    private DiveRepository diveRepository = null;

    @GetMapping(path = { "/", "/dashboard" })
    public String doDashboard() {
        return "/dashboard";
    }

    DiveRepository getDiveRepository() {
        return this.diveRepository;
    }
    @Autowired
    void setDiveRepository(DiveRepository diveRepository) {
        this.diveRepository = diveRepository;
    }

}
