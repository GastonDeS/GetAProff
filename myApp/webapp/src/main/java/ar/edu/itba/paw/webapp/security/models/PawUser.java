package ar.edu.itba.paw.webapp.security.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class PawUser extends User {
    private Integer fileNumber;
    private Long userId;
    private String name;
    private String email;
    private boolean isTeacher;

    public PawUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                   Long userId, String name, String email,
                      boolean isTeacher) {
        super(username, password, authorities);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.isTeacher = isTeacher;
    }

    public ar.edu.itba.paw.models.User toUser() {
        return new ar.edu.itba.paw.models.User.Builder(this.name).userId(this.userId)
                .build();
    }

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setAdmin(boolean admin) {
        isTeacher = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PawUser user = (PawUser) o;
        return Objects.equals(fileNumber, user.fileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fileNumber);
    }
}
