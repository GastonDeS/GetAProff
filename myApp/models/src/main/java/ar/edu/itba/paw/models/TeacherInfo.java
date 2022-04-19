package ar.edu.itba.paw.models;

public class TeacherInfo extends UserInfo{
    protected final int minPrice, maxPrice, reviews;
    protected final float rate;

    public TeacherInfo(Long userId, String name, int maxPrice, int minPrice, String description, float rate, String schedule, String mail, int reviews) {
        super(name, mail, description, schedule, userId);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.rate = rate;
        this.reviews = reviews;
    }

    public int getReviews() {
        return reviews;
    }

    public float getRate() {
        return rate;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TeacherInfo)) return false;
        TeacherInfo aux = (TeacherInfo) object;
        return aux.getUserId().equals(this.getUserId()) &&
                aux.getName().equals(this.getName()) &&
                aux.getMail().equals(this.getMail()) &&
                aux.getSchedule().equals(this.getSchedule()) &&
                aux.getDescription().equals(this.getDescription()) &&
                aux.maxPrice == this.maxPrice &&
                aux.minPrice == this.minPrice &&
                aux.rate == this.rate;
    }
}
