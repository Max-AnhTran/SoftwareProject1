package haaga_helia.fi.SoftwareProject1.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "UserTable")
public class AppUser {
    //as the teacher said, adding unneccessary things for now goes against scrum, so they'll be omitted (email, password)
    public enum Role {
        TEACHER, STUDENT
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "role", nullable = false)
    private Role role;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public AppUser() {
    }
    public AppUser(String username, Role role) {
        super();
        this.username = username;
        this.role = role;
    }


}
