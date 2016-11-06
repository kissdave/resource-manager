package hu.bme.simonyi.dave.resourcemanager.web;

import hu.bme.simonyi.dave.resourcemanager.exceptions.FormProcessException;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Abstract class to process and handle form data.
 */
public abstract class FormHandler {
    BindingResult bindingResult;
    RedirectAttributes redirectAttributes;
    String message;
    String attributeName;
    Object modelObject;
    String resultPage;
    String errorPage;

    public FormHandler setErrorPage(String errorPage) {
        this.errorPage = errorPage;
        return this;
    }

    static final Logger logger = Logger.getLogger(FormHandler.class);

    /**
     * Constructor with all variables.
     * @param bindingResult
     * @param redirectAttributes
     * @param message
     * @param attributeName
     * @param modelObject
     * @param resultPage
     */
    public FormHandler(BindingResult bindingResult, RedirectAttributes redirectAttributes, String message, String attributeName, Object modelObject, String resultPage) {
        this.bindingResult = bindingResult;
        this.redirectAttributes = redirectAttributes;
        this.message = message;
        this.attributeName = attributeName;
        this.modelObject = modelObject;
        this.resultPage = resultPage;
    }

    /**
     * Processes the form data, adds error messages as attributes and redirects to the result page.
     * @return The name of the result page.
     */
    public String processForm() {
        if (bindingResult == null || !bindingResult.hasErrors()) {

            try {

                processFormData();

            } catch (Exception e) {
                logger.error("Exception during form handling. ", e);
                message = "Error: " + e.getMessage();
                if(!errorPage.isEmpty()) {
                    redirectAttributes.addFlashAttribute("message", message);
                    return "redirect:/" + errorPage;
                }


            }

            redirectAttributes.addFlashAttribute("message", message);
        } else {
            keepErrors();
        }
        return "redirect:/" + resultPage;
    }

    /**
     * Keeps the errors during the process.
     */
    private void keepErrors() {
        redirectAttributes.addFlashAttribute(
                BindingResult.MODEL_KEY_PREFIX + attributeName,
                bindingResult);
        redirectAttributes.addFlashAttribute(attributeName, modelObject);
    }

    /**
     * Abstract method, implementation depends on the type of the data is processed.
     * @throws FormProcessException
     */
    public abstract void processFormData() throws FormProcessException;

}
