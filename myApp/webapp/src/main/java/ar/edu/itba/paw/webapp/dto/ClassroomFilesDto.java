package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.webapp.controller.FilesController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;

import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassroomFilesDto {
    List<Link> shared;
    List<Link> notShared;

    public static ClassroomFilesDto getClassroomFilesDto(List<SubjectFile> shared, List<SubjectFile> notShared) {
        ClassroomFilesDto classroomFilesDto = new ClassroomFilesDto();
        classroomFilesDto.notShared = new ArrayList<>();
        classroomFilesDto.shared = new ArrayList<>();
        if(shared != null && !shared.isEmpty())
            classroomFilesDto.shared = shared.stream().map(e -> JaxRsLinkBuilder.linkTo(FilesController.class).slash(e.getFileId()).withRel(e.getFileName())).collect(Collectors.toList());
        if(notShared!= null && !notShared.isEmpty())
            classroomFilesDto.notShared = notShared.stream().map(e -> JaxRsLinkBuilder.linkTo(FilesController.class).slash(e.getFileId()).withRel(e.getFileName())).collect(Collectors.toList());
        return classroomFilesDto;
    }

    public List<Link> getShared() {
        return shared;
    }

    public List<Link> getNotShared() {
        return notShared;
    }

    public void setShared(List<Link> shared) {
        this.shared = shared;
    }

    public void setNotShared(List<Link> notShared) {
        this.notShared = notShared;
    }
}
