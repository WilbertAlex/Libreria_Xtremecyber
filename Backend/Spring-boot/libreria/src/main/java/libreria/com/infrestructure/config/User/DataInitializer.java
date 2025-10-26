package libreria.com.infrestructure.config.User;


import libreria.com.domain.model.entity.User;
import libreria.com.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class DataInitializer {


    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User user = new User();
                user.setUsername("admin");
                user.setPassword(encoder.encode("1234"));
                user.setRole("ADMIN");
                userRepository.save(user);
                System.out.println("✅ Usuario 'admin' creado con contraseña '1234'");
            }
        };
    }
}
