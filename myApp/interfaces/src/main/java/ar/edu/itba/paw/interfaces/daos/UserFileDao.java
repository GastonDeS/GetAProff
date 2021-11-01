package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;

import java.util.List;

public interface UserFileDao {

    List<UserFile> getAllUserFiles(User owner);

    UserFile saveNewFile(byte[] file, String fileName, User owner);

    //int removeUserFile();
}
