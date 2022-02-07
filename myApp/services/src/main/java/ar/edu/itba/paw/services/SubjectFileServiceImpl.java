package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.interfaces.services.SubjectFileService;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectFileServiceImpl implements SubjectFileService {

    @Autowired
    SubjectFileDao subjectFileDao;

    @Transactional
    @Override
    public List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId) {
        return subjectFileDao.getAllSubjectFilesFromUser(ownerId);
    }

    @Transactional
    @Override
    public Optional<SubjectFile> saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Long subejctId, Integer level) {
        return Optional.ofNullable(subjectFileDao.saveNewSubjectFile(file, fileName, ownerId, subejctId, level));
    }

    @Transactional
    @Override
    public int deleteSubjectFile(Long fileId) {
        return subjectFileDao.deleteSubjectFile(fileId);
    }

    @Transactional
    @Override
    public List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level) {
        return subjectFileDao.filterUserSubjectFilesBySubjectAndLevel(userId,subjectId,level);
    }


    @Override
    public SubjectFile getSubjectFileById(Long fileId) {
        return subjectFileDao.getSubjectFileById(fileId);
    }
}
