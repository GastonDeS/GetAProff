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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "userroles",
            joinColumns = @JoinColumn(
                    name = "userid", referencedColumnName = "userid"),
            inverseJoinColumns = @JoinColumn(
                    name = "roleid", referencedColumnName = "roleid"))
    private List<Role> userRoles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(
                    name = "teacherid", referencedColumnName = "userid"),
            inverseJoinColumns = @JoinColumn(
                    name = "studentid", referencedColumnName = "userid"))
    private List<User> favourites;

    @OneToOne
    private Image image;

    @OneToMany(mappedBy = "fileOwner", cascade = CascadeType.ALL)
    private List<UserFile> userFilesList;


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
        this.favourites = new ArrayList<>();
    }

    public List<User> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<User> favourites) {
        this.favourites = favourites;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public void setUserFilesList(List<UserFile> userFilesList) {
        this.userFilesList = userFilesList;
    }

    public List<UserFile> getUserFilesList() {
        return userFilesList;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", userid, name, mail);
    }
}
