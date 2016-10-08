package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceFault;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceFaultRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.service.ResourceFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = "/faults")
    public String faultsHome(Model model) {
        model.addAttribute("fault", new ResourceFault());
        model.addAttribute("resourceList", resourceRepository.findAll());
        model.addAttribute("faultList", resourceFaultRepository.findAll());
        return "faults";
    }

    @RequestMapping(value = "/faults/createFault", method = RequestMethod.POST)
    public String createResource(
            Model model,
            @ModelAttribute("fault") @Valid final ResourceFault resourceFault,
            @RequestParam("resourceID") final Integer resourceID,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute("fault", resourceFault);
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Hibajegy felvéve",
                "fault",
                resourceFault,
                "faults"
        ) {
            @Override
            public void processFormData() throws Exception {
                resourceFaultService.createFault(resourceFault, resourceID);
            }
        }.processForm();
    }

}
