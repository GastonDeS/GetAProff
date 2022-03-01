package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.UserFile;

import java.util.List;
import java.util.Optional;

public interface UserFileService {
    List<UserFile> getAllUserFiles(Long ownerId);

    Optional<UserFile> saveNewFile(byte[] file, String fileName, Long ownerId);

    int deleteFile(Long fileId);

    Optional<UserFile> getFileById(Long fileId);

}
