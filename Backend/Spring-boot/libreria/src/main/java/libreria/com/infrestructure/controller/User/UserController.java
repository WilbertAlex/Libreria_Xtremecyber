package libreria.com.infrestructure.controller.User;

import libreria.com.domain.model.entity.User;
import libreria.com.domain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userRepository.findAll();

        // Convertir a DTO para no exponer la contraseña
        List<UserResponse> response = users.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // DTO para no exponer la contraseña
    public static class UserResponse {
        private Long id;
        private String username;
        private String role;

        public UserResponse(Long id, String username, String role) {
            this.id = id;
            this.username = username;
            this.role = role;
        }

        // Getters
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getRole() { return role; }

        // Setters
        public void setId(Long id) { this.id = id; }
        public void setUsername(String username) { this.username = username; }
        public void setRole(String role) { this.role = role; }
    }
}