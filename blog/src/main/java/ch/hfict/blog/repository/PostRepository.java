package ch.hfict.blog.repository;

import ch.hfict.blog.model.Post;
import ch.hfict.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    public List<Post> findByUser(User user);
}
