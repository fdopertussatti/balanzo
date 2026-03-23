package br.com.balanzo.security.authorization;

import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Authorizes operations on resources scoped to a family (budgets, goals, tasks, assets, etc.).
 * Aligns with {@code estrategia-de-autorizacao-e-compartilhamento-familiar.md}: contextual access
 * by membership and visibility, not role alone.
 */
@Component
public class FamilyScopeAccess {

    private final AuthorizationContextResolver contextResolver;
    private final DomainAuthorizationService authorizationService;

    public FamilyScopeAccess(AuthorizationContextResolver contextResolver,
                             DomainAuthorizationService authorizationService) {
        this.contextResolver = contextResolver;
        this.authorizationService = authorizationService;
    }

    /**
     * User must be an active family member with at least read-level access to shared data.
     */
    public void requireMemberCanView(UUID userId, UUID familyId) {
        var ctx = contextResolver.resolve(userId, AuthorizationContext.Operation.VIEW);
        var scope = new ResourceScope(null, familyId, ResourceScope.VisibilityLevel.SHARED_READ);
        authorizationService.requireAuthorized(ctx, scope);
    }

    /**
     * User must be an active family member with edit-level access (create/update shared resources).
     */
    public void requireMemberCanEdit(UUID userId, UUID familyId) {
        var ctx = contextResolver.resolve(userId, AuthorizationContext.Operation.EDIT);
        var scope = new ResourceScope(null, familyId, ResourceScope.VisibilityLevel.SHARED_EDIT);
        authorizationService.requireAuthorized(ctx, scope);
    }

    /**
     * User must be owner or admin in the family (add members, manage shared resources).
     */
    public void requireMemberCanManage(UUID userId, UUID familyId) {
        var ctx = contextResolver.resolve(userId, AuthorizationContext.Operation.MANAGE);
        var scope = new ResourceScope(null, familyId, ResourceScope.VisibilityLevel.SHARED_MANAGE);
        authorizationService.requireAuthorized(ctx, scope);
    }
}
