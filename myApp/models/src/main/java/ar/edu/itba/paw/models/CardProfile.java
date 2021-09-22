package ar.edu.itba.paw.models;

public class CardProfile {
    private int userId, price, level;
    private String name, subject, description;
    private String[] userSchedule;

    public CardProfile(int userId, String name, String subject, int price, int level, String[] userSchedule, String desc) {
        this.userId = userId;
        this.name = name;
        this.level = level;
        this.subject = subject;
        this.price = price;
        this.userSchedule = userSchedule;
        this.description = desc;
    }

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

    public String[] getUserSchedule() {
        return userSchedule;
    }

    public void setUserSchedule(String[] userSchedule) {
        this.userSchedule = userSchedule;
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
