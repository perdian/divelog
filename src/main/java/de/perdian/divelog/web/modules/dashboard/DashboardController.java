package de.perdian.divelog.web.modules.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(path = { "/", "/dashboard" })
    public String doDashboard() {
        return "/dashboard";
    }

}
