package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "classes")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_classid_seq")
    @SequenceGenerator(name = "classes_classid_seq", sequenceName = "classes_classid_seq", allocationSize = 1)
    private Long classId;

    @JoinColumn(name = "studentid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "classes_studentid_fkey"))
    @ManyToOne(optional = false)
    private User student;

    @JoinColumn(name = "teacherid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "classes_teacherid_fkey"))
    @ManyToOne(optional = false)
    private User teacher;

    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid", foreignKey = @ForeignKey(name = "classes_subjectid_fkey"))
    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "shared",
            joinColumns = @JoinColumn(
                    name = "classid", referencedColumnName = "classid"),
            inverseJoinColumns = @JoinColumn(
                    name = "fileid", referencedColumnName = "fileid"))
    private List<SubjectFile> sharedFilesByTeacher;

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
        this.sharedFilesByTeacher = new ArrayList<>();
    }

    private Lecture(Builder builder) {
        this.teacher = builder.teacher;
        this.student = builder.student;
        this.subject = builder.subject;
        this.level = builder.level;
        this.price = builder.price;
        this.status = builder.status;
        this.studentLastTime = builder.studentLastTime;
        this.teacherLastTime = builder.teacherLastTime;
        this.sharedFilesByTeacher = builder.sharedFilesByTeacher;
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

    public List<SubjectFile> getSharedFilesByTeacher() {
        return sharedFilesByTeacher;
    }

    public void setSharedFilesByTeacher(List<SubjectFile> sharedFilesByTeacher) {
        this.sharedFilesByTeacher = sharedFilesByTeacher;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Lecture)) return false;
        Lecture aux = (Lecture) object;
        return aux.subject.equals(this.subject)
                && aux.student.equals(this.student)
                && aux.teacher.equals(this.teacher)
                && aux.classId.equals(this.classId)
                && aux.status == this.status
                && aux.level == this.level
                && aux.price == this.price;
    }

    public static class Builder
    {
        private User teacher, student;
        private Subject subject;
        private int level, price, status;
        private Timestamp studentLastTime, teacherLastTime;
        private List<SubjectFile> sharedFilesByTeacher;

        public Builder(User teacher, User student, Subject subject) {
            this.teacher = teacher;
            this.student = student;
            this.subject = subject;
        }
        public Builder teacher(User teacher) {
            this.teacher = teacher;
            return this;
        }
        public Builder student(User student) {
            this.student = student;
            return this;
        }
        public Builder subject(Subject subject) {
            this.subject = subject;
            return this;
        }
        public Builder level(int level) {
            this.level = level;
            return this;
        }
        public Builder price(int price) {
            this.price = price;
            return this;
        }
        public Builder status(int status) {
            this.status = status;
            return this;
        }
        public Builder studentLastTime(Timestamp studentLastTime) {
            this.studentLastTime = studentLastTime;
            return this;
        }
        public Builder sharedFilesByTeacher(List<SubjectFile> sharedFilesByTeacher) {
            this.sharedFilesByTeacher = sharedFilesByTeacher;
            return this;
        }
        public Builder teacherLastTime(Timestamp teacherLastTime) {
            this.teacherLastTime = teacherLastTime;
            return this;
        }
        public Lecture build() {
            return new Lecture(this);
        }
    }
}
