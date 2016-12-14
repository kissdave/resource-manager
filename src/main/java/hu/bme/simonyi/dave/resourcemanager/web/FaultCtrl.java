package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.exceptions.FormProcessException;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceFault;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceFaultRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.service.ResourceFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by dkiss on 2016. 10. 08..
 */
@Controller
public class FaultCtrl {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceFaultRepository resourceFaultRepository;

    @Autowired
    ResourceFaultService resourceFaultService;

    private static final String FAULT = "fault";


    @RequestMapping(value = "/faults")
    public String faultsHome(Model model) {
        model.addAttribute(FAULT, new ResourceFault());
        model.addAttribute("resourceList", resourceRepository.findAll());
        model.addAttribute("faultList", resourceFaultRepository.findAll());
        model.addAttribute("newLineChar", '\n');
        return "faults";
    }

    @RequestMapping(value = "/faults/createFault", method = RequestMethod.POST)
    public String createResourceFault(
            Model model,
            @ModelAttribute(FAULT) @Valid final ResourceFault resourceFault,
            @RequestParam("resourceID") final Integer resourceID,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute(FAULT, resourceFault);
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Hibajegy felvéve",
                FAULT,
                resourceFault,
                "faults"
        ) {
            @Override
            public void processFormData() throws FormProcessException {
                resourceFaultService.createFault(resourceFault, resourceID);
            }
        }.processForm();
    }

    @RequestMapping(value = "/faults/appendTextToFault", method = RequestMethod.POST)
    public String appendTextToFault(
            Model model,
            @RequestParam("FaultID") final Integer id,
            @RequestParam("textToAppend") final String text,
            @RequestParam(value = "commentActive", required = false) final boolean active,
            RedirectAttributes redirectAttributes
    ) {
        return new FormHandler(
                null,
                redirectAttributes,
                "Hibajegy módosítva",
                text,
                String.class,
                "faults"
        ) {
            @Override
            public void processFormData() throws FormProcessException {
                boolean processedActive = false;
                if (active) {
                    processedActive = true;
                }
                resourceFaultService.appendToFault(id, text, processedActive);
            }
        }.processForm();
    }
}
