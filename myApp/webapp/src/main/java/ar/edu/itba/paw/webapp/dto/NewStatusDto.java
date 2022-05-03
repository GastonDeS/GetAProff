package ar.edu.itba.paw.webapp.dto;

public class NewStatusDto {
    private Long userId;
    private int status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
