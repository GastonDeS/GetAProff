package ar.edu.itba.paw.models;

public class TeacherInfo {
    private final int minPrice, maxPrice, reviews;
    private final float rate;
    private final String name, mail, description, schedule;
    private final Long userId;

    public TeacherInfo(Long userId, String name, int maxPrice, int minPrice, String description, float rate, String schedule, String mail, int reviews) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.rate = rate;
        this.name = name;
        this.mail = mail;
        this.userId = userId;
        this.description = description;
        this.schedule = schedule;
        this.reviews = reviews;
    }

    public int getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }

    public Long getUserId() {
        return userId;
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
        return aux.userId.equals(this.userId) &&
                aux.name.equals(this.name) &&
                aux.mail.equals(this.mail) &&
                aux.schedule.equals(this.schedule) &&
                aux.description.equals(this.description) &&
                aux.maxPrice == this.maxPrice &&
                aux.minPrice == this.minPrice &&
                aux.rate == this.rate;
    }
}
