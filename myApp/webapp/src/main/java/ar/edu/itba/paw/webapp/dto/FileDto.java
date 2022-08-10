package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Post;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.UserFile;

public class FileDto {
    private String name;

    private byte[] file;

    public static FileDto fromUserFile(UserFile file) {
        FileDto fileDto = new FileDto();
        fileDto.file = file.getFile();
        fileDto.name = file.getFileName();
        return fileDto;
    }

    public static FileDto fromSubjectFile(SubjectFile file) {
        FileDto fileDto = new FileDto();
        fileDto.file = file.getFile();
        fileDto.name = file.getFileName();
        return fileDto;
    }

    public static FileDto fromPostFile(Post post) {
        FileDto fileDto = new FileDto();
        fileDto.file = post.getFile();
        fileDto.name = post.getFilename();
        return fileDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
