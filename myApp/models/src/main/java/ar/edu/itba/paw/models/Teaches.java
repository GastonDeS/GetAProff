package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "teaches")
@IdClass(TeachesId.class)
public class Teaches {

    @Column
    private int price;

    @Id
    private int level;

    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    @Id
    private User teacher;

    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid")
    @ManyToOne(optional = false)
    @Id
    private Subject subject;

    Teaches() {
        //Just for hibernate
    }

    public Teaches (User teacher, Subject subject, int price, int level) {
        this.teacher = teacher;
        this.subject = subject;
        this.price = price;
        this.level = level;
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

    public enum Level {
        NONE,
        PRIMARY,
        SECONDARY,
        TERTIARY
    }
}
