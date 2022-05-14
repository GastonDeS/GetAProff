package ar.edu.itba.paw.webapp.requestDto;

public class EditUserDto {

    private Long id;

    private String name;

    private String description;

    private String schedule;

    private boolean switchRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public boolean isSwitchRole() {
        return switchRole;
    }

    public void setSwitchRole(boolean switchRole) {
        this.switchRole = switchRole;
    }
}
