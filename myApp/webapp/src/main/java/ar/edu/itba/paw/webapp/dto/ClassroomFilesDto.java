package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectFile;

import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class ClassroomFilesDto {
    List<PaginatedFileDto> shared;
    List<PaginatedFileDto> notShared;

    public static ClassroomFilesDto getClassroomFilesDto(UriInfo uri, List<SubjectFile> shared, List<SubjectFile> notShared) {
        ClassroomFilesDto classroomFilesDto = new ClassroomFilesDto();
        classroomFilesDto.shared = shared.stream()
                .map(e -> PaginatedFileDto.getPaginatedFileDto(uri, "files", "",e.getFileName(), e.getFileId()))
                .collect(Collectors.toList());
        classroomFilesDto.notShared = notShared.stream()
                .map(e -> PaginatedFileDto.getPaginatedFileDto(uri, "files", "",e.getFileName(), e.getFileId()))
                .collect(Collectors.toList());
        return classroomFilesDto;
    }

    public List<PaginatedFileDto> getShared() {
        return shared;
    }

    public void setShared(List<PaginatedFileDto> shared) {
        this.shared = shared;
    }

    public List<PaginatedFileDto> getNotShared() {
        return notShared;
    }

    public void setNotShared(List<PaginatedFileDto> notShared) {
        this.notShared = notShared;
    }
}
