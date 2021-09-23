package ar.edu.itba.paw.models;

public class User {

    private  String name, password, mail, description, schedule;
    private int id;
    private final int userRole; // 1 teacher ; 0 student

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

    public User(String name, String password, int id, String mail, int userRole,String description, String schedule){
        this(name,password,id,mail, userRole);
        this.description = description;
        this.schedule = schedule;

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

    public int getUserRole() {
        return userRole;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Mail: %s\n", id, name, mail);
    }
}
