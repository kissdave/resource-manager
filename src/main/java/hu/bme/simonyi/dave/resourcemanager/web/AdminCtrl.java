package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.exceptions.FormProcessException;
import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.model.ResourceType;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceTypeRepository;
import hu.bme.simonyi.dave.resourcemanager.service.ResourceService;
import hu.bme.simonyi.dave.resourcemanager.service.ResourceTypeService;
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

    @Autowired
    ResourceTypeService resourceTypeService;

    public static final String MANAGE = "manage";
    public static final String RESOURCE = "resource";
    public static final String RESOURCETYPE = "resourceType";
    public static final String REDIRECT = "redirect:/";

    /**
     * Displays the home screen of the Administration menu,
     * also it fills the tables with the existing data
     * @param model The data passed to the view.
     * @return The name of the HTML page to load.
     */
    @RequestMapping(value = "/manage")
    public String manageHome(Model model) {
        if (!model.containsAttribute(RESOURCE)) {
            model.addAttribute(RESOURCE, new Resource());
        }
        model.addAttribute(RESOURCETYPE, new ResourceType());
        model.addAttribute("resourceTypeList", resourceTypeRepository.findAll());
        model.addAttribute("resourceList", resourceRepository.findAll());
        return MANAGE;
    }

    /**
     * Processes the new resource form, binds the data to the existing model and passes it to the service to create database entity.
     * @param model The data passed to the view.
     * @param resource The data submitted from the form, bind to the existing model.
     * @param resourceTypeID The ID submitted from the form
     * @param bindingResult The result of the binding process
     * @param redirectAttributes
     * @return The name of the HTML page to load.
     */
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
            public void processFormData() throws FormProcessException {
                resourceService.createResource(resource, resourceTypeID);
            }
        }.processForm();
    }

    /**
     * Gets the resource to the form, to edit the data.
     * @param model The data passed to the view.
     * @param id The identifier of the resource
     * @param redirectAttributes
     * @return The name of the HTML page to load.
     */
    @RequestMapping(value = "/manage/updateResource/{id}", method = RequestMethod.GET)
    public String updateResourceGet(
            Model model,
            @PathVariable("id") final Integer id,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute(RESOURCE, resourceRepository.findOne(id));
        return REDIRECT + MANAGE;
    }

    /**
     * Processes the resource form, binds the data to the existing model and passes it to the service to update a database entity.
     * @param model The data passed to the view.
     * @param resource
     * @param id
     * @param resourceTypeID
     * @param bindingResult
     * @param redirectAttributes
     * @return The name of the HTML page to load.
     */
    @RequestMapping(value = "/manage/updateResource/{id}", method = RequestMethod.POST)
    public String updateResourcePost(
            Model model,
            @ModelAttribute(RESOURCE) @Valid final Resource resource,
            @PathVariable("id") final Integer id,
            @RequestParam("resourceTypeID") final Integer resourceTypeID,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Erőforrás frissítve",
                RESOURCE,
                resource,
                MANAGE
        ) {
            @Override
            public void processFormData() throws FormProcessException {
                resource.setResourceID(id);
                resourceService.updateResource(resource, resourceTypeID);
            }
        }.processForm();
    }

    /**
     * Changes the Active field of a Resource to the opposite, it uses the service.
     * @param model The data passed to the view.
     * @param id The identifier of the Resource to change
     * @return The name of the HTML page to load.
     */
    @RequestMapping(value = "/manage/changeActive/{id}")
    public String changeActive(
            Model model,
            @PathVariable("id") final Integer id
    ) {

        resourceService.changeActive(id);
        return REDIRECT + MANAGE;
    }

    /**
     * Changes the Archive field of a Resource to the opposite, it uses the service.
     * @param model The data passed to the view.
     * @param id The identifier of the Resource to change
     * @return The name of the HTML page to load.
     */
    @RequestMapping(value = "/manage/changeArchive/{id}")
    public String changeArchive(
            Model model,
            @PathVariable("id") final Integer id
    ) {

        resourceService.changeArchive(id);
        return REDIRECT + MANAGE;
    }

    /**
     * Processes the new resourceType form, binds the data to the existing model and passes it to the service to create database entity.
     * @param model The data passed to the view.
     * @param resourceType The data submitted from the form, bind to the existing model.
     * @param bindingResult The result of the binding process
     * @param redirectAttributes
     * @return The name of the HTML page to load.
     */
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
            public void processFormData() throws FormProcessException {
                resourceTypeService.createResourceType(resourceType);
            }
        }.processForm();
    }
}
