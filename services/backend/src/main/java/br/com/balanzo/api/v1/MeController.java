package br.com.balanzo.api.v1;

import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import java.security.Principal;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
public class MeController {

    private final UserRepository userRepository;

    public MeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<MeResponse> get(Principal principal) {
        UUID userId = userIdFrom(principal);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getStatus().name(),
                user.getDefaultCurrency(),
                user.getTimezone()
        ));
    }

    private UUID userIdFrom(Principal principal) {
        if (principal instanceof Jwt jwt && jwt.getSubject() != null) {
            try {
                return UUID.fromString(jwt.getSubject());
            } catch (IllegalArgumentException ignored) {}
        }
        return null;
    }

    public record MeResponse(UUID id, String email, String name, String status,
                             String defaultCurrency, String timezone) {}
}
