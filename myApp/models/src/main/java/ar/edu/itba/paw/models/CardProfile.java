package ar.edu.itba.paw.models;

public class CardProfile {
    private int userId, price;
    private String name, subject;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public CardProfile (int userId, String name, String subject, int price) {
        this.userId = userId;
        this.name = name;
        this.subject = subject;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Subject: %s - Price: %d\n", userId, name, subject, price);
    }
}
