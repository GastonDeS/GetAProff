package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String password;

    @Column(nullable = false)
    private String mail;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserRole> userRoles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(
                    name = "studentid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "favourites_studentid_fkey")),
            inverseJoinColumns = @JoinColumn(
                    name = "teacherid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "favourites_teacherid_fkey")))
    private List<User> favourites;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(
                    name = "teacherid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "favourites_teacherid_fkey")),
            inverseJoinColumns = @JoinColumn(
                    name = "studentid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "favourites_studentid_fkey")))
    private List<User> favouriteOf;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Image image;

    @OneToMany(mappedBy = "fileOwner", cascade = CascadeType.ALL)
    private List<UserFile> userFilesList;

    @OneToMany(mappedBy = "fileOwner", cascade = CascadeType.ALL)
    private List<SubjectFile> subjectsFilesList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Teaches> subjectsTaughtByUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private List<Lecture> classesTakenByStudent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Lecture> classesGivenByTeacher;

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
        this.favourites = new ArrayList<>();
    }

    public List<User> getFavouriteOf() {
        return favouriteOf;
    }

    public void setFavouriteOf(List<User> favouriteOf) {
        this.favouriteOf = favouriteOf;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Teaches> getSubjectsTaughtByUser() {
        return subjectsTaughtByUser;
    }

    public void setSubjectsTaughtByUser(List<Teaches> subjectsTaughtByUser) {
        this.subjectsTaughtByUser = subjectsTaughtByUser;
    }

    public List<Lecture> getClassesTakenByStudent() {
        return classesTakenByStudent;
    }

    public void setClassesTakenByStudent(List<Lecture> classesTakenByStudent) {
        this.classesTakenByStudent = classesTakenByStudent;
    }

    public List<Lecture> getClassesGivenByTeacher() {
        return classesGivenByTeacher;
    }

    public void setClassesGivenByTeacher(List<Lecture> classesGivenByTeacher) {
        this.classesGivenByTeacher = classesGivenByTeacher;
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

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isTeacher() {
        for (UserRole role : userRoles) {
            if (role.getRoleid().equals(Roles.TEACHER.getId())) {
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

    public List<SubjectFile> getSubjectsFilesList() {
        return subjectsFilesList;
    }

    public void setSubjectsFilesList(List<SubjectFile> subjectsFilesList) {
        this.subjectsFilesList = subjectsFilesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(description, user.description) &&
                Objects.equals(schedule, user.schedule) &&
                Objects.equals(userid, user.userid) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(userRoles, user.userRoles) &&
                Objects.equals(favourites, user.favourites) &&
                Objects.equals(favouriteOf, user.favouriteOf) &&
                Objects.equals(image, user.image) &&
                Objects.equals(userFilesList, user.userFilesList) &&
                Objects.equals(subjectsFilesList, user.subjectsFilesList) &&
                Objects.equals(ratings, user.ratings) &&
                Objects.equals(subjectsTaughtByUser, user.subjectsTaughtByUser) &&
                Objects.equals(classesTakenByStudent, user.classesTakenByStudent) &&
                Objects.equals(classesGivenByTeacher, user.classesGivenByTeacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, schedule, userid, password, mail, userRoles, favourites, favouriteOf, image, userFilesList, subjectsFilesList, ratings, subjectsTaughtByUser, classesTakenByStudent, classesGivenByTeacher);
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (!(object instanceof User)) return false;
//        User aux = (User) object;
//        return aux.userid.equals(this.userid);
//    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", userid, name, mail);
    }
}
