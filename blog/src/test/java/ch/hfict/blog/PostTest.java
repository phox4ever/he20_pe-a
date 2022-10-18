package ch.hfict.blog;

import ch.hfict.blog.model.Post;
import ch.hfict.blog.model.PostDto;
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
public class PostTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    public void init() {
        token = Crypto.createToken(1L);
    }


    @Test
    public void createPostNoAuthHeader() throws Exception {
        PostDto post = new PostDto("Second Post", "Content of second post", 1L);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<PostDto> request = new HttpEntity<>(post, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/posts", request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
        assertThat(response.getBody(), equalTo("Authorization header not found\r\n"));
    }

    @Test
    public void createPostAuthHeaderNoToken() throws Exception {
        PostDto post = new PostDto("Second Post", "Content of second post", 1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("");
        HttpEntity<PostDto> request = new HttpEntity<>(post, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/posts", request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
        assertThat(response.getBody(), equalTo("Missing token\r\n"));
    }

    @Test
    public void createPostAuthHeaderInvalidToken() throws Exception {
        PostDto post = new PostDto("Second Post", "Content of second post", 1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("xyz");
        HttpEntity<PostDto> request = new HttpEntity<>(post, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/posts", request, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
        assertThat(response.getBody(), equalTo("Invalid token\r\n"));
    }

    @Test
    public void createPostWithAuthHomer() throws Exception {
        PostDto post = new PostDto("Second Post", "Content of second post", 1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<PostDto> request = new HttpEntity<>(post, headers);
        ResponseEntity<Post> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/posts", request, Post.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getTitle(), equalTo(post.getTitle()));
        assertThat(Objects.requireNonNull(response.getBody()).getUser().getId(), equalTo(post.getUserId()));
    }

    @Test
    public void createPostWithAuthMarge() throws Exception {
        PostDto post = new PostDto("Second Post", "Content of second post", 2L);
        HttpHeaders headers = new HttpHeaders();
        String token2 = Crypto.createToken(post.getUserId());
        assert token2 != null;
        headers.setBearerAuth(token2);
        HttpEntity<PostDto> request = new HttpEntity<>(post, headers);
        ResponseEntity<Post> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/posts", request, Post.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getTitle(), equalTo(post.getTitle()));
        assertThat(Objects.requireNonNull(response.getBody()).getUser().getId(), equalTo(post.getUserId()));
    }
}
