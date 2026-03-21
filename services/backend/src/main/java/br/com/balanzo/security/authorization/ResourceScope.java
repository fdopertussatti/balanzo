package br.com.balanzo.security.authorization;

import java.util.UUID;

/**
 * Scope of a resource for authorization.
 * Owner, family, visibility level.
 */
public record ResourceScope(
        UUID ownerUserId,
        UUID familyId,
        VisibilityLevel visibility
) {
    public enum VisibilityLevel {
        PRIVATE,
        SHARED_READ,
        SHARED_EDIT,
        SHARED_MANAGE,
        ANALYTICAL_ONLY
    }
}
