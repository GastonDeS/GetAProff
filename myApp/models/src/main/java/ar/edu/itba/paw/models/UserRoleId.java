package ar.edu.itba.paw.models;

import java.io.Serializable;

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
}
