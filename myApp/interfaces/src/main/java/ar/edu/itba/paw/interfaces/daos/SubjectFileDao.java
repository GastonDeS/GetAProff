package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.UserFile;

import java.util.List;

public interface SubjectFileDao {
    List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId);

    SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Subject subject, Integer level);

    int deleteSubjectFile(Long fileId);

    List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level);

    SubjectFile getSubjectFileById(Long fileId);
}
