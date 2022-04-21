package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Subject;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubjectLevelDto extends SubjectDto{

    private List<Integer> levels;
    private String name, url;
    private Long subjectId;

    public static SubjectLevelDto fromSubjectLevel(UriInfo uri, Map.Entry<Subject, List<Integer>> subjectAndLevels) {
        SubjectLevelDto subjectLevelDto = new SubjectLevelDto();
        subjectLevelDto.levels = subjectAndLevels.getValue().stream().sorted().collect(Collectors.toList());
        subjectLevelDto.name = subjectAndLevels.getKey().getName();
        subjectLevelDto.subjectId = subjectAndLevels.getKey().getSubjectId();
        subjectLevelDto.url = uri.getBaseUriBuilder().path("subjects")
                .path(String.valueOf(subjectAndLevels.getKey().getSubjectId())).build().toString();
        return subjectLevelDto;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Long getSubjectId() {
        return subjectId;
    }

    @Override
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}
