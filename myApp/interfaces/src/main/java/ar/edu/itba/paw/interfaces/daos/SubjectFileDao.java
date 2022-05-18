package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.SubjectFile;

import java.util.List;

public interface SubjectFileDao {
    List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId);

    SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Long subjectId, Integer level);

    int deleteSubjectFile(Long fileId);

    List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level);

    SubjectFile getSubjectFileById(Long fileId);

    List<SubjectFile> getAllSubjectFilesFromUserBySubjectIdAndLevel(Long ownerId, Long subjectId, int level);

}
