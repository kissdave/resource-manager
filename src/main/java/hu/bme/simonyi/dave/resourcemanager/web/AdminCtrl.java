package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceTypeRepository;
import hu.bme.simonyi.dave.resourcemanager.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by dkiss on 2016. 10. 01..
 */
@Controller
public class AdminCtrl {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Autowired
    ResourceService resourceService;

    public static final String MANAGE = "manage";
    public static final String RESOURCE = "resource";
    public static final String RESOURCETYPE = "resourceType";

    @RequestMapping(value = "/manage")
    public String manageHome(Model model) {
        model.addAttribute(RESOURCE, new Resource());
        model.addAttribute(RESOURCETYPE, new ResourceType());
        model.addAttribute("resourceTypeList", resourceTypeRepository.findAll());
        model.addAttribute("resourceList", resourceRepository.findAll());
        return MANAGE;
    }

    @RequestMapping(value = "/manage/createResource", method = RequestMethod.POST)
    public String createResource(
            Model model,
            @ModelAttribute(RESOURCE) @Valid final Resource resource,
            @RequestParam("resourceTypeID") final Integer resourceTypeID,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute(RESOURCE, resource);
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Erőforrás hozzáadva.",
                RESOURCE,
                resource,
                MANAGE
        ) {
            @Override
            public void processFormData() throws Exception {
                resourceService.createResource(resource, resourceTypeID);
            }
        }.processForm();
    }

    @RequestMapping(value = "/manage/createResourceType", method = RequestMethod.POST)
    public String createResourceType(
            Model model,
            @ModelAttribute(RESOURCETYPE) @Valid final ResourceType resourceType,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        model.addAttribute(RESOURCETYPE, resourceType);
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Erőforrástípus hozzáadva.",
                RESOURCETYPE,
                resourceType,
                MANAGE
        ) {
            @Override
            public void processFormData() throws Exception {
                resourceTypeRepository.save(resourceType);
            }
        }.processForm();
    }

    @RequestMapping(value = "/manage/changeActive/{id}")
    public String changeActive(
            Model model,
            @PathVariable("id") final Integer id
            ) {

        resourceService.changeActive(id);
        return "redirect:/" + MANAGE;
    }

    @RequestMapping(value = "/manage/changeArchive/{id}")
    public String changeArchive(
            Model model,
            @PathVariable("id") final Integer id
    ) {

        resourceService.changeArchive(id);
        return "redirect:/" + MANAGE;
    }
}
