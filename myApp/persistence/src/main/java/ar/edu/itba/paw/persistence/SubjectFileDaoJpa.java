package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubjectFileDaoJpa implements SubjectFileDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId) {
        final User owner = entityManager.getReference(User.class, ownerId);
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf where sf.teachesInfo.teacher = :owner", SubjectFile.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Subject subject, Integer level) {
        final TeachesId teachesId = new TeachesId(ownerId,subject.getSubjectId(),level);
        final Teaches teaches = entityManager.find(Teaches.class, teachesId);
        final SubjectFile newSubjectFile = new SubjectFile( null, fileName, file, teaches);
        entityManager.persist(newSubjectFile);
        return newSubjectFile;
    }

    @Override
    public int deleteSubjectFile(Long fileId) {
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf where sf.fileId = :file", SubjectFile.class);
        query.setParameter("file", fileId);
        SubjectFile subjectFile = query.getSingleResult();
        entityManager.remove(subjectFile);
        return 0;
    }

    @Override
    public List<SubjectFile> filterUserSubjectFilesBySubjectAndLevel(Long userId, Long subjectId, Integer level) {
        String mainQuery = "SELECT sf FROM SubjectFile sf WHERE sf.teachesInfo in ( from Teaches t where t.teacher =:user ";
        Subject selectedSubject = entityManager.find(Subject.class, subjectId);
        User mayBeOwner = entityManager.find(User.class, userId);

        if (subjectId > 0)
            mainQuery += "AND t.subject =:subject ";
        if (level > 0)
            mainQuery += "AND t.level =:level ";

        mainQuery += " )";

        TypedQuery<SubjectFile> query = entityManager.createQuery(mainQuery, SubjectFile.class);
        if (subjectId > 0)
            query.setParameter("subject", selectedSubject);
        if (level > 0)
            query.setParameter("level", level);

        query.setParameter("user", mayBeOwner);

        return query.getResultList();
    }

    @Override
    public SubjectFile getSubjectFileById(Long fileId) {
        return entityManager.find(SubjectFile.class, fileId);
    }
}
