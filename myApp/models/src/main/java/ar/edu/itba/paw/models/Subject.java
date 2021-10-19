package ar.edu.itba.paw.models;

public class Subject {
    private Long id;
    private String name;

    public Subject(String name, Long id){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
