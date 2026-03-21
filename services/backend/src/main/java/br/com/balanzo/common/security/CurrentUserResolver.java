package br.com.balanzo.common.security;

import br.com.balanzo.common.exception.AuthRequiredException;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Resolves the current user ID from the security context.
 * Controllers should use this instead of duplicating Principal/Jwt parsing.
 */
@Component
public class CurrentUserResolver {

    /**
     * Extracts user ID from principal when present.
     *
     * @return Optional with user ID if authenticated, empty otherwise
     */
    public Optional<UUID> resolve(Principal principal) {
        if (principal instanceof Jwt jwt && jwt.getSubject() != null) {
            try {
                return Optional.of(UUID.fromString(jwt.getSubject()));
            } catch (IllegalArgumentException ignored) {
            }
        }
        return Optional.empty();
    }

    /**
     * Requires authenticated user. Throws if not authenticated.
     *
     * @throws AuthRequiredException when principal is missing or invalid
     */
    public UUID require(Principal principal) {
        return resolve(principal)
                .orElseThrow(AuthRequiredException::new);
    }
}
