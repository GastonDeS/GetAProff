package ar.edu.itba.paw.models;

public enum Roles {
    STUDENT("USER_STUDENT", 0l),
    TEACHER("USER_TEACHER", 1l);

    private String name;
    private Long id;

    Roles(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
