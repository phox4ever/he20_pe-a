package ch.hfict.blog.model;

public class UserDto {
    private final String username;
    private final String password;

    public UserDto(String username, String password) {
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
