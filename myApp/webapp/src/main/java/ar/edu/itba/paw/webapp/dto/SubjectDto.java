package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectInfo;

import javax.ws.rs.core.GenericEntity;
import java.util.ArrayList;
import java.util.List;

public class SubjectDto {
    private String name;

    private Long id;

    private int price, level;

    public static GenericEntity<List<SubjectDto>> fromSubjectInfo(List<SubjectInfo> subjects) {
        List<SubjectDto> subjectDtos = new ArrayList<>();
        subjects.forEach((subject) -> {
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.id = subject.getSubjectId();
            subjectDto.name = subject.getName();
            subjectDto.level = subject.getLevel();
            subjectDto.price = subject.getPrice();
            subjectDtos.add(subjectDto);
        });
        return new GenericEntity<List<SubjectDto>>(subjectDtos){};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
