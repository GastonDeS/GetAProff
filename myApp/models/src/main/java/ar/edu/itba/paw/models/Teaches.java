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

    @JoinColumn(name = "userid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "teaches_userid_fkey"))
    @ManyToOne(optional = false)
    @Id
    private User teacher;

    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid", foreignKey = @ForeignKey(name = "teaches_subjectid_fkey"))
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Teaches)) return false;
        Teaches aux = (Teaches) object;
        if (!aux.teacher.equals(teacher)) return false;
        if (!aux.subject.equals(subject)) return false;
        return aux.level == level;
    }

    public enum Level {
        NONE,
        PRIMARY,
        SECONDARY,
        TERTIARY
    }
}
