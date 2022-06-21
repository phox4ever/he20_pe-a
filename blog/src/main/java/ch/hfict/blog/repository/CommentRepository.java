package ch.hfict.blog.repository;

import ch.hfict.blog.model.Comment;

import ch.hfict.blog.model.Post;
import ch.hfict.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    public List<Comment> findByUserId(Long id);
    public List<Comment> findByPostId(Long id);
}
