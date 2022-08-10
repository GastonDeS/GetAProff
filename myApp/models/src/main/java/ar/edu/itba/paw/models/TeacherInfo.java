package ar.edu.itba.paw.models;

public class TeacherInfo extends UserInfo {
    protected final int minPrice, maxPrice, reviews;
    protected final float rate;

    public TeacherInfo(Long userId, String name, int maxPrice, int minPrice, String description, float rate, String schedule, String mail, int reviews) {
        super(name, mail, description, schedule, userId);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.rate = rate;
        this.reviews = reviews;
    }

    private TeacherInfo(Builder builder) {
        super(builder.name, builder.mail, builder.description, builder.schedule, builder.userId);
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.rate = builder.rate;
        this.reviews = builder.reviews;
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

    public static class Builder
    {
        private Long userId;
        private final String mail;
        private String name;
        private String description;
        private String schedule;
        private int minPrice, maxPrice, reviews;
        private float rate;

        public Builder(String mail, String name, Long userId) {
            this.mail = mail;
            this.userId = userId;
            this.name = name;
            this.schedule = "";
            this.description = "";
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder schedule(String schedule) {
            this.schedule = schedule;
            return this;
        }
        public Builder minPrice(int minPrice) {
            this.minPrice = minPrice;
            return this;
        }
        public Builder maxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }
        public Builder reviews(int reviews) {
            this.reviews = reviews;
            return this;
        }
        public Builder rate(float rate) {
            this.rate = rate;
            return this;
        }
        public TeacherInfo build() {
            return new TeacherInfo(this);
        }
    }
}
