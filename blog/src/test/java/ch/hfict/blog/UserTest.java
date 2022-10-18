package ch.hfict.blog;

import ch.hfict.blog.model.User;
import ch.hfict.blog.model.UserDto;
import ch.hfict.blog.utils.Crypto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void init() {
        String token = Crypto.createToken(1L);
        if (token != null) {
            restTemplate.getRestTemplate().setInterceptors(
                    Collections.singletonList((request, body, execution) -> {
                        request.getHeaders()
                                .setBearerAuth(token);
                        return execution.execute(request, body);
                    }));
        }
    }

    @Test
    public void listAllUsers() throws Exception {

        ResponseEntity<User[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/users", User[].class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        User[] users = response.getBody();
        assert users != null;
        assertThat(users.length, equalTo(2));
    }

    @Test
    public void createUser() throws Exception {
        UserDto user = new UserDto("bart", "12345");
        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", request, User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getUsername(), equalTo(user.getUsername()));
    }

    @Test
    public void doNotReturnPassword() throws Exception {
        ResponseEntity<User> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/users/1", User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getPassword(), equalTo(null));
    }

    @Test
    public void duplicateUsername() throws Exception {
        UserDto user = new UserDto("homer", "1234");
        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", request, User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.CONFLICT));
    }

    @Test
    public void invalidPassword() throws Exception {
        UserDto user = new UserDto("lisa", "");
        HttpEntity<UserDto> request = new HttpEntity<>(user);
        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", request, User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

        user.setPassword(null);
        request = new HttpEntity<>(user);
        response = restTemplate.postForEntity(
                "http://localhost:" + port + "/users", request, User.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}


