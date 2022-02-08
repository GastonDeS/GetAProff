package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.UserFile;

public class FileDto {

    private byte[] file;

    public static FileDto fromUserFile(UserFile file) {
        FileDto fileDto = new FileDto();
        fileDto.file = file.getFile();
        return fileDto;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
