package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Size;

public class AcceptForm {

    @Size(max = 255)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
