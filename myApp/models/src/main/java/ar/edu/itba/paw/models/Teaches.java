package ar.edu.itba.paw.models;

public class Teaches {
    private int userId, subjectId, price;

    public Teaches (int userId, int subjectId, int price){
        this.userId = userId;
        this.subjectId = subjectId;
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
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
}
