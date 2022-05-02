package ar.edu.itba.paw.webapp.requestDto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;

public class NewSubjectDto {

    private Long subjectId;

    @Min(1)
    private int price;

    @Range(min = 0, max = 3)
    private int level;

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
