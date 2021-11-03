package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.UserFile;

import java.util.List;

public interface UserFileService {
    List<UserFile> getAllUserFiles(Long ownerId);

    UserFile saveNewFile(byte[] file, String fileName, Long ownerId);

    int deleteFile(Long fileId);

    UserFile getFileById(Long fileId);

}
