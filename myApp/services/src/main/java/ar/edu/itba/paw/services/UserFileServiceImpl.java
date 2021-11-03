package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.UserFileDao;
import ar.edu.itba.paw.interfaces.services.UserFileService;
import ar.edu.itba.paw.models.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override
    public List<UserFile> saveMultipleNewFiles(MultipartFile[] files, Long ownerId) throws IOException {
        List<UserFile> savedFiles = new ArrayList<>();
        for(MultipartFile file : files)
            savedFiles.add(userFileDao.saveNewFile(file.getBytes(),file.getOriginalFilename(),ownerId));
        return savedFiles;
    }

    @Transactional
    @Override
    public int deleteFile(Long fileId){
        return userFileDao.deleteFile(fileId);
    }
}
