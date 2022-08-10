package ar.edu.itba.paw.models;

public class UserInfo {
    private final String name, mail, description, schedule;
    private final Long userId;

    public UserInfo(String name, String mail, String description, String schedule, Long userId) {
        this.name = name;
        this.mail = mail;
        this.description = description;
        this.schedule = schedule;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public Long getUserId() {
        return userId;
    }
}
