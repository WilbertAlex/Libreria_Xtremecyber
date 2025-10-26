package libreria.com.infrestructure.repository.UserImpl;

import libreria.com.domain.model.entity.User;
import libreria.com.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryImpl extends JpaRepository<User, Long>, UserRepository {
    Optional<User> findByUsername(String username);
    // findAll(), findById(), save(), count() ya vienen de JpaRepository
}