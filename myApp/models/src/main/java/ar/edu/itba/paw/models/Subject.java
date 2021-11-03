package ar.edu.itba.paw.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_subjectid_seq")
    @SequenceGenerator(name = "subject_subjectid_seq", sequenceName = "subject_subjectid_seq", allocationSize = 1)
    private Long subjectId;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Teaches> teachersTeachingSubject;

    public Subject(){
        //For Hibernate
    }

    public Subject(String name, Long id){
        this.subjectId = id;
        this.name = name;
    }

    public Long getId() {
        return subjectId;
    }

    public void setId(Long id) {
        this.subjectId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s\n", name);
    }
}
