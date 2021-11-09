package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "classes")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_classid_seq")
    @SequenceGenerator(name = "classes_classid_seq", sequenceName = "classes_classid_seq", allocationSize = 1)
    private Long classId;

    @JoinColumn(name = "studentid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "classes_studentid_fkey"))
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User student;

    @JoinColumn(name = "teacherid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "classes_teacherid_fkey"))
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User teacher;

    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid", foreignKey = @ForeignKey(name = "classes_subjectid_fkey"))
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Subject subject;

    @Column
    private int level, price, status;

    @Column
    private Timestamp studentLastTime, teacherLastTime;

    @OneToMany(mappedBy = "associatedLecture", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> classPosts;

    private Integer notifications;

    Lecture() {
        //For Hibernate
    }

    public Lecture(User student, User teacher, Subject subject, int level, int price, Timestamp studentLastTime, Timestamp teacherLastTime) {
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.status = Status.PENDING.value;
        this.studentLastTime = studentLastTime;
        this.teacherLastTime = teacherLastTime;
    }

    public Integer getNotifications() {
        return notifications;
    }

    public void setNotifications(Integer notifications) {
        this.notifications = notifications;
    }

    public Timestamp getStudentLastTime() {
        return studentLastTime;
    }

    public void setStudentLastTime(Timestamp studentLastTime) {
        this.studentLastTime = studentLastTime;
    }

    public Timestamp getTeacherLastTime() {
        return teacherLastTime;
    }

    public void setTeacherLastTime(Timestamp teacherLastTime) {
        this.teacherLastTime = teacherLastTime;
    }

    public List<Post> getClassPosts() {
        return classPosts;
    }

    public void setClassPosts(List<Post> classPosts) {
        this.classPosts = classPosts;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public enum Status {
        PENDING(0),
        ACCEPTED(1),
        FINISHED(2),
        CANCELEDS(3),
        CANCELEDT(4),
        REJECTED(5),
        RATED(6);

        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
