package ar.edu.itba.paw.models;

public class CardProfile {
    private final int userId, minPrice, maxPrice;
    private final String name, description;

    public CardProfile(int userId, String name, int maxPrice, int minPrice, String desc) {
        this.userId = userId;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.description = desc;
    }

    public int getUserId() {
        return userId;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
