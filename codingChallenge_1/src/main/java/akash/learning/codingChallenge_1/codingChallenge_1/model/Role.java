package akash.learning.codingChallenge_1.codingChallenge_1.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    // @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade =
    // CascadeType.ALL)
    // private List<Person> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public List<Person> getUsers() {
    // return users;
    // }

    // public void setUsers(List<Person> users) {
    // this.users = users;
    // }

    public static String getAdminRoles() {
        return "ROLE_ADMIN,ROLE_USER";
    }

    public static String getUserRoles() {
        return "ROLE_USER";
    }

    public static String getSuperAdminRoles() {
        return "ROLE_SUPER_ADMIN,ROLE_ADMIN,ROLE_USER";
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
