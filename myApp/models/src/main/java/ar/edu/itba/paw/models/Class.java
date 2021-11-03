package ar.edu.itba.paw.models;


import javax.persistence.*;

@Entity
@Table(name = "classes")
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_classid_seq")
    @SequenceGenerator(name = "classes_classid_seq", sequenceName = "classes_classid_seq", allocationSize = 1)
    private Long classId;

    @JoinColumn(name = "studentid", referencedColumnName = "userid")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User student;

    @JoinColumn(name = "teacherid", referencedColumnName = "userid")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private User teacher;

    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Subject subject;

    @Column
    private int level, price, status;


    Class() {
        //For Hibernate
    }

    public Class(User student, User teacher, Subject subject, int level, int price) {
        this.student = student;
        this.teacher = teacher;
        this.subject = subject;
        this.level = level;
        this.price = price;
        this.status = Status.PENDING.value;
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
        RATED(6),
        ;

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
