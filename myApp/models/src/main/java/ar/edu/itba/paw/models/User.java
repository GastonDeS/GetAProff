package ar.edu.itba.paw.models;

public class User {

    private String name, password, mail;
    private int id;

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public User(String name, String password, int id, String mail){
        this(name, mail);
        this.id = id;
        this.password = password;
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
}
