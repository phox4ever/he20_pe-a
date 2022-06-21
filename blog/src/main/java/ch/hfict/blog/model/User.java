package ch.hfict.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Comment> comments;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User() {
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, username='%s']",
                id, username);
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
