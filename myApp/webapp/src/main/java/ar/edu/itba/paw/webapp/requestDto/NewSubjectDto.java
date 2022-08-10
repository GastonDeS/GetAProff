package ar.edu.itba.paw.webapp.requestDto;

public class NewSubjectDto {
    private String subject;
    private String message;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subjectName) {
        this.subject = subjectName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
