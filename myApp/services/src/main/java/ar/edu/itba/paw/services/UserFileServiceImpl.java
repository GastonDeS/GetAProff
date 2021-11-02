package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.UserFileDao;
import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileDao userFileDao;

    @Override
    public List<UserFile> getAllUserFiles(Long ownerId) {
        return userFileDao.getAllUserFiles(ownerId);
    }

    @Transactional
    @Override
    public UserFile saveNewFile(byte[] file, String fileName, Long ownerId) {
        return userFileDao.saveNewFile(file,fileName,ownerId);
    }
}
