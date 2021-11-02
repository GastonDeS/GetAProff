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
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserFile> getAllUserFiles(Long ownerId) {
        User owner = userDao.get(ownerId).orElseThrow(RuntimeException::new);
        return userFileDao.getAllUserFiles(owner);
    }

    @Transactional
    @Override
    public UserFile saveNewFile(byte[] file, String fileName, Long ownerId) {
        User owner = userDao.get(ownerId).orElseThrow(RuntimeException::new);
        return userFileDao.saveNewFile(file,fileName,owner);
    }
}
