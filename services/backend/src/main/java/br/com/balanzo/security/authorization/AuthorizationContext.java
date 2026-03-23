package br.com.balanzo.security.authorization;

import br.com.balanzo.domain.familia.entity.FamilyMemberRole;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Context for authorization decisions.
 * Who is the user, in which context, what families they belong to, and their role in each.
 */
public record AuthorizationContext(
        UUID userId,
        Set<UUID> familyIds,
        Map<UUID, FamilyMemberRole> familyRoles,
        Operation operation
) {
    public enum Operation {
        VIEW,
        EDIT,
        MANAGE
    }
}
