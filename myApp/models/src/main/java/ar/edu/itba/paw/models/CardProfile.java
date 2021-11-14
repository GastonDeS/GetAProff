package ar.edu.itba.paw.models;

public class CardProfile {
    private final int minPrice, maxPrice;
    private final String name, description;
    private final float rate;
    private final Long userId;

    public CardProfile(Long userId, String name, int maxPrice, int minPrice, String desc, float rate) {
        this.userId = userId;
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.description = desc;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public Long getUserId() {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CardProfile)) return false;
        CardProfile aux = (CardProfile) object;
        return aux.userId.equals(this.userId) &&
                aux.name.equals(this.name) &&
                aux.maxPrice == this.maxPrice &&
                aux.minPrice == this.minPrice &&
                aux.description.equals(this.description) &&
                aux.rate == this.rate;
    }
}
