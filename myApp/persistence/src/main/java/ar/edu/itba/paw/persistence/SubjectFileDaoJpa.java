package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.models.Subject;
import ar.edu.itba.paw.models.SubjectFile;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SubjectFileDaoJpa implements SubjectFileDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId) {
        final User owner = entityManager.getReference(User.class, ownerId);
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf where sf.fileOwner = :owner",SubjectFile.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Subject subject, Integer level) {
        final User owner = entityManager.getReference(User.class, ownerId);
        final SubjectFile newSubjectFile = new SubjectFile(owner,null,fileName,file,level,subject);
        entityManager.persist(newSubjectFile);
        return newSubjectFile;
    }

    @Override
    public int deleteSubjectFile(Long fileId) {
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf where sf.fileId = :file", SubjectFile.class);
        query.setParameter("file",fileId);
        SubjectFile subjectFile = query.getSingleResult();
        entityManager.remove(subjectFile);
        return 0;
    }

    @Override
    public SubjectFile getSubjectFileById(Long fileId) {
        return entityManager.find(SubjectFile.class,fileId);
    }
}
