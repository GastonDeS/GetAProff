package ar.edu.itba.paw.models;

public class SubjectInfo {

    private String name;
    private int price, level, id;

    public SubjectInfo(int id, String name, int price, int level) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
