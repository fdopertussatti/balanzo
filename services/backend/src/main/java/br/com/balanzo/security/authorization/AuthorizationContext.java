package br.com.balanzo.security.authorization;

import java.util.Set;
import java.util.UUID;

/**
 * Context for authorization decisions.
 * Who is the user, in which context, what families they belong to.
 */
public record AuthorizationContext(
        UUID userId,
        Set<UUID> familyIds,
        Operation operation
) {
    public enum Operation {
        VIEW,
        EDIT,
        MANAGE
    }
}
