package ch.hfict.blog.model;

public class JwtDto {
    private String token;
    private User user;

    public JwtDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
