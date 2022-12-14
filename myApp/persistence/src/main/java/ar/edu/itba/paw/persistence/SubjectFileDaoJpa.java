package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.SubjectFileDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public class SubjectFileDaoJpa implements SubjectFileDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubjectFile> getAllSubjectFilesFromUser(Long ownerId) {
        final User owner = entityManager.getReference(User.class, ownerId);
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf join fetch sf.teachesInfo t where t.teacher = :owner", SubjectFile.class);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public List<SubjectFile> getAllSubjectFilesFromUserBySubjectIdAndLevel(Long ownerId, Long subjectId, int level) {
        final User owner = entityManager.getReference(User.class, ownerId);
        TypedQuery<SubjectFile> query = entityManager.createQuery("from SubjectFile sf join fetch sf.teachesInfo t where t.teacher = :owner and t.level = :level and t.subject.id = :subjectId", SubjectFile.class);
        query.setParameter("subjectId", subjectId);
        query.setParameter("level", level);
        query.setParameter("owner", owner);
        return query.getResultList();
    }

    @Override
    public SubjectFile saveNewSubjectFile(byte[] file, String fileName, Long ownerId, Long subjectId, Integer level) {
        final TeachesId teachesId = new TeachesId(ownerId, subjectId, level);
        final Optional<Teaches> teaches = Optional.ofNullable(entityManager.find(Teaches.class, teachesId));
        if (!teaches.isPresent()) return null;
        final SubjectFile newSubjectFile = new SubjectFile( null, fileName, file, teaches.get());
        entityManager.persist(newSubjectFile);
        return newSubjectFile;
    }

    @Override
    public int deleteSubjectFile(Long fileId) {
        final Query query = entityManager.createQuery("delete from SubjectFile sf where sf.fileId = :file");
        query.setParameter("file", fileId);
        return query.executeUpdate();
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
