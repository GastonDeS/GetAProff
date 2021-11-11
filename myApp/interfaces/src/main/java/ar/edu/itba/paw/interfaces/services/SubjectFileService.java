package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectFile;

import java.util.List;

public interface SubjectFileService {
    List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId);

    SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Subject subject, Integer level);

    int deleteSubjectFile(Long fileId);

    List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level);

    SubjectFile getSubjectFileById(Long fileId);
}
