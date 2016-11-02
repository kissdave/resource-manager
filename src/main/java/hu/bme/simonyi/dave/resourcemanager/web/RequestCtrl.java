package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.exceptions.FormProcessException;
import hu.bme.simonyi.dave.resourcemanager.model.Request;
import hu.bme.simonyi.dave.resourcemanager.model.Resource;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dkiss on 2016. 10. 29..
 */
@Controller
public class RequestCtrl {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    RequestService requestService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/requests")
    public String requestsHome(Model model) {
        model.addAttribute("request", new Request());
        return "requests";
    }

    @RequestMapping(value = "/request/createRequest/", method = RequestMethod.GET)
    public String newRequestForm(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("resourceList", resourceRepository.findAll());
        return "request";
    }

    @RequestMapping(value = "/request/createRequest/", method = RequestMethod.POST)
    public String newRequestProcess(
            Model model,
            @ModelAttribute("request") @Valid final Request request,
            @RequestParam("resourceID") final Integer resourceID,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        return new FormHandler(
                bindingResult,
                redirectAttributes,
                "Foglalás sikeresen leadva, hamarosan visszajelzünk, hogy teljesíthető-e az igény.",
                "request",
                request,
                "requests"
                ) {
            @Override
            public void processFormData() throws FormProcessException {
                requestService.createRequest(request, resourceID, 1);
            }
        }.processForm();
    }

    @RequestMapping(value = "/request/updateRequest/{id}", method = RequestMethod.POST)
    public String updateRequest() {
        // TODO Handle request updating
        return "requests";
    }

    @RequestMapping(value = "/request/approveRequest/{id}", method = RequestMethod.POST)
    public String approveRequest(
            Model model,
            @PathVariable("id") final Integer id,
            @RequestParam("comment") final String comment,
            RedirectAttributes redirectAttributes) {
        return new FormHandler(
                null,
                redirectAttributes,
                "Foglalás elfogadva",
                comment,
                String.class,
                "requests") {
            @Override
            public void processFormData() throws FormProcessException {
                requestService.answerRequest(id, comment, true);
            }
        }.processForm();
    }

    @RequestMapping(value = "/request/declineRequest/{id}", method = RequestMethod.POST)
    public String declineRequest(
            Model model,
            @PathVariable("id") final Integer id,
            @RequestParam("comment") final String comment,
            RedirectAttributes redirectAttributes) {
        return new FormHandler(
                null,
                redirectAttributes,
                "Foglalás elutasítva",
                comment,
                String.class,
                "requests") {
            @Override
            public void processFormData() throws FormProcessException {
                requestService.answerRequest(id, comment, true);
            }
        }.processForm();
    }
}
