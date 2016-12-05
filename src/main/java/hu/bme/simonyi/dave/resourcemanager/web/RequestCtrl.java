package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.exceptions.FormProcessException;
import hu.bme.simonyi.dave.resourcemanager.model.*;
import hu.bme.simonyi.dave.resourcemanager.repository.RequestRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.ResourceTypeRepository;
import hu.bme.simonyi.dave.resourcemanager.repository.UserRepository;
import hu.bme.simonyi.dave.resourcemanager.security.CustomUserDetails;
import hu.bme.simonyi.dave.resourcemanager.service.RequestService;
import hu.bme.simonyi.dave.resourcemanager.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dkiss on 2016. 10. 29..
 */
@Controller
public class RequestCtrl {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestService requestService;

    @Autowired
    UserRepository userRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = "/requests")
    public String requestsHome(Model model) {
        model.addAttribute("request", new Request());

        if(isLoggedInUserAdmin()) {
            model.addAttribute("requestList", requestRepository.findAll());
        } else {
            final User user = userRepository.findOne(getLoggedInUserID());
            List<Request> requestList = requestRepository.findAll().stream().filter(x -> x.getUser().getUserID().equals(user.getUserID())).collect(Collectors.toList());
            model.addAttribute("requestList", requestList);
        }
        return "requests";
    }

    @RequestMapping(value = "/request/createRequest/", method = RequestMethod.GET)
    public String newRequestForm(Model model) {
        model.addAttribute("request", new Request());
        model.addAttribute("resourceTypeList", resourceTypeRepository.findAll());
        model.addAttribute("resourceList", resourceRepository.findAll());
        return "request";
    }

    @RequestMapping(value = "/request/createRequest/", method = RequestMethod.POST)
    public String newRequestProcess(
            Model model,
            @RequestParam(value = "resourceID") final Integer resourceID,
            @RequestParam(value = "dateFrom") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date dateFrom,
            @RequestParam(value = "dateTo") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date dateTo,
            @RequestParam(value = "handleBefore") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date handleBefore,
            @RequestParam(value = "handleAfter") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date handleAfter,
            @RequestParam(value = "eventName") final String eventName,
            @RequestParam(value = "eventDescription") final String eventDescription,
            @RequestParam(value = "comment") final String comment,
            RedirectAttributes redirectAttributes
    ) {
        return new FormHandler(
                null,
                redirectAttributes,
                "Foglalás sikeresen leadva, hamarosan visszajelzünk, hogy teljesíthető-e az igény.",
                "eventName",
                String.class,
                "requests"
        ) {
            @Override
            public void processFormData() throws FormProcessException {
                if (eventName.isEmpty() || dateFrom == null || dateTo == null || resourceID == null) {
                    throw new FormProcessException("Please provide eventName, date and resource information for the request!");
                } else {
                    requestService.createRequest(new Request(eventName, eventDescription, dateFrom, dateTo, handleBefore, handleAfter, comment, null, null, null), resourceID, getLoggedInUserID());
                }

            }
        }.setErrorPage("request/createRequest/").processForm();
    }

    @RequestMapping(value = "/request/updateRequest/{id}", method = RequestMethod.GET)
    public String updateRequestGet(
            Model model,
            @PathVariable("id") final Integer id) {
        model.addAttribute("request", requestRepository.findOne(id));
        model.addAttribute("resourceTypeList", resourceTypeRepository.findAll());
        model.addAttribute("resourceList", resourceRepository.findAll());
        return "request";
    }

    @RequestMapping(value = "/request/updateRequest/{id}", method = RequestMethod.POST)
    public String updateRequestPost(
            Model model,
            @RequestParam(value = "resourceID") final Integer resourceID,
            @RequestParam(value = "dateFrom") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date dateFrom,
            @RequestParam(value = "dateTo") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date dateTo,
            @RequestParam(value = "handleBefore") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date handleBefore,
            @RequestParam(value = "handleAfter") @DateTimeFormat(pattern = Utils.DATEFORMAT) final Date handleAfter,
            @RequestParam(value = "eventName") final String eventName,
            @RequestParam(value = "eventDescription") final String eventDescription,
            @RequestParam(value = "comment") final String comment,
            @PathVariable("id") final Integer id,
            RedirectAttributes redirectAttributes) {
        return new FormHandler(
                null,
                redirectAttributes,
                "Foglalás sikeresen módosítva, hamarosan visszajelzünk, hogy teljesíthető-e az igény.",
                "eventName",
                String.class,
                "requests"
        ) {
            @Override
            public void processFormData() throws FormProcessException {
                if (eventName.isEmpty() || dateFrom == null || dateTo == null || resourceID == null) {
                    throw new FormProcessException("Please provide eventName, date and resource information for the request!");
                } else {
                    Request req = new Request(eventName, eventDescription, dateFrom, dateTo, handleBefore, handleAfter, comment, null, null, null);
                    req.setRequestID(id);
                    requestService.updateRequest(req, resourceID);
                }

            }
        }.setErrorPage("request/updateRequest/" + id).processForm();
    }

    @RequestMapping(value = "/request/approveRequest/", method = RequestMethod.POST)
    public String approveRequest(
            Model model,
            @RequestParam("requestID") final Integer id,
            @RequestParam("textToAppend") final String comment,
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

    @RequestMapping(value = "/request/declineRequest/", method = RequestMethod.POST)
    public String declineRequest(
            Model model,
            @RequestParam("requestID") final Integer id,
            @RequestParam("textToAppend") final String comment,
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
                requestService.answerRequest(id, comment, false);
            }
        }.processForm();
    }

    @RequestMapping(value = "/requests/getResources/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String getResources(
            Model model,
            @PathVariable("id") final Integer id
    ) {
        final ResourceType resourceType = resourceTypeRepository.findOne(id);
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Resource resource : resourceType.getResources()) {
            if(!resource.getActive()) {
                continue;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", resource.getResourceID());
            jsonObject.put("name", resource.getResourceName());
            jsonArray.put(jsonObject);
        }
        response.put("resources", jsonArray);
        return response.toString();
    }

    public Integer getLoggedInUserID() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByusername(username);
        return user.getUserID();
    }

    public Boolean isLoggedInUserAdmin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final User user = userRepository.findByusername(username);
        for( UserRole role : user.getRoles()) {
            if(role.getRoleName().toUpperCase().contains("ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
