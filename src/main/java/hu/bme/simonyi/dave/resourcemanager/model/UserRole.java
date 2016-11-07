package hu.bme.simonyi.dave.resourcemanager.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by dkiss on 2016. 11. 07..
 */
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer userRoleID;

    @NotNull
    private String roleName;

    @ManyToOne
    private User user;

    public UserRole() {
    }

    public Integer getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(Integer userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
