package hu.bme.simonyi.dave.resourcemanager.exceptions;

/**
 * Created by dkiss on 2016. 10. 21..
 */
public class FormProcessException extends Exception {
    public FormProcessException() {
        // A default empty constructor for the Exception
    }

    /**
     * Constructor handles the message to the superclass
     * @param message Handled to the superclass.
     */
    public FormProcessException(String message) {
        super(message);
    }
}
