package ch.hfict.blog.model;

public class LoginDto {
    private final String username;
    private final String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
