package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by dkiss on 2016. 10. 01..
 */
@Controller
public class AdminCtrl {

    @Autowired
    ResourceRepository resourceRepository;

    public static final String MANAGE = "manage";

    @RequestMapping(value = "/manage")
    public String manageHome() {
        return MANAGE;
    }

    @RequestMapping(value = "/manage/createResource", method = RequestMethod.POST)
    public String createResource(
            Model model,
            @ModelAttribute("resource") @Valid final Resource resource,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("resource", new Resource());
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Resource created",
                "resource",
                resource,
                "manage"
        ) {
            @Override
            public void processFormData() throws Exception {
                resourceRepository.save(resource);
            }
        }.processForm();
    }
}
