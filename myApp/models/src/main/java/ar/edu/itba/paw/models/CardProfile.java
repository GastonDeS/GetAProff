package ar.edu.itba.paw.models;

public class CardProfile {
    private final int userId, minPrice, maxPrice, image;
    private final String name, description;
    private final float rate;

    public CardProfile(int userId, String name, int maxPrice, int minPrice, String desc, int image, float rate) {
        this.userId = userId;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.description = desc;
        this.image = image;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
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

    public int getImage() {
        return image;
    }
}
