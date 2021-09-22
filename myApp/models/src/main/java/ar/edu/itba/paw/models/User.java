package ar.edu.itba.paw.models;

public class User {

    private String name, password, mail, description;
    private int id;
    private int userRole; // 1 teacher ; 0 student

    public User(String name, String mail, String password, int userRole) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String name, String password, int id, String mail, int userRole){
        this(name, mail, password, userRole);
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", id, name, mail);
    }
}
