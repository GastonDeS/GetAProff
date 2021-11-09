package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.interfaces.services.SubjectFileService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectFileServiceImpl implements SubjectFileService {

    @Autowired
    SubjectFileDao subjectFileDao;

    @Override
    public List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId) {
        return subjectFileDao.getAllSubjectFilesFromUser(ownerId);
    }

    @Override
    public SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Subject subject, Integer level) {
        return subjectFileDao.saveNewSubjectFile(file,fileName,ownerId,subject,level);
    }

    @Transactional
    @Override
    public List<SubjectFile> saveMultipleNewSubjectFiles(MultipartFile[] files, Long ownerId, Subject subject, Integer level) throws IOException {
        List<SubjectFile> newFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            SubjectFile newFile = subjectFileDao.saveNewSubjectFile(file.getBytes(), file.getOriginalFilename(), ownerId, subject, level);
            newFiles.add(newFile);
        }
        return newFiles;
    }

    @Transactional
    @Override
    public int deleteSubjectFile(Long fileId) {
        return subjectFileDao.deleteSubjectFile(fileId);
    }

    @Override
    public List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level) {
        return subjectFileDao.filterUserSubjectFilesBySubjectAndLevel(userId,subjectId,level);
    }


    @Override
    public SubjectFile getSubjectFileById(Long fileId) {
        return subjectFileDao.getSubjectFileById(fileId);
    }
}
