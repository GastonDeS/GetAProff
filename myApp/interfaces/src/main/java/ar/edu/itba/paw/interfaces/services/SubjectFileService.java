package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.SubjectFile;

import java.util.List;
import java.util.Optional;

public interface SubjectFileService {
    List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId);

    Optional<SubjectFile> saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Long subjectId, Integer level);

    int deleteSubjectFile(Long fileId);

    List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level);

    SubjectFile getSubjectFileById(Long fileId);
}
