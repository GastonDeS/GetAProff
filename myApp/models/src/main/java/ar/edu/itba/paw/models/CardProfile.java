package ar.edu.itba.paw.models;

public class CardProfile {
    private final int userId, price, level;
    private final String name, subject, description;


    public CardProfile(int userId, String name, String subject, int price, int level, String desc) {
        this.userId = userId;
        this.name = name;
        this.level = level;
        this.subject = subject;
        this.price = price;
        this.description = desc;
    }

    public int getUserId() {
        return userId;
    }


    public int getPrice() {
        return price;
    }


    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public int getLevel() {
        return level;
    }
    
    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return String.format("Id: %d - Name: %s - Subject: %s - Price: %d\n", userId, name, subject, price);
    }
}
