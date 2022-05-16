package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;

@SqlResultSetMappings(
        {@SqlResultSetMapping(
                name = "TeacherInfoMapping",
                classes = @ConstructorResult(
                        targetClass = TeacherInfo.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name"),
                                @ColumnResult(name = "maxPrice", type = int.class),
                                @ColumnResult(name = "minPrice", type = int.class),
                                @ColumnResult(name = "desc"),
                                @ColumnResult(name = "rate", type = float.class),
                                @ColumnResult(name = "sch"),
                                @ColumnResult(name = "mail"),
                                @ColumnResult(name = "reviews", type = int.class)
                        }
                )
        ), @SqlResultSetMapping(
                name = "SubjectInfoMapping",
                classes = @ConstructorResult(
                        targetClass = SubjectInfo.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "name"),
                                @ColumnResult(name = "price", type = int.class),
                                @ColumnResult(name = "level", type = int.class),
                        }
                )
        )}
)
@Entity
@Table(name = "teaches")
@IdClass(TeachesId.class)
public class Teaches {

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "teachesInfo", cascade = CascadeType.ALL)
    private List<SubjectFile> subjectsFilesList;

    Teaches() {
        //Just for hibernate
    }

    public Teaches (User teacher, Subject subject, int price, int level) {
        this.teacher = teacher;
        this.subject = subject;
        this.price = price;
        this.level = level;
    }

    private Teaches(Builder builder) {
        this.price = builder.price;
        this.level = builder.level;
        this.teacher = builder.teacher;
        this.subject = builder.subject;
        this.subjectsFilesList = builder.subjectsFilesList;
    }

    public List<SubjectFile> getSubjectsFilesList() {
        return subjectsFilesList;
    }

    public void setSubjectsFilesList(List<SubjectFile> subjectsFilesList) {
        this.subjectsFilesList = subjectsFilesList;
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

    public static class Builder
    {
        private int price, level;
        private User teacher;
        private Subject subject;
        private List<SubjectFile> subjectsFilesList;

        public Builder() {
        }

        public Builder teacher(User teacher) {
            this.teacher = teacher;
            return this;
        }

        public Builder subject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder price(int price) {
            this.price = price;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public Builder favourites(List<SubjectFile> subjectsFilesList) {
            this.subjectsFilesList = subjectsFilesList;
            return this;
        }

        public Teaches build() {
            return new Teaches(this);
        }
    }
}
