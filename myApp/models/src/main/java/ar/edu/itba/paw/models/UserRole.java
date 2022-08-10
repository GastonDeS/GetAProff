package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "userroles")
@IdClass(UserRoleId.class)
public class UserRole {

    @Column
    @Id
    private Long roleid;

    @JoinColumn(name = "userid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "userroles_userid_fkey"))
    @ManyToOne(optional = false)
    @Id
    private User user;

    UserRole() {
        //Just for Hibernate
    }

    public UserRole(Long roleid, User user) {
        this.roleid = roleid;
        this.user = user;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
