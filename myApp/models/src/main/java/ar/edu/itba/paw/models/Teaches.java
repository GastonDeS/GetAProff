package ar.edu.itba.paw.models;

public class Teaches {
    private int price, level;
    private Long userId, subjectId;

    public Teaches (Long userId, Long subjectId, int price, int level){
        this.userId = userId;
        this.subjectId = subjectId;
        this.price = price;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("UserID: %d - SubjectID: %d - Price: %d\n", userId, subjectId, price);
    }

    public enum Level {
        NONE,
        PRIMARY,
        SECONDARY,
        TERTIARY;
    }
}
