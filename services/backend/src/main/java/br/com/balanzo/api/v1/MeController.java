package br.com.balanzo.api.v1;

import br.com.balanzo.application.identidade.UpdateProfile;
import br.com.balanzo.common.exception.ResourceNotFoundException;
import br.com.balanzo.common.security.CurrentUserResolver;
import br.com.balanzo.domain.identidade.entity.User;
import br.com.balanzo.infrastructure.persistence.identidade.UserRepository;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/me")
public class MeController {

    private final UserRepository userRepository;
    private final CurrentUserResolver currentUser;
    private final UpdateProfile updateProfile;

    public MeController(UserRepository userRepository, CurrentUserResolver currentUser,
                        UpdateProfile updateProfile) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.updateProfile = updateProfile;
    }

    @GetMapping
    public ResponseEntity<MeResponse> get(Principal principal) {
        UUID userId = currentUser.require(principal);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return ResponseEntity.ok(toResponse(user));
    }

    @PatchMapping
    public ResponseEntity<MeResponse> update(Principal principal, @Valid @RequestBody UpdateProfileRequest request) {
        UUID userId = currentUser.require(principal);
        User user = updateProfile.run(userId, request.name(), request.defaultCurrency(), request.timezone());
        return ResponseEntity.ok(toResponse(user));
    }

    private MeResponse toResponse(User user) {
        return new MeResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getStatus().name(),
                user.getDefaultCurrency(),
                user.getTimezone()
        );
    }

    public record MeResponse(UUID id, String email, String name, String status,
                             String defaultCurrency, String timezone) {}

    public record UpdateProfileRequest(String name, String defaultCurrency, String timezone) {}
}
