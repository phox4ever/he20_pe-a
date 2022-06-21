package ch.hfict.blog.repository;

import ch.hfict.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findByUsername(String username);
}
