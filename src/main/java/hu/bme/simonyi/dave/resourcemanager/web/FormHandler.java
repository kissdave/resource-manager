package hu.bme.simonyi.dave.resourcemanager.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class FormHandler {

    BindingResult bindingResult;
    RedirectAttributes redirectAttributes;
    String message;
    String attributeName;
    Object modelObject;
    String resultPage;

    public FormHandler(BindingResult bindingResult, RedirectAttributes redirectAttributes, String message, String attributeName, Object modelObject, String resultPage) {
        this.bindingResult = bindingResult;
        this.redirectAttributes = redirectAttributes;
        this.message = message;
        this.attributeName = attributeName;
        this.modelObject = modelObject;
        this.resultPage = resultPage;
    }

    public String processForm() {
        if (!bindingResult.hasErrors()) {

            try {

                processFormData();

            } catch (Exception e) {
                e.printStackTrace();
                message = "Error: " + e.getMessage();
            }

            redirectAttributes.addFlashAttribute("message", message);
        } else {
            keepErrors();
        }
        return "redirect:/" + resultPage;
    }

    private void keepErrors() {
        redirectAttributes.addFlashAttribute(
                BindingResult.MODEL_KEY_PREFIX + attributeName,
                bindingResult);
        redirectAttributes.addFlashAttribute(attributeName, modelObject);
    }

    public abstract void processFormData() throws Exception;

}
