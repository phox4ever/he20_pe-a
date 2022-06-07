package ch.hfict.blog.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;

    private String password;

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
