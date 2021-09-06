package ar.edu.itba.paw.models;

public class CardProfile {
    private int userId, price;
    private String name, subject;

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
