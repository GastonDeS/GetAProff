package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String schedule;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
    @SequenceGenerator(name = "users_userid_seq", sequenceName = "users_userid_seq", allocationSize = 1)
    private Long userid;

    @Column(nullable = false, length = 160)
    private String password; //Por default toma nombre de columna password

    @Column(nullable = false)
    private String mail;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "userroles",
            joinColumns = @JoinColumn(
                    name = "userid", referencedColumnName = "userid"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleid", referencedColumnName = "roleid"))
    private List<Role> userRoles;

    User() {
        //Just for Hibernate
    }

    // For testing
    public User(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    // For creating user (empty description and schedule)
    public User(String name, String password, Long id, String mail, String description, String schedule){
        this(name, mail, password);
        this.userid = id;
        this.description = description;
        this.schedule = schedule;
        this.userRoles = new ArrayList<>();
    }

    public Long getId() {
        return userid;
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

    public void setId(Long id) {
        this.userid = id;
    }

    public void setUserRoles(List<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Role> getUserRoles() {
        return userRoles;
    }

    public boolean isTeacher() {
        for (Role role : userRoles) {
            if (role.getRole().equals("USER_TEACHER")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", userid, name, mail);
    }
}
