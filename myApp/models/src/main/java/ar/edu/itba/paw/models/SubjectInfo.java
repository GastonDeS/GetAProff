package ar.edu.itba.paw.models;

public class SubjectInfo {

    private String name;
    private Long subjectId;
    private int price, level;

    public SubjectInfo(Long subjectId, String name, int price, int level) {
        this.subjectId = subjectId;
        this.name = name;
        this.price = price;
        this.level = level;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
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
