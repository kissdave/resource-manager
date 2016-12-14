package hu.bme.simonyi.dave.resourcemanager.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkiss on 2016. 10. 01..
 */
@Controller
public class HomeCtrl {

    private static final String INDEX = "index";

    /**
     * Loads the welcome screen.
     * @return Returns the index.html
     */
    @RequestMapping("*")
    public String home() {
        return INDEX;
    }
}
