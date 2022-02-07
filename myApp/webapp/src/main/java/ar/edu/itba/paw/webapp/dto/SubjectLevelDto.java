package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectLevelDto {

    private SubjectDto subject;

    private List<Integer> levels;

    public static SubjectLevelDto fromSubjectLevel(UriInfo uri, Map.Entry<Subject, List<Integer>> subjectAndLevels) {
        SubjectLevelDto subjectLevelDto = new SubjectLevelDto();
        subjectLevelDto.subject = SubjectDto.get(uri, subjectAndLevels.getKey());
        subjectLevelDto.levels = subjectAndLevels.getValue().stream().sorted().collect(Collectors.toList());
        return subjectLevelDto;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }
}
