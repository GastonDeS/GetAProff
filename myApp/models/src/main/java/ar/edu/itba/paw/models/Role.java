package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class  Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_roleid_seq")
    @SequenceGenerator(name = "roles_roleid_seq", sequenceName = "roles_roleid_seq", allocationSize = 1)
    private Long roleId;

    @Column
    private String role;

    @ManyToMany(mappedBy = "userRoles")
    private Collection<User> users;

    public Role(Long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    Role() {
        //Just for Hibernate
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
