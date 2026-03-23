package br.com.balanzo.security.authorization;

import br.com.balanzo.domain.familia.entity.FamilyMemberStatus;
import br.com.balanzo.infrastructure.persistence.familia.FamilyMemberRepository;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Resolves AuthorizationContext from userId.
 * Fetches family membership and roles for authorization decisions.
 */
@Component
public class AuthorizationContextResolver {

    private final FamilyMemberRepository familyMemberRepository;

    public AuthorizationContextResolver(FamilyMemberRepository familyMemberRepository) {
        this.familyMemberRepository = familyMemberRepository;
    }

    /**
     * Builds context with user's active family IDs and roles.
     */
    public AuthorizationContext resolve(UUID userId, AuthorizationContext.Operation operation) {
        var members = familyMemberRepository.findByUserIdAndStatus(userId, FamilyMemberStatus.active);
        Set<UUID> familyIds = members.stream()
                .map(m -> m.getFamily().getId())
                .collect(Collectors.toSet());
        Map<UUID, br.com.balanzo.domain.familia.entity.FamilyMemberRole> familyRoles = members.stream()
                .collect(Collectors.toMap(m -> m.getFamily().getId(), m -> m.getRole()));
        return new AuthorizationContext(userId, familyIds, familyRoles, operation);
    }
}
