package ar.edu.itba.paw.webapp.requestDto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EditUserDto {

    @NotNull(groups = {NewUserDto.Teacher.class, NewUserDto.Student.class})
    private Long id;

    @NotBlank(groups = {NewUserDto.Teacher.class, NewUserDto.Student.class})
    @Pattern(regexp = "^$|^([A-ZÀ-ÿ-,a-z. ']+[ ]*)+$", groups = {NewUserDto.Teacher.class, NewUserDto.Student.class})
    private String name;

    @NotNull(groups = {NewUserDto.Teacher.class})
    private String switchRole;

    @NotBlank(groups = {NewUserDto.Teacher.class})
    private String description;

    @NotBlank(groups = {NewUserDto.Teacher.class})
    private String schedule;

    public interface Teacher {
    }

    public interface Student {
    }

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

    public String getSwitchRole() {
        return switchRole;
    }

    public void setSwitchRole(String switchRole) {
        this.switchRole = switchRole;
    }
}
