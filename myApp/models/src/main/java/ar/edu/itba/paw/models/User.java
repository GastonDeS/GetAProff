package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private  String name, password, mail, description, schedule;
    private int id;
    private List<Role> userRoles;

    // For testing
    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    // For creating user (empty description and schedule)
    public User(String name, String password, int id, String mail, String description, String schedule){
        this(name, mail, password);
        this.id = id;
        this.description = description;
        this.schedule = schedule;
        this.userRoles = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserRoles(List<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Role> getUserRoles() {
        return userRoles;
    }

    public boolean isTeacher() {
        for (Role role : userRoles) {
            if (Objects.equals(role.getRole(),"USER_TEACHER")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", id, name, mail);
    }
}
