package ar.edu.itba.paw.webapp.forms;

import javax.validation.constraints.Size;

public class AcceptForm {

    @Size(max = 256)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
