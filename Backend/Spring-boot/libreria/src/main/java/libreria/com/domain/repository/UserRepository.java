package libreria.com.domain.repository;

import libreria.com.domain.model.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    User save(User user);
    long count();
    List<User> findAll();
}