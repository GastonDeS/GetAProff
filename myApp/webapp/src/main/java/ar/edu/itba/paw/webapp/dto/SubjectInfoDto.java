package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.Teaches;
import ar.edu.itba.paw.models.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class SubjectInfoDto {
    private String subjectName;

    private Long teacherId, subjectId;

    private List<Integer> levels, prices;

    public static SubjectInfoDto fromSubjectInfo(String subject, List<Teaches> subjectData) {
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.levels = new ArrayList<>();
        subjectInfoDto.prices = new ArrayList<>();
        subjectInfoDto.teacherId = subjectData.get(0).getTeacher().getId();
        subjectInfoDto.subjectId = subjectData.get(0).getSubject().getSubjectId();
        subjectInfoDto.subjectName = subject;
        subjectData.forEach(teaches -> {
            subjectInfoDto.levels.add(teaches.getLevel());
            subjectInfoDto.prices.add(teaches.getPrice());
        });
        return subjectInfoDto;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }
}

