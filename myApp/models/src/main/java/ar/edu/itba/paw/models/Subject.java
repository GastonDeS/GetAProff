package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_subjectid_seq")
    @SequenceGenerator(name = "subject_subjectid_seq", sequenceName = "subject_subjectid_seq", allocationSize = 1)
    private Long subjectid;

    @Column
    private String name;

    public Subject(String name, Long subjectid){
        this.subjectid = subjectid;
        this.name = name;
    }

    public Long getId() {
        return subjectid;
    }

    public void setId(Long subjectid) {
        this.subjectid = subjectid;
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
