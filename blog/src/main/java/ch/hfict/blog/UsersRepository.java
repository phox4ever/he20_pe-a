package ch.hfict.blog;

import ch.hfict.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Long> {

    public List<User> findByUsername(String username);

    User findById(long id);
}
