package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;


import java.io.IOException;
import java.util.List;

public interface UserFileService {
    List<UserFile> getAllUserFiles(Long ownerId);

    UserFile saveNewFile(byte[] file, String fileName, Long ownerId);
    int deleteFile(Long fileId);

}
