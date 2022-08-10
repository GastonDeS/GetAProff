package ar.edu.itba.paw.webapp.forms;


import org.hibernate.validator.constraints.Range;

public class SubjectsForm {

    @Range(min = 1, max = 999999999)
    private int price;

    private int level;

    private Long subjectId;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
