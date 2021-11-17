package ar.edu.itba.paw.models;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {

    private Long roleid, user;

    UserRoleId() {
        //Just for hibernate
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(roleid, that.roleid) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleid, user);
    }
}
