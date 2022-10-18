package ch.hfict.blog;

import ch.hfict.blog.utils.Crypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	@Value("${jwt_secret}")
	public void setJwtSecret(String secret){
		Crypto.secret = secret;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
