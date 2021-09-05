package ar.edu.itba.paw.models;

public class Teaches {
    private int userId, subjectId, price;
    private String timeInterval;

    public Teaches (int userId, int subjectId, int price, String timeInterval){
        this.userId = userId;
        this.subjectId = subjectId;
        this.price = price;
        this.timeInterval = timeInterval;
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

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }
}
